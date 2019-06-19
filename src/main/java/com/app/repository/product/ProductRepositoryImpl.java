package com.app.repository.product;

import com.app.connection.DbConnection;
import com.app.connection.DbStatus;
import com.app.connection.DbTables;
import com.app.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    private Connection connection = DbConnection.getInstance()
            .getConnection();

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
            System.err.println("FAILED TO INSERT ROW IN Product TABLE [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
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
            statement.setInt(6, product.getId());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            System.err.println("FAILED TO UPDATE A ROW IN Product TABLE [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        }
        return DbStatus.UPDATED;
    }

    private void addOrUpdate(Product product, PreparedStatement statement) throws SQLException {
        statement.setString(1, product.getName());
        statement.setBigDecimal(2, product.getPrice());
        statement.setInt(3, product.getProducerId());
        statement.setInt(4, product.getCountryId());
        statement.setInt(5, product.getCategoryId());
    }

    @Override
    public DbStatus delete(Integer id) {
        try {
            String sql = "delete from " + DbTables.Product + " where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            System.err.println("FAILED TO DELETE ROW FROM Product TABLE [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        }
        return DbStatus.DELETED;
    }

    @Override
    public Optional<Product> findOneById(Integer id) {
        final String sql = "select id, name, price, producerId, countryId, categoryId from Product where id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(
                        Product
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .price(resultSet.getBigDecimal(3))
                                .producerId(resultSet.getInt(4))
                                .countryId(resultSet.getInt(5))
                                .categoryId(resultSet.getInt(6))
                                .build()
                );
            }
            return Optional.empty();
        } catch (Exception e) {
            System.err.println("FAILED TO GET ONE ROW FROM Product TABLE [ ERROR " + e.getMessage() + " ]");
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
                System.err.println("FAILED TO CLOSE CONNECTION IN Product TABLE [ ERROR " + e.getMessage() + " ]");
                return Optional.empty();
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
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .price(resultSet.getBigDecimal(3))
                                .producerId(resultSet.getInt(4))
                                .countryId(resultSet.getInt(5))
                                .categoryId(resultSet.getInt(6))
                                .build()
                );
            }
            return products;

        } catch (SQLException e) {
            System.err.println("FAILED TO GET ALL ROWS FROM Product TABLE [ ERROR " + e.getMessage() + " ]");
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
                System.err.println("FAILED TO CLOSE CONNECTION IN Product TABLE [ ERROR " + e.getMessage() + " ]");
                return null;
            }
        }
    }
}
