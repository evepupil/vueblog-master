package com.markerhub.util;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;



/**
 * @ClassName : COSClientUtil
 * @Author : Administrator
 * @Date: 2021/8/19 15:12
 * @Description :腾讯云上传工具
 */
public class COSClientUtil {
    //todo 这些变量信息自行到 腾讯云对象存储控制台 获取
    // 存储通名称			替换成自己的
    private static final String bucketName = "blog-1257029975";
    //secretId 			替换成自己的
    private static final String secretId = "AKIDjMmz0nLgmhRyHNEpCKEtJUQ7aonX2HH3";
    // secretKey		        替换成自己的
    private static final String secretKey = "ZtyoQoxsYzB9sMcdPwEH6g291f7MM7ez";

    // 1 初始化用户身份信息(secretId, secretKey)
    private static final COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
    // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
    private static final ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing"));
    // 3 生成cos客户端
    private static final COSClient cosClient = new COSClient(cred, clientConfig);
    // 文件存储目录
    //private String filedir = "blog/";
    private COSClient cOSClient;

    public COSClientUtil() {
        cOSClient = new COSClient(cred, clientConfig);
    }
    public static String uploadfile(MultipartFile localFile,String path) throws CosClientException, CosServiceException {
        String fileName = localFile.getOriginalFilename();
        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        File file=null;
        try {
             file= MultipartFileToFile.multipartFileToFile(localFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String substring = fileName.substring(0,fileName.lastIndexOf("."));
            substring=StringTransfer.stringToLegal(substring);
            String end=fileName.substring(fileName.lastIndexOf("."));
            Random random = new Random();
            // 指定要上传到 COS 上的路径
            fileName = path + "/"+substring+end;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName , file);
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭客户端(关闭后台线程)
            file.delete();
            cosclient.shutdown();
        }
        return fileName;
    }

    /**
     * 销毁
     */
    public void destory() {
        cOSClient.shutdown();
    }

    /**
     * 上传图片
     *
     * @param url
     */
    public void uploadImg2Cos(String url,String path) throws Exception {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2Cos(fin, split[split.length - 1],path);
        } catch (FileNotFoundException e) {
            throw new Exception("图片上传失败");
        }
    }

    public String uploadFile2Cos(MultipartFile file,String path) throws Exception {
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new Exception("上传图片大小不能超过10M！");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        //Random random = new Random();
        String name =substring;

        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2Cos(inputStream, name, path);
            return name;
        } catch (Exception e) {
            throw new Exception("图片上传失败");
        }
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        return getUrl(fileUrl);
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        String url="https://blog-1257029975.cos.ap-beijing.myqcloud.com";
        if (url != null) {
            return url+key;
        }
        return null;
    }

    /**
     * 上传到COS服务器 如果同名文件会覆盖服务器上的
     *
     * @param instream
     *            文件流
     * @param fileName
     *            文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2Cos(InputStream instream, String fileName,String path) {
        String ret = "";
        try {

            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);

            // 上传文件
            PutObjectResult putResult = cOSClient.putObject(bucketName+"/"+path,  fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Description: 判断Cos服务文件上传时文件的contentType
     *
     * @param filenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg") || filenameExtension.equalsIgnoreCase("jpg")
                || filenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

}
