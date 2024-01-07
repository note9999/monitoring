package com.example.searchbasic;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final SearchKeywordRepository searchKeywordRepository;

    public SearchService(SearchKeywordRepository searchKeywordRepository) {
        this.searchKeywordRepository = searchKeywordRepository;
    }
 @Transactional
    public SearchKeywordDto save(String keyword) {
        SearchKeyword searchKeyword = searchKeywordRepository.findById(keyword).orElse(new SearchKeyword(keyword,0L));
        searchKeyword.increaseSearchCnt();
        return new SearchKeywordDto(searchKeywordRepository.save(searchKeyword));
    }

}