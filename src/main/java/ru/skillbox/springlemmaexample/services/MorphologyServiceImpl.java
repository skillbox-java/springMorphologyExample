package ru.skillbox.springlemmaexample.services;

import org.apache.lucene.morphology.LuceneMorphology;
import org.springframework.stereotype.Service;
import ru.skillbox.springlemmaexample.dto.SaveWordResponse;
import ru.skillbox.springlemmaexample.dto.SearchWordRequest;
import ru.skillbox.springlemmaexample.dto.WordResponse;
import ru.skillbox.springlemmaexample.dto.WordsListResponse;
import ru.skillbox.springlemmaexample.exceptions.WordNotFitToDictionaryException;
import ru.skillbox.springlemmaexample.model.WordEntity;
import ru.skillbox.springlemmaexample.repositories.WordRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MorphologyServiceImpl implements MorphologyService {
    private final LuceneMorphology luceneMorphology;
    private final WordRepository wordRepository;

    // спринг по типу аргумента, ищет в своем контексте объект типа и
    // сам подставляет в конструктор, на этапе создания объекта
    // объект создается в классе LemmaConfiguration.java
    public MorphologyServiceImpl(LuceneMorphology luceneMorphology, WordRepository wordRepository) {
        this.luceneMorphology = luceneMorphology;
        this.wordRepository = wordRepository;
    }

    /**
     * @param word слово
     * @return список морфологических форм
     */
    public List<String> morphologyForms(String word) {
        // проверяем, является ли строка словом и
        // принадлежит язык слова выбранному словарю
        // если слово не подходит - возвращаем пустой список
        if (!luceneMorphology.checkString(word)) {
            return Collections.emptyList();
        }

        return luceneMorphology.getMorphInfo(word);
    }

    /**
     * @param word слово для сохранения в базу
     *             <p>Для примера, полученный список переводится в строку
     *             и сохраняется в бд</p>
     *             <p>
     *             <a href="https://medium.com/@kirill.sereda/%D1%82%D1%80%D0%B0%D0%BD%D0%B7%D0%B0%D0%BA%D1%86%D0%B8%D0%B8-%D0%B2-spring-framework-a7ec509df6d2">
     *             подробнее про аннотацию @Transactional</a>
     *             </p>
     */
    @Override
    @Transactional
    public SaveWordResponse saveWord(String word) {
        if (!luceneMorphology.checkString(word)) {
            // если слово не подходит для морфологического анализа - бросаем исключение
            // такое исключение можно перехватить внутри Spring и создать специальный ответ
            // смотри exceptions/DefaultAdvice.java
            throw new WordNotFitToDictionaryException(word);
        }
        List<String> morphInfo = luceneMorphology.getMorphInfo(word);
        WordEntity newWord = createWordEntity(word, morphInfo);

        wordRepository.save(newWord);

        return createSaveWordResponse(word, morphInfo);
    }

    @Override
    public WordsListResponse searchWords(SearchWordRequest searchWordRequest) {
        List<WordEntity> wordEntityList = wordRepository.findAllContains(searchWordRequest.getQuery(), searchWordRequest.getLimit());

        List<WordResponse> wordResponseList = convertToResponse(wordEntityList);

        return new WordsListResponse(wordEntityList.size(), wordResponseList);
    }

    private List<WordResponse> convertToResponse(List<WordEntity> wordEntityList) {
        List<WordResponse> wordResponseList = new ArrayList<>();
        for (WordEntity wordEntity : wordEntityList) {
            WordResponse wordResponse = new WordResponse(wordEntity.getWord(),
                    wordEntity.getMorphologyInfo(),
                    wordEntity.getCount());
            wordResponseList.add(wordResponse);
        }
        return wordResponseList;
    }

    private SaveWordResponse createSaveWordResponse(String word, List<String> morphInfo) {
        SaveWordResponse saveWordResponse = new SaveWordResponse();
        saveWordResponse.setWord(word);
        saveWordResponse.setMorphInfo(morphInfo);
        return saveWordResponse;
    }

    private WordEntity createWordEntity(String word, List<String> morphInfo) {
        WordEntity newWord = new WordEntity();
        newWord.setWord(word);
        newWord.setMorphologyInfo(String.valueOf(morphInfo));
        return newWord;
    }

}
