package kr.co.khacademy.semi2.web.admin.role.item;

import kr.co.khacademy.semi2.web.admin.role.AdminRoleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestMethodOrder(OrderAnnotation.class)
class AdminRoleItemRequestTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("유효성 검사 성공")
    @Order(1)
    public void testValidation(String id) {
        AdminRoleItemRequest.builder()
            .id(id)
            .build();
    }

    public static Stream<Arguments> testValidation() {
        return Stream.of(
            arguments("1"),
            arguments("01")
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("유효성 검사 실패 - ID 범위")
    @Order(2)
    public void testValidationFailureWithInvalidIdRange(String id) {
        assertThatThrownBy(() -> AdminRoleItemRequest.builder()
            .id(id)
            .build())
            .isInstanceOf(AdminRoleException.class);
    }

    public static Stream<Arguments> testValidationFailureWithInvalidIdRange() {
        return Stream.of(
            arguments("0"),
            arguments("-1"),
            arguments("A"),
            arguments("0A"),
            arguments("")
        );
    }
}