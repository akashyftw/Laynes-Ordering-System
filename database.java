import java.sql.*;
import java.io.*;  
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class database {

    public static ArrayList<ArrayList<String>> readCSVFileName(String filename)  {
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        ArrayList<String> rows = new ArrayList<String>();

        Scanner sc;

        try {
            sc = new Scanner(new File(filename));
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                rows = new ArrayList<String> (Arrays.asList(line.split(",")));
                data.add(rows);
            }
            data.remove(0);
            // System.out.println(data);
        sc.close();  //closes the scanner
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

  //Commands to run this script
  //This will compile all java files in this directory
  //javac *.java
  //This command tells the file where to find the postgres jar which it needs to execute postgres commands, then executes the code
  //Windows: java -cp ".;postgresql-42.2.8.jar" jdbcpostgreSQL
  //Mac/Linux: java -cp ".:postgresql-42.2.8.jar" jdbcpostgreSQL

  public static void main(String args[]) {

    //Building the connection with your credentials

     Connection conn = null;
     String teamNumber = "4";
     String sectionNumber = "970";
     String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
     String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
     String userName = "csce315" + sectionNumber + "_" + teamNumber + "user";
     String userPassword = "catGoesMeow";

    //Connecting to the database
    try {
        conn = DriverManager.getConnection(dbConnectionString,userName, userPassword);
     } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
     }

     System.out.println("Opened database successfully");

     try{
       // Create a statement object
       Statement stmt = conn.createStatement();

       
       // Query Strings to create all tables
    //    String createTable1 = "CREATE TABLE Worker (workerID int, salary float, firstName text, lastName text);";
      //  String createTable2 = "CREATE TABLE RecipeQuantities (menuItem text, chickenBreast float, flour float, salt float, blackPepper float, fries float, thickBread float, potatoSalad float, liquidMargarine float, garlicPowder float, ranch float, ketchupLgContainer float, ketchupPackets float, mayo float, teaBags float, sugarForTea float, worcestershireSauce float, slicedCheese float, baconSlices float, fryerOil float, jonesdrjones float, jonesOrangeCream float, jonesRootBeer float, jonesCola float, jonesLemonLime float, jonesSugarFreeCola float, bottledRootBeer float, bottledCreamSoda float, bottledOrangeCream float, bottledBerryLemonade float);";
    //    String createTable3 = "CREATE TABLE Orders (orderID int, cost float, soldBy date, workerID int, managerID int, menu501 int, menu502 int, menu503 int, menu504 int, menu505 int, menu506 int, menu507 int, menu508 int, menu509 int, menu510 int, menu511 int, menu512 int, menu513 int, menu514 int, menu515 int, menu516 int, menu517 int, menu518 int, menu519 int);";
    //    String createTable4 = "CREATE TABLE Manager (managerID int, salary float, firstName text, lastName text);";
       String createTable5 = "CREATE TABLE Inventory (sku text, description text, location text, serving int, price float, weight float, expirationDate date, quantity int);";
    //    String createTable6 = "CREATE TABLE ConversionChart (description text, sku text, deliveredBy text, inPounds float, inOunces float, isMenuItem bool);";

       // Creating all the tables 
    //    stmt.executeUpdate(createTable1);
      //  stmt.executeUpdate(createTable2);
    //    stmt.executeUpdate(createTable3);
    //    stmt.executeUpdate(createTable4);
       stmt.executeUpdate(createTable5);
    //    stmt.executeUpdate(createTable6);

        // Reading in all the CSV files
        // ArrayList<ArrayList<String>> managerList = readCSVFileName("Entities/Manager.csv");
        // ArrayList<ArrayList<String>> workerList = readCSVFileName("Entities/Worker.csv");
        // ArrayList<ArrayList<String>> recipeList = readCSVFileName("Entities/RecipeQuantities.csv");
        // ArrayList<ArrayList<String>> orderList = readCSVFileName("Entities/Orders.csv");
        ArrayList<ArrayList<String>> inventoryList = readCSVFileName("Entities/Inventory.csv");
        // ArrayList<ArrayList<String>> conversionList = readCSVFileName("Entities/conversion_chart.csv");

        // For Manager
        // for(int i = 0; i < managerList.size(); i++) {

        //     String managerID = managerList.get(i).get(0); // int
        //     String salary = managerList.get(i).get(1); // int
        //     String firstName = managerList.get(i).get(2); // text
        //     String lastName = managerList.get(i).get(3); // text

        //     String sqlStatement = String.format("INSERT INTO Manager VALUES ('%s', %s, '%s', '%s');", managerID, salary, firstName, lastName);
        //     // Running a query
        //     stmt.executeUpdate(sqlStatement);
        // }

        // For Worker
        // for(int i = 0; i < workerList.size(); i++) {

        //     String workerID = workerList.get(i).get(0); // int
        //     String salary = workerList.get(i).get(1); // float
        //     String firstName = workerList.get(i).get(2); // text
        //     String lastName = workerList.get(i).get(3); // text

        //     // Query String
        //     String sqlStatement = String.format("INSERT INTO Worker VALUES ('%s', %s, '%s', '%s');", workerID, salary, firstName, lastName);
        //     // Running a query
        //     stmt.executeUpdate(sqlStatement);
        // }

        // For RecipeQuantities
        // for(int i = 0; i < recipeList.size(); i++) {

        //     String menuItem = recipeList.get(i).get(0); // text
        //     String chickenBreast = recipeList.get(i).get(1); // int
        //     String flour = recipeList.get(i).get(2); // int
        //     String salt = recipeList.get(i).get(3); // int
        //     String blackPepper = recipeList.get(i).get(4); // int
        //     String fries = recipeList.get(i).get(5); // int
        //     String thickBread = recipeList.get(i).get(6); // int
        //     String potatoSalad = recipeList.get(i).get(7); // int
        //     String liquidMargarine = recipeList.get(i).get(8); // int
        //     String garlicPowder = recipeList.get(i).get(9); // int
        //     String ranch = recipeList.get(i).get(10); // int
        //     String ketchupLgContainer = recipeList.get(i).get(11); // int
        //     String ketchupPackets = recipeList.get(i).get(12); // int
        //     String mayo = recipeList.get(i).get(13); // int
        //     String teaBags = recipeList.get(i).get(14); // int
        //     String sugarForTea = recipeList.get(i).get(15); // int
        //     String worcestershireSauce = recipeList.get(i).get(16); // int
        //     String slicedCheese = recipeList.get(i).get(17); // int
        //     String baconSlices = recipeList.get(i).get(18); // int
        //     String fryerOil = recipeList.get(i).get(19); // int
        //     String jonesdrjones = recipeList.get(i).get(20);
        //     String jonesOrangeCream = recipeList.get(i).get(21); // int
        //     String jonesRootBeer = recipeList.get(i).get(22); // int
        //     String jonesCola = recipeList.get(i).get(23); // int
        //     String jonesLemonLime = recipeList.get(i).get(24); // int
        //     String jonesSugarFreeCola = recipeList.get(i).get(25); // int
        //     String bottledRootBeer = recipeList.get(i).get(26); // int
        //     String bottledCreamSoda = recipeList.get(i).get(27); // int
        //     String bottledOrangeCream = recipeList.get(i).get(28); // int
        //     String bottledBerryLemonade = recipeList.get(i).get(29); // int

        //     // Query String
        //     String sqlStatement = String.format("INSERT INTO RecipeQuantities VALUES ('%s', %s, %s, %s, %s, %s,%s, %s, %s, %s,%s, %s, %s, %s,%s, %s, %s, %s,%s, %s, %s, %s,%s, %s, %s, %s,%s, %s, %s, %s);", menuItem, chickenBreast, flour, salt, blackPepper, fries, thickBread, potatoSalad, liquidMargarine, garlicPowder, ranch, ketchupLgContainer, ketchupPackets, mayo, teaBags, sugarForTea, worcestershireSauce, slicedCheese, baconSlices, fryerOil, jonesdrjones, jonesOrangeCream, jonesRootBeer, jonesCola, jonesLemonLime, jonesSugarFreeCola, bottledRootBeer, bottledCreamSoda, bottledOrangeCream, bottledBerryLemonade);
        //     // Running a query
        //     stmt.executeUpdate(sqlStatement);
        // }

        // For Orders
        // for(int i = 0; i < orderList.size(); i++) {

        //     String orderID = orderList.get(i).get(0); // int
        //     String cost = orderList.get(i).get(1); // float
        //     String soldBy = orderList.get(i).get(2); // date
        //     String workerID = orderList.get(i).get(3); // int
        //     String managerID = orderList.get(i).get(4); // int
        //     String menu501 = orderList.get(i).get(5); // int
        //     String menu502 = orderList.get(i).get(6); // int
        //     String menu503 = orderList.get(i).get(7); // int
        //     String menu504 = orderList.get(i).get(8); // int 
        //     String menu505 = orderList.get(i).get(9); // int
        //     String menu506 = orderList.get(i).get(10); // int
        //     String menu507 = orderList.get(i).get(11); // int
        //     String menu508 = orderList.get(i).get(12); // int
        //     String menu509 = orderList.get(i).get(13); // int
        //     String menu510 = orderList.get(i).get(14); // int
        //     String menu511 = orderList.get(i).get(15); // int
        //     String menu512 = orderList.get(i).get(16); // int
        //     String menu513 = orderList.get(i).get(17); // int
        //     String menu514 = orderList.get(i).get(18); // int
        //     String menu515 = orderList.get(i).get(19); // int
        //     String menu516 = orderList.get(i).get(20); // int
        //     String menu517 = orderList.get(i).get(21); // int
        //     String menu518 = orderList.get(i).get(22); // int
        //     String menu519 = orderList.get(i).get(23); // int
            
        //     // Query String
        //     String sqlStatement = String.format("INSERT INTO Orders VALUES (%s, %s, '%s', %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);", orderID, cost, soldBy, workerID, managerID, menu501, menu502, menu503, menu504, menu505, menu506, menu507, menu508, menu509, menu510, menu511, menu512, menu513, menu514, menu515, menu516, menu517, menu518, menu519);
        //     // Running a query
        //     stmt.executeUpdate(sqlStatement);
        // }

        // For Inventory
        for(int i = 0; i < inventoryList.size(); i++) {

            String sku = inventoryList.get(i).get(0); //text
            String description = inventoryList.get(i).get(1); //text
            String location = inventoryList.get(i).get(2); //text
            String serving = inventoryList.get(i).get(3); //int
            String price = inventoryList.get(i).get(4); //float
            String weight = inventoryList.get(i).get(5); //float
            String expirationDate = inventoryList.get(i).get(6); //date
            String quantity = inventoryList.get(i).get(7); //float
            
            // Query String
            String sqlStatement = String.format("INSERT INTO Inventory VALUES ('%s', '%s', '%s', %s, %s, %s, '%s', %s);", sku, description, location, serving, price, weight, expirationDate, quantity);
            // Running a query
            stmt.executeUpdate(sqlStatement);
        }

        // For ConversionChart
        // for(int i = 0; i < conversionList.size(); i++) {

        //     String description = conversionList.get(i).get(0); //text
        //     String sku = conversionList.get(i).get(1); //text
        //     String deliveredBy = conversionList.get(i).get(2); //text
        //     String inPound = conversionList.get(i).get(3); //float
        //     String inOunce = conversionList.get(i).get(4); //float
        //     String isMenuItem = conversionList.get(i).get(5); //bool
        //     // Query String
        //     String sqlStatement = String.format("INSERT INTO ConversionChart VALUES ('%s', '%s', '%s', %s, %s, %s);", description, sku, deliveredBy, inPound, inOunce, isMenuItem);
        //     // Running a query
        //     stmt.executeUpdate(sqlStatement);
        // }
       System.out.println("--------------------Query Results--------------------");
   } catch (Exception e){
       e.printStackTrace();
       System.err.println(e.getClass().getName()+": "+e.getMessage());
       System.exit(0);
   }

    //closing the connection
    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch(Exception e) {
      System.out.println("Connection NOT Closed.");
    }
  }
}


