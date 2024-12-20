package com.calmomentree.projectree.controllers.apis;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;

import com.calmomentree.projectree.helpers.FileHelper;
import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.models.Member;
import com.calmomentree.projectree.models.ReviewBoard;
import com.calmomentree.projectree.models.ReviewImg;
import com.calmomentree.projectree.models.UploadItem;
import com.calmomentree.projectree.services.ReviewBoardService;
import com.calmomentree.projectree.services.ReviewImgService;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
public class ReviewBoardRestController {

    @Autowired
    private RestHelper restHelper;

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private ReviewBoardService reviewBoardService;

    @Autowired
    private ReviewImgService reviewImgService;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handle(Exception ex) {
        return "error/404";
    }

    /**
     * 리뷰 게시글 업로드
     * 
     * @param reviewTitle 
     * @param reviewContent
     * @param rating
     * @param photo
     * @return
     */
    @PostMapping("/api/board/product/add")
    public Map<String, Object> reviewAdd(
            @RequestParam("reviewTitle") String reviewTitle,
            @RequestParam("reviewContent") String reviewContent,
            @RequestParam("rating") int rating,
            @RequestParam(value = "photo", required = false) List<MultipartFile> photo,
            @RequestParam("prodId") int prodId,
            @SessionAttribute("memberInfo") Member memberInfo) {
       
        // 업로드 사진에 대한 처리
        List<UploadItem> uploadItemList = new ArrayList<>();

        for(int i=0;i<photo.size();i++) {
            // 업로드된 파일을 저장하고, 저장된 파일의 정보를 반환받는다.
            UploadItem uploadItem = new UploadItem();
            try {
                uploadItem = fileHelper.saveMultipartFile(photo.get(i));
            } catch (NullPointerException e) {
                // 업로드 된 항목이 없는 경우는 에러가 아니므로 계속 진행
            } catch (Exception e) {
                // 업로드 된 항목이 있으나, 이를 처리하다가 에러가 발생한 경우
                return restHelper.serverError(e);
            }

            uploadItemList.add(uploadItem);
        }
       
        // 리뷰 게시글 저장을 위한 객체 생성 및 데이터 입력
        ReviewBoard input = new ReviewBoard();
        input.setReviewTitle(reviewTitle);
        input.setReviewContent(reviewContent);
        input.setRating(rating);
        input.setProdId(prodId);
        input.setMemberId(memberInfo.getMemberId());

        try {
            reviewBoardService.addItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        
        // 업로드 된 파일이 있을 경우
        if (uploadItemList != null) {
            
            for (int i=0; i<uploadItemList.size(); i++) {
                // 업로드 된 파일의 정보를 DB에 저장
                if (uploadItemList.get(i).getFilePath() != null) {
                    // 업로드 파일 저장을 위한 객체 생성 및 데이터 입력
                    ReviewImg input2 = new ReviewImg();
                    input2.setImgUrl(uploadItemList.get(i).getFilePath());
                    input2.setReviewBoardId(input.getReviewBoardId());
                    
                    try {
                        reviewImgService.addItem(input2);
                    } catch (Exception e) {
                        return restHelper.serverError(e);
                    }
                }            
            }
        }

        return restHelper.sendJson();
    }

    /**
     * 리뷰 게시글을 삭제
     * 
     * @param reviewBoardId 삭제할 리뷰 게시글의 ID
     * @param memberInfo 세션에 저장된 회원 정보
     * @return 처리 결과를 포함한 JSON 응답
     */
    @DeleteMapping("/api/review/delete/{reviewBoardId}")
    private Map<String, Object> reviewDelete(
        @PathVariable("reviewBoardId") int reviewBoardId,
        @SessionAttribute("memberInfo") Member memberInfo) {
        
        // 리뷰 게시글 삭제를 위한 객체 생성 및 데이터 입력
        ReviewBoard input = new ReviewBoard();
        input.setReviewBoardId(reviewBoardId);
        input.setMemberId(memberInfo.getMemberId());
        
        // 리뷰 게시글에 포함된 이미지 파일 삭제를 위한 객체 생성 및 데이터 입력
        ReviewImg img = new ReviewImg();
        img.setReviewBoardId(reviewBoardId);
        
        // 리뷰 이미지 삭제
        try {
            reviewImgService.deleteItem(img);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        // 리뷰 게시글 삭제
        try {
            reviewBoardService.deleteItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        
        return restHelper.sendJson();
    }
}
