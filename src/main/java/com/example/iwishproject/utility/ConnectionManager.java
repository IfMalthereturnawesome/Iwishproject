package com.example.iwishproject.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
  private static String DB_URL = System.getenv("url");
  private static String UID = System.getenv("user");
  private static String PWD = System.getenv("password");
  public static Connection getConnection(){
    Connection connection = null;
    try{
      connection = DriverManager.getConnection(DB_URL, UID, PWD);
      return connection;
    }catch (SQLException e){
      System.out.println("Virker ikke");
      e.printStackTrace();
    }
    return connection;
  }
  public static void getOffConnection(){
    try {
      getConnection().close();
    } catch (SQLException e) {
      System.out.println("Kunne ikke lukke for serveren");
      e.printStackTrace();
    }
  }
}
