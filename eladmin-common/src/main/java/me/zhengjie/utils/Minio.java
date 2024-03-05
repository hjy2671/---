package me.zhengjie.utils;

import io.minio.*;
import io.minio.errors.*;
import me.zhengjie.base.FileInfo;
import me.zhengjie.config.MinioProperties;
import me.zhengjie.exception.MinioException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author hjy
 * @date 2024/3/3 19:59
 */
public class Minio {

    private static MinioClient client;
    private static MinioProperties config;

    public static MinioClient instance() {
        if (client != null)
            return client;

        config = SpringContextHolder.getBean(MinioProperties.class);

        client = MinioClient.builder()
                .endpoint(config.endpoint)
                .credentials(config.accessKey, config.accessSecret)
                .build();

        try {
            if (!client.bucketExists(BucketExistsArgs.builder().bucket(config.bucket).build()))
                client.makeBucket(MakeBucketArgs.builder().bucket(config.bucket).build());
        } catch (Exception e) {
            throw new MinioException("文件存储系统创建失败: " + e.getMessage());
        }

        return client;
    }

    public static FileInfo upload(MultipartFile file, Function<String, String> nameHandler) {
        String filename = nameHandler.apply(file.getOriginalFilename());
        try {
            instance().putObject(
                    PutObjectArgs.builder()
                            .bucket(config.bucket)
                            .stream(file.getInputStream(), -1, config.maxSize)
                            .object(filename)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new MinioException(e.getMessage());
        }
        return FileInfo.builder()
                .originalName(file.getOriginalFilename())
                .filename(filename)
                .url(url(filename)).build();
    }

    public static List<FileInfo> uploadFiles(MultipartFile[] files, Function<String, String> nameHandler)  throws Exception {

        List<SnowballObject> snowballObjects = new LinkedList<>();
        List<FileInfo> fileInfos = new LinkedList<>();

        for (MultipartFile file : files) {
            String newName = nameHandler.apply(file.getOriginalFilename());
            snowballObjects.add(new SnowballObject(newName, file.getInputStream(), file.getSize(), null));
            fileInfos.add(
                    FileInfo.builder()
                    .originalName(file.getOriginalFilename())
                    .filename(newName)
                    .url(url(newName))
                    .build()
            );
        }

        instance().uploadSnowballObjects(
                UploadSnowballObjectsArgs.builder()
                        .bucket(config.bucket)
                        .objects(snowballObjects)
                        .build()
        );
        return fileInfos;
    }

    public static GetObjectResponse download(String filename) {
        try {
            return instance().getObject(GetObjectArgs.builder().bucket(config.bucket).object(filename).build());
        } catch (Exception e) {
            throw new MinioException(e.getMessage());
        }
    }


    private static String url(String filename) {
        return config.endpoint + "/" + config.bucket + "/" + filename;
    }

    private static String fileType(String originalName) {
        return originalName.substring(originalName.lastIndexOf("."));
    }

}
