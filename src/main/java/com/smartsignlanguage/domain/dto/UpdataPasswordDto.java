package com.smartsignlanguage.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdataPasswordDto {
    private String old_password;
    private String new_password;
}
