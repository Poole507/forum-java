package com.smartsignlanguage.config;


import com.smartsignlanguage.properties.AliOssProperties;
import com.smartsignlanguage.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OssConfiguration {
 
    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil getAliOssUtil(AliOssProperties aliOssProperties) {
        log.info("创建OssUtil");
        AliOssUtil aliOssUtil = new AliOssUtil(
                aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName()
                );
        return aliOssUtil;
    }
}