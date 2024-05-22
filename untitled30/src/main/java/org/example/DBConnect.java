package org.example;

import java.sql.*;
import java.util.ArrayList;

public class DBConnect {
    private static String url = "jdbc:sqlite::resource:employeeSystem.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createNewTable() {
        String sqlEmployees = "CREATE TABLE IF NOT EXISTS Employees ("
                + "idPerson INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "idChip INTEGER, "
                + "chipCode TEXT)";
        executeSQL(sqlEmployees);

        String sqlChips = "CREATE TABLE IF NOT EXISTS Chip ("
                + "idChip INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "old TEXT)";
        executeSQL(sqlChips);
    }

    public static int insertChip(String date) {
        String sql = "INSERT INTO Chip(name) VALUES(?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, date);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Vrací ID nově vloženého čipu
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static int insertEmployee(String name, String chipCode) {
        String sql = "INSERT INTO Employees(name, chipCode) VALUES(?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setString(2, chipCode);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Vrací ID nově vloženého zaměstnance
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static void addChipToEmployee(int idEmployee, int idChip) {
        String sql = "UPDATE Employees SET idChip=? WHERE idPerson=?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idChip);
            pstmt.setInt(2, idEmployee);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void executeSQL(String sql) {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readNotes() {
        String sql = "SELECT old FROM Chip";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String old = rs.getString("old");
                System.out.println("Stáří čipu je " + old);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertEmployeeWithChip(Employee employee) {
        int employeeId = insertEmployee(employee.getName(), employee.getChipCode());
        if (employeeId != -1) {
            int chipId = insertChip(employee.getChipCode());
            if (chipId != -1) {
                addChipToEmployee(employeeId, chipId);
                System.out.println("zaměstnanec je přdán");
            } else {
                System.out.println("chyba ");
            }
        } else {
            System.out.println("chuba");
        }
    }

    public ArrayList<Employee> getAllEmployees() {
        String sql = "SELECT * FROM Employees";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            ArrayList<Employee> employees = new ArrayList<>();
            while (rs.next()) {
                employees.add(new Employee(rs.getString("name"), rs.getString("idChip"), rs.getString("chipCode")));
            }
            return employees;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public static void printAllChips() {
        String sql = "SELECT * FROM Chip";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Chip Table:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("idChip") +
                        ", Old: " + rs.getString("old"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

// tohle je na mě už vážne moc já to nezzládám prosím pomoc 5 hodin to tu nějak dělám a pořád nějaké errory udělám jednu část a potom na mě vyleze 5 nových erororů hmm to je fakt sranda ani tan chatgpt to necápe co má asi tak dělat proč tohle vůbec píšu já jsem asi vážně zoufalej proč proč proč
// jenom tohle vážne něco proč tohle je tak moc už na mě
// proč já jsem natom tak špatně já se asi na to
// konečně to už něco dělá asi tak po 50 hidinách jo ale pořád není vyhráno