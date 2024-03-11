package com.macroz.medalnetserver.model;

import jakarta.persistence.*;

import java.io.Serializable;

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

	public Medal() {}

	public Medal(String name, String category, String base64Image, int year) {
		this.name = name;
		this.category = category;
		this.base64Image = base64Image;
		this.year = year;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Medal{" +
				"id=" + id +
				", name='" + name + '\'' +
				", category='" + category + '\'' +
				", imageURL='" + base64Image + '\'' +
				", year=" + year +
				'}';
	}
}
