import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.Writer;
import org.apache.commons.csv.CSVPrinter;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;


public class ShorterLink2 {

    static Map<String, String> records = new HashMap<>();

    static Map<String, String[]> valores = new HashMap<>();

    public static void main(String[] args) {

        //write();
        auntogeneratecsv();
        try {
            consultLongLinks();
        } catch (IOException e) {
        }
    }

    //Write simple csv sample
    public static void auntogeneratecsv() {
        try {
            //We have to create the CSVPrinter class object
            Writer writer = Files.newBufferedWriter(Paths.get("src/main/java/student.csv"));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Long", "Short", "ExpirationDate"));
            //Writing records in the generated CSV file
            csvPrinter.printRecord("https://dzone.com/articles/working-with-csv-files-in-java-using-apache-common", "ABC", "3");
            csvPrinter.printRecord("https://www.lynda.com/Maven-tutorials/Project-dependencies/504792/528783-4.html", "ABD", "1");
            csvPrinter.printRecord("https://commons.apache.org/proper/commons-csv/archives/1.4/index.html", "AB1", "4");
            csvPrinter.flush();

            readCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Leemos el archivo en este path "src/main/java/student.csv"
    public static void readCSV() throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/java/student.csv"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("Long", "Short", "ExpirationDate").withIgnoreHeaderCase().withTrim());

        for (CSVRecord csvRecord : csvParser) {
            // Accessing Values by Column Index
            String longLink = csvRecord.get(0);
            //Accessing the values by column header name
            String shortLink = csvRecord.get(1);
            String expirationDate = csvRecord.get(2);
            insertDatainDB(longLink, shortLink, expirationDate);
            //splitURL(longLink);
        }
    }

    //INsertamos los datos del archivo que se leyo en readcsv
    public static boolean insertDatainDB(String longLink, String shortLink, String expirationDate) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/Users/darkh0le/Downloads/linkShortener.db");
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");

            stmt = c.createStatement();
//            String sql = "INSERT INTO main.links (longLink,shortLink,expirationDate) " +"VALUES (" + longLink + "," + shortLink + "," + expirationDate + " );";
            String sql = "INSERT INTO \"shortener\" (\"longLink\", \"expirationDate\") VALUES ('" + longLink + "', '" + expirationDate + "')";

            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        //System.out.println("Records created successfully");
        return false;
    }

    public static void getOriginalLink(String shortLink) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/Users/darkh0le/Downloads/linkShortener.db");
            c.setAutoCommit(false);
            //System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "select longLink from shortener where shortLink = '" + shortLink + "'";

            ResultSet results = stmt.executeQuery(sql);

            while (results.next()) {
                //System.out.println(results.getString("longLink"));
                records.put(shortLink, results.getString("longLink"));
                System.out.println("Resultado despues de ingresar:  " + results.getString("longLink") + " ShortLink = " + shortLink);
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void consultLongLinks() throws IOException {

        BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/java/consultsLongLinks.csv"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("Short").withIgnoreHeaderCase().withTrim());

        for (CSVRecord csvRecord : csvParser) {
            // Accessing Values by Column Index
            String shortLink = csvRecord.get(0);
            getOriginalLink(shortLink);
        }
        writerecord();
    }

    public static void writerecord() {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get("src/main/java/resultOriginal.csv"));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Short", "OriginalLink"));
            Iterator it = records.entrySet().iterator();
            System.out.println(records);
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                System.out.println("Record a imprimir" + pair.getKey() + pair.getValue());
                csvPrinter.printRecord(pair.getKey(), pair.getValue());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public static String[] splitURL(String url){
//
//        String[] value_split = url.split(Pattern.quote("|"));
//
//    }
}