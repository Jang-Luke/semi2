package kr.co.khacademy.semi2.web.admin.role.list;

import kr.co.khacademy.semi2.web.admin.role.AdminRoleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminRoleListRequestTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("유효성 검사 성공")
    @Order(1)
    public void testValidation(
        String page,
        String limit,
        String roleSearchField,
        String keyword
    ) {
        AdminRoleListRequest.builder()
            .page(page)
            .limit(limit)
            .roleSearchField(roleSearchField)
            .keyword(keyword)
            .build();
    }

    public static Stream<Arguments> testValidation() {
        return Stream.of(
            arguments("1", "10", "name", "123"),
            arguments("10", "100", "name", "123"),
            arguments("10", "100", "name", null),
            arguments("10", "100", null, null),
            arguments("10", null, null, null),
            arguments(null, null, null, null),
            arguments("1", "10", "NAME", "123")
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("유효성 검사 실패 - 페이지")
    @Order(2)
    public void testValidationFailureWithInvalidPage(
        String page,
        String limit,
        String roleSearchField,
        String keyword
    ) {
        assertThatThrownBy(() -> AdminRoleListRequest.builder()
            .page(page)
            .limit(limit)
            .roleSearchField(roleSearchField)
            .keyword(keyword)
            .build())
            .isInstanceOf(AdminRoleException.class);
    }

    public static Stream<Arguments> testValidationFailureWithInvalidPage() {
        return Stream.of(
            arguments("0", null, null, null),
            arguments("-1", null, null, null),
            arguments("A", null, null, null),
            arguments("0A", null, null, null),
            arguments("", null, null, null)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("유효성 검사 실패 - 항목 제한")
    @Order(3)
    public void testValidationFailureWithInvalidLimit(
        String page,
        String limit,
        String roleSearchField,
        String keyword
    ) {
        assertThatThrownBy(() -> AdminRoleListRequest.builder()
            .page(page)
            .limit(limit)
            .roleSearchField(roleSearchField)
            .keyword(keyword)
            .build())
            .isInstanceOf(AdminRoleException.class);
    }

    public static Stream<Arguments> testValidationFailureWithInvalidLimit() {
        return Stream.of(
            arguments(null, "0", null, null),
            arguments(null, "-1", null, null),
            arguments(null, "A", null, null),
            arguments(null, "0A", null, null),
            arguments(null, "", null, null)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("유효성 검사 실패 - 역할 검색 필드")
    @Order(4)
    public void testValidationFailureWithInvalidRoleSearchField(
        String page,
        String limit,
        String roleSearchField,
        String keyword
    ) {
        assertThatThrownBy(() -> AdminRoleListRequest.builder()
            .page(page)
            .limit(limit)
            .roleSearchField(roleSearchField)
            .keyword(keyword)
            .build())
            .isInstanceOf(AdminRoleException.class);
    }

    public static Stream<Arguments> testValidationFailureWithInvalidRoleSearchField() {
        return Stream.of(
            arguments(null, null, "test", null)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("유효성 검사 실패 - 키워드")
    @Order(5)
    public void testValidationFailureWithInvalidKeyword(
        String page,
        String limit,
        String roleSearchField,
        String keyword
    ) {
        assertThatThrownBy(() -> AdminRoleListRequest.builder()
            .page(page)
            .limit(limit)
            .roleSearchField(roleSearchField)
            .keyword(keyword)
            .build())
            .isInstanceOf(AdminRoleException.class);
    }

    public static Stream<Arguments> testValidationFailureWithInvalidKeyword() {
        return Stream.of(
            arguments(null, null, null, "12"),
            arguments(null, null, null, "1".repeat(256))
        );
    }
}