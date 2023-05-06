package kr.co.khacademy.semi2.web.admin.role.item;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.khacademy.semi2.model.Role;
import kr.co.khacademy.semi2.web.admin.role.AdminRoleService;
import kr.co.khacademy.semi2.web.admin.role.AdminRoleServiceImpl;

import java.io.IOException;

@WebServlet("/admin/role/item")
public class AdminRoleItemController extends HttpServlet {

    private static final AdminRoleService adminRoleService = AdminRoleServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminRoleItemRequest adminRoleItemRequest = AdminRoleItemRequest.of(req);
        Role role = adminRoleService.getItem(adminRoleItemRequest);
        req.setAttribute("role", role);
        req.getRequestDispatcher("/WEB-INF/views/admin/role/item.jsp").forward(req, resp);
    }
}
