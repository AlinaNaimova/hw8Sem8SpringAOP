package ru.geegbrain.hw5sem5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geegbrain.hw5sem5.model.Task;
import ru.geegbrain.hw5sem5.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * Класс TaskService представляет собой сервисный слой для управления задачами.
 * Здесь определены методы для выполнения бизнес-логики над задачами.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Получение всех задач
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Создание новой задачи
    public Task createTask(Task task) {
        task.setCreationDate(new Date());
        task.setStatus("Pending");
        return taskRepository.save(task);
    }

    // Получение задачи по ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Обновление задачи по ID
    public Task updateTask(Long id, Task task) {
        Optional<Task> existingTaskOptional = taskRepository.findById(id);
        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();
            existingTask.setDescription(task.getDescription());
            existingTask.setStatus(task.getStatus());
            return taskRepository.save(existingTask);
        } else {
            return null;
        }
    }

    // Удаление задачи по ID
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
