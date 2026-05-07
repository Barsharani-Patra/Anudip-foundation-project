package com.ghs.service;

import com.ghs.dao.UserDAO;
import com.ghs.model.User;

public class UserService {
    private final UserDAO uDao = new UserDAO();

    public String registerUser(User user) {
        if (user.getUsername() == null || user.getUsername().strip().isEmpty()) {
            return "Registration Failed: Username is required.";
        }
        return (uDao.registerUser(user) > 0) ? "User registered successfully!" : "Registration failed.";
    }

    public User login(String u, String p) {
        return (u == null || p == null) ? null : uDao.login(u, p);
    }
}