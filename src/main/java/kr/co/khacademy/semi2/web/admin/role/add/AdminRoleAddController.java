package kr.co.khacademy.semi2.web.admin.role.add;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.khacademy.semi2.web.admin.role.AdminRoleService;
import kr.co.khacademy.semi2.web.admin.role.AdminRoleServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/admin/role/add")
@Slf4j
public class AdminRoleAddController extends HttpServlet {

    private static final AdminRoleService adminRoleService = AdminRoleServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/admin/role/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminRoleAddRequest adminRoleAddRequest = AdminRoleAddRequest.of(req);
        long id = adminRoleService.add(adminRoleAddRequest);
        String location = String.format("/admin/role/item=%d", id);
        resp.sendRedirect(location);
    }
}
