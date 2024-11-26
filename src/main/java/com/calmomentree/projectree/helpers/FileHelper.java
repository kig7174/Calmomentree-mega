package com.calmomentree.projectree.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.calmomentree.projectree.models.UploadItem;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

@Slf4j
@Component
public class FileHelper {

    /** 업로드 된 파일이 저장될 경로 (application.properies로부터 읽어옴) */
    // -> import org.springframework.beans.factory.annotation.Value;
    @Value("${upload.dir}")
    private String uploadDir;

    /** 업로드 된 파일이 노출될 URL 경로 (application.properies로부터 읽어옴) */
    @Value("${upload.url}")
    private String uploadUrl;

    @Value("${thumbnail.width}")
    private int thumbnailWidth;

    @Value("${thumbnail.height}")
    private int thumbnailHeight;

    @Value("${thumbnail.crop}")
    private boolean thumbnailCrop;

    /**
     * 파일에 데이터를 쓰는 메서드
     * 
     * @param filePath - 파일 경로
     * @param data     - 저장할 데이터
     * @throws Exception - 파일 입출력 예외
     */
    public void wirte(String filePath, byte[] data) throws Exception {
        OutputStream os = null;
        try {
            // 저장할 파일 스트림 생성
            os = new FileOutputStream(filePath);
            // 파일 쓰기
            os.write(data);
        } catch (FileNotFoundException e) {
            log.error("파일을 찾을 수 없습니다.", e);
            throw e;
        } catch (IOException e) {
            log.error("파일을 쓸 수 없습니다.", e);
            throw e;
        } catch (Exception e) {
            log.error("파일 입출력 오류가 발생했습니다.", e);
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("파일을 닫는 중 오류가 발생했습니다.", e);
                    throw e;
                }
            } // end if
        } // try ~ catch ~ finally
    }

    /**
     * 파일에서 데이터를 읽는 메서드
     * 
     * @param filePath - 파일 경로
     * @return - 파일에 저장된 데이터
     * @throws Exception - 파일 입출력 예외
     */
    public byte[] read(String filePath) throws Exception {
        byte[] data = null;

        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            data = new byte[is.available()];
            is.read(data);
        } catch (FileNotFoundException e) {
            log.error("파일을 찾을 수 없습니다.", e);
            throw e;
        } catch (IOException e) {
            log.error("파일을 쓸 수 없습니다.", e);
            throw e;
        } catch (Exception e) {
            log.error("파일 입출력 오류가 발생했습니다.", e);
            throw e;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    log.error("파일을 닫는 중 오류가 발생했습니다.", e);
                    throw e;
                }
            }
        }

        return data;
    }

    /**
     * 파일에 문자열을 쓰는 메서드
     * 
     * @param filePath - 파일 경로
     * @param content  - 저장할 문자열
     * @throws Exception - 파일 입출력 예외
     */
    public void writeString(String filePath, String content) throws Exception {
        try {
            this.wirte(filePath, content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("지원하지 않는 인코딩입니다.", e);
            throw e;
        }
    }

    /**
     * 파일에서 문자열을 읽는 메서드
     * 
     * @param filePath - 파일 경로
     * @return - 파일에 저장된 문자열
     * @throws Exception - 파일 입출력 예외
     */
    public String readString(String filePath) throws Exception {
        String content = null;

        try {
            byte[] data = read(filePath);
            content = new String(data, "utf-8");
        } catch (Exception e) {
            log.error("지원하지 않는 인코딩입니다.", e);
            throw e;
        }

        return content;
    }

    /**
     * 컨트롤러로부터 업로드 된 파일의 정보를 전달받아 지정된 위치에 저장한다.
     * 
     * @param multipartFile - 업로드 된 파일 정보
     * @return 파일 정보를 답고 있는 객체
     * @throws NullPointerException 업로드 된 파일이 없는 경우
     * @throws Exception            파일 저장에 실패한 경우
     */
    @SuppressWarnings("null")
    public UploadItem saveMultipartFile(MultipartFile multipartFile)
            throws NullPointerException, Exception {

        /** 1) 업로드 파일 저장하기 */
        // 파일의 원본 이름 추출
        String originName = multipartFile.getOriginalFilename();

        // 업로드 된 파일이 존재하는지 확인
        if (originName != null && originName.isEmpty()) {
            NullPointerException e = new NullPointerException("업로드 된 파일이 없습니다.");
            log.error("업로드 실패", e);
            throw e;
        }

        /** 2) 업로드 된 파일이 저장될 폴더 생성 */
        // 업로드 된 파일이 저장될 폴더의 이름을 "년/월/일"형식으로 생성한다.
        Calendar c = Calendar.getInstance();
        // -> "$"를 "%"로 변경해서 실습하세요.
        String targetDir = String.format("%s/%04d/%02d/%02d",
                uploadDir, c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));

        // 폴더가 존재하지 않는다면 생성한다.
        File f = new File(targetDir);
        if (!f.exists()) {
            f.mkdirs();
        }

        /** 3) 저장될 파일의 이름을 생성한다. */
        // 파일의 원본 이름에서 확장자만 추출
        String ext = originName.substring(originName.lastIndexOf("."));
        String fileName = null; // 웹 서버에 저장될 파일이름
        File targetFile = null; // 저장된 파일 정보를 담기 위한 File객체
        int count = 0; // 중복된 파일 수

        // 일단 무한루프
        while (true) {
            // 저장될 파일 이름 --> 현재시각 + 카운트값 + 확장자
            fileName = String.format("%d%d%s", System.currentTimeMillis(), count, ext);
            // 업로드 파일이 저장될 폴더 + 파일이름으로 파일객체를 생성한다.
            targetFile = new File(targetDir, fileName);

            // 동일한 이름의 파일이 없다면 반복 중단
            if (!targetFile.exists()) {
                break;
            }

            // if문을 빠져나올 경우 중복된 이름의 파일이 존재한다는 의미이므로 count를 1증가
            count++;
        }

        /** 4) 파일 업로드 수행 */
        try {
            multipartFile.transferTo(targetFile);
        } catch (Exception e) {
            log.error("업로드 된 파일을 저장하는 중에 문제가 발생했습니다.", e);
            throw e;
        }

        /** 5) 업로드 경로 정보 처리하기 */
        // 복사된 파일의 절대경로를 추출
        // -> 운영체제 호환 (Windows -> Linux)을 위해 역슬래시를 슬래시로 변환
        // -> D:/ingyeom/08-SpringBoot/upload/myphoto.jpg
        String absPath = targetFile.getAbsolutePath().replace("\\", "/");
        log.debug("업로드 된 파일의 경로 : " + absPath);

        // 업로드 된 파일의 절대경로(absPath)에서 환경설정 파일에 명시된 폴더까지의 위치는 삭제하여
        // 환경설정 파일에 명시된 upload.dir 이후의 위치만 추출 (window)
        String filePath = null;
        if (absPath.substring(0, 1).equals("/")) {
            // Mac, Linux용 경로 처리
            // absPath : /Users/ingyeom/08-SpringBoot/upload/myphoto.jpg
            // uploadDir : /Users/ingyeom/08-SpringBoot/upload
            filePath = absPath.replace(uploadDir, "");
        } else {
            // Window용 경로 처리 -> 설정 파일에 명시한 첫 글자(/)를 제거해야 함
            filePath = absPath.replace(uploadDir.substring(1), "");
        }

        // 업로드 경로를 웹 상에서 접근 가능한 경로 문자열로 변환
        String fileUrl = String.format("%s%s", uploadUrl, filePath);

        /** 6) 업로드 결과를 Beans에 저장 */
        UploadItem item = new UploadItem();
        item.setContentType(multipartFile.getContentType());
        item.setFieldName(multipartFile.getName());
        item.setFileSize(multipartFile.getSize());
        item.setOriginName(originName);
        item.setFilePath(filePath);
        item.setFileUrl(fileUrl);

        /** 7) 파일 유형이 이미지라면 썸네일 생성 */
        if (item.getContentType().indexOf("image") > -1) {
            // 썸네일 이미지 생성
            try {
                String thumbnailPath = this.createThumbnail(filePath, thumbnailWidth, thumbnailHeight, thumbnailCrop);
                String thumbnailUrl = String.format("%s%s", uploadUrl, thumbnailPath);
                item.setThumbnailPath(thumbnailPath);
                item.setThumbnailUrl(thumbnailUrl);
            } catch (Exception e) {
                log.error("썸네일 생성에 실패했습니다.", e);
            }
        }

        // 업로드 된 정보를 로그로 기록
        log.debug(item.toString());

        return item;
    }

    /**
     * 컨트롤러로부터 업로드받은 상품 이미지 정보를 전달받아 지정된 위치에 저장
     * 
     * @param multipartFile - 전달받은 이미지 정보
     * @param prodName      - 폴더 생성을 위한 상품 이름
     * @param imgType       - 상품 이미지 유형 (목록, 상세, 정보)
     * @return - 파일정보를 담고 있는 객체
     * @throws NullPointerException - 업로드 된 이미지가 없는 경우
     * @throws Exception            - 이미지 업로드에 실패한 경우
     */
    @SuppressWarnings("null")
    public UploadItem saveProdImgMF(MultipartFile multipartFile, String prodName, String imgType)
            throws NullPointerException, Exception {

        /** 1) 업로드 파일 저장하기 */
        // 파일의 원본 이름 추출
        String originName = multipartFile.getOriginalFilename();

        // 업로드 된 파일이 존재하는지 확인
        if (originName != null && originName.isEmpty()) {
            NullPointerException e = new NullPointerException("업로드 된 파일이 없습니다.");
            log.error("업로드 실패", e);
            throw e;
        }

        /** 2) 업로드 된 파일이 저장될 폴더 생성 */
        String targetDir = String.format("%s/%s/%s/%s",
                uploadDir, "product_img", prodName, imgType);

        // 폴더가 존재하지 않는다면 생성한다.
        File f = new File(targetDir);
        if (!f.exists()) {
            f.mkdirs();
        }

        /** 3) 저장될 파일의 이름을 생성한다. */
        // 파일의 원본 이름에서 확장자만 추출
        String ext = originName.substring(originName.lastIndexOf("."));
        String origin = originName.substring(0, originName.lastIndexOf(".")); // 파일의 이름 추출
        String fileName = null; // 웹 서버에 저장될 파일이름
        File targetFile = null; // 저장된 파일 정보를 담기 위한 File객체
        int count = 0; // 중복된 파일 수

        // 일단 무한루프
        while (true) {
            // 저장될 파일 이름 --> 현재시각 + 카운트값 + 확장자
            fileName = String.format("%d%d%s", imgType, count, ext);
            // 업로드 파일이 저장될 폴더 + 파일이름으로 파일객체를 생성한다.
            targetFile = new File(targetDir, fileName);

            // 동일한 이름의 파일이 없다면 반복 중단
            if (!targetFile.exists()) {
                break;
            }

            // if문을 빠져나올 경우 중복된 이름의 파일이 존재한다는 의미이므로 count를 1증가
            count++;
        }

        /** 4) 파일 업로드 수행 */
        try {
            multipartFile.transferTo(targetFile);
        } catch (Exception e) {
            log.error("업로드 된 파일을 저장하는 중에 문제가 발생했습니다.", e);
            throw e;
        }

        /** 5) 업로드 경로 정보 처리하기 */
        // 복사된 파일의 절대경로를 추출
        // -> 운영체제 호환 (Windows -> Linux)을 위해 역슬래시를 슬래시로 변환
        // -> D:/ingyeom/08-SpringBoot/upload/myphoto.jpg
        String absPath = targetFile.getAbsolutePath().replace("\\", "/");
        log.debug("업로드 된 파일의 경로 : " + absPath);

        // 업로드 된 파일의 절대경로(absPath)에서 환경설정 파일에 명시된 폴더까지의 위치는 삭제하여
        // 환경설정 파일에 명시된 upload.dir 이후의 위치만 추출 (window)
        String filePath = null;
        if (absPath.substring(0, 1).equals("/")) {
            // Mac, Linux용 경로 처리
            // absPath : /Users/ingyeom/08-SpringBoot/upload/myphoto.jpg
            // uploadDir : /Users/ingyeom/08-SpringBoot/upload
            filePath = absPath.replace(uploadDir, "");
        } else {
            // Window용 경로 처리 -> 설정 파일에 명시한 첫 글자(/)를 제거해야 함
            filePath = absPath.replace(uploadDir.substring(1), "");
        }

        // 업로드 경로를 웹 상에서 접근 가능한 경로 문자열로 변환
        String fileUrl = String.format("%d%s%s", origin, uploadUrl, filePath);

        /** 6) 업로드 결과를 Beans에 저장 */
        UploadItem item = new UploadItem();
        item.setContentType(multipartFile.getContentType());
        item.setFieldName(multipartFile.getName());
        item.setFileSize(multipartFile.getSize());
        item.setOriginName(originName);
        item.setFilePath(filePath);
        item.setFileUrl(fileUrl);

        /** 7) 파일 유형이 이미지라면 썸네일 생성 */
        if (item.getContentType().indexOf("image") > -1) {
            // 썸네일 이미지 생성
            try {
                String thumbnailPath = this.createThumbnail(filePath, thumbnailWidth, thumbnailHeight, thumbnailCrop);
                String thumbnailUrl = String.format("%s%s", uploadUrl, thumbnailPath);
                item.setThumbnailPath(thumbnailPath);
                item.setThumbnailUrl(thumbnailUrl);
            } catch (Exception e) {
                log.error("썸네일 생성에 실패했습니다.", e);
            }
        }

        // 업로드 된 정보를 로그로 기록
        log.debug(item.toString());

        return item;
    }

    /**
     * 리사이즈 된 썸네일 이미지를 생성하고 경로를 리턴한다.
     * 
     * @param path   - 원본 파일 경로
     * @param width  - 최대 이미지 가로 크기
     * @param height - 최대 이미지 세로 크기
     * @param crop   - 이미지 크롭 사용 여부
     * @return 생성된 이미지의 절대 경로
     * @throws Exception
     */
    public String createThumbnail(String path, int width, int height, boolean crop) throws Exception {
        /** 1) 썸네일 생성 정보를 로그로 기록하기 */
        log.debug(String.format("[THumbnail] path: %s, size: %d%d, crop: %s",
                path, width, height, String.valueOf(crop)));

        /** 2) 저장될 썸네일 이미지의 경로 문자열 만들기 */
        File loadFile = new File(this.uploadDir, path); // 원본파일의 전체 경로 --> 업로드 폴더(상수값) + 파일명
        String dirPath = loadFile.getParent(); // 전체 경로에서 파일이 위치한 폴더 경로 분리
        String fileName = loadFile.getName(); // 전체 경로에서 파일 이름만 분리
        int p = fileName.lastIndexOf("."); // 파일이름에서 마지막 점(.)의 위치
        String name = fileName.substring(0, p); // 파일명 분리 -> 파일이름에서 마지막 점의 위치 전까지
        String ext = fileName.substring(p + 1); // 확장자 분리 -> 파일이름에서 마지막 점의 위치 다음부터 끝까지

        // 최종 파일이름을 구성한다 -> 원본이름 + 요청된 사이즈
        // -> ex) myphoto.jpg => myphoto_320x240.jpg
        String thumbName = name + "_" + width + "x" + height + "." + ext;

        File f = new File(dirPath, thumbName); // 생성될 썸네일 파일 객체 --> 업로드 폴더 + 썸네일 이름
        String saveFile = f.getAbsolutePath(); // 생성될 썸네일 파일 객체로부터 절대경로 추출 (리턴할 값)

        // 생성될 썸네일 이미지의 경로를 로그로 기록
        log.debug(String.format("[THumbnail] saveFiles: %s", saveFile));

        /** 3) 썸네일 이미지 생성하고 최종 경로 리턴 */
        // 해당 경로에 이미지가 없는 경우만 수행
        if (!f.exists()) {
            // 원본 이미지 가져오기
            // -> import net.coobird.thumbnailator.Thumbnails;
            // -> import net.coobird.thumbnailator.Thumbnails.Builder;
            Builder<File> builder = Thumbnails.of(loadFile);
            // 이미지 크롭 여부 파라미터에 따라 크롭 옵션을 지정
            if (crop == true) {
                // -> import net.coobird.thumbnailator.geometry.Positions;
                builder.crop(Positions.CENTER);
            }

            builder.size(width, height); // 축소할 사이즈 지정
            builder.useExifOrientation(true); // 세로로 촬영된 사진을 회전시킴
            builder.outputFormat(ext); // 파일의 확장명 지정
            try {
                builder.toFile(saveFile);
            } catch (IOException e) {
                log.error("썸네일 이미지 생성에 실패했습니다.", e);
                throw e;
            }
        }

        // 저장할 파일경로 지정
        String thumbnailPath = null;
        saveFile = saveFile.replace("\\", "/");
        if (saveFile.substring(0, 1).equals("/")) {
            // Mac, Linux용 경로 처리
            thumbnailPath = saveFile.replace(uploadDir, "");
        } else {
            // Window용 경로 처리 --> 설정 파일에 명시한 첫 글자(/)를 제거해야 함
            thumbnailPath = saveFile.replace(uploadDir.substring(1), "");
        }

        return thumbnailPath;
    }

    public List<UploadItem> saveProdImgMF(MultipartFile[] uploadFiles, String prodName, String imgType)
            throws NullPointerException, Exception {
        if (uploadFiles.length < 1) {
            NullPointerException e = new NullPointerException("업로드 된 파일이 없습니다.");
            log.error("업로드 실패", e);
            throw e;
        }

        List<UploadItem> uploadList = new ArrayList<UploadItem>();

        for (int i = 0; i < uploadFiles.length; i++) {
            // 에러가 발생하더라고 다음 항목을 처리하기 위해 반복을 중단하지 않는다.
            try {
                UploadItem item = this.saveProdImgMF(uploadFiles[i], prodName, imgType);
                uploadList.add(item);
            } catch (NullPointerException e) {
                log.error(String.format("%d번째 항목이 업로드 되지 안흥ㅁ"), i);
            } catch (Exception e) {
                log.error(String.format("%d번째 항목 저장 실패 ::: %s", e.getLocalizedMessage()));
            }
        }

        // 업로드 된 파일이 하나도 없다면 에러로 간주한다.
        if (uploadList.size() == 0) {
            Exception e = new Exception("파일 업로드 실패");
            log.error("업로드 실패", e);
            throw e;
        }

        return uploadList;
    }

    public List<UploadItem> saveMultipartFile(MultipartFile[] uploadFiles) throws NullPointerException, Exception {
        if (uploadFiles.length < 1) {
            NullPointerException e = new NullPointerException("업로드 된 파일이 없습니다.");
            log.error("업로드 실패", e);
            throw e;
        }

        List<UploadItem> uploadList = new ArrayList<UploadItem>();

        for (int i = 0; i < uploadFiles.length; i++) {
            // 에러가 발생하더라고 다음 항목을 처리하기 위해 반복을 중단하지 않는다.
            try {
                UploadItem item = this.saveMultipartFile(uploadFiles[i]);
                uploadList.add(item);
            } catch (NullPointerException e) {
                log.error(String.format("%d번째 항목이 업로드 되지 안흥ㅁ"), i);
            } catch (Exception e) {
                log.error(String.format("%d번째 항목 저장 실패 ::: %s", e.getLocalizedMessage()));
            }
        }

        // 업로드 된 파일이 하나도 없다면 에러로 간주한다.
        if (uploadList.size() == 0) {
            Exception e = new Exception("파일 업로드 실패");
            log.error("업로드 실패", e);
            throw e;
        }

        return uploadList;
    }

    public String getUrl(String path) {
        if (path == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(this.uploadUrl); // "/files"
        builder.append(path.trim()); // "/2024/11/12/photo.png"

        return builder.toString();
    }

    /**
     * 게시판 파일 업로드 시 지정된 위치에 저장한다.
     * @param multipartFile - 업로드 된 파일 정보
     * @param boardCategory - 생성할 폴더
     * @return              - 파일 정보를 담고 있는 객체
     * @throws NullPointerException - 업로드 된 파일이 없는 경우
     * @throws Exception            - 파일 저장에 실패한 경우
     */
    @SuppressWarnings("null")
    public UploadItem uploadImgFile(MultipartFile multipartFile, String boardCategory)
            throws NullPointerException, Exception {
        /** 1) 업로드 파일 저장하기 */
        // 파일의 원본 이름 추출
        String originName = multipartFile.getOriginalFilename();

        // 업로드 된 파일이 존재하는지 확인한다.
        if (originName != null && originName.isEmpty()) {
            NullPointerException e = new NullPointerException("업로드 된 파일이 없습니다.");
            log.error("업로드 실패", e);
            throw e;
        }

        /** 2) 업로드 된 파일이 저장될 폴더 생성 */
        Calendar c = Calendar.getInstance();
        String targetDir = String.format("%s/%s/%04d.%02d.%02d",
                uploadDir, boardCategory, c.get(Calendar.YEAR), c.get(Calendar.MONTH) +1 ,
                c.get(Calendar.DAY_OF_MONTH));

        // 폴더가 존재하지 않는다면 생성한다.
        File f = new File(targetDir);

        if (!f.exists()) {
            f.mkdirs();
        }

        /** 저장될 파일의 이름을 생성한다. */
        // 파일의 원본 이름에서 확장자만 추출
        String ext = originName.substring(originName.lastIndexOf("."));
        // 파일의 이름 추출
        String origin = originName.substring(0, originName.lastIndexOf("."));
        // 웹 서버에 저장될 파일이름
        String fileName = null;
        // 저장된 파일 정보를 담기 위한 File객체
        File targetFile = null;
        // 중복된 파일 수
        int count = 0;

        // 일단 무한루프
        while (true) {
            // 저장될 파일 이름 --> 파일이름 + 카운트값 + 확장자
            fileName = String.format("%s%d%s", origin, count, ext);
            // 업로드 파일이 저장될 폴더 + 파일이름으로 파일객체를 생성한다.
            targetFile = new File(targetDir, fileName);

            // 동일한 이름의 파일이 없다면 반복 중단.
            if (!targetFile.exists()) {
                break;
            }

            // if문을 빠져나올 경우 중복된 이름의 파일이 존재한다는 의미이므로 count를 1증가
            count++;
        }

        /** 5) 파일 업로드 수행 */

        try {
            multipartFile.transferTo(targetFile);
        } catch (Exception e) {
            log.error("업로드 된 파일을 저장하는 중에 문제가 발생했습니다.", e);
            throw e;
        }

        /** 6) 업로드 경로 정보 처리하기 */
        // 복사된 파일의 절대경로를 추출한다.
        // -> 운영체제 호환(windows -> Linux)을 위해 역슬래시를 슬래시로 변환한다.
        // -> C:/Users/user/study-springboot/upload/파일이름
        String absPath = targetFile.getAbsolutePath().replace("\\", "/");

        // 업로드 된 파일의 절대경로(absPath)에서 환경설정 파일에 명시된 폴더까지의 위치는 삭제하여
        // 환경설정 파일에 명시된 upload.dir 이후의 위치만 추출한다.(윈도우만...ㅠㅠ)
        String filePath = null;
        if (absPath.substring(0, 1).equals("/")) {
            // mac, Linux용 경로 처리
            // absPath: /Users/user/study-springboot/upload/파일이름
            // uploadDir: /Users/user/study-springboot/upload
            filePath = absPath.replace(uploadDir, "");
        } else {
            // window용 경로 처리 --> 설정 파일에 명시한 첫 글자(/)를 제거해야 함
            filePath = absPath.replace(uploadDir.substring(1), "");
        }

        // 업로드 경로를 웹 상에서 접근 가능한 경로 문자열로 변환
        String fileUrl = String.format("%s%s", uploadUrl, filePath);

        /** 7) 업로드 결과를 Beans에 저장 */
        UploadItem item = new UploadItem();
        item.setContentType(multipartFile.getContentType());
        item.setFieldName(multipartFile.getName());
        item.setFileSize(multipartFile.getSize());
        item.setOriginName(originName);
        item.setFilePath(filePath);
        item.setFileUrl(fileUrl);
        
        /** 8) 파일 유형이 이미지라면 썸네일 생성 */
        if (item.getContentType().indexOf("image") > -1) {
            // 썸네일 이미지 생성
            try {
                String thumbnailPath = this.createThumbnail(filePath, thumbnailWidth, thumbnailHeight, thumbnailCrop);
                String thumbnailUrl = String.format("%s%s", uploadUrl, thumbnailPath);
                item.setThumbnailPath(thumbnailPath);
                item.setThumbnailUrl(thumbnailUrl);
            } catch (Exception e) {
                log.error("썸네일 생성에 실패했습니다.", e);
            }
        }

        // 업로드된 정보를 로그로 기록
        log.debug(item.toString());

        return item;
    }

    /**
     * 파일 경로 앞의 URL prefix를 제거하고 리턴한다.
     * 
     * @param path - 파일 경로
     * @return DB에 저장되어 있는 실제 경로 문자열
     */
    public String getFilePath(String path) {
        if (path == null) {
            return null;
        }
        return path.replace(this.uploadUrl, "");
    }

    /**
     * 업로드 파일 삭제하기
     * @param filePath
     */
    public void deleteUploadFile(String filePath) {
        // 업로드 된 파일 경로가 없다면 처리 중단
        if (filePath == null || filePath.equals("")) {
            return;
        }

        /** 1) 원본 파일 삭제하기 */
        // 사용자가 업로드한 프로필 사진의 실제 경로
        File f = new File(uploadDir, filePath);
        log.debug("파일 삭제 >>> : " + f.getAbsolutePath());

        // 파일이 있다면 삭제한다.
        if (f.exists()) {
            try {
                f.delete();
                log.debug("파일 삭제 성공");
            } catch (Exception e) {
                log.debug("파일 삭제 실패", e);
            }
        }

        /** 2) 썸네일 이미지 삭제하기 */

        // 원본 이미지의 경로 객체 생성
        // --> import java.nio.file.Path;
        // --> import java.nio.file.Paths;
        Path path = Paths.get(f.getAbsolutePath());

        // 원본 이미지에 대한 컨텐츠 종류값(MimeType) 조회
        String contentType = null;
        try {
            // --> import java.nio.file.Files;
            contentType = Files.probeContentType(path);
        } catch (IOException e) {

        }

        // 컨텐츠 종류를 알아내지 못했거나 이미지 형식이 아니라면 처리 종료
        if (contentType == null || contentType.indexOf("image/") == -1) {
            return;
        }

        // 썸네일 이미지의 이름 생성하기
        String name = f.getName(); // 실제 파일 경로에서 파일명만 가져옴
        String parent = f.getParent(); // 실제 파일 경로에서 폴더위치만 가져옴
        int p = name.lastIndexOf("."); // 마지막 "."의 위치
        String thumbName = name.substring(0, p) + "_" +
                thumbnailWidth + "x" + thumbnailHeight + name.substring(p);

        // 썸네일 이미지의 실제 경로
        File thumbFile = new File(parent, thumbName);
        log.debug("썸네일 삭제 >>> " + thumbFile.getAbsolutePath());

        if (thumbFile.exists()) {
            try {
                thumbFile.delete();
                log.debug("썸네일 삭제 성공");
            } catch (Exception e) {
                log.error("썸네일 삭제 실패", e);
            }
        }
    }
}
