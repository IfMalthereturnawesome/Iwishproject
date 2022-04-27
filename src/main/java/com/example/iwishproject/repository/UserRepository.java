package com.example.iwishproject.repository;

import com.example.iwishproject.model.User;
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

  public User findUser(String eMail){
    //get connection from ConnectionManager
    getConnection();

    try {
      Statement s = getConnection().createStatement();
      final String QUERY = "SELECT * FROM registeredusers WHERE eMail = ?";

      PreparedStatement psProduct = getConnection().prepareStatement(QUERY); //prepared statement

      psProduct.setString(1, eMail); // set eMail der skal søges på
      ResultSet rs = psProduct.executeQuery();  // Execute query

      //read data from resultset
      rs.next();
      {
        int ID = rs.getInt(1);
        String mail = rs.getString(2);
        String password = rs.getString(3);
        return new User(ID, mail, password);
      }

    } catch (SQLException e) {
      System.out.println("Could not create connection");
      e.printStackTrace();
    }
    return null; //User not found
  }

  public boolean passwordCheck(User user, String password){
    if (password.equals(user.getPassword())) {
      return true;

    } else {
      return false;
    }
  }

}
