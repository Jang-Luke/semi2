package kr.co.khacademy.semi2.web.admin.role.item;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.khacademy.semi2.web.admin.role.AdminRoleException;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

@Value
public class AdminRoleItemRequest {

    long id;

    @Builder
    private static AdminRoleItemRequest of(String id) {
        Long newId = Optional.ofNullable(id)
            .map(AdminRoleItemRequest::parseValidIdRange)
            .orElseThrow(() -> new AdminRoleException(
                String.format("Invalid id was entered: \"%s\"", id)));

        return new AdminRoleItemRequest(newId);
    }

    private static Long parseValidIdRange(String id) {
        try {
            long newId = Long.parseLong(id);
            if (0L < newId) {
                return newId;
            }
        } catch (NumberFormatException ignored) {
        }
        return null;
    }

    public static AdminRoleItemRequest of(HttpServletRequest httpServletRequest) {
        String id = httpServletRequest.getParameter("id");

        return AdminRoleItemRequest.of(id);
    }
}
