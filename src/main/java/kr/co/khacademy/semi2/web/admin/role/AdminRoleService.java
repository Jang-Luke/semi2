package kr.co.khacademy.semi2.web.admin.role;

import kr.co.khacademy.semi2.model.Role;
import kr.co.khacademy.semi2.web.admin.role.add.AdminRoleAddRequest;
import kr.co.khacademy.semi2.web.admin.role.item.AdminRoleItemRequest;

public interface AdminRoleService {

    long add(AdminRoleAddRequest adminRoleAddRequest);

    Role getItem(AdminRoleItemRequest adminRoleItemRequest);
}
