package me.zhengjie.modules.system.service.impl;

import io.minio.SnowballObject;
import me.zhengjie.base.FileInfo;
import me.zhengjie.modules.system.service.FileService;
import me.zhengjie.utils.Minio;
import me.zhengjie.utils.SecurityUtils;
import org.apache.hc.core5.util.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author hjy
 * @date 2024/3/3 20:48
 */
@Service
public class FileServiceImpl implements FileService {


    @Override
    public boolean upload(MultipartFile file, String path) {

        try {
            Minio.upload(
                    file.getInputStream(),
                    file.getOriginalFilename(),
                    file.getContentType(),
                    name -> newFilename("/path", fileType(name))
            );
        } catch (IOException e) {
            throw new RuntimeException("上传文件失败:" + e.getMessage());
        }

        return true;
    }

    public boolean upload(MultipartFile[] files, String path) {


        return true;
    }

    private String newFilename(String path, String type) {
        return //SecurityUtils.getCurrentUsername() +
                "admin"+
                        path +
                        "/" +
                        UUID.randomUUID() +
                        type;
    }

    private String fileType(String originalName) {
        return originalName.substring(originalName.lastIndexOf("."));
    }
}
