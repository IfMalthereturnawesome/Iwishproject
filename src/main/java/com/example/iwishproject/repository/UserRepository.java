package com.example.iwishproject.repository;

import com.example.iwishproject.model.User;
import com.example.iwishproject.model.Wish;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.iwishproject.utility.ConnectionManager.getConnection;

@Repository
public class UserRepository {

  public void createUser(User user){
    getConnection();
    try{
      final String QUERY = "INSERT INTO registeredusers(ID, eMail, firstName, lastName, password) VALUES (?, ?, ?, ?, ?)";

      PreparedStatement preparedStatement = getConnection().prepareStatement(QUERY);
      //Opretter bruger
      preparedStatement.setInt(1, user.getID());
      preparedStatement.setString(2, user.geteMail());
      preparedStatement.setString(3, user.getFirstName());
      preparedStatement.setString(4, user.getLastName());
      preparedStatement.setString(5, user.getPassword());
      preparedStatement.executeUpdate();

    } catch(SQLException e){
      System.out.println("Could not create");
      e.printStackTrace();
    }
  }

  public User findUser(String inputEMail){
    //get connection from ConnectionManager
    getConnection();
    final String QUERY = "SELECT * FROM registeredusers WHERE eMail = '"+inputEMail+"'";
    User selectedUser = null;
      try {
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY);

        while (resultSet.next()) {
          int ID = resultSet.getInt(1);
          String eMail = resultSet.getString(2);
          String firstName = resultSet.getString(3);
          String lastName = resultSet.getString(4);
          String password = resultSet.getString(5);
          selectedUser = new User(ID,eMail,password,firstName,lastName);
        }

    } catch (SQLException e) {
      System.out.println("Could not find user");
      e.printStackTrace();
    }
    return selectedUser; //User not found
  }

  public boolean passwordCheck(User user, String password){
    if (password.equals(user.getPassword())) {
      return true;
    } else {
      return false;
    }
  }

}
