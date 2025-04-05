package com.smartsignlanguage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//通过此注解我们就可以通过已sky.alioss为其中在application.yml中配置这四个属性
@ConfigurationProperties(prefix = "sign.alioss")
@Data
public class AliOssProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

}