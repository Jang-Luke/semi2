package kr.co.khacademy.semi2.web.admin.role;

import kr.co.khacademy.semi2.common.DataSourceUtils;
import kr.co.khacademy.semi2.web.admin.role.add.AdminRoleAddRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class AdminRoleServiceImplTest {

    private static final AdminRoleService adminRoleService = AdminRoleServiceImpl.getInstance();

    @BeforeAll
    public void setup() throws SQLException {
        try (
            Connection connection = DataSourceUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS role" +
                    "(`id` BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    " `name` VARCHAR(255) NOT NULL," +
                    " `permissions` TEXT NOT NULL)"
            )
        ) {
            preparedStatement.executeUpdate();
        }
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("추가 성공")
    @Order(1)
    public void testAddition(AdminRoleAddRequest adminRoleAddRequest, long expected) {
        long id = adminRoleService.add(adminRoleAddRequest);
        assertThat(id).isEqualTo(expected);
    }

    public static Stream<Arguments> testAddition() {
        return Stream.of(
            arguments(
                AdminRoleAddRequest.builder()
                    .name("1234")
                    .permissions(new String[]{"admin-page"})
                    .build(),
                1L
            ),
            arguments(
                AdminRoleAddRequest.builder()
                    .name("1234")
                    .permissions(new String[]{"admin-page", "user"})
                    .build(),
                2L
            )
        );
    }

    @AfterAll
    public void cleanup() throws SQLException {
        try (
            Connection connection = DataSourceUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                "DROP TABLE IF EXISTS role"
            )
        ) {
            preparedStatement.executeUpdate();
        }
    }
}