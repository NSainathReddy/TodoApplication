package com.myFirst.springBoot.myFirstWebApp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@Service
@SessionAttributes("name")
public class TodoService {
	
	private static List<Todo> todos = new ArrayList<>();
	
	private static int count = 0;
	
	static {
		todos.add(new Todo(++count, "Sainath", "Get AWS Certified", LocalDate.now().plusYears(1), false));
		todos.add(new Todo(++count, "Sainath", "Learn Devops", LocalDate.now().plusYears(2), false));
		todos.add(new Todo(++count, "Sainath", "Learn Full Stack Development", LocalDate.now().plusYears(3), false));
	}
	
	public List<Todo> findByUsername(String username) {
		Predicate<? super Todo> predicate = todo->todo.getUsername().equalsIgnoreCase(username);
		return todos.stream().filter(predicate).toList();
	}
	
	public void addTodo(String username,String des,LocalDate date,boolean done) {
		Todo t = new Todo(++count,username,des,date,done);
		todos.add(t);
	}
	
	public void deleteById(int id) {
		Predicate<? super Todo> predicate = todo->todo.getId()==id;
		todos.removeIf(predicate);
	}

	public Todo findById(int id) {
		Predicate<? super Todo> predicate = todo->todo.getId()==id;
		Todo todo = todos.stream().filter(predicate).findFirst().get();
		return todo;
	}

	public void updateTodos(@Valid Todo todo) {
		deleteById(todo.getId());
		todos.add(todo);
	}
	
}
