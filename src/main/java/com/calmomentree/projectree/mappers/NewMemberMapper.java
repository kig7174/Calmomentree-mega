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
        public int insert();

        @Insert("INSERT INTO new_member (date, member_count) " +
                        "VALUES " +
                        "(DATE(DATE_ADD(NOW(), INTERVAL -1 DAY)), 0)")
        public int insertDefault();

        @Select("SELECT id, date, member_count, DATE_FORMAT(date, '%a') AS day " +
                        "FROM new_member " +
                        "WHERE date > date(DATE_ADD(NOW(), INTERVAL -1 MONTH)) " +
                        "ORDER BY date ASC ")
        @Results(id = "resultMap", value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "date", column = "date"),
                        @Result(property = "memberCount", column = "member_count")
        })
        public List<NewMember> selectItem();

        @Select("SELECT DATE_FORMAT(date, '%Y-%m-%u') AS week, SUM(member_count) AS member_count " +
                        "FROM new_member " +
                        "GROUP BY week " +
                        "ORDER BY week DESC " +
                        "LIMIT 0, 4 ")
        @ResultMap("resultMap")
        public List<NewMember> selectWeekly();

}
