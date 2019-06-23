package com.app.repository.customer;

import com.app.connection.DbConnection;
import com.app.connection.DbStatus;
import com.app.connection.DbTables;
import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
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
            statement.setLong(3, customer.getAge());
            statement.setLong(4, customer.getCountryId());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO INSERT ROW IN Customer TABLE [ ERROR " + e.getMessage() + " ]");
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
            statement.setLong(3, customer.getAge());
            statement.setLong(4, customer.getCountryId());
            statement.setLong(5, customer.getId());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO UPDATE A ROW IN Customer TABLE [ ERROR " + e.getMessage() + " ]");
        }
        return DbStatus.UPDATED;
    }

    @Override
    public DbStatus delete(Long id) {
        try {
            String sql = "delete from " + DbTables.Customer + " where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO DELETE ROW FROM Customer TABLE [ ERROR " + e.getMessage() + " ]");
        }
        return DbStatus.DELETED;
    }

    @Override
    public Optional<Customer> findOneById(Long id) {
        final String sql = "select id, name, surname, age, countryId from Customer where id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                    Customer
                        .builder()
                        .id(resultSet.getLong(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .age(resultSet.getInt(4))
                        .countryId(resultSet.getLong(5))
                        .build()
                );
            }

            return Optional.empty();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO GET ONE ROW FROM Customer TABLE [ ERROR " + e.getMessage() + " ]");
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
                        .id(resultSet.getLong(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .age(resultSet.getInt(4))
                        .countryId(resultSet.getLong(5))
                        .build()
                );
            }
            return customers;
        } catch (SQLException e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO GET ALL ROWS FROM Customer TABLE [ ERROR " + e.getMessage() + " ]");
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
            }
        }
    }
}

