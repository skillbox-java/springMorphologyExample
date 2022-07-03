package ru.skillbox.springlemmaexample.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.springlemmaexample.dto.OneWordLemmaRequest;
import ru.skillbox.springlemmaexample.dto.SaveWordResponse;
import ru.skillbox.springlemmaexample.dto.SearchWordRequest;
import ru.skillbox.springlemmaexample.dto.WordsListResponse;
import ru.skillbox.springlemmaexample.services.MorphologyService;

import java.util.List;

// тело ответа будет конвертироваться в JSON формат
// запрос будет ожидать JSON в теле запроса
@RestController
// все запросы в этом классе будут начинаться с /api/lemma
@RequestMapping("/api/lemma/")
public class LemmaController {
    private final MorphologyService morphologyService;

    public LemmaController(MorphologyService morphologyService) {
        this.morphologyService = morphologyService;
    }

    /**
     * @param oneWordLemmaRequest запрос ожидает в теле запроса
     *                            данные соответсвующее классу OneWordLemmaRequest
     * @return возвращает ответ в виде объекта LemmasResponse
     * <p>
     * метод принимает запросы POST с адресом /api/lemma/info
     * <p>
     * пример тела запроса JSON
     * {"name":"брат"}
     * <p>
     * Зачем используется ResponseEntity? данный класс - обертка над ответом, позволяет
     * модифицировать параметры ответа, например, http статус ответа.
     */
    @PostMapping("/info")
    public ResponseEntity<List<String>> lemma(@RequestBody OneWordLemmaRequest oneWordLemmaRequest) {
        // через статический метод ok() можно короче создавать ответ.
        return ResponseEntity.ok(morphologyService.morphologyForms(oneWordLemmaRequest.getWord()));
    }

    /**
     * @param oneWordLemmaRequest слово, которое будет сохранено в бд
     */
    @PostMapping("/save")
    public ResponseEntity<SaveWordResponse> saveWordInfo(@RequestBody OneWordLemmaRequest oneWordLemmaRequest) {
        SaveWordResponse saveWordResponse = morphologyService.saveWord(oneWordLemmaRequest.getWord());
        return new ResponseEntity<>(saveWordResponse, HttpStatus.CREATED);
    }

    /**
     * @param searchWordRequest данные для запроса
     */
    @PostMapping("/search")
    public ResponseEntity<WordsListResponse> searchWords(@RequestBody SearchWordRequest searchWordRequest) {
        WordsListResponse wordsListResponse = morphologyService.searchWords(searchWordRequest);
        return new ResponseEntity<>(wordsListResponse, HttpStatus.OK);
    }

}
