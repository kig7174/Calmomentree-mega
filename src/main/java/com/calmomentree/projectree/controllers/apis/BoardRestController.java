package com.calmomentree.projectree.controllers.apis;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.calmomentree.projectree.helpers.Pagination;
import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.models.Board;
import com.calmomentree.projectree.services.BoardService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// @Slf4j
// @RestController
// public class BoardRestController {

//     @Autowired
//     private RestHelper restHelper;

//     @Autowired
//     private BoardService boardService;

//     @GetMapping("/api/board/qna/list")
//     public Map<String, Object> getList(
//             // 검색어 파라미터
//             @RequestParam(value = "keyword", required = false) String keyword,
//             @RequestParam(value = "page", defaultValue = "1") int nowPage) {

//                 int totalCount = 0; // 전체 게시글 수
//                 int listCount = 10; // 한 페이지당 표시할 목록 수
//                 int pageCount = 5; // 한 그룹당 표시할 페이지 번호 수

//                 // 페이지 번호를 계산한 결과가 저장될 객체
//         Pagination pagination = null;

//         // 조회 조건에 사용할 객체
//         Board input = new Board();
//         input.setBoardTitle(keyword);

//         List<Board> output = null;

//         try {
//             // 전체 게시글 수 조회
//             totalCount = boardService.getCount(input);
//             // 페이지 번호 계산 ---> 계산결과를 로그로 출력될 것이다.
//             pagination = new Pagination(nowPage, totalCount, listCount, pageCount);

//             // SQL의 Limit절에서 사용될 값을 Beans의 static 변수에 저장
//             Board.setOffset(pagination.getOffset());
//             Board.setListCount(pagination.getListCount());

//             output = boardService.getList(input);
//         } catch (Exception e) {
//             return restHelper.serverError(e);

//         }

//         Map<String, Object> data = new LinkedHashMap<String, Object>();
//         data.put("keyword", keyword);
//         data.put("boardQnas", output);
//         data.put("pagination", pagination);

//         return restHelper.sendJson(data);
    
// }

// }
