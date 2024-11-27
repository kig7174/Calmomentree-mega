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
    @Insert("INSERT INTO prod_imgs " +
            "(img_type, img_url, prod_id) " +
            "VALUE " +
            "(#{imgType}, #{imgUrl}, #{prodId})")
    @Options(useGeneratedKeys = true, keyProperty = "prodImgId", keyColumn = "prod_img_id")
    public int insert(ProdImg input);

    @Update("UPDATE prod_imgs SET " +
            "img_type=#{imgType}, " +
            "img_url=#{imgUrl}, " +
            "prod_id=#{prodId}, " +
            "WHERE prod_img_id=#{prodImgId}")
    public int update(ProdImg input);

    @Delete("DELETE FROM prod_imgs WHERE prod_img_id=#{prodImgId}")
    public int delete(ProdImg input);

    @Select("SELECT " +
            "prod_img_id, img_type, img_url, prod_id " +
            "FROM prod_imgs " +
            "WHERE prod_img_id=#{prodImgId}")
    @Results(id="prodImgMap", value={
        @Result(property="prodImgId", column="prod_img_id"),
        @Result(property="imgType", column="img_type"),
        @Result(property="imgUrl", column="img_url"),
        @Result(property="prodId", column="prod_id")
    })
    public ProdImg selectItem(ProdImg input);

    @Select("SELECT " +
            "prod_img_id, img_type, img_url, prod_id " +
            "FROM prod_imgs " +
            "WHERE prod_id=#{prodId} AND img_type=#{imgType}")
    @ResultMap("prodImgMap")
    public List<ProdImg> selectList(ProdImg input);

    @Select("SELECT " +
            "COUNT(*) " +
            "FROM prod_imgs " +
            "WHERE prod_id=#{prodId}}")
    public int selectCount(ProdImg input);
}
