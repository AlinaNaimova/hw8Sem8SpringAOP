package ru.geegbrain.hw8Sem8SpringAOP.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

/**
 * Класс Task представляет собой модель задачи.
 * Здесь содержатся поля и методы для работы с задачами.
 */
@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String status;
    private Date creationDate;

}

