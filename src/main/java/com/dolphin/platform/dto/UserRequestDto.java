package com.dolphin.platform.dto;

import lombok.Data;

@Data
public class UserRequestDto {
	private String username;
    private String password;
    private String fullname;
	private String mobile;
}
