package com.codecool.shop.config;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ConnectDB {

    private static DataSource instance;

    public DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        // TODO: update database parameters
        dataSource.setDatabaseName("codecoolshop");
        dataSource.setUser("username");
        dataSource.setPassword("password");

        dataSource.getConnection().close();
        return dataSource;
    }


    public static DataSource getInstance(){
        if(instance == null){
            try {
                instance = new ConnectDB().connect();
            } catch (SQLException throwables) {
                System.out.println("Trouble at connecting to database. " + throwables);
            }
        }
        return instance;
    }

}
