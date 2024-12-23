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
    @Insert(
            "INSERT INTO sales (total_sales, date) " +
            "SELECT SUM(order_price) AS order_price, DATE_FORMAT(order_date, '%Y-%m-%d') AS order_date FROM orders AS ord " +

            "INNER JOIN order_items AS item " +
            "ON ord.order_id = item.order_id " +

            "WHERE DATE_FORMAT(order_date, '%Y-%m-%d') = DATE(DATE_ADD(NOW(),  INTERVAL -1 DAY)) " +

            "GROUP BY DATE_FORMAT(order_date, '%Y-%m-%d')"
            )
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public int insert();

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

    @Select("...")
    @ResultMap("resultMap")
    public List<Sale> selectList(Sale input);

    @Select("...")
    public int selectCount(Sale input);
}

