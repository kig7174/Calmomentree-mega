package com.calmomentree.projectree.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.calmomentree.projectree.models.NewMember;

@Mapper
public interface NewMemberMapper {
        @Insert("INSERT INTO new_member (date, member_count) " +
                        "SELECT DATE(join_date) AS join_date, COUNT(*) AS member_count " +
                        "FROM members " +
                        "WHERE DATE(join_date) = DATE(DATE_ADD(NOW(), INTERVAL -1 DAY)) " +
                        "GROUP BY join_date ")
        @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
        public int insert(NewMember input);

        @Insert("INSERT INTO new_member (date, member_count) " +
                        "VALUES " +
                        "(DATE(DATE_ADD(NOW(), INTERVAL -1 DAY)), 0)")
        public int insertDefault(NewMember input);

        // @Delete("DELETE FROM new_member " +
        //         "WHERE date < DATE(DATE_ADD(NOW(), INTERVAL -3 MONTH)) ")
        // public int autoDelete();

        @Select("SELECT id, date, member_count, " +
                "CASE DAYOFWEEK(date) " +
                        "WHEN 1 THEN '일' " +
                        "WHEN 2 THEN '월' " +
                        "WHEN 3 THEN '화' " +
                        "WHEN 4 THEN '수' " +
                        "WHEN 5 THEN '목' " +
                        "WHEN 6 THEN '금' " +
                        "WHEN 7 THEN '토' " +
                "END AS day " +
                "FROM new_member " +
                "WHERE date <= date(DATE_ADD(NOW(), INTERVAL -1 DAY)) " +
                "ORDER BY date DESC " +
                "LIMIT 0, 7 ")
        @Results(id = "resultMap", value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "date", column = "date"),
                        @Result(property = "memberCount", column = "member_count")                    
        })
        public List<NewMember> selectWeekly();

        @Select("SELECT CONCAT(DATE_FORMAT(MIN(date), '%Y-%m-%d'), ' ~ ', DATE_FORMAT(MAX(date), '%Y-%m-%d')) AS week, " +
                        "SUM(member_count) AS member_count " +
                        "FROM new_member " +
                        "GROUP BY FLOOR(DATEDIFF(DATE_ADD(NOW(), INTERVAL -1 DAY), date) / 7) " +
                        "ORDER BY MIN(date) DESC " +
                "LIMIT 0, 4 ")
        @ResultMap("resultMap")
        public List<NewMember> selectMonthly();

}
