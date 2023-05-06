package kr.co.khacademy.semi2.web.admin.role.add;

import kr.co.khacademy.semi2.model.Role;
import kr.co.khacademy.semi2.web.admin.role.AdminRoleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestMethodOrder(OrderAnnotation.class)
class AdminRoleAddRequestTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("유효성 검사 성공")
    @Order(1)
    public void testValidation(String name, String[] permissions) {
        AdminRoleAddRequest.builder()
            .name(name)
            .permissions(permissions)
            .build();
    }

    public static Stream<Arguments> testValidation() {
        return Stream.of(
            arguments("1234", new String[]{"admin-page"}),
            arguments("abcd", new String[]{"admin-page"}),
            arguments("가나다라", new String[]{"admin-page"}),
            arguments("_abc", new String[]{"admin-page"}),
            arguments("abc_", new String[]{"admin-page"}),
            arguments("____", new String[]{"admin-page"}),
            arguments("가나다라", new String[]{"ADMIN-PAGE"}),
            arguments("____", new String[]{"admin-page", "role", "user"}),
            arguments("____", new String[]{"admin-page", "role", "user", "user"}),
            arguments("1".repeat(255), new String[]{"admin-page"})
            );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("유효성 검사 실패 - 이름 길이")
    @Order(2)
    public void testValidationFailureWithInvalidNameLength(String name, String[] permissions) {
        assertThatThrownBy(() -> AdminRoleAddRequest.builder()
            .name(name)
            .permissions(permissions)
            .build())
            .isInstanceOf(AdminRoleException.class);
    }

    public static Stream<Arguments> testValidationFailureWithInvalidNameLength() {
        return Stream.of(
            arguments("123", new String[]{"admin-page"}),
            arguments("1".repeat(256), new String[]{"admin-page"})
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("유효성 검사 실패 - 이름 정규식")
    @Order(3)
    public void testValidationFailureWithInvalidNameRegex(String name, String[] permissions) {
        assertThatThrownBy(() -> AdminRoleAddRequest.builder()
            .name(name)
            .permissions(permissions)
            .build())
            .isInstanceOf(AdminRoleException.class);
    }

    public static Stream<Arguments> testValidationFailureWithInvalidNameRegex() {
        return Stream.of(
            arguments("abcd#", new String[]{"admin-page"}),
            arguments("abcdㅏ", new String[]{"admin-page"}),
            arguments("abcd@", new String[]{"admin-page"}),
            arguments("abc@d", new String[]{"admin-page"})
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("유효성 검사 실패 - 권한 이름")
    @Order(4)
    public void testValidationFailureWithInvalidPermissionName(String name, String[] permissions) {
        assertThatThrownBy(() -> AdminRoleAddRequest.builder()
            .name(name)
            .permissions(permissions)
            .build())
            .isInstanceOf(AdminRoleException.class);
    }

    public static Stream<Arguments> testValidationFailureWithInvalidPermissionName() {
        return Stream.of(
            arguments("1234", new String[]{"a"}),
            arguments("1234", new String[]{"adminpage"}),
            arguments("1234", new String[]{"admin_page"}),
            arguments("1234", new String[]{"aadmin-page"}),
            arguments("1234", new String[]{"admin-pagee"}),
            arguments("1234", new String[]{"admin-page", "userr"})
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Role 변환 성공")
    @Order(5)
    public void testConversionToRole(AdminRoleAddRequest adminRoleAddRequest, Role expected) {
        assertThat(adminRoleAddRequest.toRole()).isEqualTo(expected);
    }

    public static Stream<Arguments> testConversionToRole() {
        return Stream.of(
            arguments(
                AdminRoleAddRequest.builder()
                    .name("1234")
                    .permissions(new String[]{"admin-page"})
                    .build(),
                Role.builder()
                    .name("1234")
                    .permissions("admin-page")
                    .build()
            ),
            arguments(
                AdminRoleAddRequest.builder()
                    .name("1234")
                    .permissions(new String[]{"admin-page", "user"})
                    .build(),
                Role.builder()
                    .name("1234")
                    .permissions("admin-page:user")
                    .build()
            )
        );
    }
}