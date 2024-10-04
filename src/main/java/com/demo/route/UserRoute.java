package com.demo.route;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import com.demo.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "user", value = "/user/*")
public class UserRoute extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }
        try {
            switch (pathInfo) {
                case "/display-All-Users":
                    request.getRequestDispatcher("/UserService").forward(request, response);
                    break;
                case "/add":
                    request.getRequestDispatcher("/AddUser.jsp").forward(request, response);
                    break;
                case "/update-user":
                    String email = request.getParameter("email");
                    Map<String, Object> user = new UserRepository().findUserByEmail(email.trim());
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/UpdateUser.jsp").forward(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the exception
            getServletContext().log("Error in UserRoute", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if ("/new".equals(pathInfo)) {
            request.getRequestDispatcher("/UserService").forward(request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if ("/update".equals(pathInfo)) {
            request.getRequestDispatcher("/UserService").forward(request, response);
        }
    }
}