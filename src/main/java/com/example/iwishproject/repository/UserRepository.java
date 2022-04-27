package com.example.iwishproject.repository;

import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.iwishproject.utility.ConnectionManager.getConnection;

@Repository
public class UserRepository {

  public void createUser(String userName, String password){
    getConnection();
    try{
      final String CREATEQUERY = "CREATE USER " +"'"+ userName +"'" + "@'localhost' IDENTIFIED BY " + "'"+ password+"'";
      final String GRANTQUERY = "GRANT SELECT ON * . * TO " + userName + "@localhost";

      PreparedStatement preparedStatement = getConnection().prepareStatement(CREATEQUERY);
      PreparedStatement preparedStatementGrant = getConnection().prepareStatement(GRANTQUERY);
      //Opretter bruger
      preparedStatement.executeQuery();
      //giver tilladelse til SELECT
      preparedStatementGrant.executeQuery();

    } catch(SQLException e){
      System.out.println("Could not create");
      e.printStackTrace();
    }
  }

  public void login(String userName, String password){

  }
}
