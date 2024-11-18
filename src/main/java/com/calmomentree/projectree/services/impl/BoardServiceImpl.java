package com.calmomentree.projectree.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calmomentree.projectree.mappers.BoardMapper;
import com.calmomentree.projectree.models.Board;
import com.calmomentree.projectree.services.BoardService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public Board addItem(Board input) throws Exception {
        int rows = 0;

        try {
            rows = boardMapper.insert(input);

            if (rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }

        return boardMapper.selectItem(input);
    }

    @Override
    public Board editItem(Board input) throws Exception {
        int rows = 0;

        try {
            rows = boardMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return boardMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Board input) throws Exception {
        int rows = 0;

        try {
            rows = boardMapper.delete(input);

            if (rows == 0) {
                throw new Exception("삭제된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 삭제에 실패했습니다.", e);
            throw e;
        }

        return rows;
    }

    @Override
    public Board getItem(Board input) throws Exception {
        Board output = null;

        try {
            output = boardMapper.selectItem(input);

            if (output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("교수 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public List<Board> getList(Board input) throws Exception {
        List<Board> output = null;

        try {
            output = boardMapper.selectList(input);
        } catch (Exception e) {
            log.error("교수 목록 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public int getCount(Board input) throws Exception {
        int output = 0;

        try {
            output = boardMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }

        return output;
    }
}