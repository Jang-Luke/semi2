package kr.co.khacademy.semi2.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum Permission {

    ADMIN_PAGE("admin-page"),
    ROLE("role"),
    USER("user"),
    PRODUCT("product"),
    REPLY("reply"),
    ANNOUNCEMENT("announcement"),
    INQUIRY("inquiry"),
    FAQ("faq");

    private final String value;

    public static Optional<Permission> of(String value) {
        return Arrays.stream(values())
            .filter(permission -> permission.value.equals(value.toLowerCase()))
            .findAny();
    }
}