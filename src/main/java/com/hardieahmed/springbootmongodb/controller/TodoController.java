package com.hardieahmed.springbootmongodb.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hardieahmed.springbootmongodb.exception.TodoCollectionException;
import com.hardieahmed.springbootmongodb.model.TodoDTO;
import com.hardieahmed.springbootmongodb.service.TodoService;


@RestController
public class TodoController {
		
	@Autowired
	private TodoService todoService;
	
	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos() {
		List<TodoDTO> todos = todoService.getAllTodos();	
		return new ResponseEntity<List<TodoDTO>>(todos, todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> getTodo(@PathVariable("id") String id) {
		try {
			return new ResponseEntity<>(todoService.getTodo(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {
		try {
			todoService.createTodo(todo);
			return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("/todos/{id}")
	public ResponseEntity<?> getTodo(@PathVariable("id") String id, @RequestBody TodoDTO todo) {
		try {
			todoService.updateTodo(id, todo);
			return new ResponseEntity<>(todoService.getTodo(id), HttpStatus.OK);
		} catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable("id") String id) {	
		try {
			todoService.deleteTodoById(id);
			return new ResponseEntity<>("Record of id " + id + " deleted successfully", HttpStatus.OK);
		} catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
}
