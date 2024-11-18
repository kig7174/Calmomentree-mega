package com.calmomentree.projectree.services;

import java.util.List;

import com.calmomentree.projectree.models.Board;

public interface BoardService {
    public Board addItem(Board params) throws Exception;

    public Board editItem(Board params) throws Exception;

    public int deleteItem(Board params) throws Exception;

    public Board getItem(Board params) throws Exception;

    public List<Board> getList(Board params) throws Exception;

    public int getCount(Board params) throws Exception;
}

