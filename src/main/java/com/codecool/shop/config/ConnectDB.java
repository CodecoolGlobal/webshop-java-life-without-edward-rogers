package com.codecool.shop.config;

import com.codecool.shop.util.ConnectionVariables;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class ConnectDB {

    private static DataSource instance;

    public DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        try {
            ConnectionVariables connectionVariables = new ConnectionVariables("./src/main/resources/DBvariables.txt");
            dataSource.setDatabaseName(connectionVariables.getDBName());
            dataSource.setUser(connectionVariables.getUserName());
            dataSource.setPassword(connectionVariables.getPassword());
            dataSource.getConnection().close();
            return dataSource;
        } catch (IOException e) {
            System.out.println("There is a problem with connection. Type: " + e.getMessage());
            return null;
        }
    }


    public static DataSource getInstance(){
        if(instance == null){
            try {
                instance = new ConnectDB().connect();
            } catch (SQLException e) {
                System.out.println("Trouble at connecting to database. " + e);
            }
        }
        return instance;
    }

}
