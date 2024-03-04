package me.zhengjie.utils;

import io.minio.*;
import io.minio.errors.*;
import me.zhengjie.base.FileInfo;
import me.zhengjie.config.MinioProperties;
import me.zhengjie.exception.MinioException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

    public static FileInfo upload(InputStream is, String originalName, String contentType, Function<String, String> nameHandler) {
        String filename = nameHandler.apply(originalName);
        try {
            instance().putObject(
                    PutObjectArgs.builder()
                            .bucket(config.bucket)
                            .stream(is, -1, config.maxSize)
                            .object(filename)
                            .contentType(contentType)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioException(e.getMessage());
        }
        return FileInfo.builder()
                .originalName(originalName)
                .filename(filename)
                .type(fileType(filename))
                .url(url(filename)).build();
    }

    public static Map<String, String> uploadFiles(List<SnowballObject> list)  throws Exception {
        instance().uploadSnowballObjects(
                UploadSnowballObjectsArgs.builder()
                        .bucket(config.bucket)
                        .objects(list)
                        .build()
        );
        return url(list);
    }

    public static GetObjectResponse download(String filename) {
        try {
            return instance().getObject(GetObjectArgs.builder().bucket(config.bucket).object(filename).build());
        } catch (Exception e) {
            throw new MinioException(e.getMessage());
        }
    }

    private static Map<String, String> url(List<SnowballObject> list) {
        HashMap<String, String> map = new HashMap<>();
        list.forEach(o -> {
            String filename = o.name();
            map.put(filename, url(filename));
        });
        return map;
    }

    private static String url(String filename) {
        return config.endpoint + "/" + config.bucket + "/" + filename;
    }

    private static String getContentType(String suffix) {
        switch (suffix) {
            case ".jpg": return "image/jpeg";
            case ".png": return "image/png";
            case ".gif": return "image/gif";
            default: return "application/octet-stream";
        }
    }

    private static String fileType(String originalName) {
        return originalName.substring(originalName.lastIndexOf("."));
    }

}
