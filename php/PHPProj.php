 <html>
   <head>
   <title> CS377 PHP Project</title>
   </head>
   <body>
      
   <H3>
   <HR>
   <!-- Answer to the query -->
   <HR>
   </H3>
   <P> 
   <UL>
      
   <?php

   $conn = mysqli_connect("holland.mathcs.emory.edu","cs377", "abc123", "spjDB");    
      
   if ( mysqli_connect_errno() != 0 )     # -----------  check connection error
   {      
      printf("Connect failed: %s\n", mysqli_connect_error());
      exit(1);
   }      
      
   // function to change unix wildcard characters to sql wildcard chars
   function change($x){
    for ( $i = 0; $i < strlen($x); $i++ )                   
      {
      if ( $x[$i] == '*' )
         $x[$i] = '%';
      if ( $x[$i] == '?' )
         $x[$i] = '_';
      }
      return $x;
   }

   // $query = $_POST['query'];   # Get the query input from the webpage
   $sname = change($_POST['sname']);
   $pname = change($_POST['pname']);
   $jname = change($_POST['jname']);
   $scity = change($_POST['scity']);
   $pcity = change($_POST['pcity']);
   $jcity = change($_POST['jcity']);

   $query = "select s.sname as 'Supplier Name', s.city as 'Supplier City', 
   p.pname as 'Part Name', p.city as 'Part City', j.jname as 'Project Name', 
   j.city as 'Project City', r.qty as 'Quantity Shipped'
   from supplier s, part p, proj j, spj r
   where s.snum = r.snum
   and p.pnum = r.pnum
   and j.jnum = r.jnum";
   
   // if the input is not empty
    $sname = trim($sname);
    if($sname != "")
      $query = $query." and s.sname like '$sname'\n";

    $pname = trim($pname);
    if($pname != "")
      $query = $query." and p.pname like '$pname'\n";

    $jname = trim($jname);
    if($jname != "")
      $query = $query." and j.jname like '$jname'\n";

    $scity = trim($scity);
    if($scity != "")
      $query = $query." and s.city like '$scity'\n";

    $pcity = trim($pcity);
    if($pcity != "")
      $query = $query." and p.city like '$pcity'\n";

    $jcity = trim($jcity);
    if($jcity != "")
      $query = $query." and j.city like '$jcity'\n";

   // print("<UL><TABLE bgcolor=\"#FFEEEE\" BORDER=\"5\">\n");
   // print("<TR> <TD><FONT color=\"blue\"><B><PRE>\n");
   // print( $query );   # echo the query 
   // print("</PRE></B></FONT></TD></TR></TABLE></UL>\n");
   // print("<P><HR><P>\n");
      
   if ( ($result = mysqli_query($conn, $query)) == NULL )      # Execute query
   {      
      printf("Error: %s\n", mysqli_error($conn));
      exit(1);
   }      
      
   print("<UL>\n");
   print("<TABLE bgcolor=\"lightyellow\" BORDER=\"5\">\n");

   # ------------------------------------------------------------
   # Print names of attributes in one row of the table
   # ------------------------------------------------------------
   print("<TR bgcolor=\"lightcyan\">\n");     # Start row of HTML table

   while ( ($field_details = mysqli_fetch_field($result)) != NULL )
   {
      print ('<TH> <FONT color="black">' . $field_details->name . "</FONT> </TH>"); # One item in row
   }

   print ("</TR>\n");   # End row of HTML table


   # ------------------------------------------------------------
   # Print the tuples
   # ------------------------------------------------------------
   while ( ($row = mysqli_fetch_row( $result )) != NULL )
   {      
      # Print one tuple as a row in table

      print("<TR>\n");     # Start row
      for ( $i = 0; $i < count($row); $i++ )
      {
         print ("<TD>" . $row[$i] . "</TD>");  # Print values in one row
      }
      print ("</TR>\n");   # End row
   }      

   print("</TABLE>\n");
   print("</UL>\n");
   print("<P>\n");
      
   mysqli_free_result($result);
      
   mysqli_close($conn);

   ?>     
      
   </UL>
   <P> 
   <HR>
   <HR>
   <HR>
   <HR>
