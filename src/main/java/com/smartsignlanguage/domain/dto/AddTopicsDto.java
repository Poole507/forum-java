package com.smartsignlanguage.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddTopicsDto {
    private String title;
    private String content;
    private Integer sectionId;
    // 附件
    private MultipartFile file;
}
