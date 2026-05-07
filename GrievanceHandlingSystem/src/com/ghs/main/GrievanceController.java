package com.ghs.main;

import java.util.*;
import com.ghs.model.*;
import com.ghs.service.*;

public class GrievanceController {
    private final Scanner sc = new Scanner(System.in);
    private final UserService uS = new UserService();
    private final GrievanceService gS = new GrievanceService();
    private User activeUser = null;

    public void startSystem() {
        while (true) {
            System.out.println("\n--- MAIN MENU ---\n1. Login\n2. Register\n3. Exit");
            String in = sc.nextLine();
            if ("1".equals(in)) login();
            else if ("2".equals(in)) register();
            else if ("3".equals(in)) break;
        }
    }

    private void login() {
        System.out.print("Username: "); String u = sc.nextLine();
        System.out.print("Password: "); String p = sc.nextLine();
        activeUser = uS.login(u, p);
        if (activeUser != null) runUserSession();
    }

    private void register() {
        System.out.print("Name: "); String u = sc.nextLine();
        System.out.print("Pass: "); String p = sc.nextLine();
        System.out.println("Role: 1.Admin | 2.Employee | 3.Manager");
        int r = Integer.parseInt(sc.nextLine());
        System.out.println(uS.registerUser(new User(0, u, p, r)));
    }

    private void runUserSession() {
        while (activeUser != null) {
            int role = activeUser.getRoleId();
            if (role == 1) adminFlow();
            else if (role == 2) employeeFlow();
            else if (role == 3) managerFlow();
        }
    }

    private void employeeFlow() {
        System.out.println("1. Raise\n2. Logout");
        int c = Integer.parseInt(sc.nextLine());
        if (c == 1) {
            System.out.print("Title: "); String t = sc.nextLine();
            System.out.print("Desc: "); String d = sc.nextLine();
            System.out.println(gS.raiseGrievance(new Grievance(0, t, d, "Pending", activeUser.getUserId(), null)));
        } else activeUser = null;
    }

    private void managerFlow() {
        System.out.println("1. View\n2. Logout");
        int c = Integer.parseInt(sc.nextLine());
        if (c == 1) gS.fetchAllReports().forEach(r -> System.out.println(r.getId() + ": " + r.getTitle()));
        else activeUser = null;
    }

    private void adminFlow() {
        System.out.println("1. Audit\n2. Logout");
        int c = Integer.parseInt(sc.nextLine());
        if (c == 2) activeUser = null;
    }
}