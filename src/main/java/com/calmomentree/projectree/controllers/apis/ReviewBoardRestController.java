package com.calmomentree.projectree.controllers.apis;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.calmomentree.projectree.helpers.FileHelper;
import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.models.ReviewBoard;
import com.calmomentree.projectree.models.ReviewImg;
import com.calmomentree.projectree.models.UploadItem;
import com.calmomentree.projectree.services.ReviewBoardService;
import com.calmomentree.projectree.services.ReviewImgService;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
public class ReviewBoardRestController {

    @Autowired
    private RestHelper restHelper;

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private ReviewBoardService reviewBoardService;

    @Autowired
    private ReviewImgService reviewImgService;

    /**
     * 리뷰 게시글 업로드
     * 
     * @param reviewTitle
     * @param reviewContent
     * @param rating
     * @param photo
     * @return
     */
    @PostMapping("/api/board/product/add")
    public Map<String, Object> reviewAdd(
            @RequestParam("reviewTitle") String reviewTitle,
            @RequestParam("reviewContent") String reviewContent,
            @RequestParam("rating") int rating,
            @RequestParam(value = "photo", required = false) MultipartFile photo,

            @RequestParam(value = "prodId", defaultValue = "5") int prodId,
            @RequestParam(value = "memberId", defaultValue = "1") int memberId) {

        // 업로드 사진에 대한 처리
        UploadItem uploadItem = null;

        try {
            uploadItem = fileHelper.saveMultipartFile(photo);
        } catch (NullPointerException e) {
            // 업로드 된 항목이 없는 경우는 에러가 아니므로 계속 진행
        } catch (Exception e) {
            // 업로드 된 항목이 있으나, 이를 처리하다가 에러가 발생한 경우
            return restHelper.serverError(e);
        }

        ReviewBoard input = new ReviewBoard();
        input.setReviewTitle(reviewTitle);
        input.setReviewContent(reviewContent);
        input.setRating(rating);

        input.setProdId(prodId);
        input.setMemberId(memberId);

        try {
            reviewBoardService.addItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        
        // 업로드 사진 있어?
        if (uploadItem != null) {
            
            ReviewImg input2 = new ReviewImg();
            input2.setImgUrl(uploadItem.getFilePath());
            input2.setReviewBoardId(input.getReviewBoardId());
            
            try {
                reviewImgService.addItem(input2);
            } catch (Exception e) {
                return restHelper.serverError(e);
            }

        }

        // DB에 저장
        

        Map<String, Object> data = new LinkedHashMap<String, Object>();

        return restHelper.sendJson(data);
    }

}