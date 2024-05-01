package pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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


}
