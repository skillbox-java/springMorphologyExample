package ru.skillbox.springlemmaexample.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Все классы с аннотацией @Entity автоматически
 * ассоциируются с таблицей из БД к которой подключено
 * приложение
 */
@Entity(name = "words")
@NoArgsConstructor
@Getter
@Setter
public class WordEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    Long id;

    /**
     * nullable = поле не может быть null
     * unique = поле содержит уникальный значения
     * <p>
     * данные параметры задаются, если hibernate
     * создает таблицу, а не через миграции
     */
    @Column(nullable = false, unique = true)
    String word;

    String morphologyInfo;

    int count;
}
