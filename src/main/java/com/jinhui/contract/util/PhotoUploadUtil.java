package com.jinhui.contract.util;

import com.jinhui.contract.vo.PhotoResult;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 使用七牛存储图片
 *
 * 修改硬编码 wei
 * FILE: com.da.blog.util.PhotoUploadUtil.java
 * MOTTO:  不积跬步无以至千里,不积小流无以至千里
 */
@Component
public class PhotoUploadUtil {
    private static Logger logger = LoggerFactory.getLogger(PhotoUploadUtil.class);
    //设置好账号的accessKey和secretKey
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    //要上传的空间
    @Value("${qiniu.bucketName}")
    private String bucketName;
    @Value("${qiniu.basePath}")
    public String basePath;

    //上传到七牛后保存的文件名
    public String getFilePath(String fileName){
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH)+1;
        int day = instance.get(Calendar.DATE);
        return year+"/"+month+"/"+day+"/"+fileName;
    }

    public Auth getAuth(){
        return Auth.create(accessKey,secretKey);
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(){
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucketName);
    }

    /**
     * 长传图片
     * @param realName 图片路径名
     * @param filename 图片名
     * @return
     */
    public PhotoResult uploadPhoto(String realName, String filename){
        PhotoResult result = new PhotoResult();
        try {
            Configuration cfg = new Configuration(Zone.zone1());
            Response response = new UploadManager(cfg).put(realName, getFilePath(filename), getUpToken());
            if (response.isOK()){
                result.setSuccess(1);
                result.setUrl(basePath+getFilePath(filename));
                return result;
            }
        } catch (QiniuException e) {
            result.setSuccess(0);
            result.setMessage(e.getMessage());
            return result;
        }
        return result;
    }


    /**
     * 长传图片
     * @param data 图片路径名
     * @param filename 图片名
     * @return
     */
    public PhotoResult uploadPhoto(byte[] data, String filename){
        PhotoResult result = new PhotoResult();
        try {
            Configuration cfg = new Configuration(Zone.zone1());
            Response response = new UploadManager(cfg).put(data, getFilePath(filename), getUpToken());
            if (response.isOK()){
                result.setSuccess(1);
                result.setUrl(basePath+getFilePath(filename));
                return result;
            }
        } catch (QiniuException e) {
            result.setSuccess(0);
            result.setMessage(e.getMessage());
            return result;
        }
        return result;
    }

    /**
     * 删除图片
     * @param fileNames
     * @return
     */
    public int deletePhoto(String[] fileNames){
        int result = 0;
        Configuration cfg = new Configuration(Zone.zone1());
        BucketManager bucketManager = new BucketManager(getAuth(),cfg);
        for (String filename : fileNames) {
            try {
                bucketManager.delete(bucketName,filename);
                result++;
            } catch (QiniuException e) {
                logger.error("{}.deletePhoto方法发生异常,异常原因{}",PhotoUploadUtil.class.getCanonicalName(),e.getMessage());
                return 0;
            }
        }
        return result;
    }

}
