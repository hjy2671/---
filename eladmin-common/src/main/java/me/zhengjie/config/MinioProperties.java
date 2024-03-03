package me.zhengjie.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hjy
 * @date 2024/3/2 22:40
 */
@Data
@ConfigurationProperties(prefix = "minio")
@Configuration
public class MinioProperties {

    public String endpoint;
    public String accessKey ;
    public String accessSecret ;
    public String bucket;
    public long maxSize;

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize << 23;
    }
}
