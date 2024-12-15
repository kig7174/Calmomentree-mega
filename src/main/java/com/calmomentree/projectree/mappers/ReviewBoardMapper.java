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

import com.calmomentree.projectree.models.ReviewBoard;

@Mapper
public interface ReviewBoardMapper {

        /**
         * 리뷰 게시글 등록하기
         * 
         * @param input
         * @return
         */
        @Insert("INSERT INTO review_boards (" +
                        "review_title, review_content, rating, " +
                        "write_date, edit_date, prod_id, member_id " +
                        ") VALUES (" +
                        "#{reviewTitle}, #{reviewContent}, #{rating}, " +
                        "NOW(), NOW(), #{prodId}, #{memberId} )")
        @Options(useGeneratedKeys = true, keyProperty = "reviewBoardId", keyColumn = "review_board_id")
        public int insert(ReviewBoard input);

        /**
         * 리뷰 게시글 수정하기
         * 
         * @param input
         * @return
         */
        @Update("UPDATE review_boards " +
                        "SET " +
                        "review_title = #{reviewTitle} " +
                        "reivew_content = #{reviewContent} " +
                        "rating = #{rating} " +
                        "edit_date = NOW() " +
                        "WHERE review_board_id = #{reviewBoardId} AND member_id = #{memberId}")
        public int update(ReviewBoard input);

        /**
         * 리뷰 게시글 삭제하기
         * 
         * @param input
         * @return
         */
        @Delete("DELETE FROM review_boards " +
                        "WHERE " +
                        "review_board_id = #{reviewBoardId} AND " +
                        "member_id = #{memberId} ")
        public int delete(ReviewBoard input);

        /**
         * 리뷰 게시글 상세조회
         * 
         * @param input
         * @return
         */
        @Select("SELECT " +
                        "r.review_board_id, review_title, review_content, " +
                        "rating, DATE_FORMAT(r.write_date,'%Y-%m-%d') AS write_date, " +
                        "DATE_FORMAT(r.edit_date,'%Y-%m-%d') AS edit_date, p.prod_id, m.member_id, " +
                        "replace(user_name,substring(user_name,2),'****') AS user_name, " +
                        "ROW_NUMBER() OVER(ORDER BY r.review_board_id) AS rownum, " +
                        " p.prod_name_kor, p.price, " +
                         
                        "( SELECT img_url FROM prod_imgs " +
                        "WHERE prod_id = r.prod_id AND img_type = 'list' " +
                        "ORDER BY prod_img_id LIMIT 0,1 ) AS img_url " +
                "FROM review_boards r " +

                        // 회원
                        "INNER JOIN members m ON r.member_id = m.member_id " +
                        // 상품
                        "INNER JOIN products p ON p.prod_id = r.prod_id " +
                        // 상품이미지
                        // "INNER JOIN prod_imgs pro ON r.prod_id = pro.prod_id " +
                        // 리뷰이미지
                        "WHERE r.review_board_id = #{reviewBoardId}")
        @Results(id = "resultMap", value = {
                        @Result(property = "reviewBoardId", column = "review_board_id"),
                        @Result(property = "reviewTitle", column = "review_title"),
                        @Result(property = "reviewContent", column = "review_content"),
                        @Result(property = "rating", column = "rating"),
                        @Result(property = "writeDate", column = "write_date"),
                        @Result(property = "editDate", column = "edit_date"),
                        @Result(property = "prodId", column = "prod_id"),
                        @Result(property = "memberId", column = "member_id"),
                        @Result(property = "userName", column = "user_name"),
                        @Result(property = "rownum", column = "rownum"),
                        @Result(property = "imgUrl", column = "img_url"),
                        @Result(property = "prodNameKor", column = "prod_name_kor"),
                        @Result(property = "price", column = "price")
        })
        public ReviewBoard selectItem(ReviewBoard input);

        /**
         * 리뷰 게시글 목록조회
         * 
         * @param input
         * @return
         */
        @Select("<script> " +
                "SELECT " +
                        "r.review_board_id, review_title, review_content, " +
                        "rating, DATE_FORMAT(r.write_date,'%Y-%m-%d') AS write_date, " +
                        "DATE_FORMAT(r.edit_date,'%Y-%m-%d') AS edit_date, r.prod_id, m.member_id, " +
                        "replace(user_name,substring(user_name,2),'****') AS user_name, " +
                        "ROW_NUMBER() OVER(ORDER BY r.review_board_id) AS rownum , " +
                        
                        "( SELECT img_url FROM prod_imgs " +
                        "WHERE prod_id = r.prod_id AND img_type = 'list' " +
                        "ORDER BY prod_img_id LIMIT 0,1 ) AS img_url " +
                
                "FROM review_boards r " +
                "INNER JOIN members m ON r.member_id = m.member_id " +

                "<where> " +
                        "<if test = 'reviewTitle != null'>review_title LIKE concat('%',#{reviewTitle},'%')</if> " +
                        "<if test = 'memberId != 0'>AND m.member_id = #{memberId}</if> " +
                        // "<if test = 'prodId != 0'>OR r.prod_id = #{prodId}</if> " +
                "</where> " +
                        "ORDER BY rownum DESC " +

                        "<if test='listCount > 0'>LIMIT #{offset}, #{listCount}</if> " +
                "</script> ")
        @ResultMap("resultMap")
        public List<ReviewBoard> selectList(ReviewBoard input);

        /**
         * 검색 결과의 수를 조회하는 메서드
         * 목록 조회와 동일한 검색 조건을 적용해야 한다.
         * 
         * @param input - 조회 조건을 담고 있는 객체
         * @return - 조회 결과 수
         */
        @Select("<script> " +
                        "SELECT COUNT(*) AS cnt " +
                        "FROM review_boards " +
                        "<where> " +
                        "<if test = 'reviewTitle != null'>review_title LIKE concat('%',#{reviewTitle},'%')</if> " +
                        // "<if test = 'prodId != null'>OR prod_id = #{prodId} </if> " +
                        "</where> " +
                        "</script>")
        public int selectCount(ReviewBoard input);
}
