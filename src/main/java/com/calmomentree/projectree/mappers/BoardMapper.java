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
     * 
     * @param input
     * @return
     */
    @Delete("...")
    public int delete(Board input);

    /**
     * 
     * @param input
     * @return
     */
    @Select("...")
    @Results(id="resultMap", value={
        @Result(property="...", column="..."),
        @Result(property="...", column="..."),
        @Result(property="...", column="...")
    })
    public Board selectItem(Board input);

    /**
     * 
     * @param input
     * @return
     */
    @Select("...")
    @ResultMap("resultMap")
    public List<Board> selectList(Board input);

    /**
     * 
     * @param input
     * @return
     */
    @Select("...")
    public int selectCount(Board input);
}

