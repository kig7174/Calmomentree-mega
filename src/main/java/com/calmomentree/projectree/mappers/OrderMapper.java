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

import com.calmomentree.projectree.models.Order;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO orders ( " +
                "order_no, order_state, order_date, member_name, " +
                "member_email, member_postcode, member_addr1, member_addr2, member_tel, " +
                "receiver_name, receiver_postcode, receiver_addr1, receiver_addr2, receiver_tel, " +
                "total_price, req, member_id, reg_date, edit_date " +
                " ) VALUES ( " +
                // 202411160001
                "DATE_FORMAT(NOW(),'%Y%m%d') AS order_no, " +
                "#{orderState}, DATE_FORMAT(NOW(), '%Y-%m-%d') AS order_date, #{memberName}, " +
                "#{memberEmail}, #{memberPostcode}, #{memberAddr1}, #{memberAddr2}, #{memberTel}, " +
                "#{receiverName}, #{receiverPostcode}, #{receiverAddr1}, #{receiverAddr2}, #{receiverTel}, " +
                "#{totalPrice}, #{req}, #{memberId}, NOW(), NOW() ")
                
    @Options(useGeneratedKeys = true, keyProperty = "orderId", keyColumn = "order_id")
    public int insert(Order input);

    @Update("UPDATE orders SET " +
                // 주문자 정보
                "member_name = #{memberName}, " +
                "member_email = #{memberEmail}, " +
                "member_postcode = #{memberPostcode}, " +
                "member_addr1 = #{memberAddr1}, " +
                "member_addr2 = #{memberAddr2}, " +
                "member_tel = #{memberTel}, " +
                // 받는사람 정보
                "receiver_name = #{receiverName}, " +
                "receiver_postcode = #{receiverPostcode}, "+
                "receiver_addr1 = #{receiverAddr1}, " +
                "receiver_addr2 = #{receiverAddr2}, " +
                "receiver_tel = #{receiverTel}, " +
                "total_price = #{totalPrice}, " +
                "req = #{req}, " +
                "edit_date = NOW() " +
                "WHERE order_id = #{orderId} AND member_id = #{memberId} ")
    public int update(Order input);

    @Delete("DELETE FROM orders WHERE order_id = #{orderId} AND member_id = #{memberId} ")
    public int delete(Order input);

    @Select("SELECT " +
            "order_id, order_no, order_state, order_date, member_name, " +
            "member_email, member_postcode, member_addr1, member_addr2, member_tel, " +
            "receiver_name, receiver_postcode, receiver_addr1, receiver_addr2, receiver_tel, " +
            "total_price, req, member_id, reg_date, edit_date " +
            "FROM orders " +
            "WHERE order_id = #{orderId} AND member_id = #{memberId} ")
    @Results(id="resultMap", value={
        @Result(property="orderId", column="order_id"),
        @Result(property="orderNo", column="order_no"),
        @Result(property="orderState", column="order_state"),
        @Result(property="orderDate", column="order_date"),
        @Result(property="memberName", column="member_name"),
        @Result(property="memberEmail", column="member_email"),
        @Result(property="memberPostcode", column="member_postcode"),
        @Result(property="memberAddr1", column="member_addr1"),
        @Result(property="memberAddr2", column="member_addr2"),
        @Result(property="memberTel", column="member_tel"),
        @Result(property="receiverName", column="receiver_name"),
        @Result(property="receiverPostcode", column="receiver_postcode"),
        @Result(property="receiverAddr1", column="receiver_addr1"),
        @Result(property="receiverAddr2", column="receiver_addr2"),
        @Result(property="receiverTel", column="receiver_tel"),
        @Result(property="totalPrice", column="total_price"),
        @Result(property="req", column="req"),
        @Result(property="memberId", column="member_id"),
        @Result(property="regDate", column="reg_date"),
        @Result(property="editDate", column="edit_date")
    })
    public Order selectItem(Order input);

    @Select("...")
    @ResultMap("resultMap")
    public List<Order> selectList(Order input);

    @Select("...")
    public int selectCount(Order input);
}
