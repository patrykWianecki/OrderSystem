package com.app.repository.product;

import com.app.connection.DbConnection;
import com.app.connection.DbStatus;
import com.app.connection.DbTables;
import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    private Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public DbStatus add(Product product) {
        try {
            String sql = "insert into " + DbTables.Product +
                " (name, price, producerId, countryId, categoryId) values (?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            addOrUpdate(product, statement);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO INSERT ROW IN Product TABLE [ ERROR " + e.getMessage() + " ]");
        }
        return DbStatus.INSERTED;
    }

    @Override
    public DbStatus update(Product product) {
        try {
            String sql = "update " + DbTables.Product + " set " +
                " name = ?, price = ?, producerId = ?, countryId = ?, categoryId = ? where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            addOrUpdate(product, statement);
            statement.setLong(6, product.getId());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO UPDATE A ROW IN Product TABLE [ ERROR " + e.getMessage() + " ]");
        }
        return DbStatus.UPDATED;
    }

    private void addOrUpdate(Product product, PreparedStatement statement) throws SQLException {
        statement.setString(1, product.getName());
        statement.setBigDecimal(2, product.getPrice());
        statement.setLong(3, product.getProducerId());
        statement.setLong(4, product.getCountryId());
        statement.setLong(5, product.getCategoryId());
    }

    @Override
    public DbStatus delete(Long id) {
        try {
            String sql = "delete from " + DbTables.Product + " where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO DELETE ROW FROM Product TABLE [ ERROR " + e.getMessage() + " ]");
        }
        return DbStatus.DELETED;
    }

    @Override
    public Optional<Product> findOneById(Long id) {
        final String sql = "select id, name, price, producerId, countryId, categoryId from Product where id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(
                    Product
                        .builder()
                        .id(resultSet.getLong(2))
                        .price(resultSet.getBigDecimal(3))
                        .producerId(resultSet.getLong(4))
                        .countryId(resultSet.getLong(5))
                        .categoryId(resultSet.getLong(6))
                        .build()
                );
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO GET ONE ROW FROM Product TABLE [ ERROR " + e.getMessage() + " ]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("FAILED TO CLOSE CONNECTION IN Product TABLE [ ERROR " + e.getMessage() + " ]");
            }
        }
    }

    @Override
    public List<Product> findAll() {
        final String sql = "select id, name, price, producerId, countryId, categoryId from Product";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(
                    Product
                        .builder()
                        .id(resultSet.getLong(1))
                        .name(resultSet.getString(2))
                        .price(resultSet.getBigDecimal(3))
                        .producerId(resultSet.getLong(4))
                        .countryId(resultSet.getLong(5))
                        .categoryId(resultSet.getLong(6))
                        .build()
                );
            }
            return products;
        } catch (SQLException e) {
            throw new MyException(ExceptionCode.REPOSITORY, "FAILED TO GET ALL ROWS FROM Product TABLE [ ERROR " + e.getMessage() + " ]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("FAILED TO CLOSE CONNECTION IN Product TABLE [ ERROR " + e.getMessage() + " ]");
            }
        }
    }
}
