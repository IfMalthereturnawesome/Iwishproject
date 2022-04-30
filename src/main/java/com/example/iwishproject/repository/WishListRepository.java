package com.example.iwishproject.repository;

import com.example.iwishproject.model.Wish;
import com.example.iwishproject.model.WishList;
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
public class WishListRepository {

    public List<WishList> findAllWishLists(int InputUserID){
        //tom arraylist
        ArrayList<WishList> wishlist = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            final String SQL_QUERY = "SELECT * FROM allwishlist WHERE userID = "+InputUserID;
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);

            // Læser fra tabel
            while(resultSet.next()){
                //Giver informationerne fra tabel til attribute
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                int userID = resultSet.getInt(4);
                String photos = resultSet.getString(5);
                //Tilføjer dem til min constructor, for at lave Ønske objekter samt tilføjer dem til min arraylist

                wishlist.add(new WishList(id,title,description,userID,photos));

            }
            statement.close();
            getOffConnection();

        } catch (SQLException e) {
            System.out.println("Virker ikke");
            e.printStackTrace();
        }
        // Returner listen med alle wishlists
        return wishlist;
    }

    public WishList findWishListByID(int InputUserID, int inputWishListID){
        //tom arraylist
        WishList wishlist = null;
        try {
            Statement statement = getConnection().createStatement();
            final String SQL_QUERY = "SELECT * FROM allwishlist WHERE userID = "+inputWishListID  +" AND id = "+InputUserID;
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);

            // Læser fra tabel
            while(resultSet.next()){
                //Giver informationerne fra tabel til attribute
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                int userID = resultSet.getInt(4);
                String photos = resultSet.getString(5);
                //Tilføjer dem til min constructor, for at lave Ønske objekter samt tilføjer dem til min arraylist

                wishlist = new WishList(id,title,description,userID,photos);

            }
            statement.close();
            getOffConnection();

        } catch (SQLException e) {
            System.out.println("Virker ikke");
            e.printStackTrace();
        }
        // Returner listen med alle wishlists
        return wishlist;
    }

    //Tilføje en ønske
    public WishList addWishList(WishList wishList){
        getConnection();
        try{
            //prep statement
            PreparedStatement preparedStatement = getConnection().prepareStatement(
                    "INSERT INTO allwishlist(id, title, description, userID, photos) " +
                            "VALUES (?, ?, ?, ?, ?)");
            //set attributer
            preparedStatement.setInt(1,wishList.getId());
            preparedStatement.setString(2, wishList.getTitle());
            preparedStatement.setString(3, wishList.getDescription());
            preparedStatement.setInt(4,wishList.getUserID());
            preparedStatement.setString(5, wishList.getPhotos());
            //execute statement
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Could not create");
            e.printStackTrace();
        }
        return wishList;
    }

    //Slet en ønske
    public void deleteWishList(int id){
        getConnection();
        try{
            PreparedStatement preparedStatement = getConnection().prepareStatement(
                    "DELETE FROM allwishlist WHERE id = ?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch(SQLException e){
            System.out.println("Could not create");
            e.printStackTrace();
        }
    }

    //Rediger en ønskeliste
    public void editWishList(WishList wishList){
        Scanner input = new Scanner(System.in);
        final String UPDATE_QUERY = "UPDATE allwishlist SET title = ?, description = ?, userID = ?, photos = ? WHERE id = ?";
        getConnection();

        try{
            PreparedStatement preparedStatementUpdateRow = getConnection().prepareStatement(UPDATE_QUERY);
            preparedStatementUpdateRow.setInt(1,wishList.getId());
            preparedStatementUpdateRow.setString(2, wishList.getTitle());
            preparedStatementUpdateRow.setString(3, wishList.getDescription());
            preparedStatementUpdateRow.setInt(4, wishList.getUserID());
            preparedStatementUpdateRow.setString(5, wishList.getPhotos());

            preparedStatementUpdateRow.executeUpdate();

        }catch (SQLException e){
            System.out.println(e);
        }
    }
}
