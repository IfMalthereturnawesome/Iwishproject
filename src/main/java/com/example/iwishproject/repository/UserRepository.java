package com.example.iwishproject.repository;

import com.example.iwishproject.model.User;
import com.example.iwishproject.model.Wish;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.example.iwishproject.utility.ConnectionManager.getConnection;
import static com.example.iwishproject.utility.ConnectionManager.getOffConnection;

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
    User loginUser = null;
      try {
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY);

        while (resultSet.next()) {
          int ID = resultSet.getInt(1);
          String eMail = resultSet.getString(2);
          String firstName = resultSet.getString(3);
          String lastName = resultSet.getString(4);
          String password = resultSet.getString(5);
          loginUser = new User(ID,eMail,password,firstName,lastName);
        }

    } catch (SQLException e) {
      System.out.println("Could not find user");
      e.printStackTrace();
    }
    return loginUser; //User not found
  }
  public User findUserById(String id) {
    return findUserWhere("id", id);
  }

  public List<User> findUserName(int id){
    getConnection();
    ArrayList<User> userList = new ArrayList<>();
    final String QUERY = "SELECT * FROM registeredusers WHERE ID = '"+id+"'";
    //User loginUser = null;

    try {
      Statement statement = getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery(QUERY);

      while (resultSet.next()) {
        int ID = resultSet.getInt(1);
        String eMail = resultSet.getString(2);
        String firstName = resultSet.getString(3);
        String lastName = resultSet.getString(4);
        String password = resultSet.getString(5);
        userList.add(new User(ID,eMail,password,firstName,lastName));
       // loginUser = new User(firstName);
      }
      statement.close();
      getOffConnection();

    } catch (SQLException e) {
      System.out.println("Could not find user");
      e.printStackTrace();
    }
    return userList; //User not found
  }


  public User findUserWhere(String field,String userID){
    //get connection from ConnectionManager
    getConnection();
    final String QUERY = "SELECT * FROM registeredusers WHERE " + field + " = \"" + userID + "\"";
    User loginUser = null;
    try {
      Statement statement = getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery(QUERY);

      while (resultSet.next()) {
        int ID = resultSet.getInt(1);
        String eMail = resultSet.getString(2);
        String firstName = resultSet.getString(3);
        String lastName = resultSet.getString(4);
        String password = resultSet.getString(5);
        loginUser = new User(ID,eMail,password,firstName,lastName);
      }

    } catch (SQLException e) {
      System.out.println("Could not find user");
      e.printStackTrace();
    }
    return loginUser; //User not found
  }

  public boolean passwordCheck(User user, String password){
    if (password.equals(user.getPassword())) {
      return true;
    } else {
      return false;
    }
  }

}
