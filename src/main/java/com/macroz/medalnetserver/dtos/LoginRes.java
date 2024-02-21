package com.macroz.medalnetserver.dtos;

import com.macroz.medalnetserver.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRes {
	private String token;
	private User user;

	public LoginRes(String token, User user) {
		this.token = token;
		this.user = user;
	}
}
