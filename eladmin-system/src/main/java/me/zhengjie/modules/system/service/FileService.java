package me.zhengjie.modules.system.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author hjy
 * @date 2024/3/3 20:48
 */
public interface FileService {

    boolean upload(MultipartFile file, String path);

}
