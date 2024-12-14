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

import com.calmomentree.projectree.models.ReviewImg;

@Mapper
public interface ReviewImgMapper {

    /**
     * INSERT
     * @param input
     * @return
     */
    @Insert("INSERT INTO review_imgs ( img_url, review_board_id ) VALUES ( #{imgUrl}, #{reviewBoardId} )") 
    @Options(useGeneratedKeys = true, keyProperty = "boardImgId", keyColumn = "board_img_id")
    public int insert(ReviewImg input);

    @Update("...")
    public int update(ReviewImg input);

    @Delete("DELETE FROM " +
                "review_imgs " +
            "WHERE review_board_id = #{reviewBoardId} ")
    public int delete(ReviewImg input);

    /**
     * SELECT ITEM
     * @param input
     * @return
     */
    @Select("SELECT " +
                "board_img_id, img_url, review_board_id " +
            "FROM review_imgs " +
            "WHERE board_img_id = #{boardImgId}")
    @Results(id="resultMap", value={
        @Result(property="boardImgId", column="board_img_id"),
        @Result(property="imgUrl", column="img_url"),
        @Result(property="reviewBoardId", column="review_board_id")
    })
    public ReviewImg selectItem(ReviewImg input);

    /**
     * SELECT LIST
     * @param input
     * @return
     */
    @Select("SELECT " +
                "board_img_id, img_url, review_board_id " +
            "FROM review_imgs " +
            "WHERE review_board_id = #{reviewBoardId}")
    @ResultMap("resultMap")
    public List<ReviewImg> selectList(ReviewImg input);

    @Select("...")
    public int selectCount(ReviewImg input);
}
