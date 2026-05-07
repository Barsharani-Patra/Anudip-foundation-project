package com.ghs.dao;

import java.sql.*;
import com.ghs.connection.MyConnection;
import com.ghs.model.User;

public class UserDAO {
    
    public int registerUser(User user) {
        String sql = "INSERT INTO users (username, password, role_id) VALUES (?, ?, ?)";
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, user.getUsername());
            ps.setObject(2, user.getPassword());
            ps.setObject(3, user.getRoleId());
            return ps.executeUpdate();
        } catch (SQLException e) { return 0; }
    }

    public User login(String username, String password) {
        String sql = "SELECT user_id, username, role_id FROM users WHERE username = ? AND password = ?";
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt(1), rs.getString(2), null, rs.getInt(3));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}