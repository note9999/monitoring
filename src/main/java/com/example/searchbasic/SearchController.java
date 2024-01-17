package com.example.searchbasic;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService){

        this.searchService = searchService;
    }

    @PostMapping("/search")
    public SearchKeywordDto search(String keyword) {
//      return searchService.save(keyword);
        log.info("[SearchController.search] keyword = {}", keyword);
        return null;
    }

}
