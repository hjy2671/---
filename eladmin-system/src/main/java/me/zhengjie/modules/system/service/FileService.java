package me.zhengjie.modules.system.service;

import me.zhengjie.base.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author hjy
 * @date 2024/3/3 20:48
 */
public interface FileService {

    FileInfo upload(MultipartFile file, String path);

    List<FileInfo> upload(MultipartFile[] files, String path);

}
