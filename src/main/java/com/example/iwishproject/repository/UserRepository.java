package com.example.iwishproject.repository;

import com.example.iwishproject.model.User;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
      preparedStatement.setString(3, user.getPassword());
      preparedStatement.setString(4, user.getFirstName());
      preparedStatement.setString(5, user.getLastName());
      preparedStatement.executeUpdate();

    } catch(SQLException e){
      System.out.println("Could not create");
      e.printStackTrace();
    }
  }

  public void login(String userName, String password){

  }
}
