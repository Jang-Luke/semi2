package kr.co.khacademy.semi2.model;

import lombok.Builder;
import lombok.Value;

@Value
public class Criteria {

    long page;
    long limit;
    String searchField;
    String keyword;

    @Builder
    private static Criteria of(
        long page,
        long limit,
        String searchField,
        String keyword
    ) {
        return new Criteria(
            page,
            limit,
            searchField,
            keyword
        );
    }
}
