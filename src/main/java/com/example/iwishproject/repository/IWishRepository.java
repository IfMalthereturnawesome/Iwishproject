package com.example.iwishproject.repository;

import com.example.iwishproject.model.Wish;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.example.iwishproject.utility.ConnectionManager.getConnection;
import static com.example.iwishproject.utility.ConnectionManager.getOffConnection;

@Repository
public class IWishRepository {

//Viser alle ønsker
  public List<Wish> findAllWishes(){
    //tom arraylist
    ArrayList<Wish> wishes = new ArrayList<>();
    try {
      //Forbinder
      getConnection();
      System.out.println("Forbundet til DB");
      Statement statement = getConnection().createStatement();
      final String SQL_QUERY = "SELECT * FROM ønsker";
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


}

