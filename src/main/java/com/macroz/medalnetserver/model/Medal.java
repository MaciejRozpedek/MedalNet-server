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
	private String imageURL;
	private int year;

	public Medal() {}

	public Medal(String name, String category, String imageURL, int year) {
		this.name = name;
		this.category = category;
		this.imageURL = imageURL;
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

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
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
				", imageURL='" + imageURL + '\'' +
				", year=" + year +
				'}';
	}
}
