package ru.geegbrain.hw8Sem8SpringAOP.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geegbrain.hw8Sem8SpringAOP.model.Task;

/**
 * Класс TaskRepository представляет собой репозиторий для работы с задачами.
 * Здесь определены методы для доступа к данным задач из базы данных.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
