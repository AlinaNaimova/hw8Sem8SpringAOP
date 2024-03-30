package ru.geegbrain.hw8Sem8SpringAOP.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import ru.geegbrain.hw8Sem8SpringAOP.model.Task;
import ru.geegbrain.hw8Sem8SpringAOP.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TaskServiceIntegrationTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Создание новой задачи")
    public void testCreateTask() {
        // Создание заглушки для taskRepository
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);

        // Создание экземпляра TaskService с использованием заглушки taskRepository
        TaskService taskService = new TaskService(taskRepository);

        // Настройка поведения заглушки для метода save
        Mockito.when(taskRepository.save(ArgumentMatchers.any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            savedTask.setId(1L); // Присваиваем id задачи
            return savedTask;
        });

        // Вызов метода createTask
        Task task = new Task(); // Создание объекта Task без аргументов
        task.setTitle("Test task");
        task.setDescription("Test description");
        Task savedTask = taskService.createTask(task);

        // Проверка сохраненной задачи
        assertNotNull(savedTask);
        assertEquals(1L, savedTask.getId());
    }

    @Test
    @DisplayName("Получение всех задач")
    public void testGetAllTasks() {
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        TaskService taskService = new TaskService(taskRepository);

        List<Task> tasks = new ArrayList<>();

        Task task1 = new Task();
        task1.setTitle("Задача 1");

        Task task2 = new Task();
        task2.setTitle("Задача 2");

        tasks.add(task1);
        tasks.add(task2);

        Mockito.when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> allTasks = taskService.getAllTasks();

        assertNotNull(allTasks);
        assertEquals(2, allTasks.size());
    }

    @Test
    @DisplayName("Получение задачи по айди")
    public void testGetTaskById() {
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        TaskService taskService = new TaskService(taskRepository);

        Long taskId = 1L;
        Task task = new Task(); // Создание объекта Task без аргументов
        task.setTitle("Test task");
        task.setDescription("Test description");
        task.setId(taskId);
        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Optional<Task> foundTaskOptional = taskService.getTaskById(taskId);

        assertNotNull(foundTaskOptional);
        assertTrue(foundTaskOptional.isPresent());
        assertEquals(taskId, foundTaskOptional.get().getId());
    }

    @Test
    @DisplayName("Обновление задачи")
    public void testUpdateTask() {
        // Создаем новую задачу
        Task newTask = new Task();
        newTask.setDescription("Новая задача");
        newTask.setStatus("TODO");
        newTask.setCreationDate(new Date());
        Task savedTask = taskRepository.save(newTask);

        // Обновляем описание и статус задачи
        Task updatedTask = new Task();
        updatedTask.setId(savedTask.getId());
        updatedTask.setDescription("Обновленная задача");
        updatedTask.setStatus("IN_PROGRESS");
        updatedTask.setCreationDate(savedTask.getCreationDate());

        // Вызываем метод updateTask
        Task result = taskService.updateTask(savedTask.getId(), updatedTask);

        // Проверяем, что задача была успешно обновлена
        assertEquals(updatedTask.getDescription(), result.getDescription());
        assertEquals(updatedTask.getStatus(), result.getStatus());
    }

    @Test
    @DisplayName("Проверка на исключение при попытке обновления несуществующей задачи")
    public void testUpdateTaskWithNonExistingId() {
        // Пытаемся обновить задачу с несуществующим ID
        Task updatedTask = new Task();
        updatedTask.setId(999L); // Несуществующий ID
        updatedTask.setDescription("Обновленная задача");
        updatedTask.setStatus("IN_PROGRESS");

        // Проверяем, что метод выбрасывает исключение при попытке обновления несуществующей задачи
        assertThrows(IllegalArgumentException.class, () -> taskService.updateTask(999L, updatedTask));
    }

    @Test
    @DisplayName("Удаление задачи по айди")
    public void testDeleteTask() {
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        TaskService taskService = new TaskService(taskRepository);

        Long taskId = 1L;

        taskService.deleteTask(taskId);

        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(taskId);
    }
}