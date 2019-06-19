package com.app.repository.customer;

import com.app.connection.DbConnection;
import com.app.connection.DbStatus;
import com.app.connection.DbTables;
import com.app.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl implements CustomerRepository {

    private Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public DbStatus add(Customer customer) {
        try {
            String sql = "insert into " + DbTables.Customer +
                    " (name, surname, age, countryId) values (?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getSurname());
            statement.setInt(3, customer.getAge());
            statement.setInt(4, customer.getCountryId());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            System.err.println("FAILED TO INSERT ROW IN Customer TABLE [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        }
        return DbStatus.INSERTED;
    }

    @Override
    public DbStatus update(Customer customer) {
        try {
            String sql = "update " + DbTables.Customer + " set " +
                    " name = ?, surname = ?, age = ?, countryId = ? where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getSurname());
            statement.setInt(3, customer.getAge());
            statement.setInt(4, customer.getCountryId());
            statement.setInt(5, customer.getId());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            System.err.println("FAILED TO UPDATE A ROW IN Customer TABLE [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        }
        return DbStatus.UPDATED;
    }

    @Override
    public DbStatus delete(Integer id) {
        try {
            String sql = "delete from " + DbTables.Customer + " where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            System.err.println("FAILED TO DELETE ROW FROM Customer TABLE [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        }
        return DbStatus.DELETED;
    }

    @Override
    public Optional<Customer> findOneById(Integer id) {
        final String sql = "select id, name, surname, age, countryId from Customer where id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Customer
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .surname(resultSet.getString(3))
                                .age(resultSet.getInt(4))
                                .countryId(resultSet.getInt(5))
                                .build()
                );
            }

            return Optional.empty();
        } catch (Exception e) {
            System.err.println("FAILED TO GET ONE ROW FROM Customer TABLE [ ERROR " + e.getMessage() + " ]");
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
                System.err.println("FAILED TO CLOSE CONNECTION IN Customer TABLE [ ERROR " + e.getMessage() + " ]");
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Customer> findAll() {
        final String sql = "select id, name, surname, age, countryId from Customer";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(
                        Customer
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .surname(resultSet.getString(3))
                                .age(resultSet.getInt(4))
                                .countryId(resultSet.getInt(5))
                                .build()
                );
            }
            return customers;

        } catch (SQLException e) {
            System.err.println("FAILED TO GET ALL ROWS FROM Customer TABLE [ ERROR " + e.getMessage() + " ]");
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
                System.err.println("FAILED TO CLOSE CONNECTION IN Customer TABLE [ ERROR " + e.getMessage() + " ]");
                return null;
            }
        }
    }
}

