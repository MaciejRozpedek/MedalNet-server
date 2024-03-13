package com.macroz.medalnetserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "medals")
public class Medal implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String category;
	private int year;
	private String base64Image;
	private Long userId;

	public Medal() {}

	public Medal(String name, String category, String base64Image, int year, Long userId) {
		this.name = name;
		this.category = category;
		this.base64Image = base64Image;
		this.year = year;
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Medal{" +
				"id=" + id +
				", name='" + name + '\'' +
				", category='" + category + '\'' +
				", imageURL='" + base64Image + '\'' +
				", year=" + year +
				", userId=" + userId +
				'}';
	}
}
