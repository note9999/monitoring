package com.example.searchbasic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/search")
    public SearchKeywordDto search(@RequestBody String keyword) {
        log.error("[SearchController.search] keyword = {}", keyword);
        return searchService.save(keyword);
    }

}