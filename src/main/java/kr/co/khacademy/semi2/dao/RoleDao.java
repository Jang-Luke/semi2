package kr.co.khacademy.semi2.dao;

import kr.co.khacademy.semi2.model.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoleDao {

    private static class RoleDaoLazyHolder {
        static final RoleDao instance = new RoleDao();
    }

    public static RoleDao getInstance() {
        return RoleDaoLazyHolder.instance;
    }

    public long create(Role role, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
            "INSERT INTO role VALUES (DEFAULT, ?, ?)",
            PreparedStatement.RETURN_GENERATED_KEYS
        )) {
            preparedStatement.setString(1, role.getName());
            preparedStatement.setString(2, role.getPermission());

            if (preparedStatement.executeUpdate() == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }
        }
        throw new SQLException("An exception occurred while executing SQL query");
    }

    public Role read(long id, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT * FROM role WHERE id = ?"
        )) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }
        }
        throw new SQLException("An exception occurred while executing SQL query");
    }

    private Role mapRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        String permissions = resultSet.getString(3);

        return Role.builder()
            .id(id)
            .name(name)
            .permissions(permissions)
            .build();
    }
}
