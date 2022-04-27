package com.example.iwishproject.repository;

import com.example.iwishproject.model.Wish;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.iwishproject.utility.ConnectionManager.getConnection;
import static com.example.iwishproject.utility.ConnectionManager.getOffConnection;

@Repository
public class IWishRepository {

  //Viser alle ønsker
  public List<Wish> findAllWishes(){
    //tom arraylist
    ArrayList<Wish> wishes = new ArrayList<>();
    try {
      Statement statement = getConnection().createStatement();
      final String SQL_QUERY = "SELECT * FROM wishes";
      ResultSet resultSet = statement.executeQuery(SQL_QUERY);

      // Læser fra tabel
      while(resultSet.next()){
        //Giver informationerne fra tabel til attributer
        int id = resultSet.getInt(1);
        String title = resultSet.getString(2);
        String description = resultSet.getString(3);
        double price = resultSet.getDouble(4);
        String link = resultSet.getString(5);
        //Tilføjer dem til min constructor, for at lave Ønske objekter samt tilføjer dem til min arraylist

        wishes.add(new Wish(id,title,description,price,link));

      }
      statement.close();
      getOffConnection();

    } catch (SQLException e) {
      System.out.println("Virker ikke");
      e.printStackTrace();
    }
    // Returner listen med alle pokemons
    return wishes;
  }

  //Tilføje en ønske
  public void addWish(Wish wish){
    getConnection();
    try{
      //prep statement
      PreparedStatement preparedStatement = getConnection().prepareStatement(
          "INSERT INTO wishes(id, title, description, price, link) " +
              "VALUES (?, ?, ?, ?, ?)");
      //set attributer
      preparedStatement.setInt(1,wish.getId());
      preparedStatement.setString(2, wish.getTitle());
      preparedStatement.setString(3, wish.getDescription());
      preparedStatement.setDouble(4, wish.getPrice());
      preparedStatement.setString(1, wish.getLink());
      //execute statement
      preparedStatement.executeUpdate();
    }
    catch(SQLException e){
      System.out.println("Could not create");
      e.printStackTrace();
    }
  }

  //Slet en ønske
  public void deleteWish(int wishID){
    getConnection();
    try{
      PreparedStatement preparedStatement = getConnection().prepareStatement(
          "DELETE FROM wishes WHERE id = ?");

      preparedStatement.setInt(1, wishID);

      preparedStatement.executeUpdate();

    } catch(SQLException e){
      System.out.println("Could not create");
      e.printStackTrace();
    }
  }

  //Rediger en ønske
  public void editWish(Wish wish){
    Scanner input = new Scanner(System.in);
    final String UPDATE_QUERY = "UPDATE wish SET title = ?, description = ?, price = ?, link = ? WHERE id = ?";
    getConnection();

    try{
      PreparedStatement preparedStatementUpdateRow = getConnection().prepareStatement(UPDATE_QUERY);
      preparedStatementUpdateRow.setInt(1,wish.getId());
      preparedStatementUpdateRow.setString(2, wish.getTitle());
      preparedStatementUpdateRow.setString(3, wish.getDescription());
      preparedStatementUpdateRow.setDouble(4, wish.getPrice());
      preparedStatementUpdateRow.setString(5, wish.getLink());

      preparedStatementUpdateRow.executeUpdate();

    }catch (SQLException e){
      System.out.println(e);
    }
  }

}

