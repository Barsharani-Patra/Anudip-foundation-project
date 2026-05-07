package com.ghs.service;

import com.ghs.dao.GrievanceDAO;
import com.ghs.model.Grievance;
import java.util.List;

public class GrievanceService {
    private final GrievanceDAO gDao = new GrievanceDAO();

    public String raiseGrievance(Grievance g) {
        boolean valid = g.getTitle() != null && g.getTitle().trim().length() >= 3;
        if (!valid) return "Error: Title is too short.";
        return (gDao.addGrievance(g) > 0) ? "Grievance submitted successfully!" : "Submission failed.";
    }

    public List<Grievance> searchGrievances(String k) {
        return gDao.searchByTitle(k);
    }

    public String resolveGrievance(int id) {
        return (gDao.updateStatus(id, "Resolved") > 0) ? "Grievance marked as Resolved." : "Update failed.";
    }

    public List<Grievance> fetchAllReports() {
        return gDao.getAllGrievances();
    }

    public String removeGrievance(int id) {
        return (gDao.deleteGrievance(id) > 0) ? "Grievance # " + id + " deleted successfully." : "Delete failed.";
    }
}