package com.example.searchbasic;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
class SearchServiceIntegrationTest {

    @Autowired
    private SearchService searchService;

    @Autowired
    private SearchKeywordRepository searchKeywordRepository;

    private final String existKeyword = "존재하는 검색어";

    private final Long existSearchCnt = 22L;

    @BeforeEach
        // @BeforeEach?
        // 테스트 메서드가 실행되기 전에 실행되는 메서드를 정의할 때 사용
        // 주로 초기화 작업을 수행하여 각 테스트 메서드가 독립적으로 실행될 수 있도록 함
        // 테스트 메서드 간에 서로 영향을 주지 않고 독립적으로 실행하기 위해 사용

    void setUp() {
        SearchKeywordRepository.deleteAll();
        SearchKeywordRepository.save(new SearchKeyword(existKeyword, existSearchCnt));

    }

    //searchService.save(existingKeyword)를 호출하여 검색어를 저장하고,
    // 그 결과로 반환된 SearchKeywordDto의 검색 횟수가 기대값과 일치하는지 확인
    @Test
    @DisplayName("존재하는 키워드가 검색되는 케이스")
    // @DisplayName -> 테스트 클래스 또는 테스트 메서드의 이름을 사용자가 지정한 문자열로 변경할 때 사용.
    //  테스트 결과 리포트에서 해당 테스트가 어떤 기능을 검증하는지 더 명확하게 보여주기 위해!!
    void exist_keyword() {
        //when
        SearchKeywordDto res = searchService.save(existKeyword);

        //then
        Assertions.assertEquals(existSearchCnt + 1, res.getSearchCnt());
        //Assertions.assertEquals(expected, actual): 두 값이 동일한지 확인하는 메서드
    }

    @Test
    @DisplayName("존재하는 키워드가 동시에 검색되는 케이스")
    void exist_keyword_concurrently() {

        AtomicReference<Throwable> e = new AtomicReference<>();
        //AtomicReference: Java에서 다중 스레드 환경에서 안전하게 값 변경하고 읽을 수 있게하는 클래스
        // 특정 데이터 타입의 값을 원자적(atomic)갱신 할 수 있도록 지원함.
        // 여러 스레드가 동시에 해당값을 수정하거나 읽어도 안전하게 작동!!

        //when
        CompletableFuture.allOf(
                //CompletableFuture
                CompletableFuture.runAsync(() -> searchService.save(existKeyword)),
                CompletableFuture.runAsync(() -> searchService.save(existKeyword))
        ).exceptionally(throwable -> {
            e.set(throwable.getCause());
            return null;
        }).join();

        //then
        Assertions.assertNotNull(e.get());
        Assertions.assertInstanceOf(ObjectOptimisticLockingFailureException.class, e.get());
        Assertions.assertEquals(existSearchCnt + 1, searchKeywordRepository.findById(existKeyword).get().getSearchCnt());

    }
}
