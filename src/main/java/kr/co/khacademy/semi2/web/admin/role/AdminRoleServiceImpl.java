package kr.co.khacademy.semi2.web.admin.role;

import kr.co.khacademy.semi2.common.DataSourceUtils;
import kr.co.khacademy.semi2.dao.RoleDao;
import kr.co.khacademy.semi2.model.Role;
import kr.co.khacademy.semi2.web.admin.role.add.AdminRoleAddRequest;
import kr.co.khacademy.semi2.web.admin.role.item.AdminRoleItemRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdminRoleServiceImpl implements AdminRoleService {

    private static final RoleDao roleDao = RoleDao.getInstance();

    private static class AdminRoleServiceImplLazyHolder {
        static final AdminRoleServiceImpl instance = new AdminRoleServiceImpl();
    }

    public static AdminRoleServiceImpl getInstance() {
        return AdminRoleServiceImplLazyHolder.instance;
    }

    @Override
    public long add(AdminRoleAddRequest adminRoleAddRequest) {
        Role role = adminRoleAddRequest.toRole();
        try (Connection connection = DataSourceUtils.getConnection()) {
            long id = roleDao.create(role, connection);
            connection.commit();
            return id;
        } catch (SQLException e) {
            throw new AdminRoleException(e);
        }
    }

    @Override
    public Role getItem(AdminRoleItemRequest adminRoleItemRequest) {
        try (Connection connection = DataSourceUtils.getConnection()) {
            long id = adminRoleItemRequest.getId();
            return roleDao.read(id, connection);
        } catch (SQLException e) {
            throw new AdminRoleException(e);
        }
    }
}
