package com.macroz.medalnetserver.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResDTO {
	private String token;

	public RegisterResDTO(String token) {
		this.token = token;
	}
}
