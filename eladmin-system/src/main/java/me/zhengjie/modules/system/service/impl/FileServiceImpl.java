package me.zhengjie.modules.system.service.impl;

import me.zhengjie.base.FileInfo;
import me.zhengjie.modules.system.service.FileService;
import me.zhengjie.utils.Minio;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
            FileInfo upload = Minio.upload(file, name -> newFilename(path, fileType(name)));

        } catch (Exception e) {
            throw new RuntimeException("上传文件失败:" + e.getMessage());
        }

        return true;
    }

    @Override
    public boolean upload(MultipartFile[] files, String path) {
        try {
            List<FileInfo> fileInfos = Minio.uploadFiles(files, name -> newFilename(path, fileType(name)));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private String newFilename(String path, String type) {
        return //SecurityUtils.getCurrentUsername() +
                "admin" + path + "/" + UUID.randomUUID() + type;
    }

    private String fileType(String originalName) {
        return originalName.substring(originalName.lastIndexOf("."));
    }
}
