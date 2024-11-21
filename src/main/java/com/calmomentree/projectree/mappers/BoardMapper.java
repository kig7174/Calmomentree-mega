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

import com.calmomentree.projectree.models.Board;

@Mapper
public interface BoardMapper {

        /**
         * 게시글 작성
         * 
         * @param input
         * @return
         */
        @Insert("INSERT INTO boards ( " +
                        "board_category, board_title, board_content, write_date, edit_date, " +
                        "is_public, board_pw, upload_img, member_id " +
                        ") VALUES ( " +
                        "#{boardCategory},#{boardTitle},#{boardContent},now(),now(), " +
                        "#{isPublic}, #{boardPw}, #{uploadImg}, #{memberId} )")
        @Options(useGeneratedKeys = true, keyProperty = "boardId", keyColumn = "board_id")
        public int insert(Board input);

        /**
         * 게시글 수정 (--------- 추가 사항 있을 것 같음 -----------)
         * 
         * @param input
         * @return
         */
        @Update("UPDATE boards " +
                        "SET " +
                        "board_content = #{boardContent}, " +
                        "edit_date = NOW(), " +
                        "is_public = #{isPublic}, " +

                        // 한번 설정한 비번은 변경불가

                        "upload_img = #{uploadImg} " +
                        "WHERE board_id = #{boardId} AND member_id = #{memberId}")
        public int update(Board input);

        /**
         * 게시글 삭제 (--------------- 수정해야되나...? --------------------)
         * 
         * @param input
         * @return
         */
        @Delete("<script> " +
                        "DELETE FROM boards " +
                        "<where> " +
                        "<if test= 'boardId != null'>board_id = #{boardId} </if> " +
                        "<if test= 'memberId != 0'>AND member_id = #{memberId}</if> " +
                        "</where>" +
                        "</script>")
        public int delete(Board input);

        /**
         * 한개의 게시글 조회
         * 
         * @param input
         * @return
         */
        @Select("SELECT " +
                        "board_id, board_category, board_title, board_content, " +
                        "DATE_FORMAT(b.write_date,'%Y-%m-%d') AS write_date, DATE_FORMAT(b.edit_date,'%Y-%m-%d') AS edit_date, is_public, board_pw, " +
                        "upload_img, m.member_id, replace(user_name,substring(user_name,2),'****') AS user_name, " +
                        "ROW_NUMBER() OVER(ORDER BY board_id) AS rownum " +
                        "FROM boards b " +
                        "INNER JOIN members m ON b.member_id = m.member_id " +
                        "WHERE board_id = #{boardId}")
        @Results(id = "resultMap", value = {
                        @Result(property = "boardId", column = "board_id"),
                        @Result(property = "boardCategory", column = "board_category"),
                        @Result(property = "boardTitle", column = "board_title"),
                        @Result(property = "boardContent", column = "board_content"),
                        @Result(property = "writeDate", column = "write_date"),
                        @Result(property = "editDate", column = "edit_date"),
                        @Result(property = "isPublic", column = "is_public"),
                        @Result(property = "boardPw", column = "board_pw"),
                        @Result(property = "uploadImg", column = "upload_img"),
                        @Result(property = "memberId", column = "member_id"),
                        @Result(property = "userName", column = "user_name"),
                        @Result(property = "rownum", column = "rownum"),
        })
        public Board selectItem(Board input);

        /**
         * 게시판 카테고리/작성자 에 따른 목록 조회 (--- 수정필요할듯 ---) , 제목에 대한 검색
         * 
         * @param input
         * @return
         */
        @Select("<script> " +
                "SELECT " +
                        "board_id, board_category, board_title, board_content, " +
                        "DATE_FORMAT(b.write_date,'%Y-%m-%d') AS write_date, DATE_FORMAT(b.edit_date,'%Y-%m-%d') AS edit_date, is_public, board_pw," +
                        "upload_img, m.member_id, replace(user_name,substring(user_name,2),'****') AS user_name, " +
                        "ROW_NUMBER() OVER(ORDER BY board_id) AS rownum " +
                "FROM boards b " +
                "INNER JOIN members m ON b.member_id = m.member_id " +
                "<where> " +
                        // 제목에 대해서만 검색기능
                        "<if test = 'boardTitle != null'>board_title LIKE concat('%',#{boardTitle},'%')</if> " +

                        "<if test = 'boardCategory != null'>AND board_category = #{boardCategory}</if> " +
                        "<if test = 'memberId != 0'>AND m.member_id = #{memberId}</if> " +
                "</where> " +
                        "ORDER BY rownum DESC " +

                        "<if test='listCount > 0'>LIMIT #{offset}, #{listCount}</if> " +
                "</script> ")
        @ResultMap("resultMap")
        public List<Board> boardList(Board input);

        /**
         * 검색 결과의 수를 조회하는 메서드
         * 목록 조회와 동일한 겸색 조건을 적용해야 한다.
         * 
         * @param input - 조회 조건을 담고 있는 객체
         * @return - 조회 결과 수
         */
        @Select("<script> " +
                        "SELECT COUNT(*) AS cnt " +
                        "FROM boards " +
                        "<where> " +
                        "<if test = 'boardTitle != null'>board_title LIKE concat('%',#{boardTitle},'%')</if> " +
                        "</where> " +
                "</script>")
        public int selectCount(Board input);
}
