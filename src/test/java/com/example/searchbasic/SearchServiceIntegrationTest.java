package com.example.searchbasic;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SearchServiceIntegrationTest {

    @Autowired
    private SearchService searchService;

    @Autowired
    private SearchKeywordRepository searchKeywordRepository;

    private final String existKeyword = "존재하는 검색어";

    private final Long existSearchCnt = 22L;

    @BeforeEach

    void setUp() {
        SearchKeywordRepository.deleteAll();
        SearchKeywordRepository.save(new SearchKeyword(existKeyword, existSearchCnt));

    }
    @Test
    @DisplayName("존재하는 키워드가 검색되는 케이스")
    void exist_keyword() {
        //when
        SearchKeywordDto res = searchService.save(existKeyword);

        //then
        Assertions.assertEquals(existSearchCnt + 1, res.getSearchCnt());

    }


}
