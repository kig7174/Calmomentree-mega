package com.calmomentree.projectree.controllers.apis;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.calmomentree.projectree.helpers.FileHelper;
import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.models.Board;
import com.calmomentree.projectree.models.Member;
import com.calmomentree.projectree.models.UploadItem;
import com.calmomentree.projectree.services.BoardService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
public class BoardRestController {

    @Autowired
    private RestHelper restHelper;

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private BoardService boardService;

    /**
     * 게시글 삭제 처리
     * @param boardId - 게시글 번호
     */
    @DeleteMapping("/api/board/delete/{boardId}")
    public Map<String, Object> deleteQna(
            @PathVariable("boardId") int boardId) {

        Board input = new Board();
        input.setBoardId(boardId);

        try {
            boardService.deleteItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        return restHelper.sendJson();

    }

    /**
     * 게시글 등록 처리
     * @param boardTitle - 게시글 제목
     * @param boardCategory -게시글 카테고리
     * @param boardContent - 게시글 내용
     * @param photo - 업로드 사진 URL
     * @param boardPw - 게시글 비밀번호
     * @param isPublic - 공개 여부
     * @param memberId - 회원 번호
     */
    @PostMapping("/api/board/add")
    public Map<String, Object> boardAdd(
            @RequestParam("boardTitle") String boardTitle,
            @RequestParam("boardCategory") String boardCategory,
            @RequestParam("boardContent") String boardContent,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "boardPw", required = false) String boardPw,
            @RequestParam("isPublic") String isPublic,

            // memberId 수정해야 됨
            @SessionAttribute("memberInfo") Member memberInfo) {
            // @RequestParam(value = "memberId", defaultValue = "1") int memberId
        // 게시글 공개시 비밀번호 NULL처리
        if (isPublic.equals("Y")) {
            boardPw = null;
        }

        // 업로드 사진에 대한 처리
        UploadItem uploadItem = null;

        try {
            uploadItem = fileHelper.uploadImgFile(photo, boardCategory);
        } catch (NullPointerException e) {
            // 업로드 된 항목이 없는 경우는 에러가 아니므로 계속 진행
        } catch (Exception e) {
            // 업로드 된 항목이 있으나, 이를 처리하다가 에러가 발생한 경우
            return restHelper.serverError(e);
        }

        Board input = new Board();
        input.setBoardTitle(boardTitle);
        input.setBoardContent(boardContent);
        input.setBoardCategory(boardCategory);
        input.setBoardPw(boardPw);
        input.setIsPublic(isPublic);
        input.setMemberId(memberInfo.getMemberId());

        // 업로드 사진 있어?
        if(uploadItem != null) {
            input.setUploadImg(uploadItem.getFilePath());
        }

        // DB에 저장
        try {
            boardService.addItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        // Map<String, Object> data = new LinkedHashMap<String, Object>();

        return restHelper.sendJson();
    }

    /**
     * 게시글 수정 처리
     * @param boardId - 게시글 번호
     * @param memberId - 회원 번호
     * @param boardContent - 게시글 내용
     * @param boardCategory - 게시글 카테고리
     * @param deletePhoto - 업로드 사진 삭제 여부
     * @param photo - 업로드 사진 URL
     * @param boardPw - 게시글 비밀번호
     * @param isPublic - 공개 여부
     */
    @PutMapping("/api/board/modify_ok/{boardId}")
    public Map<String, Object> boardEditOk(
        @PathVariable("boardId") int boardId,
        @RequestParam("boardContent") String boardContent,
        @RequestParam("boardCategory") String boardCategory,
        @RequestParam(value = "delete_photo", defaultValue = "N") String deletePhoto,
        @RequestParam(value = "photo", required = false) MultipartFile photo,
        @RequestParam(value = "boardPw", required = false) String boardPw,
        @RequestParam("isPublic") String isPublic,

        // memberId 수정해야 됨
        @SessionAttribute("memberInfo") Member memberInfo
        // @RequestParam(value = "memberId", defaultValue = "1") int memberId
        ) {
        
        // 게시글 공개시 비밀번호 NULL처리
        if (isPublic.equals("Y")) {
            boardPw = null;
        }
        // 업로드 사진에 대한 처리
        UploadItem uploadItem = null;

        try {
            uploadItem = fileHelper.uploadImgFile(photo, boardCategory);
        } catch (NullPointerException e) {
            // 업로드 된 항목이 없는 경우는 에러가 아니므로 계속 진행
        } catch (Exception e) {
           return restHelper.serverError(e);
        }

        // 정보를 Service에 전달하기 위한 객체 구성
        Board board = new Board();
        board.setBoardContent(boardContent);
        board.setBoardPw(boardPw);
        board.setIsPublic(isPublic);
        board.setBoardId(boardId);
        board.setMemberId(memberInfo.getMemberId());

        String currentPhoto = fileHelper.getFilePath(board.getUploadImg());
        
        // 기존에 등록된 사진이 있는 경우
        if(currentPhoto != null && currentPhoto.equals("")) {
            currentPhoto = fileHelper.getFilePath(currentPhoto);

            // 기존 사진의 삭제가 요청 되었다면?
            if(deletePhoto.equals("Y")) {
                fileHelper.deleteUploadFile(currentPhoto);

                // 업로드 된 사진이 있다면 Beans에 포함
                // 기존 파일이 있을 경우에는 삭제없이는 정보를 갱신하면 안됨
                if(uploadItem != null) {
                    board.setUploadImg(uploadItem.getFilePath());
                } else {
                    // 삭제만 하고 새로운 파일은 업로드 안하면?
                    board.setUploadImg(null);
                }
            } else {
                // 삭제 요청이 없으면 기존 사진 유지.
                board.setUploadImg(currentPhoto);
            }
        } else {
            // 업로드 된 사진이 있다면 Beans에 포함
            if(uploadItem != null) {
                board.setUploadImg(uploadItem.getFilePath());
            }
            
        }

        // DB에 저장하기
        try {
           boardService.editItem(board);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        // Map<String, Object> data = new LinkedHashMap<String, Object>();

        return restHelper.sendJson();
    }
}
