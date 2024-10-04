package com.demo.service;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UserService", value = "/UserService")
public class UserService extends HttpServlet {

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        userRepository = new UserRepository();
    }

    public void createUser(User user) {
        userRepository.create(user);
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public void deleteUser(String email) {
        userRepository.delete(email);
    }

    public List<User> displayUsers() {
        return userRepository.display();
    }

    private User extractUserFromRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String adresse = request.getParameter("adresse");
        Boolean isManager = request.getParameter("manager") != null;
        return new User(name, email, password, adresse, isManager);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = extractUserFromRequest(request);
        createUser(user);
        response.sendRedirect(request.getContextPath() + "/user/display-All-Users");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        User user = userRepository.findUserById(id);
        if (user != null) {
            user.setName(request.getParameter("name"));
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            user.setAdresse(request.getParameter("adresse"));
            user.setManager(request.getParameter("manager") != null);
            updateUser(user);
        }
        response.sendRedirect(request.getContextPath() + "/user/display-All-Users");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        deleteUser(email);
        response.sendRedirect(request.getContextPath() + "/user/display-All-Users");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<User> users = displayUsers();
        request.setAttribute("usersList", users);
        request.getRequestDispatcher("/GestionUsers.jsp").forward(request, response);
    }
}