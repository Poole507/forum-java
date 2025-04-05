package com.smartsignlanguage.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuaryTopicBySectionIdDto extends PageDto{
    private Integer sectionId;
}
