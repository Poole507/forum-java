package com.smartsignlanguage.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AnnouncementDto {
    //公告标题
    private String title;
    //公告内容
    private String content;

}
