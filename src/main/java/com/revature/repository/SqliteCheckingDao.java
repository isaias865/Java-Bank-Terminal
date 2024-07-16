package com.revature.repository;

import com.revature.entity.CheckingAccount;
import com.revature.entity.User;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteCheckingDao implements CheckingDao {

    @Override
    public CheckingAccount createCheckingAccount(CheckingAccount newAccount) {
        String sql = "insert into account values (?, ?, ?, ?)";
        try(Connection connection = DatabaseConnector.createConnection()){
            // we can use a PreparedStatement to control how the user data is injected
            // into our query. the PreparedStatement helps to format the data so to help
            // protect us from SQL injection (someone trying to mess with our database
            // via the data they provide)
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // remember that indexing for Java sql resources starts at 1, not 0
            preparedStatement.setString(1, newAccount.getOwner());
            preparedStatement.setString(2, newAccount.getName());
            preparedStatement.setDouble(3, newAccount.getSolaris());
            preparedStatement.setDouble(4, newAccount.getSpice());
            // executeUpdate returns the row count affected, since we want a single
            // record created we can check that the rowCount value is 1 to know if we
            // have success or not
            int result = preparedStatement.executeUpdate();
            if (result == 1){
                return newAccount;
            }
            // if we did not create the new user we throw a custom exception and handle
            // the problem somewhere else
            throw new UserSQLException("User could not be created: please try again");
        } catch (SQLException exception){
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public void deposit(CheckingAccount account, double amount){
        String sql = "update account set solaris = ? where username = ? and accountName = ?";
        try(Connection connection = DatabaseConnector.createConnection()){
            // we can use a PreparedStatement to control how the user data is injected
            // into our query. the PreparedStatement helps to format the data so to help
            // protect us from SQL injection (someone trying to mess with our database
            // via the data they provide)
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // remember that indexing for Java sql resources starts at 1, not 0
            preparedStatement.setDouble(1, amount + account.getSolaris());
            preparedStatement.setString(2, account.getOwner());
            preparedStatement.setString(3, account.getName());
            // executeUpdate returns the row count affected, since we want a single
            // record created we can check that the rowCount value is 1 to know if we
            // have success or not
            int result = preparedStatement.executeUpdate();
            if (result == 1){
                return;
            }
            // if we did not create the new user we throw a custom exception and handle
            // the problem somewhere else
            throw new UserSQLException("Solaris could not be deposited: please try again");
        } catch (SQLException exception){
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public void spice(CheckingAccount account, double cost, double spice) {
        String sql = "update account set solaris = ?, spice = ? where username = ? and accountName = ?";
        try(Connection connection = DatabaseConnector.createConnection()){
            // we can use a PreparedStatement to control how the user data is injected
            // into our query. the PreparedStatement helps to format the data so to help
            // protect us from SQL injection (someone trying to mess with our database
            // via the data they provide)
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // remember that indexing for Java sql resources starts at 1, not 0
            preparedStatement.setDouble(1, account.getSolaris() - cost);
            preparedStatement.setDouble(2, spice + account.getSpice());
            preparedStatement.setString(3, account.getOwner());
            preparedStatement.setString(4, account.getName());
            // executeUpdate returns the row count affected, since we want a single
            // record created we can check that the rowCount value is 1 to know if we
            // have success or not
            int result = preparedStatement.executeUpdate();
            if (result == 1){
                return;
            }
            // if we did not create the new user we throw a custom exception and handle
            // the problem somewhere else
            throw new UserSQLException("Spice could not be bought: please try again");
        } catch (SQLException exception){
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public void delete(CheckingAccount account) {
        String sql = "delete from account where username = ? and accountName = ?";
        try(Connection connection = DatabaseConnector.createConnection()){
            // we can use a PreparedStatement to control how the user data is injected
            // into our query. the PreparedStatement helps to format the data so to help
            // protect us from SQL injection (someone trying to mess with our database
            // via the data they provide)
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // remember that indexing for Java sql resources starts at 1, not 0
            preparedStatement.setString(1, account.getOwner());
            preparedStatement.setString(2, account.getName());
            // executeUpdate returns the row count affected, since we want a single
            // record created we can check that the rowCount value is 1 to know if we
            // have success or not
            int result = preparedStatement.executeUpdate();
            if (result == 1){
                return;
            }
            // if we did not create the new user we throw a custom exception and handle
            // the problem somewhere else
            throw new UserSQLException("Account could not be deleted: please try again");
        } catch (SQLException exception){
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public List<CheckingAccount> getAllCheckingAccounts(String user) {
        String owner = user;
        String sql = "select * from account where username ='" + owner + "'";
        try(Connection connection = DatabaseConnector.createConnection()){
            // we can use a Statement object to execute our query
            Statement statement = connection.createStatement();
            // if we use executeQuery we will get a ResultSet object back with the records found
            ResultSet resultSet = statement.executeQuery(sql);
            // we make our List of users we will fill with found data
            List<CheckingAccount> checkingAccounts = new ArrayList<>();
            // loop through the ResultSet using the next() method
            while(resultSet.next()){
                CheckingAccount accountRecord = new CheckingAccount();
                // you can use the column name or the column index to get the data (indexing starts at 1)
                accountRecord.setOwner(resultSet.getString("username"));
                accountRecord.setName(resultSet.getString("accountName"));
                accountRecord.setSolaris(resultSet.getDouble("solaris"));
                accountRecord.setSpice(resultSet.getDouble("spice"));

                checkingAccounts.add(accountRecord);
            }
            // return the list
            return checkingAccounts;
        } catch (SQLException exception){
            // SQLException is a checked exception, so we need to handle it here, throw a custom exception so we can
            // handle sending the results back to the user in a more appropriate layer of the application
            throw new UserSQLException(exception.getMessage());
        }
    }


}
