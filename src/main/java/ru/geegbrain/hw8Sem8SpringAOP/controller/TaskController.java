package ru.geegbrain.hw8Sem8SpringAOP.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geegbrain.hw8Sem8SpringAOP.model.Task;
import ru.geegbrain.hw8Sem8SpringAOP.service.TaskService;

import java.util.List;

/**
 * Класс TaskController представляет собой контроллер для обработки HTTP-запросов связанных с задачами.
 * Здесь определены методы для обработки запросов и вызова соответствующих методов сервисного слоя.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Получение списка всех задач
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Создание новой задачи
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // Получение задачи по ID
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id).orElse(null);
    }

    // Обновление задачи по ID
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    // Удаление задачи по ID
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
