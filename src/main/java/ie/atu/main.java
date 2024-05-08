package ie.atu;

import pool.DatabaseUtils;
import pool.Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

// Sign in as admin with "admin@atu.ie" "admin"

public class main {
    public static void main(String[] args) {
        Operations op = new Operations();
        int id = -1;

        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter 1 to sign in or 2 to sign up");
        int choice = sc.nextInt();

        if (choice == 1) {
            id = op.signIn();
            if (id != -1 && id != 4) {
                System.out.println("Select 1 to view books available to rent");
                choice = sc.nextInt();
                if (choice == 1) {
                    op.viewBooks();

                }else{
                    System.out.println("Wrong Entry.");
                }

                System.out.println("Please select the corresponding number to rent the book");
                int book = sc.nextInt();
                System.out.println("Please enter today's date (format: YYYY-MM-DD");
                String date = sc.next();

                String signUpSQL = "INSERT INTO transactions (member_id, book_id, transaction_date) VALUES (?, ?, ?)";

                try (Connection connection = DatabaseUtils.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(signUpSQL)) {

                    preparedStatement.setInt(1, id);
                    preparedStatement.setInt(2, book);
                    preparedStatement.setString(3, date);

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Transaction Successful");
                    } else {
                        System.out.println("Transaction Failed");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }else if (id == 4) {
                System.out.println("Welcome admin, please select from the following options");
                System.out.println("1: Create.\n2: Read\n3: Update\n4: Delete");
                int choice1 = sc.nextInt();
                switch (choice1) {
                    case 1: op.create();
                    break;
                    case 2: op.select();
                    break;
                    case 3: op.update();
                    break;
                    case 4: op.delete();
                    break;
                    default:
                        break;
                }
            }
        }else if (choice == 2) {
            op.signUp();

        }





    }
}
