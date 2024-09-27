package com.macroz.medalnetserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "medals")
public class Medal implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String number;
	private String name;
	private String surname;
	@Column(name = "`rank`")
	private String rank;
	private String unit;
	@Column(nullable = true)
	private int year;
	private String notes;
	private Long userId;    // ID of user, who added this medal

	public Medal() {}

	public Medal(Long id,
				 String number,
				 String name,
				 String surname,
				 String rank,
				 String unit,
				 int year,
				 String notes,
				 Long userId) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.surname = surname;
		this.rank = rank;
		this.unit = unit;
		this.year = year;
		this.notes = notes;
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Medal{" +
				"id=" + id +
				", number=" + number +
				", name='" + name + '\'' +
				", surname='" + surname + '\'' +
				", rank='" + rank + '\'' +
				", unit='" + unit + '\'' +
				", year=" + year +
				", notes='" + notes + '\'' +
				", userId=" + userId +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Medal medal = (Medal) o;
		return year == medal.year &&
				Objects.equals(id, medal.id) &&
				Objects.equals(number, medal.number) &&
				Objects.equals(name, medal.name) &&
				Objects.equals(surname, medal.surname) &&
				Objects.equals(rank, medal.rank) &&
				Objects.equals(unit, medal.unit) &&
				Objects.equals(notes, medal.notes) &&
				Objects.equals(userId, medal.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, number, name, surname, rank, unit, year, notes, userId);
	}
}
