package ru.geegbrain.hw8Sem8SpringAOP.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geegbrain.hw8Sem8SpringAOP.model.Task;
import ru.geegbrain.hw8Sem8SpringAOP.repository.TaskRepository;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TaskServiceAnnotationTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Получение списка всех задач")
    public void testGetAllTasks() {
        // Создаем список задач для возврата
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());

        // Устанавливаем поведение репозитория при вызове метода findAll
        when(taskRepository.findAll()).thenReturn(tasks);

        // Вызываем метод getAllTasks и проверяем результат
        List<Task> result = taskService.getAllTasks();
        assertEquals(2, result.size());

    }

    @Test
    @DisplayName("Создание новой задачи")
    void testCreateTask() {
        Task task = new Task();
        task.setDescription("Test Task");
        task.setStatus("Pending");

        when(taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals("Pending", createdTask.getStatus());
    }

    @Test
    @DisplayName("Получение задачи по айди")
    void testGetTaskById() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setDescription("Test Task");
        task.setStatus("Pending");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskService.getTaskById(taskId);

        assertTrue(foundTask.isPresent());
        assertEquals("Test Task", foundTask.get().getDescription());
    }

    @Test
    @DisplayName("Обновление задачи")
    void testUpdateTask() {
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setDescription("Existing Task");
        existingTask.setStatus("Pending");

        Task updatedTask = new Task();
        updatedTask.setId(taskId);
        updatedTask.setDescription("Updated Task");
        updatedTask.setStatus("Completed");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.updateTask(taskId, updatedTask);

        assertNotNull(result);
        assertEquals("Updated Task", result.getDescription());
        assertEquals("Completed", result.getStatus());
    }

    @Test
    @DisplayName("Удаление задачи по айди")
    void testDeleteTask() {
        Long taskId = 1L;

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }



}

