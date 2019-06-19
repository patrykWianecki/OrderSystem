package com.app.repository.producer;

import com.app.connection.DbConnection;
import com.app.connection.DbStatus;
import com.app.connection.DbTables;
import com.app.model.Producer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerRepositoryImpl implements ProducerRepository {

    private Connection connection = DbConnection.getInstance()
            .getConnection();

    @Override
    public DbStatus add(Producer producer) {
        try {
            String sql = "insert into " + DbTables.Producer +
                    " (name, countryId) values (?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, producer.getName());
            statement.setInt(2, producer.getCountryId());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            System.err.println("FAILED TO INSERT ROW IN Producer TABLE [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        }
        return DbStatus.INSERTED;
    }

    @Override
    public DbStatus update(Producer producer) {
        try {
            String sql = "update " + DbTables.Producer + " set " +
                    " name = ?, countryId = ? where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, producer.getName());
            statement.setInt(2, producer.getCountryId());
            statement.setInt(3, producer.getId());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            System.err.println("FAILED TO UPDATE A ROW IN Producer TABLE [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        }
        return DbStatus.UPDATED;
    }

    @Override
    public DbStatus delete(Integer id) {
        try {
            String sql = "delete from " + DbTables.Producer + " where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            System.err.println("FAILED TO DELETE ROW FROM Producer TABLE [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        }
        return DbStatus.DELETED;
    }

    @Override
    public Optional<Producer> findOneById(Integer id) {
        final String sql = "select id, name, countryId from Producer where id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(
                        Producer
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .countryId(resultSet.getInt(3))
                                .build()
                );
            }
            return Optional.empty();
        } catch (Exception e) {
            System.err.println("FAILED TO GET ONE ROW FROM Producer TABLE [ ERROR " + e.getMessage() + " ]");
            return Optional.empty();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("FAILED TO CLOSE CONNECTION IN Producer TABLE [ ERROR " + e.getMessage() + " ]");
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Producer> findAll() {
        final String sql = "select id, name, countryId from Producer";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            List<Producer> producers = new ArrayList<>();
            while (resultSet.next()) {
                producers.add(
                        Producer
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .countryId(resultSet.getInt(3))
                                .build()
                );
            }
            return producers;

        } catch (SQLException e) {
            System.err.println("FAILED TO GET ALL ROWS FROM Producer TABLE [ ERROR " + e.getMessage() + " ]");
            return null;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("FAILED TO CLOSE CONNECTION IN Producer TABLE [ ERROR " + e.getMessage() + " ]");
                return null;
            }
        }
    }
}
