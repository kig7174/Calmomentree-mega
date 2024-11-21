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

import com.calmomentree.projectree.models.Category;

@Mapper
public interface CategoryMapper {
    @Insert("...")
    @Options(useGeneratedKeys = true, keyProperty = "...", keyColumn = "...")
    public int insert(Category input);

    @Update("...")
    public int update(Category input);

    @Delete("...")
    public int delete(Category input);

    @Select("...")
    @Results(id="resultMap", value={
        @Result(property="...", column="..."),
        @Result(property="...", column="..."),
        @Result(property="...", column="...")
    })
    public Category selectItem(Category input);

    @Select("...")
    @ResultMap("resultMap")
    public List<Category> selectList(Category input);

    @Select("...")
    public int selectCount(Category input);
}
