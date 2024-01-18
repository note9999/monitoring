package com.example.searchbasic;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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
        log.info("[SearchService.save] search count = {}", searchKeyword.getSearchCnt());

        return new SearchKeywordDto(searchKeywordRepository.save(searchKeyword));
    }

}