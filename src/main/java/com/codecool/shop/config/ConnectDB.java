package com.codecool.shop.config;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ConnectDB {

    public DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        // TODO: update database parameters
        dataSource.setDatabaseName("books");
        dataSource.setUser("bocz");
        dataSource.setPassword("Hiperkarma3148");

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection OK");
        return dataSource;
    }
}
