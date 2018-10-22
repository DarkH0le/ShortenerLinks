import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.sql.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

public class ShortenerMain {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = Files.newBufferedReader(Paths.get("student.csv"));
        CSVParserrser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("Student Name", "Fees").withIgnoreHeaderCase().withTrim());
        for (CSVRecord csvRecord: csvParser) {
            // Accessing Values by Column Index
            String name = csvRecord.get(0);
            //Accessing the values by column header name
            String fees = csvRecord.get("fees");
            //Printing the record
            System.out.println("Record Number - " + csvRecord.getRecordNumber());
            System.out.println("Name : " + name);
            System.out.println("Fees : " + fees);
            System.out.println("\n\n");
        }


	public boolean insertData(){
			Connection c = null;
      Statement stmt = null;
      
      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:test.db");
         c.setAutoCommit(false);
         System.out.println("Opened database successfully");

         stmt = c.createStatement();
         String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                        "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
         stmt.executeUpdate(sql);

         sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                  "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );"; 
         stmt.executeUpdate(sql);

         sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                  "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );"; 
         stmt.executeUpdate(sql);

         sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                  "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );"; 
         stmt.executeUpdate(sql);

         stmt.close();
         c.commit();
         c.close();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Records created successfully");
	}
	}
		
		}
    }
}
public class ShortenerMain {
	public static void main(String[] args) throws IOException {

		BufferedReader reader = Files.newBufferedReader(Paths.get("student.csv"));
		CSVParserrser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("Student Name", "Fees").withIgnoreHeaderCase().withTrim());
		for (CSVRecord csvRecord: csvParser) {
			// Accessing Values by Column Index
			String name = csvRecord.get(0);
			//Accessing the values by column header name
			String fees = csvRecord.get("fees");
			//Printing the record
			System.out.println("Record Number - " + csvRecord.getRecordNumber());
			System.out.println("Name : " + name);
			System.out.println("Fees : " + fees);
			System.out.println("\n\n");
		}
	 }

	public boolean insertData(){
		Connection c = null;
		Statement stmt = null;
	  
		try {
			 Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:linkShortener.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			 stmt = c.createStatement();
			String sql = "INSERT INTO links (longLink,shortLink,expirationDate) " +
						"VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
			stmt.executeUpdate(sql);

			stmt.close();
			 c.commit();
			c.close();
		} catch ( Exception e ) {
			 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("Records created successfully");
	}

	public boolean selectData(String shortLink){
		Connection c = null;
		Statement stmt = null;
	  
		try {
			 Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:linkShortener.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			 stmt = c.createStatement();
			String sql = "SELECT * FROM links WHERE shortLink = '" + shortLink + "'"; 
			stmt.executeUpdate(sql);

			stmt.close();
			 c.commit();
			c.close();
		} catch ( Exception e ) {
			 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	
		
		
	}
}
