package com.calmomentree.projectree.controllers.apis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.models.Board;
import com.calmomentree.projectree.services.BoardService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

@Slf4j
@RestController
public class BoardRestController {

    @Autowired
    private RestHelper restHelper;

    @Autowired
    private BoardService boardService;

    @DeleteMapping("/api/board/qna/delete/{boardId}")
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

}
