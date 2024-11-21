package com.calmomentree.projectree.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.calmomentree.projectree.models.ProdImg;

@Mapper
public interface ProdImgMapper {
    @Insert("...")
    @Options(useGeneratedKeys = true, keyProperty = "...", keyColumn = "...")
    public int insert(ProdImg input);

    @Update("...")
    public int update(ProdImg input);

    @Delete("...")
    public int delete(ProdImg input);

    @Select("...")
    @Results(id="resultMap", value={
        @Result(property="...", column="..."),
        @Result(property="...", column="..."),
        @Result(property="...", column="...")
    })
    public ProdImg selectItem(ProdImg input);

    @Select("...")
    @ResultMap("resultMap")
    public List<ProdImg> selectList(ProdImg input);

    @Select("...")
    public int selectCount(ProdImg input);
}
