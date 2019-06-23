package com.app.repository.country;

import com.app.connection.DbConnection;
import com.app.connection.DbStatus;
import com.app.connection.DbTables;
import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryRepositoryImpl implements CountryRepository {

    private Connection connection = DbConnection.getInstance()
        .getConnection();

    @Override
    public DbStatus add(Country country) {
        try {
            String sql = "insert into " + DbTables.Country +
                " (name) values (?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, country.getName());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO INSERT ROW IN Customer TABLE [ ERROR " + e.getMessage() + " ]");
        }
        return DbStatus.INSERTED;
    }

    @Override
    public DbStatus update(Country country) {
        try {
            String sql = "update " + DbTables.Country + " set " +
                " name = ? where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, country.getName());
            statement.setLong(2, country.getId());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO UPDATE A ROW IN Country TABLE [ ERROR " + e.getMessage() + " ]");
        }
        return DbStatus.UPDATED;
    }

    @Override
    public DbStatus delete(Long id) {
        try {
            String sql = "delete from " + DbTables.Country + " where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO DELETE ROW FROM Country TABLE [ ERROR " + e.getMessage() + " ]");
        }
        return DbStatus.DELETED;
    }

    @Override
    public Optional<Country> findOneById(Long id) {
        final String sql = "select id, name from Country where id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                    Country
                        .builder()
                        .id(resultSet.getLong(1))
                        .name(resultSet.getString(2))
                        .build()
                );
            }

            return Optional.empty();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO GET ONE ROW FROM Country TABLE [ ERROR " + e.getMessage() + " ]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("FAILED TO CLOSE CONNECTION IN Country TABLE [ ERROR " + e.getMessage() + " ]");
            }
        }
    }

    @Override
    public List<Country> findAll() {
        final String sql = "select id, name from Country";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            List<Country> countries = new ArrayList<>();
            while (resultSet.next()) {
                countries.add(
                    Country
                        .builder()
                        .id(resultSet.getLong(1))
                        .name(resultSet.getString(2))
                        .build()
                );
            }
            return countries;
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO GET ALL ROWS FROM Country TABLE [ ERROR " + e.getMessage() + " ]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("FAILED TO CLOSE CONNECTION IN Country TABLE [ ERROR " + e.getMessage() + " ]");
            }
        }
    }
}
