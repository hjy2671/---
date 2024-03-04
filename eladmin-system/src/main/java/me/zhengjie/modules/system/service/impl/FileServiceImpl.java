package me.zhengjie.modules.system.service.impl;

import me.zhengjie.base.FileInfo;
import me.zhengjie.modules.system.service.FileService;
import me.zhengjie.utils.Minio;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author hjy
 * @date 2024/3/3 20:48
 */
@Service
public class FileServiceImpl implements FileService {


    @Override
    public boolean upload(MultipartFile file, String path) {

        String originalName = file.getOriginalFilename();

        try {
            FileInfo fileInfo = new FileInfo();
            String type = fileType(originalName);

            fileInfo.setFilename(newFilename(originalName, type));
            fileInfo.setType(type);
            fileInfo.setOriginalName(originalName);

            Minio.upload(file.getInputStream(), fileInfo);

            System.out.println("保存信息");

        } catch (IOException e) {
            throw new RuntimeException("上传文件失败:" + e.getMessage());
        }

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
