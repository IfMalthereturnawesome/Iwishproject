package com.example.iwishproject.repository;

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

//Tilføjer en ønske
  public void addwish(Wish wish){
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

}

