package pool;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Operations implements CRUD{

    @Override
    public void select() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Which table would you like to view?");
        String table = sc.nextLine();
        System.out.println(table);

        ArrayList<String> columns = new ArrayList<>();
        System.out.println("Enter the columns of the table you'd like to view: ");
        System.out.println("Enter 'Done' when finished.");


        while (true) {
            String column = sc.nextLine();

            if (column.equalsIgnoreCase("Done")) {
                break;
            }

            columns.add(column);

        }

        String selectSQL = "SELECT ";

        for (int i = 0; i < columns.size(); i++) {

            selectSQL += columns.get(i);


            if (i < columns.size() - 1) {
                selectSQL += ", ";
            }
        }

        selectSQL += " FROM " + table;

        try (Connection connection = DatabaseUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {

                for (String result : columns) {
                    result += ": " + resultSet.getString(result);
                    System.out.println(result);
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void update() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the command to update the data:");
        String updateSQL = sc.nextLine();

        try (Connection connection = DatabaseUtils.getConnection();
             Statement statement = connection.createStatement()) {
            int rowsUpdated = statement.executeUpdate(updateSQL);
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the command to delete the data:");
        String deleteSQL = sc.nextLine();

        try (Connection connection = DatabaseUtils.getConnection();
             Statement statement = connection.createStatement()) {
            int rowsDeleted = statement.executeUpdate(deleteSQL);
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the command to add the data:");
        String createSQL = sc.nextLine();

        try (Connection connection = DatabaseUtils.getConnection();
             Statement statement = connection.createStatement()) {
            int rowsCreated = statement.executeUpdate(createSQL);
            System.out.println("Rows created: " + rowsCreated);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int signIn(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the email and password:");
        String email = sc.nextLine();
        String password = sc.nextLine();
        int userId = -1;

        String signInSQL = "SELECT id FROM members WHERE email = ? AND password = ?";

        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(signInSQL)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt("id");
                    System.out.println("Signed in Successfully");
                } else {
                    System.out.println("Invalid email or password");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Sign in Failed");
        }

        return userId;
    }

    public void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your forename, surname, email, and password:");
        String forename = sc.nextLine();
        String surname = sc.nextLine();
        String email = sc.nextLine();
        String password = sc.nextLine();

        String signUpSQL = "INSERT INTO members (forename, surname, email, password) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(signUpSQL)) {

            preparedStatement.setString(1, forename);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Sign up Successful");
                System.out.println("Rerun and Sign in");
            } else {
                System.out.println("Sign up Failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Sign up Failed");

        }

    }

    public void viewBooks(){
        String selectSQL = "SELECT b.id, b.title, b.year, b.price, a.author, g.genre " +
                "FROM books b " +
                "JOIN author a ON b.id = a.id " +
                "JOIN genre g ON b.id = g.id";

        try (Connection connection = DatabaseUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString("title");
                String year = resultSet.getString("year");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                String price = resultSet.getString("price");

                System.out.println(id + ": " + "Title: " + title + ", Year: " + year + ", Author: " + author + ", Genre: " + genre + ", Price: " + price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
