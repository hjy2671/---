package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.FileInfo;
import me.zhengjie.modules.system.service.FileService;
import me.zhengjie.utils.Minio;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
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
    public boolean upload(MultipartFile file) {

        String originalName = file.getOriginalFilename();

        String filename = getFilename(originalName, null);


        try {
            String url = Minio.upload(file.getInputStream(), filename);

            FileInfo fileInfo = FileInfo.builder()
                    .originalName(originalName)
                    .filename(filename)
                    .url(url).build();

            System.out.println("保存信息");

        } catch (IOException e) {
            throw new RuntimeException("上传文件失败:" + e.getMessage());
        }

        return true;
    }

    private String fileType(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }

    private String getFilename(String originalName, String type) {
//        SecurityUtils.getCurrentUsername()
        return "username" + "/" + UUID.randomUUID() + fileType(originalName);
    }
}
