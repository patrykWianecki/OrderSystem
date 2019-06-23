package com.app.repository.order;

import com.app.connection.DbConnection;
import com.app.connection.DbStatus;
import com.app.connection.DbTables;
import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {

    private Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public DbStatus add(Order orders) {
        try {
            String sql = "insert into " + DbTables.Orders +
                " (quantity, discount, date, productId, customerId) values (?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            addOrUpdate(orders, statement);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO INSERT ROW IN Orders TABLE [ ERROR " + e.getMessage() + " ]");
        }
        return DbStatus.INSERTED;
    }

    @Override
    public DbStatus update(Order orders) {
        try {
            String sql = "update " + DbTables.Orders + " set " +
                " quantity = ?, discount = ?, date = ?, productId = ?, customerId = ? where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            addOrUpdate(orders, statement);
            statement.setLong(6, orders.getId());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO UPDATE A ROW IN Orders TABLE [ ERROR " + e.getMessage() + " ]");
        }
        return DbStatus.UPDATED;
    }

    private void addOrUpdate(Order orders, PreparedStatement statement) throws SQLException {
        statement.setLong(1, orders.getQuantity());
        statement.setBigDecimal(2, orders.getDiscount());
        statement.setDate(3, Date.valueOf(orders.getDate()));
        statement.setLong(4, orders.getProductId());
        statement.setLong(5, orders.getCustomerId());
    }

    @Override
    public DbStatus delete(Long id) {
        try {
            String sql = "delete from " + DbTables.Orders + " where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO DELETE ROW FROM Orders TABLE [ ERROR " + e.getMessage() + " ]");
        }
        return DbStatus.DELETED;
    }

    @Override
    public Optional<Order> findOneById(Long id) {
        final String sql = "select id, quantity, discount, date, productId, customerId from Orders where id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(
                    Order
                        .builder()
                        .id(resultSet.getLong(1))
                        .quantity(resultSet.getInt(2))
                        .discount(resultSet.getBigDecimal(3))
                        .date(resultSet.getDate(4)
                            .toLocalDate())
                        .productId(resultSet.getLong(5))
                        .customerId(resultSet.getLong(6))
                        .build()
                );
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO GET ONE ROW FROM Orders TABLE [ ERROR " + e.getMessage() + " ]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("FAILED TO CLOSE CONNECTION IN Orders TABLE [ ERROR " + e.getMessage() + " ]");
            }
        }
    }

    @Override
    public List<Order> findAll() {
        final String sql = "select id, quantity, discount, date, productId, customerId from Orders";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(
                    Order
                        .builder()
                        .id(resultSet.getLong(1))
                        .quantity(resultSet.getInt(2))
                        .discount(resultSet.getBigDecimal(3))
                        .date(resultSet.getDate(4)
                            .toLocalDate())
                        .productId(resultSet.getLong(5))
                        .customerId(resultSet.getLong(6))
                        .build()
                );
            }
            return orders;
        } catch (SQLException e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO GET ALL ROWS FROM Orders TABLE [ ERROR " + e.getMessage() + " ]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("FAILED TO CLOSE CONNECTION IN Orders TABLE [ ERROR " + e.getMessage() + " ]");
            }
        }
    }
}

