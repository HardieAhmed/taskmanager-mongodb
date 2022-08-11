package com.hardieahmed.springbootmongodb.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="todos")
public class TodoDTO {
	
	@Id
	private String id;
	
	@NotBlank(message = "Todo cannot be blank")
	private String todo;
	
	@NotBlank(message = "Description cannot be blank")
	private String description;
	
	@NotNull(message = "Completed cannot be blank")
	private Boolean completed;
	
	private Date createdAt;
	
	private Date updatedAt;
}
