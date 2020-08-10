

/* =======================================================================
   A generic GUI for the JDBC project

   Author: Shun Yan Cheung

   Work done by: Ryan Xu
   
   To compile the project: 

	javac -cp ".:/home/cs377001/lib/mysql-connector-java.jar" JDBC.java

   To run the project: 

	java -cp ".:/home/cs377001/lib/mysql-connector-java.jar" JDBC
   ======================================================================= */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.sql.*;

public class JDBC  
{
   public static JFrame mainFrame;

   /* ***************************************************************
      The class variables (defined using static) defined in this area
      can be accessed by other "call back" classes

      You can define your SHARED program variables below this line

      SHARED variables are variables that you need to use in
      MULTIPLE methods

      (If you ONLY use a variable inside ONE method, define that
       variable as a LOCAL variable inside THAT method)
      *************************************************************** */

   /* *********************************************************
      TODO 1: Define you SHARED program variables here

	      SHARED variables are variables that will can 
	      be used by methods in *different* classes
      ********************************************************* */
   public static Connection conn = null;
   public static Statement stmt = null;
   public static boolean DatabaseSelected = false;

   public static ResultSet rset = null;
       
   public static ResultSetMetaData meta = null;



   /* ********************************************************************
      End TODO 1 -- area where you define your SHARED program variables
      ******************************************************************* */

   /* vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      This area contain SHARED GUI objects used by the GUI application

      Do NOT make any changes to the variable in this section below
      You can use these GUI objects to write your GUI application:

      GUI object variable names:

	Input:  a JTextArea object for input (the top left panel)
		Input.getText() will return the SQL query that you
		need to execute
	Output:  a JTextArea object for output (the bottom left panel)
		(1) Use Output.setText("") to clear the text area
		(2) Then use Output.append( ... ) to print text
		    Use Output.append("\n") to advance to next line

	DBName: a JTextField object to enter the database name. 
		DBName.getText() will return the database name
	Select: a JButton object to provide the "select" function.
		Activate this object to process "Select database" function
	Execute: a JButton object to provide the "execute" function.
		Activate this object to process "Execute query" function

	Column: a JTextField object to input the column number.
		Column.getText() will return the column number as String
	Max: a JButton object to provide the "max" function.
		Activate this object to process "Max" function
	Min: a JButton object to provide the "min" function.
		Activate this object to process "Min" function
	Avg: a JButton object to provide the "average" function.
		Activate this object to process "Avg" function
	Median: a JButton object to provide the "median" function.
		Activate this object to process "Median" function
      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */

   /* ==========================================================
      The query input area
      ========================================================== */
   public static JTextArea Input = new JTextArea();   // Query input !!!
                                                      // Get the query text here

   /* ==========================================================
      The query output area
      ========================================================== */
   public static JTextArea Output = new JTextArea();  // Query output !!
                                                      // Print tuples here

   /* ==========================================================
      The TOP-RIGHT panel things
      ========================================================== */
   // Database label and name
   public static JLabel DBLabel = new JLabel("Database: ");
   public static JTextField DBName = new JTextField();     // Database name !!

   // The database selection button
   public static JButton Select = new JButton("Select");   // Select button !!

   // The query execution button
   public static JButton Execute = new JButton("Execute"); // Execute button !!


   /* ==========================================================
      The BOTTON-RIGHT panel things
      ========================================================== */
   // The column selection field
   public static JLabel ColumnLabel = new JLabel("Column: ");
   public static JTextField Column = new JTextField();       // Column number !!

   // The MAX output field  and button
   public static JTextField MaxText = new JTextField();
   public static JButton Max = new JButton("Maximum");       // Max button !!

   // The MIN output field  and button
   public static JTextField MinText = new JTextField();
   public static JButton Min = new JButton("Minimum");       // Min button !!

   // The Average output field  and button
   public static JTextField AvgText = new JTextField();
   public static JButton Avg = new JButton("Average");       // Avg button !!

   // The Median output field  and button
   public static JTextField MedianText = new JTextField();
   public static JButton Median = new JButton("Median");     // Median button !!



   /* ********************************************************************
      The main program
      ******************************************************************** */
   public static void main( String[] args )
   {
      /* ===============================================
	 Make the window
         =============================================== */
      MakeGUIWindow();

      try
       {
          // Load the MySQL JDBC driver
          DriverManager.registerDriver( new com.mysql.jdbc.Driver() );
       }
       catch (Exception a)
       {
         Output.setText("");
         Output.append("Cannot load the JDBC drive.");
       }


      /* =====================================================
	 Make the Select button active:
         ===================================================== */
      Select.addActionListener(new SelectListener() );
		// Make Select button execute the actionPerform() method
		// inside the class SelectListener (see below)

      /* **********************************************************
	 TODO 2: make the OTHER buttons active 
                 (Execute, Max, Min, Avg, Median)

		 Make the buttons perform the functions given in HW
         ********************************************************** */

       // DEMO: I will make the "Execute" button active in class....
       // I will make the "Execute" do the following:
       //    1. read the text from the Input window
       //    2. copy the text 4 times into the Output window.
       //       (A) without clearing the Output window
       //       (B) first   clearing the Output window

      Execute.addActionListener(new ExecuteListener() );

      Max.addActionListener(new MaxListener() );
      Min.addActionListener(new MinListener() );
      Avg.addActionListener(new AvgListener() );
      Median.addActionListener(new MedianListener() );

   }


   // I will make the EXECUTE button active in class 


   /* ===============================================================
      ***** Sample "call back" class

      Listener class for the Select button
      =============================================================== */
   static class SelectListener implements ActionListener            
   {  
      /* =============================================================
         The "actionPerformed( )" method will be called
	 when a button is pressed

	 You must ASSOCIATE a button to this class using this call:

	    ButtonObject.addActionListener( new SelectListener( ) )
         ============================================================= */
      public void actionPerformed(ActionEvent e)
      {  
	 /* ===========================================================
	    You must replace these statements with statements that
	    perform the "SELECT button" function:

		1. make a connection to the database server
		2. allocate statement object to prepare for 
                   query execution
	    =========================================================== */

         // =============================================================
         // Dummy statements to show that the activation was successful
         // =============================================================
         // System.out.println("Hello World !!"); 
          String url = "jdbc:mysql://holland.mathcs.emory.edu:3306/";
          String dbName = DBName.getText();
          String userName = "cs377";
          String password = "abc123";

          Output.setText("");
          
          if(conn != null)
          {
             try
             {
                // Load the MySQL JDBC driver
                conn.close();
                conn = null;
                System.out.println("Closed previous connection.");
             }
             catch (SQLException a)
             {
                System.out.println("Failed to close previous connection.");
             }
          }

          try
          {
             // Connect to the database
             conn = DriverManager.getConnection(url+dbName,userName,password);
      
             // Create statement object to send query to SQL server               
             stmt = conn.createStatement ();
             Output.setText("");
             Output.append("Successfully connected to database: " + dbName);
             DatabaseSelected = true;
          }
          catch (Exception a)
          {
             Output.setText("");
             Output.append("Cannot open database: " + dbName);
             DatabaseSelected = false;
          }
      }
   }

   /* ===============================================================
      TODO 3:
      ***** Write OTHER "call back" classes here for other buttons
            (Execute, Max, Min, Avg, Median)
      =============================================================== */
   static class ExecuteListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         Output.setText( "" );

         // for ( int i = 0; i < 4; i++ )
         // {
         //    Output.append( Input.getText( ) );
         //    Output.append( "\n" );
         // }
         if(DatabaseSelected == false)
            Output.append("No database was selected.");
         else{
            try
             {
                rset = stmt.executeQuery(Input.getText());      // Exec query
       
                meta = rset.getMetaData();  // Get meta data
       
                int NCols = meta.getColumnCount();

                int[] length = new int[100];
      
                /* ===================================================
              **** Print the column names before any data ****
              =================================================== */
                for ( int i = 1; i <= NCols; i++ )
                {
                    if ( meta.getColumnType(i) == Types.CHAR )
                        length[i] = Math.max(6, meta.getColumnDisplaySize(i));
                    else{
                        length[i] = 10;
                    }
                                   // Set the length of the field to print

                    String name = meta.getColumnName(i);
                    if(name.length() > length[i])
                        name = name.substring(0, length[i]);
                    Output.append( name );  // Print field name
            
                    /* ----------------------------------------------
                       ****Pad**** the attr name i to length[i]
                       ---------------------------------------------- */
                    for (int j = name.length(); j <= length[i]; j++)
                       Output.append(" ");
                }
                Output.append("\n");
             
                /* ---------------------------------
                     Print a dividing line....
                --------------------------------- */
                for ( int i = 1; i <= NCols; i++ ){
                    for ( int j = 0; j <= length[i]; j++){
                        if(i != NCols || j != length[i])
                           Output.append("=");   // Print a dividing line
                    }
                }
                Output.append("\n");
            
                      /* ===========================================
                    Fetch and print one row at a time....
                    =========================================== */
                while ( rset.next () )    // Advance to next row
                {
                    /* ===========================================
                       Fetch the columns (attributes) from a row
                       =========================================== */
                    for(int i = 1; i <= NCols; i++)
                    {
                       if ( meta.getColumnType(i) == Types.CHAR ){
                          String nextItem;
                
                          nextItem = rset.getString(i);
                          if(rset.wasNull())
                              nextItem = "NULL";
                          Output.append(nextItem);
               
                           /* ----------------------------------------------
                         **Pad** the attr value i to length[i]
                         ---------------------------------------------- */
                           for (int j = nextItem.length(); j <= length[i]; j++)
                              Output.append(" ");
                       }
                       else if(meta.getColumnType(i) == Types.INTEGER){
                           String nextItem;
                
                          nextItem = rset.getString(i);
                          if(rset.wasNull())
                              nextItem = "NULL";
                           for (int j = nextItem.length(); j < length[i]; j++)
                              Output.append(" ");
                          Output.append(nextItem);
                          Output.append(" ");
               
                           /* ----------------------------------------------
                         **Pad** the attr value i to length[i]
                         ---------------------------------------------- */
                       }

                       else{
                          Double next = rset.getDouble(i);
                          String nextItem = "";
                          if(rset.wasNull())
                              nextItem = "NULL";
                           else{
                              String dec = Integer.toString(meta.getScale(i));
                              String form = "%." + dec + "f";
                              nextItem = String.format(form, next);
                           }
                           for (int j = nextItem.length(); j < length[i]; j++)
                              Output.append(" ");
                          Output.append(nextItem);
                          Output.append(" ");
                       }
                    }
                    Output.append("\n");
             
                }
                  // rset.close();           // Free result set buffers 
             }
             catch (Exception a)
             {
                Output.setText(a.getMessage()); // Print the error message
             }
         }
      }
   }

   static class MaxListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         try{
            MaxText.setText("");
            rset.beforeFirst();
            int col = Integer.parseInt(Column.getText());
            if(col > meta.getColumnCount()){
               MaxText.setText("col # err");
            }
            else if(rset.next()){
               if (meta.getColumnType(col) == java.sql.Types.DECIMAL ||      
                  meta.getColumnType(col) == java.sql.Types.INTEGER ){
                  // process column data as float
                  Double max = rset.getDouble(col);
                  while(rset.wasNull() && rset.next()){
                     max = rset.getDouble(col);
                  }
                  while (rset.next()){
                     Double next = rset.getDouble(col);
                     if(!rset.wasNull() && next > max)
                        max = next;
                  }
                  MaxText.setText(Double.toString(max));
               }
               else
               {
                  // process column data as string
                  String max = rset.getString(col);
                  while(rset.wasNull() && rset.next()){
                     max = rset.getString(col);
                  }
                  while (rset.next()){
                     String next = rset.getString(col);
                     if(!rset.wasNull() && next.compareTo(max) > 0)
                        max = next;
                  }
                  MaxText.setText(max);
               }
            }
         }
         catch (SQLException a){
            Output.setText(a.getMessage());
         }
      }
   }

   static class MinListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         try{
            MinText.setText("");
            rset.beforeFirst();
            int col = Integer.parseInt(Column.getText());
            if(col > meta.getColumnCount()){
               MinText.setText("col # err");
            }
            else if(rset.next()){
               if (meta.getColumnType(col) == java.sql.Types.DECIMAL ||      
                  meta.getColumnType(col) == java.sql.Types.INTEGER ){
                  // process column data as float
                  Double min = rset.getDouble(col);
                  while(rset.wasNull() && rset.next()){
                     min = rset.getDouble(col);
                  }
                  while (rset.next()){
                     Double next = rset.getDouble(col);
                     if(!rset.wasNull() && next < min)
                        min = next;
                  }
                  MinText.setText(Double.toString(min));
               }
               else
               {
                  // process column data as string
                  String min = rset.getString(col);
                  while(rset.wasNull() && rset.next()){
                     min = rset.getString(col);
                  }
                  while (rset.next()){
                     String next = rset.getString(col);
                     if(!rset.wasNull() && next.compareTo(min) < 0)
                        min = next;
                  }
                  MinText.setText(min);
               }
            }
         }
         catch (SQLException a){
            Output.setText(a.getMessage());
         }
      }
   }

   static class AvgListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         try{
            AvgText.setText("");
            rset.beforeFirst();
            int col = Integer.parseInt(Column.getText());
            if(col > meta.getColumnCount()){
               AvgText.setText("col # err");
            }
            else{
               if (meta.getColumnType(col) == java.sql.Types.DECIMAL ||      
                  meta.getColumnType(col) == java.sql.Types.INTEGER ){
                  // process column data as float
                  Double sum = 0.0;
                  int cnt = 0;
                  Double avg = 0.0;
                  while (rset.next()){
                     Double next = rset.getDouble(col);
                     if(!rset.wasNull()){
                        sum += next;
                        cnt++;
                     }
                  }
                  avg = sum/cnt;
                  AvgText.setText(Double.toString(avg));
               }
               else
               {
                  // process column data as string
                  AvgText.setText("Not num");
               }
            }
         }
         catch (SQLException a){
            Output.setText(a.getMessage());
         }
      }
   }

   static class MedianListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         try{
            MedianText.setText("");
            rset.beforeFirst();
            int col = Integer.parseInt(Column.getText());
            if(col > meta.getColumnCount()){
               MedianText.setText("col # err");
            }
            else{
               if (meta.getColumnType(col) == java.sql.Types.DECIMAL ||      
                  meta.getColumnType(col) == java.sql.Types.INTEGER ){
                  // process column data as float
                  rset.last( );
                  int NTuples = rset.getRow( );
                  rset.beforeFirst();
                  Double[] vals = new Double[NTuples];
                  int cnt = 0;
                  while(rset.next()){
                     Double num = rset.getDouble(col);
                     if(!rset.wasNull()){
                        vals[cnt] = num;
                        cnt++;
                     }
                  }
                  // sort the array
                  for (int i = 1; i < cnt; ++i) { 
                        Double key = vals[i]; 
                        int j = i - 1; 
                        while (j >= 0 && vals[j] > key) { 
                            vals[j + 1] = vals[j]; 
                            j = j - 1; 
                        } 
                        vals[j + 1] = key; 
                  } 
                  int medInd = (cnt+1)/2 - 1;
                  Double med = vals[medInd];
                  MedianText.setText(Double.toString(med));
               }
               else
               {
                  // process column data as string
                  rset.last( );
                  int NTuples = rset.getRow( );
                  rset.beforeFirst();
                  String[] vals = new String[NTuples];
                  int cnt = 0;
                  while(rset.next()){
                     String num = rset.getString(col);
                     if(!rset.wasNull()){
                        vals[cnt] = num;
                        cnt++;
                     }
                  }
                  // sort the array
                  for (int i = 1; i < cnt; ++i) { 
                        String key = vals[i]; 
                        int j = i - 1; 
                        while (j >= 0 && vals[j].compareTo(key)>0) { 
                            vals[j + 1] = vals[j]; 
                            j = j - 1; 
                        } 
                        vals[j + 1] = key; 
                  } 
                  int medInd = (cnt+1)/2 - 1;
                  String med = vals[medInd];
                  MedianText.setText(med);
               }
            }
         }
         catch (SQLException a){
            Output.setText(a.getMessage());
         }
      }
   }












  /* ===================================================================== */
  /* === STOP !! Do NOT make any changes to the program below this line !! */
  /* ===================================================================== */


  /* **********************************************************
     This section of the program makes the GUI

     +++++ DO NOT make any changes to the code below !!! +++++
     ********************************************************** */

   /* ===============================================================
      Make GUI window
      =============================================================== */
   public static void MakeGUIWindow()
   {
      /* ------------------------------------------------
         Pick fonts for the input and output text areas 
         ------------------------------------------------ */
      Font ss_font = new Font("SansSarif",Font.BOLD,16) ;
      Font ms_font = new Font("Monospaced",Font.BOLD,16) ;

      JPanel P1 = new JPanel();   // Top left panel
      JPanel P2 = new JPanel();   // Bottem left panel

      P1.setLayout( new BorderLayout() ); 
      P2.setLayout( new BorderLayout() );

      /* =============================================
         Make top left panel (Input area)
         ============================================= */

      // Put scroll bars on the "Input" text area
      JScrollPane d1 = new JScrollPane(Input, 
                                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      Input.setFont( ss_font ); // Select font for "Input" text area

      P1.add(d1, "Center");    // Add it to P1

      /* ====================================================
         Make the top right panel (DBName, Select + execute)
         ==================================================== */
      JPanel s1 = new JPanel(); // Side panel
      s1.setLayout( new GridLayout( 8,1 ) );
      s1.add( DBLabel );
      s1.add( DBName );           // DBName
      DBName.setEditable(true);
      DBName.setFont( ss_font );
      s1.add( Select );           // Select button
      s1.add( Execute );          // Execute button
      Execute.setPreferredSize(new Dimension(140, 30)) ;

      P1.add(s1, "East");      // Add it to P1 (right side)


      /* =====================================================
         Make the bottom left panel (Output area)
         ===================================================== */
      // Put scroll bars on the "Output" text area
      JScrollPane d2 = new JScrollPane(Output, 
                                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

      Output.setFont( ms_font ); // Select font for "Output" text area
      Output.setEditable(false); // Make it UNeditable

      P2.add(d2, "Center");

      /* =====================================================
         Make the bottom right panel (Max, Min, Avg, Median)
         ===================================================== */
      JPanel s3 = new JPanel(); // Put ColumnLabel and Column on 1 row
      s3.add(ColumnLabel);
      s3.add(Column);
      Column.setFont( ss_font );

      Column.setPreferredSize(new Dimension(40, 30)) ;

      JPanel s2 = new JPanel(); // Side panel
      s2.setLayout( new GridLayout( 10,1 ) );
      s2.add( s3 );

      MaxText.setPreferredSize(new Dimension(140, 30)) ;
      s2.add( MaxText );
      MaxText.setFont( ss_font );
      MaxText.setEditable(false);
      s2.add( Max );                    // Add Max button

      s2.add( MinText );
      MinText.setFont( ss_font );
      MinText.setEditable(false);
      s2.add( Min );			// Add min button

      s2.add( AvgText );
      AvgText.setFont( ss_font );
      AvgText.setEditable(false);
      s2.add( Avg );			// Add avg button

      s2.add( MedianText );
      MedianText.setFont( ss_font );
      MedianText.setEditable(false);
      s2.add( Median );			// Add median button

      P2.add(s2, "East");

      /* =================================================
         Put P1 and P2 on the mainFrame's contentPane
         ================================================= */
      mainFrame = new JFrame("CS377 JDBC project");
      mainFrame.getContentPane().setLayout( new GridLayout(2,1) );
      mainFrame.getContentPane().add( P1 );   // Add P1
      mainFrame.getContentPane().add( P2 );   // Add P2
      mainFrame.setSize(900, 700);            // Set the frame size

      /* =================================================
         Exit application if user press close on window

         This will clean up the network connection....
         ================================================= */
      // mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      mainFrame.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent we) {
             System.out.println("\nGood-bye.Thanks for using CS377-JDBC project...\n");
             System.exit(0);
          }
      });


      mainFrame.setVisible(true);
   }
}
