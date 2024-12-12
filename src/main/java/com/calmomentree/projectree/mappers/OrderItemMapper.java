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

import com.calmomentree.projectree.models.OrderItem;

@Mapper
public interface OrderItemMapper {
    /**
     * 
     * @param input
     * @return
     */
    @Insert("INSERT INTO order_items (" +
                "prod_name, order_price, order_quantity, " +
                "prod_id, order_id ) VALUE (" +
                "#{prodName}, #{orderPrice}, #{orderQuantity}, " +
                "#{prodId}, #{orderId})") 
    @Options(useGeneratedKeys = true, keyProperty = "orderItemId", keyColumn = "order_item_id")
    public int insert(OrderItem input);

    @Update("...")
    public int update(OrderItem input);

    @Delete("...")
    public int delete(OrderItem input);

    /**
     * 
     * @param input
     * @return
     */
    @Select("SELECT " +
                "order_item_id, prod_name, order_price, " +
                "order_quantity, prod_id, order_id " +
            "FROM order_items " +
            "WHERE order_item_id = #{orderItemId}")
    @Results(id="resultMap", value={
        @Result(property="orderItemId", column="order_item_id"),
        @Result(property="prodName", column="prod_name"),
        @Result(property="orderPrice", column="order_price"),
        @Result(property="orderQuantity", column="order_quantity"),
        @Result(property="prodId", column="prod_id"),
        @Result(property="orderId", column="order_id")
    })
    public OrderItem selectItem(OrderItem input);

    /**
     * 
     * @param input
     * @return
     */
    @Select("SELECT " +
                "order_item_id, prod_name, order_price, " +
                "order_quantity, prod_id, order_id " +
            "FROM order_items " +
            "WHERE order_id = #{orderId}")
    @ResultMap("resultMap")
    public List<OrderItem> selectList(OrderItem input);

    @Select("...")
    public int selectCount(OrderItem input);
}
