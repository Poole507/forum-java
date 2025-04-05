

package com.smartsignlanguage.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class AddSectionDto {
    //版块名称
    private String name;
    //版块描述
    private String description;
    // 板块id
    private Integer sectionId;
    // 版块图标URL
    private String iconUrl;
}
