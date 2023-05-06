package kr.co.khacademy.semi2.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum RoleSearchField {

    NAME("name");

    private final String value;

    public static Optional<RoleSearchField> of(String value) {
        return Arrays.stream(values())
            .filter(roleSearchField -> roleSearchField.value.equals(value.toLowerCase()))
            .findAny();
    }
}
