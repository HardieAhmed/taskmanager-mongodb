package com.hardieahmed.springbootmongodb.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import com.hardieahmed.springbootmongodb.exception.TodoCollectionException;
import com.hardieahmed.springbootmongodb.model.TodoDTO;

public interface TodoService {
	public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;
	public List<TodoDTO> getAllTodos();
	public TodoDTO getTodo(String id) throws TodoCollectionException;
	public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException;
	public void deleteTodoById(String id) throws TodoCollectionException;
}
