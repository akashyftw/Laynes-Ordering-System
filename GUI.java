import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;

public class GUI extends JFrame {
    private int amount = 0;
    private boolean isManager;
    private ArrayList<String> order;
    private ArrayList<String> ingredientNames;
    private Dictionary<String, Double> ingredients;
    private String dates[] = { "2022-02-01", "2022-02-02", "2022-02-03", "2022-02-04", "2022-02-05", "2022-02-06",
            "2022-02-07", "2022-02-08", "2022-02-09", "2022-02-10", "2022-02-11", "2022-02-12",
            "2022-02-13", "2022-02-14", "2022-02-15", "2022-02-16", "2022-02-17", "2022-02-18", "2022-02-19",
            "2022-02-20", "2022-02-21", "2022-02-22", "2022-02-23", "2022-02-24", "2022-02-25",
            "2022-02-26", "2022-02-27", "2022-02-28" };
    private String startDateOP;
    private String endDateOP;
    private String startDayOP;
    private String endDayOP;
    private int numStartDayOP;
    private int numEndDayOP;

    private String startDateIUC;
    private String endDateIUC;
    private String startDayIUC;
    private String endDayIUC;
    private int numStartDayIUC;
    private int numEndDayIUC;

    private JPanel contentPanel = new JPanel();
    private CardLayout cardLayout = new CardLayout();

    /****** DATABASE ELEMENT ******/
    private Connection conn = null;

    /****** LOGIN ELEMENTS *******/
    private GridBagLayout layoutLogin = new GridBagLayout();
    private GridBagConstraints layoutLoginGBC = new GridBagConstraints();
    private JPanel panelLogin = new JPanel();
    private JButton submit = new JButton("SUBMIT");
    private JButton button1 = new JButton("Auto Login for Manager");
    private JButton button2 = new JButton("Auto Login for Server");

    private JLabel user = new JLabel("Username");
    private JLabel pass = new JLabel("Password");
    private JTextField userIn = new JTextField(20);
    private JTextField passIn = new JTextField(20);

    /****** MANAGER HOME ELEMENTS *******/
    private GridBagLayout layoutManager = new GridBagLayout();
    private GridBagConstraints layoutManagerGBC = new GridBagConstraints();
    private JPanel panelManagerHome = new JPanel();
    private JButton button3M = new JButton("Back");
    private JButton buttonInventory = new JButton("Inventory Management");
    private JButton buttonOrderM = new JButton("Create Order");
    
    private JButton buttonData = new JButton("Data Analysis");
    private JButton quit1 = new JButton("Quit");

    /****** SERVER HOME ELEMENTS *******/
    private GridBagLayout layoutServer = new GridBagLayout();
    private GridBagConstraints layoutServerGBC = new GridBagConstraints();
    private JPanel panelServerHome = new JPanel();
    private JButton button3S = new JButton("Back");
    private JButton buttonOrderS = new JButton("Create Order");
    
    private JButton quit2 = new JButton("Quit");

    /****** INVENTORY ELEMENTS *******/
    private GridBagLayout layoutInv = new GridBagLayout();
    private GridBagConstraints layoutInvGBC = new GridBagConstraints();

    private JPanel panelInventory = new JPanel();
    private JButton button3I = new JButton("Back");

    private JTextArea invUpdate = new JTextArea("Update Inventory Manually");
    private JTextArea invAddItem = new JTextArea("Add New Menu Item");
    private JLabel newItem = new JLabel("New Item");
    private JLabel deleteItem = new JLabel("Item to Delete");
    private JLabel itemChange = new JLabel("Item");
    private JLabel quantChange = new JLabel("New Quantity");
    private JTextField itemChangeIn = new JTextField(20);
    private JTextField quantChangeIn = new JTextField(20);
    private JTextField newItemIn = new JTextField(20);
    private JTextField deleteItemIn = new JTextField(20);
    private JButton buttonInvEdit = new JButton("Edit Inventory");
    private JButton buttonAddMenu = new JButton("Add Menu Item");
    private JButton buttonDeleteItem = new JButton("Delete Menu Item");
    private JButton buttonRestock = new JButton("Restock Report");

    private String[] invTableHead = { "Item", "Weight", "Minimum Quantity" };
    private DefaultTableModel invDtmFood = new DefaultTableModel(invTableHead, 0);
    private JTable invTableFood = new JTable(invDtmFood);

    private String[] invTableHead1 = { "Item", "Weight", "Minimum Quantity" };
    private DefaultTableModel invDtmSupplies = new DefaultTableModel(invTableHead1, 0);
    private JTable invTableSupplies = new JTable(invDtmSupplies);
    private ArrayList<Integer> minQuants = new ArrayList<Integer>(Arrays.asList(
            3200, 420, 20, 700, 320, // sliced cheese
            44, 1400, 320, 80, 1300, // thick bread
            60, 800, 300, 850, 90, // tea bags
            780, 340, 2570, 520, 520, // jones orange cream
            520, 520, 520, 520, 1300, // bottled rootbeer
            1300, 1300, 1300,
            200, 170, 130, 630, 230, // napkin
            380, 30, 130, 90, 250, // 5.5 oz clear cup
            100, 800, 800, 300, 140, // hand sanitizer
            60, 340, 130, 200, 340, // toilet paper
            360, 450, 80, 7460)); // fries -- end of food

    /****** DATA ANALYSIS ELEMENTS *******/
    private GridBagLayout layoutData = new GridBagLayout();
    private GridBagConstraints layoutDataGBC = new GridBagConstraints();
    private JPanel panelData = new JPanel();
    private JButton buttonIUC = new JButton("Inventory Usage Chart");
    private JButton buttonRR = new JButton("Restock Report");
    private JButton buttonOP = new JButton("Ordering Popularity");
    private JButton button3DA = new JButton("Back");

    /****** INVENTORY USAGE CHART *******/
    private GridBagLayout layoutInventoryUsage = new GridBagLayout();
    private GridBagConstraints layoutInventoryUsageGBC = new GridBagConstraints();
    private JPanel panelInventoryUsage = new JPanel();
    private JComboBox<String> startingIUC = new JComboBox<String>(dates);
    private JComboBox<String> endingIUC = new JComboBox<String>(dates);
    private JButton buttonExitIUC = new JButton("Exit");
    private JButton buttonConfirmIUC = new JButton("Confirm");

    private String[] inventoryTableUsageHead = { "Description", "Quantity Used" };
    private DefaultTableModel usageDtm = new DefaultTableModel(inventoryTableUsageHead, 0);
    private JTable inventoryUsageTable = new JTable(usageDtm);

    /****** RESTOCK REPORT *******/
    private GridBagLayout layoutRestockReport = new GridBagLayout();
    private GridBagConstraints layoutRestockReportGBC = new GridBagConstraints();
    private JPanel panelRestockReport = new JPanel();
    private JButton buttonExitRR = new JButton("Exit");

    /****** ORDERING POPULARITY *******/
    private GridBagLayout layoutOrderingPopularity = new GridBagLayout();
    private GridBagConstraints layoutOrderingPopularityGBC = new GridBagConstraints();
    private JPanel panelOrderingPopularity = new JPanel();
    private JButton buttonExitOP = new JButton("Exit");
    private JButton buttonConfirmOP = new JButton("Confirm");
    private JComboBox<String> startingOP = new JComboBox<String>(dates);
    private JComboBox<String> endingOP = new JComboBox<String>(dates);

    /****** HOURS LOG ELEMENTS *******/
    private JPanel panelHours = new JPanel();
    private JButton button3HL = new JButton("Back");
    private JTextArea name3 = new JTextArea("Hours Log View");

    /****** TAKE ORDER ELEMENTS *******/
    private GridBagLayout layoutOrder = new GridBagLayout();
    private GridBagConstraints layoutOrderGBC = new GridBagConstraints();
    private JPanel panelOrder = new JPanel();
    private JButton button3TO = new JButton("Back");

    private String[] orderTableHead = { "Item Name", "Price" };
    private DefaultTableModel orderDtm = new DefaultTableModel(orderTableHead, 0);
    private JTable orderTable = new JTable(orderDtm);

    ImageIcon chickenfing = new ImageIcon("MenuItemImages/chickenfingerside.png");
    Image scaleImage = chickenfing.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton sideChickFing = new JButton(new ImageIcon(scaleImage));
    ImageIcon sandWich = new ImageIcon("MenuItemImages/sandwich.png");
    Image scaleImage2 = sandWich.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton itemSandwich = new JButton(new ImageIcon(scaleImage2));
    ImageIcon clubSand = new ImageIcon("MenuItemImages/clubSandwich.png");
    Image scaleImage3 = clubSand.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton itemClubSandwich = new JButton(new ImageIcon(scaleImage3));
    ImageIcon fries = new ImageIcon("MenuItemImages/fries.png");
    Image scaleImage4 = fries.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton sideFries = new JButton(new ImageIcon(scaleImage4));
    ImageIcon grilledCheeseThing = new ImageIcon("MenuItemImages/grilledCheese.png");
    Image scaleImage5 = grilledCheeseThing.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton itemGrilledCheese = new JButton(new ImageIcon(scaleImage5));
    ImageIcon potatoIcon = new ImageIcon("MenuItemImages/potatoSalad.png");
    Image scaleImage6 = potatoIcon.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton sidePotatoSalad = new JButton(new ImageIcon(scaleImage6));

    ImageIcon texasToast = new ImageIcon("MenuItemImages/texasToast.png");
    Image scaleImage8 = texasToast.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton sideTexasToast = new JButton(new ImageIcon(scaleImage8));


    // NEW ITEM ELEMENTS///
    
    private String newMenuItem;
    /////

    /// DRINKS AND LAYNES SAUCE ////

    ImageIcon tea = new ImageIcon("MenuItemImages/gallonofteaImage.png");
    Image scaleImageTea = tea.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton drinkGallonOfTea = new JButton(new ImageIcon(scaleImageTea));

    ImageIcon bottle = new ImageIcon("MenuItemImages/bottleDrink.png");
    Image scaleImageBottle = bottle.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton drinkBottle = new JButton(new ImageIcon(scaleImageBottle));

    ImageIcon fountain = new ImageIcon("MenuItemImages/fountainDrink.png");
    Image scaleImageFountain = fountain.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton drinkFountain = new JButton(new ImageIcon(scaleImageFountain));

    ImageIcon sauce = new ImageIcon("MenuItemImages/laynesSauce.png");
    Image scaleSauce = sauce.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton itemSauce = new JButton(new ImageIcon(scaleSauce));

    private JButton checkOut = new JButton("Check Out");

    ///// DRINKS ////

    ///// DUPICALTE MEALS IMAGES //////
    ImageIcon chickenfing5meal = new ImageIcon("MenuItemImages/chickenfinger5meal.png");
    Image scaleImage5meal = chickenfing5meal.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton mealChickenFing5 = new JButton(new ImageIcon(scaleImage5meal));

    ImageIcon chickenfing4meal = new ImageIcon("MenuItemImages/chickenfinger4meal.png");
    Image scaleImage4meal = chickenfing4meal.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton mealChickenFing4 = new JButton(new ImageIcon(scaleImage4meal));

    ImageIcon chickenfing3meal = new ImageIcon("MenuItemImages/chickenfinger3meal.png");
    Image scaleImage3meal = chickenfing3meal.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton mealChickenFing3 = new JButton(new ImageIcon(scaleImage3meal));

    ImageIcon chickenfingKidsmeal = new ImageIcon("MenuItemImages/kidsfingermeal.png");
    Image scaleImageKidsmeal = chickenfingKidsmeal.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton mealChickenFingKid = new JButton(new ImageIcon(scaleImageKidsmeal));

    ImageIcon familypack = new ImageIcon("MenuItemImages/familymeal.png");
    Image familypackImage = familypack.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton mealFamilyPack = new JButton(new ImageIcon(familypackImage));

    ///// CLUB SANDWICH DUPLICATE ///
    ImageIcon clubSandmeal = new ImageIcon("MenuItemImages/clubSandwichmeal.png");
    Image scaleImageclub = clubSandmeal.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton mealClubSandwich = new JButton(new ImageIcon(scaleImageclub));
    ///// CLUB SANDWICH DUPLICATE ///

    //// SANDWICH DUPLICATE /////

    ImageIcon sandWichMeal = new ImageIcon("MenuItemImages/sanwichMeal.png");
    Image scaleImageSandwichMeal = sandWichMeal.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton mealSandwich = new JButton(new ImageIcon(scaleImageSandwichMeal));

    //// SANDWICH DUPLICATE /////

    //// griled cheese dup ///
    ImageIcon grilledCheeseMeals = new ImageIcon("MenuItemImages/grilledCheeseMeal.png");
    Image scaleImage5CheeseMeal = grilledCheeseMeals.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    private JButton mealGrilledCheese = new JButton(new ImageIcon(scaleImage5CheeseMeal));
    //// griled cheese dup ///


    ImageIcon newItemToAdd = new ImageIcon("MenuItemImages/laynesImage.png");
                        
    Image scaleNewItem = newItemToAdd.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
    
    JButton newItemButton = new JButton(new ImageIcon(scaleNewItem));

    actionListener al = new actionListener();
    actionListener cl = new actionListener();

    /////// DUPLICATE MEALS

    public GUI() {
        super("Laynes Inventory");

        order = new ArrayList<String>();
        ingredientNames = new ArrayList<String>();
        ingredients = new Hashtable<String, Double>();

        ingredients.put("chickenbreast", 0.0);
        ingredients.put("flour", 0.0);
        ingredients.put("salt", 0.0);
        ingredients.put("blackpepper", 0.0);
        ingredients.put("fries", 0.0);
        ingredients.put("thickbread", 0.0);
        ingredients.put("potatosalad", 0.0);
        ingredients.put("liquidmargarine", 0.0);
        ingredients.put("garlicpowder", 0.0);
        ingredients.put("ranch", 0.0);
        ingredients.put("ketchuplgcontainer", 0.0);
        ingredients.put("ketchuppackets", 0.0);
        ingredients.put("mayo", 0.0);
        ingredients.put("teabags", 0.0);
        ingredients.put("sugarfortea", 0.0);
        ingredients.put("worcestershiresauce", 0.0);
        ingredients.put("slicedcheese", 0.0);
        ingredients.put("baconslices", 0.0);
        ingredients.put("fryeroil", 0.0);
        ingredients.put("jonesdrjones", 0.0);
        ingredients.put("jonesorangecream", 0.0);
        ingredients.put("jonesrootbeer", 0.0);
        ingredients.put("jonescola", 0.0);
        ingredients.put("joneslemonlime", 0.0);
        ingredients.put("jonessugarfreecola", 0.0);
        ingredients.put("bottledrootbeer", 0.0);
        ingredients.put("bottledcreamsoda", 0.0);
        ingredients.put("bottledorangecream", 0.0);
        ingredients.put("bottledberrylemonade", 0.0);

        ingredientNames.add("chickenbreast");
        ingredientNames.add("flour");
        ingredientNames.add("salt");
        ingredientNames.add("blackpepper");
        ingredientNames.add("fries");
        ingredientNames.add("thickbread");
        ingredientNames.add("potatosalad");
        ingredientNames.add("liquidmargarine");
        ingredientNames.add("garlicpowder");
        ingredientNames.add("ranch");
        ingredientNames.add("ketchuplgcontainer");
        ingredientNames.add("ketchuppackets");
        ingredientNames.add("mayo");
        ingredientNames.add("teabags");
        ingredientNames.add("sugarfortea");
        ingredientNames.add("worcestershiresauce");
        ingredientNames.add("slicedcheese");
        ingredientNames.add("baconslices");
        ingredientNames.add("fryeroil");
        ingredientNames.add("jonesdrjones");
        ingredientNames.add("jonesorangecream");
        ingredientNames.add("jonesrootbeer");
        ingredientNames.add("jonescola");
        ingredientNames.add("joneslemonlime");
        ingredientNames.add("jonessugarfreecola");
        ingredientNames.add("bottledrootbeer");
        ingredientNames.add("bottledcreamsoda");
        ingredientNames.add("bottledorangecream");
        ingredientNames.add("bottledberrylemonade");

        

        // buttons to test coding -- delete for final iteration
        button1.addActionListener(al);
        button2.addActionListener(al);
        button3M.addActionListener(al);
        button3S.addActionListener(al);
        button3DA.addActionListener(al);
        button3TO.addActionListener(al);
        button3I.addActionListener(al);
        button3HL.addActionListener(al);
        quit1.addActionListener(al);
        quit2.addActionListener(al);
        

        // add views/pages to contentPanel
        contentPanel.setLayout(cardLayout);

        contentPanel.add(panelLogin, "Login");
        contentPanel.add(panelManagerHome, "Manager Home");
        contentPanel.add(panelServerHome, "Server Home");
        contentPanel.add(panelInventory, "Inventory");
        contentPanel.add(panelData, "Data Analysis");
        contentPanel.add(panelInventoryUsage, "Inventory Usage");
        contentPanel.add(panelRestockReport, "Restock Report");
        contentPanel.add(panelOrderingPopularity, "Ordering Popularity");
        contentPanel.add(panelHours, "Hours Log");
        contentPanel.add(panelOrder, "Take Order");

        // show login view as default
        this.setContentPane(contentPanel);
        cardLayout.show(contentPanel, "Login");

        // colors for GUI
        String white = "#FFFFFF"; // panel background color
        String peach = "#FFE5B0"; // login text fields, home buttons
        String vanilla = "#F3E5AB";
        String mustard = "#FFDB58"; // page headers

        /*** Login Page Setup ***/
        panelLogin.setLayout(layoutLogin);
        layoutLoginGBC.anchor = GridBagConstraints.LINE_START;
        layoutLoginGBC.fill = GridBagConstraints.BOTH;
        layoutLoginGBC.insets = new Insets(5, 5, 10, 10);

        panelLogin.setBackground(Color.decode(white));
        submit.addActionListener(al);
        submit.setBackground(Color.decode(mustard));
        submit.setOpaque(true);
        submit.setBorderPainted(false);
        userIn.setBackground(Color.decode(vanilla));
        passIn.setBackground(Color.decode(vanilla));

        layoutLoginGBC.gridx = 0;
        layoutLoginGBC.gridy = 0;
        panelLogin.add(user, layoutLoginGBC);
        layoutLoginGBC.gridx = 1;
        layoutLoginGBC.gridy = 0;
        panelLogin.add(userIn, layoutLoginGBC);
        layoutLoginGBC.gridx = 0;
        layoutLoginGBC.gridy = 1;
        panelLogin.add(pass, layoutLoginGBC);
        layoutLoginGBC.gridx = 1;
        layoutLoginGBC.gridy = 1;
        panelLogin.add(passIn, layoutLoginGBC);
        layoutLoginGBC.gridwidth = 2;
        layoutLoginGBC.gridheight = 2;
        layoutLoginGBC.gridx = 0;
        layoutLoginGBC.gridy = 2;
        panelLogin.add(submit, layoutLoginGBC);
        layoutLoginGBC.gridheight = 1;
        layoutLoginGBC.gridx = 0;
        layoutLoginGBC.gridy = 4;
        panelLogin.add(button1, layoutLoginGBC);
        layoutLoginGBC.gridx = 0;
        layoutLoginGBC.gridy = 5;
        panelLogin.add(button2, layoutLoginGBC);

        /*** Manager Home Page Setup ***/
        panelManagerHome.setLayout(layoutManager);
        layoutManagerGBC.anchor = GridBagConstraints.LINE_START;
        layoutManagerGBC.fill = GridBagConstraints.BOTH;
        layoutManagerGBC.insets = new Insets(5, 5, 10, 10);

        panelManagerHome.setBackground(Color.decode(peach));
        buttonInventory.setBackground(Color.decode(white));
        buttonInventory.setOpaque(true);
        buttonInventory.setBorderPainted(true);
        buttonData.setBackground(Color.decode(white));
        buttonData.setOpaque(true);
        buttonData.setBorderPainted(true);
        buttonOrderM.setBackground(Color.decode(white));
        buttonOrderM.setOpaque(true);
        buttonOrderM.setBorderPainted(true);


        buttonInventory.addActionListener(al);
        buttonOrderM.addActionListener(al);
 
        buttonData.addActionListener(al);
        // buttonInventory.setBorder(BorderFactory.createBevelBorder(1, Color.BLACK,
        // Color.BLACK));
        // buttonOrderM.addActionListener(al);
        // buttonHoursM.addActionListener(al);
        // buttonData.addActionListener(al);
        layoutManagerGBC.gridwidth = 2;
        layoutManagerGBC.gridx = 0;
        layoutManagerGBC.gridy = 0;
        panelManagerHome.add(buttonInventory, layoutManagerGBC);
        layoutManagerGBC.gridx = 0;
        layoutManagerGBC.gridy = 1;
        panelManagerHome.add(buttonOrderM, layoutManagerGBC);
        layoutManagerGBC.gridx = 1;
        layoutManagerGBC.gridy = 2;
        panelManagerHome.add(buttonData, layoutManagerGBC);
        layoutManagerGBC.gridx = 1;
        layoutManagerGBC.gridy = 3;
        panelManagerHome.add(button3M, layoutManagerGBC);
        layoutManagerGBC.gridx = 1;
        layoutManagerGBC.gridy = 4;
        panelManagerHome.add(quit1, layoutManagerGBC);

        /*** Server Home Page Setup ***/
        panelServerHome.setLayout(layoutServer);
        layoutServerGBC.anchor = GridBagConstraints.LINE_START;
        layoutServerGBC.fill = GridBagConstraints.BOTH;
        layoutServerGBC.insets = new Insets(5, 5, 10, 10);

        panelServerHome.setBackground(Color.decode(peach));
        buttonOrderS.setBackground(Color.decode(white));
        buttonOrderS.setOpaque(true);
        buttonOrderS.setBorderPainted(true);


        buttonOrderS.addActionListener(al);


        panelServerHome.add(buttonOrderS);
        layoutServerGBC.gridwidth = 2;
        layoutServerGBC.gridx = 0;
        layoutServerGBC.gridy = 0;
        panelServerHome.add(buttonOrderS, layoutServerGBC);

        layoutServerGBC.gridx = 0;
        layoutServerGBC.gridy = 1;
        panelServerHome.add(button3S, layoutServerGBC);
        layoutServerGBC.gridx = 0;
        layoutServerGBC.gridy = 2;
        panelServerHome.add(quit2, layoutServerGBC);

        /*** Inventory Page Setup ***/
        panelInventory.setLayout(layoutLogin);
        layoutInvGBC.anchor = GridBagConstraints.LINE_START;
        layoutInvGBC.fill = GridBagConstraints.BOTH;
        layoutInvGBC.insets = new Insets(10, 10, 10, 10);

        panelInventory.setBackground(Color.decode(white));
        buttonInvEdit.addActionListener(al);
        buttonInvEdit.setBackground(Color.decode(mustard));
        buttonInvEdit.setOpaque(true);
        buttonInvEdit.setBorderPainted(false);
        buttonAddMenu.addActionListener(al);
        buttonAddMenu.setBackground(Color.decode(mustard));
        buttonAddMenu.setOpaque(true);
        buttonAddMenu.setBorderPainted(false);
        buttonDeleteItem.addActionListener(al);
        buttonDeleteItem.setBackground(Color.decode(mustard));
        buttonDeleteItem.setOpaque(true);
        buttonDeleteItem.setBorderPainted(false);
        buttonRestock.addActionListener(al);
        buttonRestock.setBackground(Color.decode(mustard));
        buttonRestock.setOpaque(true);
        buttonRestock.setBorderPainted(false);
        itemChangeIn.setBackground(Color.decode(vanilla));
        quantChangeIn.setBackground(Color.decode(vanilla));
        newItemIn.setBackground(Color.decode(vanilla));
        deleteItemIn.setBackground(Color.decode(vanilla));

        Font font = new Font("Segoe Script", Font.BOLD, 20);
        invUpdate.setFont(font);
        invAddItem.setFont(font);

        // layoutInvGBC.gridwidth = 2;
        layoutInvGBC.gridx = 0;
        layoutInvGBC.gridy = 0;
        panelInventory.add(button3I, layoutInvGBC);
        layoutInvGBC.gridx = 2;
        layoutInvGBC.gridy = 1;
        panelInventory.add(invUpdate, layoutInvGBC);

        layoutInvGBC.gridx = 1;
        layoutInvGBC.gridy = 3;
        panelInventory.add(itemChange, layoutInvGBC);
        layoutInvGBC.gridx = 2;
        layoutInvGBC.gridy = 3;
        panelInventory.add(itemChangeIn, layoutInvGBC);
        layoutInvGBC.gridx = 1;
        layoutInvGBC.gridy = 4;
        panelInventory.add(quantChange, layoutInvGBC);
        layoutInvGBC.gridx = 2;
        layoutInvGBC.gridy = 4;
        panelInventory.add(quantChangeIn, layoutInvGBC);
        layoutInvGBC.gridx = 3;
        layoutInvGBC.gridy = 4;
        panelInventory.add(buttonInvEdit, layoutInvGBC);
        layoutInvGBC.gridx = 3;
        layoutInvGBC.gridy = 6;
        panelInventory.add(buttonRestock, layoutInvGBC);
        layoutInvGBC.gridx = 2;
        layoutInvGBC.gridy = 7;
        panelInventory.add(invAddItem, layoutInvGBC);
        layoutInvGBC.gridx = 1;
        layoutInvGBC.gridy = 8;
        panelInventory.add(newItem, layoutInvGBC);
        layoutInvGBC.gridx = 2;
        layoutInvGBC.gridy = 8;
        panelInventory.add(newItemIn, layoutInvGBC);
        layoutInvGBC.gridx = 3;
        layoutInvGBC.gridy = 8;
        panelInventory.add(buttonAddMenu, layoutInvGBC);
        layoutInvGBC.gridx = 1;
        layoutInvGBC.gridy = 9;
        panelInventory.add(deleteItem, layoutInvGBC);
        layoutInvGBC.gridx = 2;
        layoutInvGBC.gridy = 9;
        panelInventory.add(deleteItemIn, layoutInvGBC);
        layoutInvGBC.gridx = 3;
        layoutInvGBC.gridy = 9;
        panelInventory.add(buttonDeleteItem, layoutInvGBC);

        /*** Data Analysis Page Setup ***/
        panelData.setLayout(layoutData);
        layoutDataGBC.anchor = GridBagConstraints.LINE_START;
        layoutDataGBC.fill = GridBagConstraints.BOTH;
        layoutDataGBC.insets = new Insets(5, 5, 5, 5);

        panelData.setBackground(Color.decode(peach));

        buttonIUC.addActionListener(al);
        buttonRR.addActionListener(al);
        buttonOP.addActionListener(al);

        buttonIUC.setBackground(Color.decode(white));
        buttonIUC.setOpaque(true);
        buttonIUC.setBorderPainted(true);
        buttonRR.setBackground(Color.decode(white));
        buttonRR.setOpaque(true);
        buttonRR.setBorderPainted(true);
        buttonOP.setBackground(Color.decode(white));
        buttonOP.setOpaque(true);
        buttonOP.setBorderPainted(true);

        layoutDataGBC.gridx = 0;
        layoutDataGBC.gridy = 0;
        panelData.add(buttonIUC, layoutDataGBC);

        // layoutDataGBC.gridx = 0;
        // layoutDataGBC.gridy = 1;
        // panelData.add(buttonRR, layoutDataGBC);

        layoutDataGBC.gridx = 0;
        layoutDataGBC.gridy = 1;
        panelData.add(buttonOP, layoutDataGBC);

        layoutDataGBC.gridx = 0;
        layoutDataGBC.gridy = 2;
        panelData.add(button3DA, layoutDataGBC);

        /*** Inventory Usage Chart Page Setup ***/
        panelInventoryUsage.setLayout(layoutInventoryUsage);
        layoutInventoryUsageGBC.anchor = GridBagConstraints.LINE_START;
        layoutInventoryUsageGBC.fill = GridBagConstraints.BOTH;
        layoutInventoryUsageGBC.insets = new Insets(5, 5, 5, 5);

        panelInventoryUsage.setBackground(Color.decode(peach));

        buttonExitIUC.addActionListener(al);
        startingIUC.addActionListener(cl);
        endingIUC.addActionListener(cl);
        buttonConfirmIUC.addActionListener(al);

        // array of string containing cities

        // create checkbox

        layoutInventoryUsageGBC.gridx = 0;
        layoutInventoryUsageGBC.gridy = 0;
        panelInventoryUsage.add(startingIUC, layoutInventoryUsageGBC);
        layoutInventoryUsageGBC.gridx = 1;
        layoutInventoryUsageGBC.gridy = 0;
        panelInventoryUsage.add(endingIUC, layoutInventoryUsageGBC);
        layoutInventoryUsageGBC.gridwidth = 2;
        layoutInventoryUsageGBC.gridx = 0;
        layoutInventoryUsageGBC.gridy = 1;
        panelInventoryUsage.add(buttonConfirmIUC, layoutInventoryUsageGBC);
        layoutInventoryUsageGBC.gridx = 0;
        layoutInventoryUsageGBC.gridy = 2;
        panelInventoryUsage.add(buttonExitIUC, layoutInventoryUsageGBC);
        // panelInventoryUsage.add(buttonExitIUC);

        /*** Restock Report Page Setup ***/
        panelRestockReport.setLayout(layoutRestockReport);
        layoutRestockReportGBC.anchor = GridBagConstraints.LINE_START;
        layoutRestockReportGBC.fill = GridBagConstraints.BOTH;
        layoutRestockReportGBC.insets = new Insets(5, 5, 5, 5);

        panelRestockReport.setBackground(Color.decode(peach));

        buttonExitRR.addActionListener(al);

        layoutRestockReportGBC.gridx = 0;
        layoutRestockReportGBC.gridy = 0;
        panelRestockReport.add(buttonExitRR, layoutRestockReportGBC);

        /*** Ordering Popularity Page Setup ***/
        panelOrderingPopularity.setLayout(layoutOrderingPopularity);
        layoutOrderingPopularityGBC.anchor = GridBagConstraints.LINE_START;
        layoutOrderingPopularityGBC.fill = GridBagConstraints.BOTH;
        layoutOrderingPopularityGBC.insets = new Insets(5, 5, 5, 5);

        panelOrderingPopularity.setBackground(Color.decode(peach));

        buttonExitOP.addActionListener(al);
        startingOP.addActionListener(cl);
        endingOP.addActionListener(cl);
        buttonConfirmOP.addActionListener(al);

        layoutOrderingPopularityGBC.gridx = 0;
        layoutOrderingPopularityGBC.gridy = 0;
        panelOrderingPopularity.add(startingOP, layoutOrderingPopularityGBC);
        layoutOrderingPopularityGBC.gridx = 1;
        layoutInventoryUsageGBC.gridy = 0;
        panelOrderingPopularity.add(endingOP, layoutOrderingPopularityGBC);
        layoutOrderingPopularityGBC.gridwidth = 2;
        layoutOrderingPopularityGBC.gridx = 0;
        layoutOrderingPopularityGBC.gridy = 1;
        panelOrderingPopularity.add(buttonConfirmOP, layoutOrderingPopularityGBC);
        layoutOrderingPopularityGBC.gridx = 0;
        layoutOrderingPopularityGBC.gridy = 2;
        panelOrderingPopularity.add(buttonExitOP, layoutOrderingPopularityGBC);

        /*** Hours Log Page Setup ***/
        panelHours.add(name3);
        panelHours.add(button3HL);

        /*** Take Order Page Setup ***/
        panelOrder.setLayout(layoutOrder);
        panelOrder.setBackground(Color.decode(peach));
        layoutOrderGBC.anchor = GridBagConstraints.LINE_START;
        layoutOrderGBC.fill = GridBagConstraints.HORIZONTAL;
        layoutOrderGBC.insets = new Insets(5, 5, 5, 5);

        mealChickenFing5.addActionListener(al);
        mealChickenFing4.addActionListener(al);
        mealChickenFing3.addActionListener(al);
        mealChickenFingKid.addActionListener(al);
        mealFamilyPack.addActionListener(al);
        mealSandwich.addActionListener(al);
        mealClubSandwich.addActionListener(al);
        mealGrilledCheese.addActionListener(al);

        sideFries.addActionListener(al);
        sideChickFing.addActionListener(al);
        sidePotatoSalad.addActionListener(al);
        sideTexasToast.addActionListener(al);

        itemGrilledCheese.addActionListener(al);
        itemSandwich.addActionListener(al);
        itemClubSandwich.addActionListener(al);

        drinkGallonOfTea.addActionListener(al);
        drinkBottle.addActionListener(al);
        drinkFountain.addActionListener(al);

        itemSauce.addActionListener(al);

        newItemButton.addActionListener(al);


        checkOut.addActionListener(al);

        layoutOrderGBC.gridx = 0;
        layoutOrderGBC.gridy = 0;
        panelOrder.add(sideChickFing, layoutOrderGBC); // side
        layoutOrderGBC.gridx = 4;
        layoutOrderGBC.gridy = 0;
        panelOrder.add(itemSandwich, layoutOrderGBC); // item
        layoutOrderGBC.gridx = 0;
        layoutOrderGBC.gridy = 1;
        panelOrder.add(itemClubSandwich, layoutOrderGBC); // item
        layoutOrderGBC.gridx = 1;
        layoutOrderGBC.gridy = 0;
        panelOrder.add(sideFries, layoutOrderGBC); // side
        layoutOrderGBC.gridx = 1;
        layoutOrderGBC.gridy = 1;
        panelOrder.add(itemGrilledCheese, layoutOrderGBC); // item
        layoutOrderGBC.gridx = 2;
        layoutOrderGBC.gridy = 0;
        panelOrder.add(sidePotatoSalad, layoutOrderGBC); // side
        layoutOrderGBC.gridx = 3;
        layoutOrderGBC.gridy = 0;
        panelOrder.add(sideTexasToast, layoutOrderGBC); // side

        //// DUPLICATE IMAGES ////

        layoutOrderGBC.gridx = 2;
        layoutOrderGBC.gridy = 1;
        panelOrder.add(mealChickenFing5, layoutOrderGBC); // meal

        layoutOrderGBC.gridx = 3;
        layoutOrderGBC.gridy = 1;
        panelOrder.add(mealChickenFing4, layoutOrderGBC); // meal

        layoutOrderGBC.gridx = 4;
        layoutOrderGBC.gridy = 1;
        panelOrder.add(mealChickenFing3, layoutOrderGBC); // meal

        layoutOrderGBC.gridx = 0;
        layoutOrderGBC.gridy = 2;
        panelOrder.add(mealChickenFingKid, layoutOrderGBC); // meal

        layoutOrderGBC.gridx = 1;
        layoutOrderGBC.gridy = 2;
        panelOrder.add(mealFamilyPack, layoutOrderGBC); // meal

        layoutOrderGBC.gridx = 2;
        layoutOrderGBC.gridy = 2;
        panelOrder.add(mealClubSandwich, layoutOrderGBC); // meal

        layoutOrderGBC.gridx = 3;
        layoutOrderGBC.gridy = 2;
        panelOrder.add(mealSandwich, layoutOrderGBC); // meal

        layoutOrderGBC.gridx = 4;
        layoutOrderGBC.gridy = 2;
        panelOrder.add(mealGrilledCheese, layoutOrderGBC); // meal

        //// DUPLICATE IMAGES ////

        ///// DRINKS AND LAYNES SAUCE ////

        layoutOrderGBC.gridx = 0;
        layoutOrderGBC.gridy = 3;
        panelOrder.add(drinkGallonOfTea, layoutOrderGBC); // drinks

        layoutOrderGBC.gridx = 1;
        layoutOrderGBC.gridy = 3;
        panelOrder.add(drinkBottle, layoutOrderGBC); // drinks

        layoutOrderGBC.gridx = 2;
        layoutOrderGBC.gridy = 3;
        panelOrder.add(drinkFountain, layoutOrderGBC); // drinks

        layoutOrderGBC.gridx = 3;
        layoutOrderGBC.gridy = 3;
        panelOrder.add(itemSauce, layoutOrderGBC); // sauce

        ///// DRINKS ////

        

        layoutOrderGBC.gridwidth = 3;
        layoutOrderGBC.gridheight = 6;
        layoutOrderGBC.gridx = 0;
        layoutOrderGBC.gridy = 5;
        panelOrder.add(checkOut, layoutOrderGBC);
        layoutOrderGBC.gridx = 4;
        layoutOrderGBC.gridy = 5;
        panelOrder.add(button3TO, layoutOrderGBC);
        

    }

    public static void main(String[] args) {
        GUI g = new GUI();

        g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        g.setSize(1500, 1050);
        g.setVisible(true);

        // Building the connection -- OPEN DATABASE
        try {
            Class.forName("org.postgresql.Driver");
            String teamNumber = "4";
            String sectionNumber = "970";
            String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
            String userName = "csce315" + sectionNumber + "_" + teamNumber + "user";
            String userPassword = "catGoesMeow";
            g.conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName,
                    userName, userPassword);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        // JOptionPane.showMessageDialog(null, "Opened database successfully");

        String name = "";
        try {
            // create a statement object
            Statement stmt = g.conn.createStatement();
            // create an SQL statement
            String sqlStatement = "SELECT * FROM inventory LIMIT 10;";
            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                name += result.getString("description") + "\n";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error accessing Database.");
        }
        // Put in while loop while jpanel open?
        // try {
        // g.conn.close();
        // System.out.println("Connection Closed.");
        // } catch(Exception e) {
        // System.out.println("Connection NOT Closed.");
        // }
    }

    public class actionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            Object src = event.getSource();
            if (src instanceof JComboBox) { // this is for the drop down
                if (src.equals(startingOP)) {
                    startDateOP = startingOP.getSelectedItem().toString();
                    startDayOP = startDateOP.substring(8);
                } else if (src.equals(endingOP)) {
                    endDateOP = endingOP.getSelectedItem().toString();
                    endDayOP = endDateOP.substring(8);
                }
                if (src.equals(startingIUC)) {
                    startDateIUC = startingIUC.getSelectedItem().toString();
                    startDayIUC = startDateIUC.substring(8);
                } else if (src.equals(endingIUC)) {
                    endDateIUC = endingIUC.getSelectedItem().toString();
                    endDayIUC = endDateIUC.substring(8);
                }
            } else if (src instanceof JButton) { // this is for the button
                if (src.equals(button1)) {
                    cardLayout.show(contentPanel, "Manager Home");
                    isManager = true;
                } else if (src.equals(button2)) {
                    cardLayout.show(contentPanel, "Server Home");
                    isManager = false;
                } else if (src.equals(button3M) || src.equals(button3S)) {
                    cardLayout.show(contentPanel, "Login");
                } else if (src.equals(quit1) || src.equals(quit2)) {
                    try {
                        conn.close();
                        System.out.println("Connection Closed.");
                    } catch (Exception e) {
                        System.out.println("Connection NOT Closed.");
                    }
                } else if (src.equals(button3DA) || src.equals(button3HL) || src.equals(button3I)
                        || src.equals(button3TO)) {
                    if (isManager) {
                        cardLayout.show(contentPanel, "Manager Home");
                    } else {
                        cardLayout.show(contentPanel, "Server Home");
                    }
                } else if (src.equals(buttonExitIUC) || src.equals(buttonExitRR) || src.equals(buttonExitOP)) {
                    cardLayout.show(contentPanel, "Data Analysis");

                } else if (src.equals(buttonInventory)) {
                    // switch to Inventory Page
                    cardLayout.show(contentPanel, "Inventory");

                    if (invTableFood.getRowCount() == 0) {
                        // create inventory table
                        int i = 0;

                        try {
                            Statement stmt = conn.createStatement();
                            String sqlStatement = "SELECT * FROM inventory ORDER BY sku ASC;";
                            ResultSet result = stmt.executeQuery(sqlStatement);

                            while (result.next()) {
                                String descrip = result.getString("description");
                                String weight = result.getString("weight");

                                if (i > 27 && i < 51) {
                                    invDtmSupplies.addRow(new Object[] { descrip, weight, minQuants.get(i) });
                                } else {
                                    invDtmFood.addRow(new Object[] { descrip, weight, minQuants.get(i) });
                                }

                                i += 1;
                            }
                        } catch (Exception e) {
                            //JOptionPane.showMessageDialog(null, "Error with login database.");
                        }

                        invTableFood.getColumnModel().getColumn(0).setPreferredWidth(200);
                        invTableFood.getColumnModel().getColumn(1).setPreferredWidth(80);
                        invTableFood.getColumnModel().getColumn(2).setPreferredWidth(50);
                        invTableFood.setEnabled(false);

                        invTableSupplies.getColumnModel().getColumn(0).setPreferredWidth(200);
                        invTableSupplies.getColumnModel().getColumn(1).setPreferredWidth(80);
                        invTableSupplies.getColumnModel().getColumn(2).setPreferredWidth(50);
                        invTableSupplies.setEnabled(false);

                        layoutInvGBC.gridx = 2;
                        layoutInvGBC.gridy = 5;
                        panelInventory.add(invTableFood, layoutInvGBC);
                        layoutInvGBC.gridx = 3;
                        layoutInvGBC.gridy = 5;
                        panelInventory.add(invTableSupplies, layoutInvGBC);
                    }

                } else if (src.equals(buttonInvEdit)) {
                    // get name of item and quant to change in inventory
                    String itemToChange = itemChangeIn.getText();
                    String quantToChange = quantChangeIn.getText();

                    // access database to check if item exists
                    String currWeight = "";
                    try {
                        Statement stmt = conn.createStatement();
                        String sqlStatement = "SELECT weight FROM inventory WHERE description = '" + itemToChange
                                + "';";
                        ResultSet result = stmt.executeQuery(sqlStatement);
                        while (result.next()) {
                            currWeight += result.getString("weight");
                        }
                    } catch (Exception e) {
                        //JOptionPane.showMessageDialog(null, "Error with database inventory");
                    }

                    int row = -1;
                    boolean supplies = false;
                    for (int i = 0; i < invDtmSupplies.getRowCount(); ++i) {
                        if (invDtmSupplies.getValueAt(i, 0).equals(itemToChange)) {
                            row += i + 1;
                            supplies = true;
                        }
                    }

                    if (row == -1) {
                        // JOptionPane.showMessageDialog(null, "This item can not be modified
                        // manually.");
                        for (int i = 0; i < invDtmFood.getRowCount(); ++i) {
                            if (invDtmFood.getValueAt(i, 0).equals(itemToChange)) {
                                row += i + 1;
                            }
                        }
                    }

                    if (currWeight == "") {
                        JOptionPane.showMessageDialog(null, "Please enter an item that exists.");
                    } else {
                        try {
                            Integer currQuant = Integer.valueOf(currWeight);
                            Integer newQuant = Integer.valueOf(quantToChange);

                            if (newQuant < 0) {
                                JOptionPane.showMessageDialog(null, "Please enter a positive quantity.");
                            } else if (newQuant == currQuant) {
                                JOptionPane.showMessageDialog(null, "Please enter a new quantity.");
                            } else if (supplies) {

                                invDtmSupplies.setValueAt(newQuant, row, 1);

                                try {
                                    Statement stmt = conn.createStatement();
                                    String sqlStatement = "UPDATE inventory SET weight = " + quantToChange
                                            + " WHERE description = '" + itemToChange + "';";
                                    ResultSet result = stmt.executeQuery(sqlStatement);
                                } catch (Exception e) {
                                    // JOptionPane.showMessageDialog(null, "Error entering new inventory quantity");
                                }

                                invDtmSupplies.setRowCount(0);

                                int i = 0;
                                try {
                                    Statement stmt = conn.createStatement();
                                    String sqlStatement = "SELECT * FROM inventory ORDER BY sku ASC;";
                                    ResultSet result = stmt.executeQuery(sqlStatement);

                                    while (result.next()) {
                                        String descrip = result.getString("description");
                                        String weight = result.getString("weight");

                                        if (i > 27 && i < 51) {
                                            invDtmSupplies.addRow(new Object[] { descrip, weight, minQuants.get(i) });
                                        }

                                        i += 1;
                                    }
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null, "Error with INV TABLE database. 1");
                                }

                            } else {
                                invDtmFood.setValueAt(newQuant, row, 1);

                                try {
                                    Statement stmt = conn.createStatement();
                                    String sqlStatement = "UPDATE inventory SET weight = " + quantToChange
                                            + " WHERE description = '" + itemToChange + "';";
                                    ResultSet result = stmt.executeQuery(sqlStatement);
                                } catch (Exception e) {
                                    // JOptionPane.showMessageDialog(null, "Error entering new inventory quantity");
                                }

                                invDtmFood.setRowCount(0);

                                int i = 0;
                                try {
                                    Statement stmt = conn.createStatement();
                                    String sqlStatement = "SELECT * FROM inventory ORDER BY sku ASC;";
                                    ResultSet result = stmt.executeQuery(sqlStatement);

                                    while (result.next()) {
                                        String descrip = result.getString("description");
                                        String weight = result.getString("weight");

                                        if (!(i > 27 && i < 51)) {
                                            invDtmFood.addRow(new Object[] { descrip, weight, minQuants.get(i) });
                                        }

                                        i += 1;
                                    }
                                } catch (Exception e) {
                                    //JOptionPane.showMessageDialog(null, "Error with INV TABLE database. 2");
                                }
                            }

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Please enter an integer quantity.");
                        }
                    }

                } else if (src.equals(buttonRestock)) {
                    String refill = "";
                    for (int i = 0; i < invTableFood.getRowCount(); i++) { // Loop through the rows

                        Float currQuantVal = Float.parseFloat(invTableFood.getValueAt(i, 1).toString());
                        Float minQuantVal = Float.parseFloat(invTableFood.getValueAt(i, 2).toString());

                        if (Float.compare(currQuantVal, minQuantVal) < 0) {
                            refill += " - " + (minQuantVal - currQuantVal) + " ounces of "
                                    + invTableFood.getValueAt(i, 0).toString() + "\n";
                        }

                    }

                    for (int i = 0; i < invTableSupplies.getRowCount(); ++i) {
                        Float currQuantVal = Float.parseFloat(invTableSupplies.getValueAt(i, 1).toString());
                        Float minQuantVal = Float.parseFloat(invTableSupplies.getValueAt(i, 2).toString());

                        if (Float.compare(currQuantVal, minQuantVal) < 0) {
                            refill += " - " + (minQuantVal - currQuantVal) + " ounces of "
                                    + invTableSupplies.getValueAt(i, 0).toString() + "\n";
                        }
                    }

                    JOptionPane.showMessageDialog(null, "The following items need to be restocked: \n" + refill);
                } else if (src.equals(buttonAddMenu)) {
                    newMenuItem = newItemIn.getText();
                    String newMenuItemDatabase = newMenuItem.replaceAll(" ", "").toLowerCase();
                    // System.out.println(newMenuItemDatabase);

                    // add new item to table in inventory page
                    invDtmFood.addRow(new Object[] { newMenuItem, 0, 2400 });

                    // add quantity needed for new item
                    minQuants.add(2400);

                    try { // add new item as an entity in Inventory

                        Statement stmt0 = conn.createStatement();
                        String sqlStatement0 = "INSERT INTO inventory VALUES ('f5046', '" + newMenuItem
                                + "', 'pantry', 0, 40.50, 3500, '2022-04-18', 0 );";
                        stmt0.execute(sqlStatement0);

                        // add new item as an entity in RecipeQuantites
                        //Statement stmt2 = conn.createStatement();
                        //String sqlStatement2 = "ALTER TABLE recipequantities ADD " + newMenuItem +
                        //";";
                        //ResultSet result = stmt2.executeQuery(sqlStatement2);
                        
                        layoutOrderGBC.gridx = 4;
                        
                        layoutOrderGBC.gridy = 3;
                        
                        layoutOrderGBC.gridheight = 1;
                        
                        layoutOrderGBC.gridwidth = 1;
                        

                        
                        panelOrder.add(newItemButton, layoutOrderGBC);
                        
                        
                         

                    } catch (Exception e) {
                       // JOptionPane.showMessageDialog(null, "Error with adding menu item to inventory");
                    }

                    try { // add new item as a recipe (column) in RecipeQuantites and set to 0 for all
                          // other entities

                        Statement stmt1 = conn.createStatement();
                        String sqlStatement1 = "ALTER TABLE recipequantities ADD " + newMenuItemDatabase
                                + " REAL DEFAULT 0;";
                        ResultSet result1 = stmt1.executeQuery(sqlStatement1);

                    } catch (Exception e) {
                        //JOptionPane.showMessageDialog(null,
                               // "Error with adding menu item attribute to RecipeQuantities");
                    }

                    /*
                     * try { // add new item as an entity in RecipeQuantites
                     * 
                     * // Statement stmt2 = conn.createStatement();
                     * // String sqlStatement2 = "ALTER TABLE recipequantities ADD " + newMenuItem +
                     * ";";
                     * // ResultSet result = stmt2.executeQuery(sqlStatement2);
                     * } catch (Exception e) {
                     * JOptionPane.showMessageDialog(null,
                     * "Error with adding menu item entity to RecipeQuantities");
                     * }
                     */

                    JOptionPane.showMessageDialog(null, newMenuItem + " was added to Menu.");
                } else if (src.equals(buttonDeleteItem)) {
                    String deadMenuItem = deleteItemIn.getText();
                    String deadMenuItemDatabase = deadMenuItem.replaceAll(" ", "").toLowerCase();
                    

                    // delete row from inv table in GUI
                    invDtmFood.removeRow(invTableFood.getRowCount() - 1);

                    // delete quantity needed for dead item
                    minQuants.remove(minQuants.size() - 1);

                    try {

                        Statement stmt0 = conn.createStatement();
                        // delete item from Inventory
                        String sqlStatement0 = "DELETE FROM inventory WHERE description = '" + deadMenuItem + "';";
                        ResultSet result = stmt0.executeQuery(sqlStatement0);

                    } catch (Exception e) {
                        //JOptionPane.showMessageDialog(null, "Error with database inventory");
                    }

                    try { // delete menu column in RecipeQuantities

                        Statement stmt1 = conn.createStatement();
                        String sqlStatement1 = "ALTER TABLE recipequantities DROP COLUMN " + deadMenuItemDatabase + ";";
                        ResultSet result1 = stmt1.executeQuery(sqlStatement1);

                    } catch (Exception e) {
                       // JOptionPane.showMessageDialog(null,
                                //"Error with deleting menu item attribute from RecipeQuantities");
                    }

                    /*
                     * try { // delete dead item entity from RecipeQuantites
                     * 
                     * // Statement stmt2 = conn.createStatement();
                     * // String sqlStatement2 = "ALTER TABLE recipequantities ADD " + newMenuItem +
                     * ";";
                     * // ResultSet result = stmt2.executeQuery(sqlStatement2);
                     * } catch (Exception e) {
                     * JOptionPane.showMessageDialog(null,
                     * "Error with adding menu item entity to RecipeQuantities");
                     * }
                     */

                    JOptionPane.showMessageDialog(null, deadMenuItem + " was deleted from Menu.");
                } else if (src.equals(buttonData)) {
                    cardLayout.show(contentPanel, "Data Analysis");
                } else if (src.equals(buttonIUC)) {
                    // Opens Inventory Usage Chart
                    cardLayout.show(contentPanel, "Inventory Usage");

                    if (inventoryUsageTable.getRowCount() == 0) {
                        // create inventory usage table
                        int i = 0;
                        try {
                            Statement stmt = conn.createStatement();
                            String sqlStatement = "SELECT * FROM inventory;";
                            ResultSet result = stmt.executeQuery(sqlStatement);

                            while (result.next()) {
                                String descrip = result.getString("description");

                                if (i < 29) {
                                    usageDtm.addRow(new Object[] { descrip, 0 });
                                }
                                i += 1;
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Error with login database.");
                        }
                    }

                    inventoryUsageTable.getColumnModel().getColumn(0).setPreferredWidth(200);
                    inventoryUsageTable.getColumnModel().getColumn(1).setPreferredWidth(80);
                    inventoryUsageTable.setEnabled(false);

                    layoutInventoryUsageGBC.gridwidth = 10;
                    layoutInventoryUsageGBC.gridheight = 10;
                    layoutInventoryUsageGBC.gridx = 2;
                    layoutInventoryUsageGBC.gridy = 4;
                    panelInventoryUsage.add(inventoryUsageTable, layoutInventoryUsageGBC);

                } else if (src.equals(buttonConfirmIUC)) {
                    numStartDayIUC = (Integer.parseInt(startDayIUC)) - 1;
                    numEndDayIUC = (Integer.parseInt(endDayIUC)) - numStartDayIUC;
                    try {
                        Statement stmtTableUsage;
                        stmtTableUsage = conn.createStatement();
                        String sqlStatement = String.format(
                                "SELECT menu501, menu502, menu503, menu504, menu505, menu506, menu507, menu508, menu509, menu510, menu511, menu512, menu513, menu514, menu515, menu516, menu517, menu518, menu519 FROM orders soldby LIMIT %s OFFSET %s;",
                                numEndDayIUC, numStartDayIUC);
                        ResultSet resultTableUsage = stmtTableUsage.executeQuery(sqlStatement);
                        Dictionary<String, Integer> orders = new Hashtable<String, Integer>();
                        orders.put("menu501", 0);
                        orders.put("menu502", 0);
                        orders.put("menu503", 0);
                        orders.put("menu504", 0);
                        orders.put("menu505", 0);
                        orders.put("menu506", 0);
                        orders.put("menu507", 0);
                        orders.put("menu508", 0);
                        orders.put("menu509", 0);
                        orders.put("menu510", 0);
                        orders.put("menu511", 0);
                        orders.put("menu512", 0);
                        orders.put("menu513", 0);
                        orders.put("menu514", 0);
                        orders.put("menu515", 0);
                        orders.put("menu516", 0);
                        orders.put("menu517", 0);
                        orders.put("menu518", 0);
                        orders.put("menu519", 0);

                        ArrayList<String> orderKeys = new ArrayList<String>();
                        for (int i = 501; i <= 519; i++) {
                            orderKeys.add("menu" + i);
                        }

                        Dictionary<String, String> convertMenuIDtoMenuItem = new Hashtable<String, String>();
                        convertMenuIDtoMenuItem.put("menu501", "5 finger meal");
                        convertMenuIDtoMenuItem.put("menu502", "4 finger meal");
                        convertMenuIDtoMenuItem.put("menu503", "3 finger meal");
                        convertMenuIDtoMenuItem.put("menu504", "kids meal");
                        convertMenuIDtoMenuItem.put("menu505", "gallon of tea");
                        convertMenuIDtoMenuItem.put("menu506", "family pack");
                        convertMenuIDtoMenuItem.put("menu507", "club sandwich meal");
                        convertMenuIDtoMenuItem.put("menu508", "club sandwich only");
                        convertMenuIDtoMenuItem.put("menu509", "sandwich meal combo");
                        convertMenuIDtoMenuItem.put("menu510", "sandwich only");
                        convertMenuIDtoMenuItem.put("menu511", "grill cheese meal combo");
                        convertMenuIDtoMenuItem.put("menu512", "grill cheese sandwich only");
                        convertMenuIDtoMenuItem.put("menu513", "laynes sauce");
                        convertMenuIDtoMenuItem.put("menu514", "chicken finger");
                        convertMenuIDtoMenuItem.put("menu515", "texas toast");
                        convertMenuIDtoMenuItem.put("menu516", "potato salad");
                        convertMenuIDtoMenuItem.put("menu517", "crinkle cut fries");
                        convertMenuIDtoMenuItem.put("menu518", "fountain drink");
                        convertMenuIDtoMenuItem.put("menu519", "bottle drink");

                        while (resultTableUsage.next()) {
                            for (int k = 501; k <= 519; k++) {
                                int tot = resultTableUsage.getInt("menu" + k) + orders.get("menu" + k);
                                orders.put("menu" + k, tot);
                            }
                        }
                        
                        Hashtable<String, Double> totalIngredientsUsed = new Hashtable<String, Double>();

                        totalIngredientsUsed.put("chickenbreast", 0.0);
                        totalIngredientsUsed.put("flour", 0.0);
                        totalIngredientsUsed.put("salt", 0.0);
                        totalIngredientsUsed.put("blackpepper", 0.0);
                        totalIngredientsUsed.put("fries", 0.0);
                        totalIngredientsUsed.put("thickbread", 0.0);
                        totalIngredientsUsed.put("potatosalad", 0.0);
                        totalIngredientsUsed.put("liquidmargarine", 0.0);
                        totalIngredientsUsed.put("garlicpowder", 0.0);
                        totalIngredientsUsed.put("ranch", 0.0);
                        totalIngredientsUsed.put("ketchuplgcontainer", 0.0);
                        totalIngredientsUsed.put("ketchuppackets", 0.0);
                        totalIngredientsUsed.put("mayo", 0.0);
                        totalIngredientsUsed.put("teabags", 0.0);
                        totalIngredientsUsed.put("sugarfortea", 0.0);
                        totalIngredientsUsed.put("worcestershiresauce", 0.0);
                        totalIngredientsUsed.put("slicedcheese", 0.0);
                        totalIngredientsUsed.put("baconslices", 0.0);
                        totalIngredientsUsed.put("fryeroil", 0.0);
                        totalIngredientsUsed.put("jonesdrjones", 0.0);
                        totalIngredientsUsed.put("jonesorangecream", 0.0);
                        totalIngredientsUsed.put("jonesrootbeer", 0.0);
                        totalIngredientsUsed.put("jonescola", 0.0);
                        totalIngredientsUsed.put("joneslemonlime", 0.0);
                        totalIngredientsUsed.put("jonessugarfreecola", 0.0);
                        totalIngredientsUsed.put("bottledrootbeer", 0.0);
                        totalIngredientsUsed.put("bottledcreamsoda", 0.0);
                        totalIngredientsUsed.put("bottledorangecream", 0.0);
                        totalIngredientsUsed.put("bottledberrylemonade", 0.0);

                        for (int keyList = 0; keyList < orderKeys.size(); keyList++) {

                            // Getting the key of a particular entry
                            String key = orderKeys.get(keyList);

                            int totalQuantity = orders.get(key);

                            String menuItem = convertMenuIDtoMenuItem.get(key);
                            Statement stmtFood = conn.createStatement();
                            String sqlStatementFood = String.format(
                                    "SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM recipequantities WHERE menuitem = '%s';",
                                    ingredientNames.get(0), ingredientNames.get(1), ingredientNames.get(2),
                                    ingredientNames.get(3), ingredientNames.get(4), ingredientNames.get(5),
                                    ingredientNames.get(6), ingredientNames.get(7), ingredientNames.get(8),
                                    ingredientNames.get(9), ingredientNames.get(10), ingredientNames.get(11),
                                    ingredientNames.get(12), ingredientNames.get(13), ingredientNames.get(14),
                                    ingredientNames.get(15), ingredientNames.get(16), ingredientNames.get(17),
                                    ingredientNames.get(18), ingredientNames.get(19), ingredientNames.get(20),
                                    ingredientNames.get(21), ingredientNames.get(22), ingredientNames.get(23),
                                    ingredientNames.get(24), ingredientNames.get(25), ingredientNames.get(26),
                                    ingredientNames.get(27), ingredientNames.get(28), menuItem);
                            ResultSet resultFood = stmtFood.executeQuery(sqlStatementFood);
                            ArrayList<Double> ingredientValues = new ArrayList<Double>();

                            while (resultFood.next()) {
                                int k;
                                for (k = 0; k < ingredientNames.size(); k++) {
                                    double weightIngredient = totalQuantity *
                                            resultFood.getDouble(ingredientNames.get(k));
                                    ingredientValues.add(weightIngredient);
                                }

                            }

                            for (int k = 0; k < ingredientValues.size(); k++) {
                                double t = totalIngredientsUsed.get(ingredientNames.get(k));
                                t += ingredientValues.get(k);
                                totalIngredientsUsed.put(ingredientNames.get(k), t);
                            }

                        }
                        for (int meow = 0; meow < inventoryUsageTable.getRowCount(); meow++) {
                            inventoryUsageTable.setValueAt(totalIngredientsUsed.get(ingredientNames.get(meow)), meow,
                                    1);
                        }

                    } catch (SQLException e) {
                    }
                } else if (src.equals(buttonRR)) {
                    // Opens Restock Report
                    cardLayout.show(contentPanel, "Restock Report");

                } else if (src.equals(buttonOP)) {
                    // Opens Ordering Popularity
                    cardLayout.show(contentPanel, "Ordering Popularity");

                } else if (src.equals(buttonConfirmOP)) {
                    numStartDayOP = (Integer.parseInt(startDayOP)) - 1;
                    numEndDayOP = (Integer.parseInt(endDayOP)) - numStartDayOP;

                    Statement stmt;
                    try {

                        stmt = conn.createStatement();
                        String forPopUpOP = "";
                        String sqlStatement = String.format(
                                "SELECT menu501, menu502, menu503, menu504, menu505, menu506, menu507, menu508, menu509, menu510, menu511, menu512, menu513, menu514, menu515, menu516, menu517, menu518, menu519 FROM orders soldby LIMIT %s OFFSET %s;",
                                numEndDayOP, numStartDayOP);
                        stmt = conn.createStatement();
                        ResultSet result = stmt.executeQuery(sqlStatement);
                        Hashtable<String, Integer> orders = new Hashtable<String, Integer>();
                        for (int i = 501; i <= 519; i++) {
                            orders.put("menu" + i, 0);
                        }

                        while (result.next()) {

                            for (int i = 501; i <= 519; i++) {

                                int tot = result.getInt("menu" + i) + orders.get("menu" + i);
                                orders.put("menu" + i, tot);
                            }

                        }

                        ArrayList<Integer> orderedOP = new ArrayList<Integer>(orders.values());

                        Collections.sort(orderedOP, Collections.reverseOrder());
                        ArrayList<String> keys = new ArrayList<String>();

                        for (int i = 0; i < orderedOP.size(); i++) {
                            for (Map.Entry entry : orders.entrySet()) {
                                if (orderedOP.get(i).equals(entry.getValue())
                                        && !keys.contains((String) entry.getKey())) {
                                    keys.add((String) entry.getKey());
                                }
                            }
                        }

                        for (int i = 0; i < orderedOP.size(); i++) {
                            String key = keys.get(i);
                            int totQuantity = orders.get(key);
                            if (i != orderedOP.size() - 1) {
                                forPopUpOP += i + 1 + ") " + key + "::  total quantity: " + totQuantity + "\n";
                            } else {
                                forPopUpOP += i + 1 + ") " + key + "::  total quantity: " + totQuantity;
                            }
                        }

                        JOptionPane.showMessageDialog(null, forPopUpOP);

                    } catch (SQLException e) {
                    }
                } else if (src.equals(buttonOrderM) || src.equals(buttonOrderS)) {

                    cardLayout.show(contentPanel, "Take Order");

                    orderDtm.setRowCount(0);
                    orderDtm.addRow(new Object[] { "Meal Item", "Price" });

                    orderTable.getColumnModel().getColumn(0).setPreferredWidth(200);
                    orderTable.getColumnModel().getColumn(1).setPreferredWidth(60);

                    layoutOrderGBC.gridx = 7;
                    layoutOrderGBC.gridy = 0;
                    panelOrder.add(orderTable, layoutOrderGBC);

                    // panelOrder.add(orderTable);

                } else if (src.equals(mealChickenFing5)) { //
                    order.add("5 finger meal");
                    orderDtm.addRow(new Object[] { "5 Chicken Fingers Meal", "$6.50" });
                } else if (src.equals(mealChickenFing4)) { //
                    order.add("4 finger meal");
                    orderDtm.addRow(new Object[] { "4 Chicken Fingers Meal", "$5.50" });
                } else if (src.equals(mealChickenFing3)) { //
                    order.add("3 finger meal");
                    orderDtm.addRow(new Object[] { "3 Chicken Fingers Meal", "$4.50" });
                } else if (src.equals(mealFamilyPack)) { //
                    order.add("family pack");
                    orderDtm.addRow(new Object[] { "Family Pack", "$32.00" });
                } else if (src.equals(mealChickenFingKid)) { //
                    order.add("kids meal");
                    orderDtm.addRow(new Object[] { "Kid's Meal", "$2.50" });
                } else if (src.equals(mealSandwich)) { //
                    order.add("sandwich meal");
                    orderDtm.addRow(new Object[] { "Sandwich Meal", "$7.50" });
                } else if (src.equals(mealGrilledCheese)) { //
                    order.add("grill cheese meal combo");
                    orderDtm.addRow(new Object[] { "Grilled Cheese Meal", "$4.50" });
                } else if (src.equals(mealClubSandwich)) { //
                    order.add("club sandwich meal");
                    orderDtm.addRow(new Object[] { "Club Sandwich Meal", "$7.50" });
                } else if (src.equals(sideFries)) {
                    order.add("crinkle cut fries");
                    orderDtm.addRow(new Object[] { "Side of Fries", "$1.75" });
                } else if (src.equals(itemGrilledCheese)) { //
                    order.add("grill cheese sandwich only");
                    orderDtm.addRow(new Object[] { "Grilled Cheese", "$3.50" });
                } else if (src.equals(itemClubSandwich)) { //
                    order.add("club sandwich only");
                    orderDtm.addRow(new Object[] { "Club Sandwich", "$4.75" });
                } else if (src.equals(itemSandwich)) { //
                    order.add("sandwich only");
                    orderDtm.addRow(new Object[] { "Sandwich", "$3.75" });
                } else if (src.equals(sideChickFing)) { //
                    order.add("chicken finger");
                    orderDtm.addRow(new Object[] { "Chicken Finger", "$1.50" });
                } else if (src.equals(sideFries)) { //
                    order.add("crinkle cut fries");
                    orderDtm.addRow(new Object[] { "Crinkle Cut Fries", "$1.75" });
                } else if (src.equals(sideTexasToast)) { //
                    order.add("texas toast");
                    orderDtm.addRow(new Object[] { "Texas Toast", "$1.50" });
                } else if (src.equals(sidePotatoSalad)) { //
                    order.add("potato salad");
                    orderDtm.addRow(new Object[] { "Potato Salad", "$1.50" });
                } else if (src.equals(drinkGallonOfTea)) { //
                    order.add("gallon of tea");
                    orderDtm.addRow(new Object[] { "Gallon of Tea", "$5.00" });
                } else if (src.equals(drinkFountain)) { //
                    order.add("fountain drink");
                    orderDtm.addRow(new Object[] { "Fountain Drink", "$1.25" });
                } else if (src.equals(drinkBottle)) { //
                    order.add("bottle drink");
                    orderDtm.addRow(new Object[] { "Bottle Drink", "$1.50" });
                } else if (src.equals(itemSauce)) { //
                    order.add("laynes sauce");
                    orderDtm.addRow(new Object[] { "Layne's Sauce", "$0.10" });
                }else if (src.equals(newItemButton)) { //
                    order.add(newMenuItem);
                    orderDtm.addRow(new Object[] { newMenuItem, "$4.20" });

                } else if (src.equals(checkOut)) {
                    try {
                        // This is where we add the values of the items to the dictionary using a nested
                        // for loop
                        // outer for loop is orders
                        // inner for loop is ingredients based on order name

                        for (int i = 0; i < order.size(); i++) {
                            String menuItem = order.get(i);
                            Statement stmt = conn.createStatement();
                            String sqlStatement = String.format(
                                    "SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM recipequantities WHERE menuitem = '%s';",
                                    ingredientNames.get(0), ingredientNames.get(1), ingredientNames.get(2),
                                    ingredientNames.get(3), ingredientNames.get(4), ingredientNames.get(5),
                                    ingredientNames.get(6), ingredientNames.get(7), ingredientNames.get(8),
                                    ingredientNames.get(9), ingredientNames.get(10), ingredientNames.get(11),
                                    ingredientNames.get(12), ingredientNames.get(13), ingredientNames.get(14),
                                    ingredientNames.get(15), ingredientNames.get(16), ingredientNames.get(17),
                                    ingredientNames.get(18), ingredientNames.get(19), ingredientNames.get(20),
                                    ingredientNames.get(21), ingredientNames.get(22), ingredientNames.get(23),
                                    ingredientNames.get(24), ingredientNames.get(25), ingredientNames.get(26),
                                    ingredientNames.get(27), ingredientNames.get(28), menuItem);
                            ResultSet result = stmt.executeQuery(sqlStatement);
                            ArrayList<Double> ingredientValues = new ArrayList<Double>();

                            while (result.next()) {
                                int k;
                                for (k = 0; k < ingredientNames.size(); k++) {
                                    double weightIngredient = result.getDouble(ingredientNames.get(k));
                                    ingredientValues.add(weightIngredient);
                                }
                            }
                            for (int k = 0; k < ingredientValues.size(); k++) {
                                double t = ingredients.get(ingredientNames.get(k));
                                t += ingredientValues.get(k);
                                ingredients.put(ingredientNames.get(k), t);
                            }
                        }

                        for (int i = 0; i < ingredientNames.size(); i++) {
                            String itemToChange = ingredientNames.get(i);
                            Statement stmt = conn.createStatement();
                            String sqlStatement2 = String.format(
                                    "SELECT weight FROM INVENTORY WHERE description = '%s';",
                                    itemToChange);
                            ResultSet result2 = stmt.executeQuery(sqlStatement2);
                            double weight = 0.0;
                            while (result2.next()) {
                                weight = result2.getDouble("weight");
                            }

                            double dictVal = ingredients.get(itemToChange);
                            double changedVal = weight - dictVal;

                            String sqlStatement3 = String.format(
                                    "UPDATE inventory SET weight = %s WHERE description = '%s';", changedVal,
                                    itemToChange);
                            stmt.executeUpdate(sqlStatement3);
                        }

                        for (int i = 0; i < ingredientNames.size(); i++) {
                            String itemToChange = ingredientNames.get(i);
                            Statement stmt = conn.createStatement();
                            String sqlStatement2 = String.format(
                                    "SELECT DISTINCT weight FROM INVENTORY WHERE description = '%s';", itemToChange);
                            ResultSet result2 = stmt.executeQuery(sqlStatement2);
                            double weight = result2.getDouble("weight");

                            double dictVal = ingredients.get(itemToChange);
                            double changedVal = weight - dictVal;

                            String sqlStatement3 = String.format(
                                    "UPDATE inventory SET weight = %s WHERE description = '%s';", changedVal,
                                    itemToChange);
                            stmt.executeQuery((sqlStatement3));
                        }

                    } catch (Exception e) {
                        // JOptionPane.showMessageDialog(null, "Error with login database.");
                    }

                    if (isManager) {
                        cardLayout.show(contentPanel, "Manager Home");
                    } else {
                        cardLayout.show(contentPanel, "Server Home");
                    }
                } else if (src.equals(submit)) {

                    String userName = userIn.getText();
                    String passWord = passIn.getText();

                    String table = "";
                    String id = "";
                    if (userName.charAt(0) == '9') {
                        table = "manager";
                        id = "managerid";
                    }
                    if (userName.charAt(0) == '1') {
                        table = "worker";
                        id = "workerid";
                    }

                    String correctPassword = "";
                    try {
                        Statement stmt = conn.createStatement();
                        String sqlStatement = "SELECT DISTINCT lastname FROM " + table + " WHERE " + id + " = "
                                + userName
                                + ";";
                        ResultSet result = stmt.executeQuery(sqlStatement);
                        while (result.next()) {
                            correctPassword += result.getString("lastname");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error with login database.");
                    }

                    if (passWord.equals(correctPassword)) {
                        if (table == "manager") {
                            cardLayout.show(contentPanel, "Manager Home");
                        }
                        if (table == "worker") {
                            cardLayout.show(contentPanel, "Server Home");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Your login information was incorrect. Please try again.");
                    }

                }

            }
        }
    }
}

