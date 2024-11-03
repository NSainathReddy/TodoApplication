package com.myFirst.springBoot.myFirstWebApp.todo;

import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

//@Controller
public class TodoController {
	
	private TodoService todoService;
	
	public TodoController(TodoService todoService) {
		super();
		this.todoService = todoService;
	}

	// /list-todos
	@RequestMapping("/list-todos")
	public String listAllTodos(ModelMap model) {
		String username = getLoggedInUserName();
		model.put("todos", todoService.findByUsername(username));
		return "listTodos";
	}

	@RequestMapping(value = "/add-todo", method=RequestMethod.GET)
	public String showNewTodo(ModelMap model) {
		Todo todo = new Todo(0,getLoggedInUserName(),"",LocalDate.now().plusYears(1),false);
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value = "/add-todo", method=RequestMethod.POST)
	public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if(result.hasErrors())
			return "todo";
		todoService.addTodo(getLoggedInUserName(),todo.getDescription(),todo.getTargetDate(),false);
		return "redirect:list-todos";
	}
	
	@RequestMapping("/delete-todo")
	public String deleteTodos(@RequestParam int id) {
		todoService.deleteById(id);
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="/update-todo", method=RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id,ModelMap model) {
		Todo todo = todoService.findById(id);
		model.addAttribute("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value = "/update-todo", method=RequestMethod.POST)
	public String showUpdateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if(result.hasErrors())
			return "todo";
		String username = getLoggedInUserName();
		todo.setUsername(username);
		todoService.updateTodos(todo);
		return "redirect:list-todos";
	}
	
	private String getLoggedInUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
}
