package kr.co.khacademy.semi2.web.admin.role.add;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.khacademy.semi2.model.Permission;
import kr.co.khacademy.semi2.web.admin.role.AdminRoleException;
import lombok.Builder;
import lombok.Value;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
public class AdminRoleAddRequest {

    String name;
    EnumSet<Permission> permissions;

    @Builder
    private static AdminRoleAddRequest of(String name, String[] permissions) {
        String newName = Optional.ofNullable(name)
            .filter(AdminRoleAddRequest::isValidNameLength)
            .filter(AdminRoleAddRequest::isValidNameRegex)
            .orElseThrow(() -> new AdminRoleException(
                String.format("Invalid name was entered: \"%s\"", name)));

        EnumSet<Permission> newPermissions = Optional.ofNullable(permissions)
            .map(Arrays::asList)
            .orElseThrow(() -> new AdminRoleException(
                String.format("Invalid permission was entered: \"%s\"", name)))
            .stream()
            .map(Permission::of)
            .map(permission -> permission.orElseThrow(() -> new AdminRoleException(
                String.format("Invalid value was entered: \"%s\"", name))))
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(Permission.class)));

        return new AdminRoleAddRequest(newName, newPermissions);
    }

    private static boolean isValidNameLength(String name) {
        return (3 < name.length()) && (name.length() < 256);
    }

    private static boolean isValidNameRegex(String name) {
        return name.matches("[A-Za-z_0-9가-힣]+");
    }

    public static AdminRoleAddRequest of(HttpServletRequest httpServletRequest) {
        String name = httpServletRequest.getParameter("name");
        String[] permissions = httpServletRequest.getParameterValues("permissions");

        return AdminRoleAddRequest.of(name, permissions);
    }
}
