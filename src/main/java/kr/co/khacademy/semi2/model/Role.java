package kr.co.khacademy.semi2.model;

import lombok.Builder;
import lombok.Value;

@Value
public class Role {

    public static final String DELIMITER = ":";

    long id;
    String name;
    String permission;

    @Builder
    private static Role of(
        long id,
        String name,
        String permissions
    ) {
        return new Role(
            id,
            name,
            permissions
        );
    }
}
