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

import com.calmomentree.projectree.models.Basket;

@Mapper
public interface BasketMapper {
    /**
     * 장바구니 등록
     * @param input
     * @return
     */
    @Insert("INSERT INTO baskets ( " +
                "quantity, basket_add_date, prod_id, member_id " +
                ") VALUES (" +
                "#{quantity}, NOW(), #{prodId}, #{memberId})")
    @Options(useGeneratedKeys = true, keyProperty = "basketId", keyColumn = "basket_id")
    public int insert(Basket input);

    @Update("UPDATE baskets " +
            "SET " +
                "quantity = #{quantity} " +
                "WHERE basket_id = #{basketId}")
    public int update(Basket input);

    /**
     * 장바구니 삭제
     * @param input
     * @return
     */
    @Delete("<script> " +
                "DELETE FROM baskets " +
                "<where> " +
                    "<if test= 'prod_id != null'>board_id = #{ProdId} </if> " +
                    "<if test= 'memberId != 0'>AND member_id = #{memberId}</if> " +
                "</where>" +
            "</script>")
    public int delete(Basket input);

    /**
     * 장바구니 조회
     * @param input
     * @return
     */
    @Select("SELECT " +
                "basket_id, quantity, basket_add_date, p.prod_id, member_id, " +
                "p.prod_name_kor, p.price, p.capacity " +
                "FROM baskets " +
            "INNER JOIN products p ON b.prod_id = p.prod_id " +
            "WHERE member_id = #{memberId}")
    @Results(id="resultMap", value={
        @Result(property="basketId", column="basket_id"),
        @Result(property="quantity", column="quantity"),
        @Result(property="basketAddDate", column="basket_add_date"),
        @Result(property="prodId", column="prod_id"),
        @Result(property="memberId", column="member_id")
    })
    public Basket selectItem(Basket input);

    /**
     * 
     * @param input
     * @return
     */
    @Select("SELECT " +
                "basket_id, quantity, basket_add_date, p.prod_id, member_id, " +
                "p.prod_name_kor AS prodNameKor, p.price, p.capacity " +
                "FROM baskets b " +
            "INNER JOIN products p ON b.prod_id = p.prod_id " +
            "WHERE member_id = #{memberId}")
    @ResultMap("resultMap")
    public List<Basket> selectList(Basket input);

    @Select("...")
    public int selectCount(Basket input);
}

