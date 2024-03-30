package ru.geegbrain.hw8Sem8SpringAOP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geegbrain.hw8Sem8SpringAOP.aspects.TrackUserAction;
import ru.geegbrain.hw8Sem8SpringAOP.model.Task;
import ru.geegbrain.hw8Sem8SpringAOP.repository.TaskRepository;

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
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    // Получение всех задач
    @TrackUserAction
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Создание новой задачи
    @TrackUserAction
    public Task createTask(Task task) {
        task.setCreationDate(new Date());
        task.setStatus("Pending");
        return taskRepository.save(task);
    }

    // Получение задачи по ID
    @TrackUserAction
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Обновление задачи по ID
    @TrackUserAction
    public Task updateTask(Long id, Task task) {
        Optional<Task> existingTaskOptional = taskRepository.findById(id);
        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();
            existingTask.setDescription(task.getDescription());
            existingTask.setStatus(task.getStatus());
            return taskRepository.save(existingTask);
        } else {
            throw new IllegalArgumentException("Task with id " + id + " not found");
        }
    }

    // Удаление задачи по ID
    @TrackUserAction
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
