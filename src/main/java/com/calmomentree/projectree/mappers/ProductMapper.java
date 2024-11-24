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

import com.calmomentree.projectree.models.Product;

@Mapper
public interface ProductMapper {
    @Insert("INSERT INTO products " +
            "(prod_name_kor, prod_name_eng, " +
            "func_txt, desc_txt, " +
            "price, is_discount, discount, " +
            "capacity, specification, " +
            "use_period, use_method, manufacturer, " +
            "release_date, edit_date, " +
            "category_id) " +

            "VALUE " +
            "(#{prodNameKor}, #{prodNameEng}, " +
            "#{funcTxt}, #{descTxt}, " +
            "#{price}, #{isDiscount}, null, " +
            "#{capacity}, #{specification}, " +
            "#{usePeriod}, #{useMethod}, #{manufacturer}, " +
            "#{releaseDate}, NOW(), " +
            "#{categoryId})")
    @Options(useGeneratedKeys = true, keyProperty = "prodId", keyColumn = "prod_id")
    public int insert(Product input);

    @Update("<script>" +
            "UPDATE products SET " +
                "prod_name_kor=#{prodNameKor}, " +
                "prod_name_eng=#{prodNameEng}, " +
                "func_txt=#{funcTxt}, " +
                "desc_txt=#{descTxt}, " +
                "price=#{price}, " +
                "is_discount=#{isDiscount}, " +
                "<if test='is_discount=\"Y\"'>discount=#{discount},</if> " +
                "capacity=#{capacity}, " +
                "specification=#{specification}, " +
                "use_period=#{usePeriod}, " +
                "use_method=#{useMethod}, " +
                "manufacturer=#{manufacturer}, " +
                "release_date=#{releaseDate}, " +
                "edit_date=NOW(), " +
                "category_id=#{categoryId} " +
            "WHERE prod_id=#{prodId} " +
            "</script>")
    public int update(Product input);

    @Delete("DELETE FROM products WHERE prod_id=#{prodId}")
    public int delete(Product input);

    @Select("SELECT " +
                "prod_id, " +
                "prod_name_kor, prod_name_eng, " +
                "func_txt, desc_txt, " +
                "price, is_discount, discount, " +
                "capacity, specification, " +
                "use_period, use_method, manufacturer, " +
                "release_date, edit_date, " +
                "category_name, " +
                "( " +
                "SELECT category_name " +
                "FROM categorys " +
                "WHERE category_id = category.parent_category_no " +
                ") AS parent_category_name " +
            "FROM products AS prod " +
            "INNER JOIN categorys AS category " +
            "ON prod.category_id = category.category_id " +
            "WHERE prod_id=#{prodId}")
    @Results(id="productMap", value={
        @Result(property="prodId", column="prod_id"),
        @Result(property="prodNameKor", column="prod_name_kor"),
        @Result(property="prodNameEng", column="prod_name_eng"),
        @Result(property="funcTxt", column="func_txt"),
        @Result(property="descTxt", column="desc_txt"),
        @Result(property="price", column="price"),
        @Result(property="isDiscount", column="is_discount"),
        @Result(property="discount", column="discount"),
        @Result(property="capacity", column="capacity"),
        @Result(property="specification", column="specification"),
        @Result(property="usePeriod", column="use_period"),
        @Result(property="useMethod", column="use_method"),
        @Result(property="manufacturer", column="manufacturer"),
        @Result(property="releaseDate", column="release_date"),
        @Result(property="editDate", column="edit_date"),
        @Result(property="categoryId", column="category_id"),
        @Result(property="categoryName", column="category_name"),
        @Result(property="parentCategoryName", column="parent_cateogry_name"),
        @Result(property="parentCategoryNo", column="parent_category_no")
    })
    public Product selectItem(Product input);

    @Select("WITH RECURSIVE category_list AS ( " +
                "SELECT " +
                    "category_id, " +
                    "category_name, " +
                    "parent_category_no " +
                "FROM categorys " +
                "WHERE parent_category_no = #{categoryId} " + 
                    
                "UNION ALL " +
                    
                "SELECT " + 
                    "a.category_id, " +
                    "a.category_name, " + 
                    "a.parent_category_no " +
                "FROM categorys AS a " +
                "INNER JOIN category_list AS b " +
                "ON a.parent_category_no = b.category_id) " +
        
            "SELECT " +
            "prod_id, " +
            "prod_name_kor, prod_name_eng, " +
            "func_txt, desc_txt, " +
            "price, is_discount, discount, " +
            "capacity, specification, " +
            "use_period, use_method, manufacturer, " +
            "release_date, edit_date, " +
            "category_name, " +
            "( " +
            "SELECT category_name " +
            "FROM categorys " +
            "WHERE category_id = category.parent_category_no " +
            ") AS parent_category_name " +
            "FROM products AS prod " +
            "INNER JOIN categorys AS category " +
            "ON prod.category_id = category.category_id " +
            "WHERE prod.category_id IN ( " +
            // "( " +
            // "CASE " +
            // "WHEN (SELECT parent_category_no FROM categorys WHERE category_id = #{categoryId}) != 0 " +
            // "THEN #{categoryId} " +
            // "WHEN (SELECT parent_category_no FROM categorys WHERE category_id = #{categoryId}) = 0 " +
            // "THEN " +        
            "(SELECT " +
            "category_id " +
            "FROM category_list AS c) " +   
            // "END " +
            ");")
    @ResultMap("productMap")
    public List<Product> selectListByCategory(Product input);

    @Select("<script>" +
            "SELECT " +
            "prod_id, " +
            "prod_name_kor, prod_name_eng, " +
            "func_txt, desc_txt, " +
            "price, is_discount, discount, " +
            "capacity, specification, " +
            "use_period, use_method, manufacturer, " +
            "release_date, edit_date, " +
            "category_name, " +
            "( " +
            "SELECT category_name " +
            "FROM categorys " +
            "WHERE category_id = category.parent_category_no " +
            ") AS parent_category_name " +
            "FROM products AS prod " +
            "INNER JOIN categorys AS category " +
            "ON prod.category_id = category.category_id " +
            "<where> " +
            "<if test='prodNameKor != null or prodNameKor != \"\"'>prod_name_kor LIKE CONCAT('%', #{prodNameKor}, '%')</if> " +
            "</where> " +
            "ORDER BY #{orderBy} " +
            "<if test='listCount > 0'>LIMIT #{offset}, #{listCount}</if> " +
            "</script>")
    @ResultMap("productMap")
    public List<Product> selectListBySearch(Product input);

    @Select("<script>" +
            "SELECT COUNT(*) AS cnt FROM products " +
            "<where> " +
            "<if test='prodNameKor != null or prodNameKor != \"\"'>prod_name_kor LIKE CONCAT('%', #{prodNameKor}, '%')</if> " +
            "</where> " +
            "</script>")
    public int selectCount(Product input);
}
