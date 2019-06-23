package com.app.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;

public class DbConnection {

    private static DbConnection ourInstance = new DbConnection();

    public static DbConnection getInstance() {
        return ourInstance;
    }

    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_NAME = "jdbc:sqlite:DataBase";

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private DbConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_NAME);
            createTables();
        } catch (ClassNotFoundException e) {
            throw new MyException(ExceptionCode.CONNECTION,
                "DATABASE DRIVER FAILED DO LOAD " + DRIVER + " [ ERROR " + e.getMessage() + " ]");
        } catch (SQLException e) {
            throw new MyException(ExceptionCode.CONNECTION,
                "FAILED TO CONNECT TO DATABASE " + DB_NAME + " [ ERROR " + e.getMessage() + " ]");
        }
    }

    public void createTables() {
        try {
            Statement statement = connection.createStatement();

            // table Category
            String sqlCategory = "create table if not exists " + DbTables.Category + " ( " +
                "id integer primary key autoincrement, " +
                "name varchar(50) not null " +
                " )";
            statement.execute(sqlCategory);

            // table Country
            String sqlCountry = "create table if not exists " + DbTables.Country + " ( " +
                "id integer primary key autoincrement, " +
                "name varchar(50) not null " +
                " )";
            statement.execute(sqlCountry);

            // table Producer
            String sqlProducer = "create table if not exists " + DbTables.Producer + " (" +
                "id integer primary key autoincrement, " +
                "name varchar(50) not null, " +
                "countryId integer, " +
                "foreign key (countryId) references country(id) on update cascade " +
                " )";
            statement.execute(sqlProducer);

            // table Customer
            String sqlCustomer = "create table if not exists " + DbTables.Customer + " (" +
                "id integer primary key autoincrement, " +
                "name varchar(50) not null, " +
                "surname varchar(50) not null, " +
                "age integer not null, " +
                "countryId integer, " +
                "foreign key (countryId) references country(id) on update cascade " +
                ")";
            statement.execute(sqlCustomer);

            // table Product
            String sqlProduct = "create table if not exists " + DbTables.Product + " (" +
                "id integer primary key autoincrement, " +
                "name varchar(50) not null, " +
                "price decimal(5,2) not null, " +
                "producerId integer, " +
                "countryId integer, " +
                "categoryId integer, " +
                "foreign key (producerId) references producer(id) on update cascade, " +
                "foreign key (countryId) references country(id) on update cascade, " +
                "foreign key (categoryId) references category(id) on update cascade " +
                ")";
            statement.execute(sqlProduct);

            // table Orders
            String sqlOrders = "create table if not exists " + DbTables.Orders + " (" +
                "id integer primary key autoincrement, " +
                "quantity integer not null, " +
                "discount decimal(3,2) not null, " +
                "date date not null, " +
                "productId integer, " +
                "customerId integer, " +
                "foreign key (productId) references product(id) on update cascade, " +
                "foreign key (customerId) references customer(id) on update cascade " +
                ")";
            statement.execute(sqlOrders);

            statement.close();
        } catch (SQLException e) {
            throw new MyException(ExceptionCode.CONNECTION, "FAILED TO CREATE TABLE [ ERROR " + e.getMessage() + " ]");
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new MyException(ExceptionCode.CONNECTION, "FAILED TO CLOSE DATABASE CONNECTION " + DB_NAME);
            }
        }
    }
}
