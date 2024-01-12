package com.example.searchbasic;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @InjectMocks
    private SearchService searchService;

    Mock
    private SearchKeywordRepository searchKeywordRepository;

    @Test
    @DisplayName("없는 검색 키워드가 검색되는 케이스")
    void not_exist_keyword(){
        String keyword = "없는 검색어";

        //given
        //given(searchKeywordRepository.findById(keyword).)
    }

}
