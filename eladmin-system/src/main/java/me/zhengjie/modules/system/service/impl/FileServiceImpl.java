package me.zhengjie.modules.system.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.zhengjie.base.FileInfo;
import me.zhengjie.modules.system.service.FileService;
import me.zhengjie.modules.system.service.mapper.FileMapper;
import me.zhengjie.utils.Minio;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * @author hjy
 * @date 2024/3/3 20:48
 */
@AllArgsConstructor
@Service
@Log4j2
public class FileServiceImpl implements FileService {

    private final FileMapper mapper;

    @Override
    public FileInfo upload(MultipartFile file, String path) {

        try {
            FileInfo upload = Minio.upload(file, name -> newFilename(path, fileType(name)));
            mapper.insert(upload);
            return upload;
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败:" + e.getMessage());
        }
    }

    @Override
    public List<FileInfo> upload(MultipartFile[] files, String path) {
        try {
            List<FileInfo> fileInfos = Minio.uploadFiles(files, name -> newFilename(path, fileType(name)));
            mapper.insertBatch(fileInfos);
            return fileInfos;
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("文件上传失败");
        }
    }

    private String newFilename(String path, String type) {
        return path + "/" + SecurityUtils.getCurrentUsername() + "/" + UUID.randomUUID() + type;
    }

    private String fileType(String originalName) {
        return originalName.substring(originalName.lastIndexOf("."));
    }
}
