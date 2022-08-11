package com.hardieahmed.springbootmongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hardieahmed.springbootmongodb.exception.TodoCollectionException;
import com.hardieahmed.springbootmongodb.model.TodoDTO;
import com.hardieahmed.springbootmongodb.repository.TodoRepository;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepository;
	
	@Override
	public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException {
		
		Optional<TodoDTO> todoOptional = todoRepository.findByTodo(todo.getTodo());
		
		if (todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
		} else {
			todo.setCreatedAt(new Date(System.currentTimeMillis()));
			todoRepository.save(todo);
		}
		
	}

	@Override
	public List<TodoDTO> getAllTodos() {
		List<TodoDTO> todos = todoRepository.findAll();
		
		if (todos.size() > 0) {
			return todos;
		}
		else {
			return new ArrayList<TodoDTO>();
		}
	}

	@Override
	public TodoDTO getTodo(String id) throws TodoCollectionException {
		Optional<TodoDTO> optionalTodo = todoRepository.findById(id);
		if (!optionalTodo.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		} else {
			return optionalTodo.get();
		}
	}

	@Override
	public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
		Optional<TodoDTO> todoWithId = todoRepository.findById(id);
		Optional<TodoDTO> todoOfSameName = todoRepository.findByTodo(todo.getTodo());
		
		if (todoWithId.isPresent()) {
			if(todoOfSameName.isPresent() && !todoOfSameName.get().getId().equals(id)) {
				throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
			}
			
			TodoDTO todoToUpdate = todoWithId.get();
			
			todoToUpdate.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoToUpdate.getCompleted());
			todoToUpdate.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToUpdate.getTodo());
			todoToUpdate.setDescription(todo.getDescription() != null ? todo.getDescription() : todoToUpdate.getDescription());
			todoToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
			
			todoRepository.save(todoToUpdate);
		} else {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}
		
	}

	@Override
	public void deleteTodoById(String id) throws TodoCollectionException {
		Optional<TodoDTO> todoOptional = todoRepository.findById(id);
		
		if (!todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		} else {
			todoRepository.deleteById(id);
		}
	}

}
