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

import com.calmomentree.projectree.models.Sale;

@Mapper
public interface SaleMapper {
    @Insert("INSERT INTO sales (total_sales, date) " +
            "SELECT SUM(total_price), DATE(order_date) AS ord_date FROM orders " +

            "WHERE order_state != '주문중' AND DATE(order_date) = DATE(DATE_ADD(NOW(),  INTERVAL -1 DAY)) " +

            "GROUP BY ord_date")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public int insert(Sale input);

    @Insert("INSERT INTO sales (total_sales, date) " +
            "VALUE " + 
            "(0, DATE(DATE_ADD(NOW(), INTERVAL -1 DAY)))")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public int insertDefault(Sale input);

    @Update("...")
    public int update(Sale input);

    @Delete("...")
    public int delete(Sale input);

    @Select("SELECT id, date, total_sales FROM sales WHERE id = #{id}")
    @Results(id="resultMap", value={
        @Result(property="id", column="id"),
        @Result(property="date", column="date"),
        @Result(property="totalSales", column="total_sales")
    })
    public Sale selectItem(Sale input);

    @Select("SELECT id, date, total_sales FROM sales WHERE date <= DATE(DATE_ADD(NOW(), INTERVAL -1 DAY)) ORDER BY date DESC LIMIT 0, 28")
    @ResultMap("resultMap")
    public List<Sale> selectList(Sale input);

    @Select("...")
    public int selectCount(Sale input);
}

