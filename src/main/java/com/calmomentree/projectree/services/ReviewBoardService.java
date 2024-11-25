package com.calmomentree.projectree.services;

import java.util.List;

import com.calmomentree.projectree.models.ReviewBoard;

public interface ReviewBoardService {
    public ReviewBoard addItem(ReviewBoard params) throws Exception;

    public ReviewBoard editItem(ReviewBoard params) throws Exception;

    public int deleteItem(ReviewBoard params) throws Exception;

    public ReviewBoard getItem(ReviewBoard params) throws Exception;

    public List<ReviewBoard> getList(ReviewBoard params) throws Exception;

    public int getCount(ReviewBoard params) throws Exception;
}

