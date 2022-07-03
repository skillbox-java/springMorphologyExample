package ru.skillbox.springlemmaexample.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.springlemmaexample.model.WordEntity;

import java.util.List;

/**
 * Интерфейс для работы с БД, содержит базовые метод save(T), Optional<T> findById(ID id) и прочие
 * Возможно добавлять свои собственные запросы в формате HQL или SQL
 */
@Repository
public interface WordRepository extends JpaRepository<WordEntity, Long> {

    /**
     * @param wordPart часть слова
     * @param limit макс количество результатов
     * @return список подходящих слов
     *
     * <p>Для создания SQL запроса, необходимо указать nativeQuery = true</p>
     * <p>каждый параметр в SQL запросе можно вставить, используя запись :ИМЯ_ПЕРЕМEННОЙ
     * перед именем двоеточие, так hibernate поймет, что надо заменить на значение переменной</p>
     */
    @Query(value = "SELECT * from words where word LIKE %:wordPart% LIMIT :limit", nativeQuery = true)
    List<WordEntity> findAllContains(String wordPart, int limit);
}
