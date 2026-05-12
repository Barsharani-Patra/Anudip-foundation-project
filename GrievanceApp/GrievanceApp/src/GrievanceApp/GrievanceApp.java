package GrievanceApp;
import java.util.*;
import java.time.LocalDateTime;

// All code in one file = zero package/import errors

public class GrievanceApp {
    
    // ENUM
    enum Role { EMPLOYEE, GRIEVANCE_MANAGER, ADMIN }

    // USER CLASS
    static class User {
        int userId;
        String username;
        String password;
        Role role;

        User(int userId, String username, String password, Role role) {
            this.userId = userId;
            this.username = username;
            this.password = password;
            this.role = role;
        }
    }

    // GRIEVANCE CLASS
    static class Grievance {
        int grievanceId;
        int employeeId;
        String description;
        String category;
        String status;
        LocalDateTime createdAt;

        Grievance(int grievanceId, int employeeId, String description, String category) {
            this.grievanceId = grievanceId;
            this.employeeId = employeeId;
            this.description = description;
            this.category = category;
            this.status = "Submitted";
            this.createdAt = LocalDateTime.now();
        }

        public String toString() {
            return "ID: " + grievanceId + " | Status: " + status + " | Category: " + category + " | Desc: " + description;
        }
    }

    // GLOBAL LISTS - acts as database
    static List<User> users = new ArrayList<>();
    static List<Grievance> grievances = new ArrayList<>();
    static int nextUserId = 1;
    static int nextGrievanceId = 1;
    static Scanner sc = new Scanner(System.in);
    static User currentUser = null;

    public static void main(String[] args) {
        // Default users - Req 1: Admin Services
        users.add(new User(nextUserId++, "admin", "admin123", Role.ADMIN));
        users.add(new User(nextUserId++, "manager1", "mgr123", Role.GRIEVANCE_MANAGER));
        users.add(new User(nextUserId++, "emp1", "emp123", Role.EMPLOYEE));

        while (true) {
            if (currentUser == null) {
                login();
            } else {
                if (currentUser.role == Role.EMPLOYEE) employeeMenu();
                else if (currentUser.role == Role.GRIEVANCE_MANAGER) managerMenu();
                else if (currentUser.role == Role.ADMIN) adminMenu();
            }
        }
    }

    static void login() {
        System.out.println("\n===== Login =====");
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        for (User u : users) {
            if (u.username.equals(username) && u.password.equals(password)) {
                currentUser = u;
                System.out.println("Welcome " + u.username + " | Role: " + u.role);
                return;
            }
        }
        System.out.println("Invalid credentials!");
    }

    // Req 5: Raise concern + Req 2: Status report + Req 4: Search
    static void employeeMenu() {
        System.out.println("\n1.Raise Grievance  2.View Mine  3.Search  4.Logout");
        System.out.print("Choose: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            System.out.print("Category: ");
            String cat = sc.nextLine();
            System.out.print("Description: ");
            String desc = sc.nextLine();
            grievances.add(new Grievance(nextGrievanceId++, currentUser.userId, desc, cat));
            System.out.println("Grievance submitted!");
        } else if (choice == 2) {
            System.out.println("\nYour Grievances:");
            for (Grievance g : grievances) {
                if (g.employeeId == currentUser.userId) System.out.println(g);
            }
        } else if (choice == 3) {
            System.out.print("Search keyword: ");
            String key = sc.nextLine().toLowerCase();
            for (Grievance g : grievances) {
                if (g.description.toLowerCase().contains(key) || g.status.toLowerCase().contains(key)) {
                    System.out.println(g);
                }
            }
        } else if (choice == 4) {
            currentUser = null;
        }
    }

    // Req 3: Manager resolves
    static void managerMenu() {
        System.out.println("\n1.View All  2.Resolve  3.Logout");
        System.out.print("Choose: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            for (Grievance g : grievances) System.out.println(g);
        } else if (choice == 2) {
            System.out.print("Enter Grievance ID to resolve: ");
            int id = sc.nextInt();
            sc.nextLine();
            for (Grievance g : grievances) {
                if (g.grievanceId == id) {
                    g.status = "Resolved";
                    System.out.println("Resolved!");
                    return;
                }
            }
            System.out.println("Not found");
        } else if (choice == 3) {
            currentUser = null;
        }
    }

    // Req 1: User Management
    static void adminMenu() {
        System.out.println("\n1.Create User  2.View All Grievances  3.Logout");
        System.out.print("Choose: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            System.out.print("Username: ");
            String u = sc.nextLine();
            System.out.print("Password: ");
            String p = sc.nextLine();
            System.out.print("Role EMPLOYEE/GRIEVANCE_MANAGER/ADMIN: ");
            Role r = Role.valueOf(sc.nextLine().toUpperCase());
            users.add(new User(nextUserId++, u, p, r));
            System.out.println("User created!");
        } else if (choice == 2) {
            for (Grievance g : grievances) System.out.println(g);
        } else if (choice == 3) {
            currentUser = null;
        }
    }
}