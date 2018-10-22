import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.Writer;
import java.util.Arrays;
import org.apache.commons.csv.CSVPrinter;
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

public class ShorterLink2 {
    public static void main(String[] args) {

        write();
    }
    //Write simple csv sample
    public static void write(){
        try {
            //We have to create the CSVPrinter class object
            Writer writer = Files.newBufferedWriter(Paths.get("src/main/java/student.csv"));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Long", "Short","ExpirationDate"));
            //Writing records in the generated CSV file
            csvPrinter.printRecord("https://dzone.com/articles/working-with-csv-files-in-java-using-apache-common", "ABC","3");
            csvPrinter.printRecord("https://www.lynda.com/Maven-tutorials/Project-dependencies/504792/528783-4.html", "ABD","1");
            csvPrinter.printRecord("https://commons.apache.org/proper/commons-csv/archives/1.4/index.html", "AB1","4");
//            //Writing records in the form of a list
//            csvPrinter.printRecord(Arrays.asList("Dev Bhatia", 4000,123));
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCSV() throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get("student.csv"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("Long", "Short","ExpirationDate").withIgnoreHeaderCase().withTrim());

        for (CSVRecord csvRecord: csvParser) {
            // Accessing Values by Column Index
            String longLink = csvRecord.get(0);
            //Accessing the values by column header name
            String shortLink = csvRecord.get("Short");
            String expirationDate = csvRecord.get("ExpirationDate");
            //Printing the record
            System.out.println("Record Number - " + csvRecord.getRecordNumber());
            System.out.println("Name : " + longLink);
            System.out.println("Fees : " + shortLink);
            System.out.println("Fees : " + expirationDate);
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
        return false;
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
        return false;
    }

}
