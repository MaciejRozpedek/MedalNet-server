package com.macroz.medalnetserver.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResDTO {
	private String token;

	public LoginResDTO(String token) {
		this.token = token;
	}
}
