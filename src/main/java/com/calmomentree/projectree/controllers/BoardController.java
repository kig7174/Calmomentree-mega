package com.calmomentree.projectree.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.calmomentree.projectree.helpers.FileHelper;
import com.calmomentree.projectree.helpers.Pagination;
import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.helpers.WebHelper;
import com.calmomentree.projectree.models.Board;
import com.calmomentree.projectree.models.UploadItem;
import com.calmomentree.projectree.services.BoardService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private WebHelper webHelper;

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private RestHelper restHelper;

    /**
     * qna 게시판 목록페이지
     */
    @GetMapping("/board/qna/list")
    public String qnaList(Model model,
            // 검색어 파라미터
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int nowPage) {

        int totalCount = 0; // 전체 게시글 수
        int listCount = 10; // 한 페이지당 표시할 목록 수
        int pageCount = 5; // 한 그룹당 표시할 페이지 번호 수

        // 페이지 번호를 계산한 결과가 저장될 객체
        Pagination pagination = null;

        // 조회 조건에 사용할 객체
        Board input = new Board();
        input.setBoardTitle(keyword);
        input.setBoardCategory("qna");

        List<Board> output = null;

        try {
            // 전체 게시글 수 조회
            totalCount = boardService.getCount(input);
            // 페이지 번호 계산 ---> 계산결과를 로그로 출력될 것이다.
            pagination = new Pagination(nowPage, totalCount, listCount, pageCount);

            // SQL의 Limit절에서 사용될 값을 Beans의 static 변수에 저장
            Board.setOffset(pagination.getOffset());
            Board.setListCount(pagination.getListCount());

            output = boardService.getList(input);
        } catch (Exception e) {
            webHelper.serverError(e);

        }

        // view에 데이터 전달
        model.addAttribute("boardQnas", output);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagination", pagination);

        return "board/qna/list";

    }

    /**
     * qna 게시판 상세페이지
     */
    @GetMapping("/board/qna/read/{boardId}")
    public String qnaRead(Model model,
            @PathVariable("boardId") int boardId) {

        // 조회 조건에 사용할 변수를 Beans에 저장
        Board input = new Board();
        input.setBoardId(boardId);
        
        Board output = null;
        
        try {
            output = boardService.getItem(input);
        } catch (Exception e) {
            webHelper.serverError(e);
        }     

        // 업로드 사진의 경로를 URL로 변환.
        output.setUploadImg(fileHelper.getUrl(output.getUploadImg()));

        // View에 데이터 전달
        model.addAttribute("boardQna", output);

        return "board/qna/read";
    }

    /**
     * 게시글 작성 (------ member_id 값 처리해야됨. -------)
     * 
     * @param boardTitle
     */
    @ResponseBody
    @PostMapping("/board/add")
    public void boardEdit(
            @RequestParam("boardTitle") String boardTitle,
            @RequestParam("boardCategory") String boardCategory,
            @RequestParam("boardContent") String boardContent,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "boardPw", required = false) String boardPw,
            @RequestParam("isPublic") String isPublic,

            // memberId 수정해야 됨
            @RequestParam(value = "memberId", defaultValue = "1") int memberId) {

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
            restHelper.serverError(e);
            return;
        }

        Board input = new Board();
        input.setBoardTitle(boardTitle);
        input.setBoardContent(boardContent);
        input.setBoardCategory(boardCategory);
        input.setBoardPw(boardPw);
        input.setIsPublic(isPublic);
        input.setMemberId(memberId);

        // 업로드 사진 있어?
        if(uploadItem != null) {
            input.setUploadImg(uploadItem.getFilePath());
        }

        // DB에 저장
        try {
            boardService.addItem(input);
        } catch (Exception e) {
            webHelper.serverError(e);
        }

        // 컨트롤러랑 javascript 둘 중 하나라도 없으면 왜 동작 안하지??????
        webHelper.redirect("/board/qua/list", "게시글이 등록되었습니다.");
    }
}
