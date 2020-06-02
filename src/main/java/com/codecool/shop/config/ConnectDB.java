package com.codecool.shop.config;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ConnectDB {
    public DataSource dataSource;

    public DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        // TODO: update database parameters
        dataSource.setDatabaseName("codecoolshop");
        dataSource.setUser("aron");
        dataSource.setPassword("T3l0M4mb0");

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection OK");
        this.dataSource = dataSource;
        return dataSource;
    }
}
