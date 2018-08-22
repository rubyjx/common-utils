package com.maiunsoft.fastdb.utils;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * fastDfs文件上传与下载工具类
 *
 * @author jx
 * @create 2018-08-21 14:38
 */
@Component
public class FastDfsUtils {

    @Autowired
    private FastFileStorageClient fastFileStorageClient ;

    /**
     * 文件上传
     * @param inputStream 文件流
     * @param fileSize   文件大小
     * @param fileName   文件名（带后缀名）
     * @return
     */
    public StorePath uploadFile(InputStream inputStream, Long fileSize, String fileName){

        if(fileSize <= 0){
            return null;
        }
        if(StringUtils.isBlank(fileName)){
            return null;
        }

        StorePath storePath = fastFileStorageClient.uploadFile(inputStream, fileSize, FilenameUtils.getExtension(fileName), null);

        return storePath;
    }


    /**
     * 删除缩略图图片
     * @param inputStream  文件流
     * @param fileSize  文件大小
     * @param fileName   文件名称
     * @return
     */
    public StorePath uploadThumbpImg(InputStream inputStream, Long fileSize, String fileName){
        if(fileSize <= 0){
            return null;
        }
        if(StringUtils.isBlank(fileName)){
            return null;
        }

        StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(inputStream, fileSize, FilenameUtils.getExtension(fileName), null);

        return storePath;
    }

    /**
     * 删除文件
     * @param groupName 组名
     * @param filePath  文件路径不包含组名
     */
    public String deleteFile(String groupName,String filePath){
        if(StringUtils.isBlank(groupName)){
            return "请输入组名！";
        }
        if(StringUtils.isBlank(filePath)){
            return "请输入被删除文件的路径！";
        }

        fastFileStorageClient.deleteFile(groupName, filePath);

        return "success";
    }


    /**
     * 下载文件
     * @param groupName 组名
     * @param filePath 文件路径
     * @param outputStream 输出流
     * @throws IOException
     */
    public void downLoadFile(String groupName, String filePath, OutputStream outputStream) throws IOException {

        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] group1s = fastFileStorageClient.downloadFile(groupName, filePath, downloadByteArray);

        outputStream.write(group1s);
    }


}
