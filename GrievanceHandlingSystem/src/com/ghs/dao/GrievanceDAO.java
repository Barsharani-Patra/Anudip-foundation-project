package com.ghs.dao;

import java.sql.*;
import java.util.*;
import com.ghs.connection.MyConnection;
import com.ghs.model.Grievance;

public class GrievanceDAO {

    public int addGrievance(Grievance g) {
        String sql = "INSERT INTO grievances (title, description, user_id) VALUES (?, ?, ?)";
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, g.getTitle());
            ps.setString(2, g.getDescription());
            ps.setInt(3, g.getUserId());
            return ps.executeUpdate();
        } catch (SQLException e) { return 0; }
    }

    public List<Grievance> searchByTitle(String keyword) {
        List<Grievance> list = new ArrayList<>();
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM grievances WHERE title LIKE ?")) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(build(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public int updateStatus(int gId, String status) {
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE grievances SET status = ? WHERE g_id = ?")) {
            ps.setString(1, status);
            ps.setInt(2, gId);
            return ps.executeUpdate();
        } catch (SQLException e) { return 0; }
    }

    public List<Grievance> getAllGrievances() {
        List<Grievance> list = new ArrayList<>();
        try (Connection con = MyConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM grievances")) {
            while (rs.next()) list.add(build(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public int deleteGrievance(int gId) {
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM grievances WHERE g_id = ?")) {
            ps.setInt(1, gId);
            return ps.executeUpdate();
        } catch (SQLException e) { return 0; }
    }

    private Grievance build(ResultSet rs) throws SQLException {
        return new Grievance(rs.getInt("g_id"), rs.getString("title"), rs.getString("description"), 
                             rs.getString("status"), rs.getInt("user_id"), rs.getTimestamp("created_at"));
    }
}