package com.macroz.medalnetserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "medal_history")
public class MedalHistory implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long medalId;  // ID of the medal

	@Lob
	private String medalJson;  // JSON representation of the medal

	private Long modifiedByUserId;  // ID of the user who modified the medal
	private LocalDateTime modifiedAt;  // Timestamp of when the change happened

	private String changeType;  // Type of change, e.g., "CREATE" , "UPDATE", "DELETE"
}
