/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcsample;

import java.sql.*;
import java.util.Scanner;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            try{
             String url = "jdbc:mysql://localhost/java_base";
             String username = "root";
             String password = "Ctnm_vfufpbyjd33";
             Scanner scanner = new Scanner (System.in);
             
             Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
             try (Connection conn = DriverManager.getConnection(url, username, password)){
             
             System.out.println("For INSERT press 1");
             System.out.println("For UPDATE press 2");
             System.out.println("For SELECT press 3");
             System.out.println("For DELETE press 4");
             System.out.println("To exit press any other key");
             String str = scanner.nextLine();
             switch(str){
                 case "1":
                     System.out.print("Input product name: ");
                     String name_product_inp = scanner.nextLine();
             
                     System.out.print("Input quantity of product: ");
                     String quantity_inp = scanner.nextLine();
             
                     System.out.print("Input sale price: ");
                     String price_inp = scanner.nextLine();
                     
                     System.out.print("Input date: ");
                     String date_inp = scanner.nextLine();
             
                     System.out.print("Input shop id: ");
                     String shop_id = scanner.nextLine();

                     String sql = "INSERT INTO sells VALUES (NULL,?,?,?,?,?)";
                     PreparedStatement preparedStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                     preparedStatement.setString(1,name_product_inp);
                     preparedStatement.setString(2,quantity_inp);
                     preparedStatement.setString(3,price_inp);
                     preparedStatement.setString(4,date_inp);
                     preparedStatement.setString(5,shop_id);
                     
                     int rows = preparedStatement.executeUpdate();
                
                     ResultSet keys = preparedStatement.getGeneratedKeys();
                     if (keys.next()){
                      long id = keys.getLong(1);
                      System.out.println("Row added id: " + id);
                     }
                     System.out.printf("%d rows added \n", rows);

                     break;
                     
                 case "2":
                     System.out.print("Input new shop name: ");
                     String name_shop = scanner.nextLine();
             
                     System.out.print("Input shop id: ");
                     int id = scanner.nextInt();
                     
                     String sql_upd = "Update shops SET name_shop = ? WHERE shop_id = ?";
                
                     PreparedStatement preparedStatement_upd = conn.prepareStatement(sql_upd);                
                         
                     preparedStatement_upd.setString(1,name_shop);
                     preparedStatement_upd.setInt(2,id);               
                                
                     preparedStatement_upd.executeUpdate();
                     break;
                     
                 case "3":
                     System.out.print("Input max price: ");
                     int max_price = scanner.nextInt();
                     
                     String sql_select = "SELECT * FROM sells WHERE price <= ?";
                     
                     PreparedStatement preparedStatement_select = conn.prepareStatement(sql_select);
                                        
                     preparedStatement_select.setInt(1, max_price);
                     
                     ResultSet resultSet = preparedStatement_select.executeQuery();
                     while(resultSet.next()){                    
                     int sell_id = resultSet.getInt(1);
                     String name_product = resultSet.getString(2);
                     int quantity = resultSet.getInt(3);
                     int price = resultSet.getInt(4);
                     System.out.printf("%d. %s - %s - %s \n", sell_id, name_product, quantity, price);
                      }
                     break;
                     
                 case "4":
                     System.out.print("Input sell id: ");
                     int sell_id = scanner.nextInt();
                     
                     String sql_del = "DELETE FROM sells WHERE sell_id = ?";
                
                     PreparedStatement preparedStatement_del = conn.prepareStatement(sql_del);                
                         
                     preparedStatement_del.setInt(1,sell_id);
                                      
                     preparedStatement_del.executeUpdate();
                     break;    
                 default:
                     break; 
                }
            }
           }
            
         catch(Exception ex){
             System.out.println("Connection failed...");
              
             System.out.println(ex);
            }  
    }
}
