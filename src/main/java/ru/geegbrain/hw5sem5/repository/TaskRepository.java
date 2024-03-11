package ru.geegbrain.hw5sem5.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.geegbrain.hw5sem5.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
