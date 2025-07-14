package dbconfig;

import java.sql.Connection;
import java.sql.Statement;

public class DBInit {

    public static void initializeDatabase(){
        try{
            Connection connection=DBConfig.getConnection();
            Statement statement = connection.createStatement();
            String createTable = """
        CREATE TABLE IF NOT EXISTS bookings (
            id IDENTITY PRIMARY KEY,
            customer_name VARCHAR(255) NOT NULL,
            date VARCHAR(50),
            time VARCHAR(50),
            people INT
        )
        """;
            statement.execute(createTable);
            System.out.println("Bookings table created.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
