package com.smartsignlanguage.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTopicNumInfo {
    private Integer topicNum;
    private Integer collectNum;
    private Integer commentNum;
}
