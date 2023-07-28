package com.example.todo.core;

import org.springframework.data.annotation.Id;

public record TodoItem(@Id Long id, String name, Boolean completed) {
}
