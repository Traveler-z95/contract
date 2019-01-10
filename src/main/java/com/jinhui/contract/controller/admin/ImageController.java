package com.jinhui.contract.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.jinhui.contract.service.UserService;
import com.jinhui.contract.util.ImageCutUtil;
import com.jinhui.contract.util.PhotoUploadUtil;
import com.jinhui.contract.vo.PhotoResult;
import com.jinhui.contract.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 上传图片的controller
 */
@RestController
public class ImageController {

    private Logger logger = LoggerFactory.getLogger(AdminContractController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoUploadUtil photoUploadUtil;

    /**
     * 头像修改
     * @param request 获取session的request
     * @param avatarSrc 图片路径
     * @param avatarData 图片裁剪的内容
     * @param file 图片
     * @return
     */
    @RequestMapping("/admin/avatar/update")
    public PhotoResult updateAvatar(HttpServletRequest request, String avatarSrc, @RequestParam(value = "avatar_data") String avatarData, @RequestParam(value = "avatar_file",required = true) MultipartFile file){
        PhotoResult result = null;
        String type = file.getContentType();
        if(type==null || !type.toLowerCase().startsWith("image/")) {
            return new PhotoResult(0,"","不支持的文件类型，仅支持图片！");
        }
        try {

            JSONObject object = (JSONObject) JSONObject.parse(avatarData);
            //上传图片
            result = photoUploadUtil.uploadPhoto(ImageCutUtil.cutImageForByte(ImageIO.read(file.getInputStream()),(int) object.getFloatValue("x"), (int) object.getFloatValue("y"), (int) object.getFloatValue("width"), (int) object.getFloatValue("height")), file.getOriginalFilename());
            User user = (User) request.getSession().getAttribute("user");
            userService.updateAvatar(result.getUrl(),user.getUserName());
            result.setMessage("修改图像成功！！！");

        }catch (IOException e){
            logger.error(e.getMessage());
            return new PhotoResult(0,"","上传图片发生异常！");
        }
        return result;
    }
}
