package com.example.todo.controllers;

import com.example.todo.core.TodoItem;
import com.example.todo.core.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoController {

    private final TodoItemRepository todoItemRepository;

    @Autowired
    public TodoController(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("items", todoItemRepository.findAll());
        return "todo";
    }

    @PostMapping("/")
    public String addToTable(@RequestParam String itemName, Model model) {
        TodoItem item = todoItemRepository.save(new TodoItem(null, itemName, false));
        model.addAttribute("item", item);
        return "todo :: todo-line";
    }

    @PostMapping("/completed/{itemId}")
    public String completedItem(@PathVariable Long itemId, Model model) {
        todoItemRepository.findById(itemId)
                .ifPresent(item -> model.addAttribute(
                        "item",
                        todoItemRepository.save(new TodoItem(item.id(), item.name(), !item.completed()))
                ));
        return "todo :: todo-line";
    }

}
