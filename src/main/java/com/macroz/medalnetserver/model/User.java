package com.macroz.medalnetserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String username;
	private String password;
	private String email;
	private String base64profilePicture;

	public User() {}

	public User(String username, String password, String email, String base64profilePicture) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.base64profilePicture = base64profilePicture;
	}
}
