package ru.skillbox.springlemmaexample.config;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Аннотация @Configuration означает, что в данном классе содержаться
 * методы, порождающие объекты, которые в дальнейшем могут использовать
 * другие классы. Здесь создадим нужный объект для работы с лемматизатором.
 * И если потребуется во всем приложении, заменить объект лемматизатор, то
 * достаточно будет изменить генерацию лемматизатора в этом классе.
 * <p>
 * Другим классам спринга, достаточно внедрить зависимость нужного типа,
 * и спринг автоматически найдет метод, создающий бин нужного типа.</p>
 */
@Configuration
public class LemmaConfiguration {

    /**
     * @return объект с русской морфологией
     * @throws IOException (если файл со словарем не найден)
     *                     <p>
     *                     Аннотация @Bean означает, что данный метод используется,
     *                     когда спрингу надо внедрить в другой класс спринга зависимость
     *                     типа LuceneMorphology</p>
     *                     <p>
     *                     С помощью данного подхода, можно внедрять в Spring классы не
     *                     входящие в контекст спринга, классы из библиотек или классы
     *                     содержащие бизнес логику
     *                     </p>
     */
    @Bean
    public LuceneMorphology luceneMorphology() throws IOException {
        return new RussianLuceneMorphology();
    }
}
