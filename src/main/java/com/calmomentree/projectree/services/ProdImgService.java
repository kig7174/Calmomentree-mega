package com.calmomentree.projectree.services;

import java.util.List;

import com.calmomentree.projectree.models.ProdImg;

public interface ProdImgService {
    public ProdImg addItem(ProdImg params) throws Exception;

    public ProdImg editItem(ProdImg params) throws Exception;

    public int deleteItem(ProdImg params) throws Exception;

    public ProdImg getItem(ProdImg params) throws Exception;

    public List<ProdImg> getList(ProdImg params) throws Exception;

    public int getCount(ProdImg params) throws Exception;
}
