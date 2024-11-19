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
     * 
     * @param input
     * @return
     */
    @Update("...")
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
            "<if test= 'memberId != null'>AND member_id = #{memberId}</if> " +
            "</where>" +
            "</script>")
    public int delete(Board input);

    /**
     * 한개의 게시글 조회
     * @param input
     * @return
     */
    @Select("SELECT " +
            "board_id, board_category, board_title, board_content, " +
            "write_date, edit_date, is_public, board_pw, " +
            "upload_img, member_id " +
            "FROM boards " +
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
    })
    public Board selectItem(Board input);

    /**
     * 게시판 카테고리별 목록 조회 (-------------- if문 넣어야함 ---------)
     * @param input
     * @return
     */
    @Select("SELECT " + 
                "board_id, board_category, board_title, board_content, " + 
                "write_date, edit_date, is_public, board_pw," + 
                "upload_img, member_id " + 
            "FROM boards " +
            "WHERE board_category = #{boardCategory}")
    @ResultMap("resultMap")
    public List<Board> categoryList(Board input);

    /**
     * 
     * @param input
     * @return
     */
    @Select("...")
    public int selectCount(Board input);
}
