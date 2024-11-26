package com.calmomentree.projectree.services;

import java.util.List;

import com.calmomentree.projectree.models.ReviewImg;

public interface ReviewImgService {
    public ReviewImg addItem(ReviewImg params) throws Exception;

    public ReviewImg editItem(ReviewImg params) throws Exception;

    public int deleteItem(ReviewImg params) throws Exception;

    public ReviewImg getItem(ReviewImg params) throws Exception;

    public List<ReviewImg> getList(ReviewImg params) throws Exception;

    public int getCount(ReviewImg params) throws Exception;
}

