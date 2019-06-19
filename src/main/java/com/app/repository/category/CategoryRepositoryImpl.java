package com.app.repository.category;

import com.app.connection.DbConnection;
import com.app.connection.DbStatus;
import com.app.connection.DbTables;
import com.app.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {

    private Connection connection = DbConnection.getInstance()
            .getConnection();

    @Override
    public DbStatus add(Category category) {
        try {
            String sql = "insert into " + DbTables.Category +
                    " (name) values (?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, category.getName());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            System.err.println("FAILED TO INSERT ROW IN Category TABLE [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        }
        return DbStatus.INSERTED;
    }

    @Override
    public DbStatus update(Category category) {
        try {
            String sql = "update " + DbTables.Category + " set " +
                    " name = ? where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            System.err.println("FAILED TO UPDATE A ROW IN Category TABLE [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        }
        return DbStatus.UPDATED;
    }

    @Override
    public DbStatus delete(Integer id) {
        try {
            String sql = "delete from " + DbTables.Category + " where id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            System.err.println("FAILED TO DELETE ROW FROM Category TABLE [ ERROR " + e.getMessage() + " ]");
            return DbStatus.ERROR;
        }
        return DbStatus.DELETED;
    }

    @Override
    public Optional<Category> findOneById(Integer id) {
        final String sql = "select id, name from Category where id = ?;";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(
                        Category
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .build()
                );
            }

            return Optional.empty();
        } catch (Exception e) {
            System.err.println("FAILED TO GET ONE ROW FROM Category TABLE [ ERROR " + e.getMessage() + " ]");
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
                System.err.println("FAILED TO CLOSE CONNECTION IN Category TABLE [ ERROR " + e.getMessage() + " ]");
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Category> findAll() {
        final String sql = "select id, name from Category";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            List<Category> categories = new ArrayList<>();
            while (resultSet.next()) {
                categories.add(
                        Category
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .build()
                );
            }
            return categories;

        } catch (SQLException e) {
            System.err.println("FAILED TO GET ALL ROWS FROM Category TABLE [ ERROR " + e.getMessage() + " ]");
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
                System.err.println("FAILED TO CLOSE CONNECTION IN Category TABLE [ ERROR " + e.getMessage() + " ]");
                return null;
            }
        }
    }
}
