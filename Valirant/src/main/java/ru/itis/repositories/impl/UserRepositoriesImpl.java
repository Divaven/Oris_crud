package ru.itis.repositories.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.models.User;
import ru.itis.repositories.UserRepositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoriesImpl implements UserRepositories {
    private static final String URL = "jdbc:postgresql://localhost:5432/Valorant";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Arsen2005";


    private static final String INSERT_USER = "INSERT INTO user (name, email, password, rating) VALUES (?, ?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE user SET name = ?, email = ?, password = ? WHERE id = ?";
    private static final String SELECT_USER = "SELECT * FROM user";
    private static final String SELECT_USER_BY_EMAIL = "SELECT id FROM user WHERE email = ?";
    private static final String SELECT_USER_BY_ID = "SELECT name, email FROM user WHERE id = ?";
    private static final String SELECT_EMAIL_VALIDITY = "SELECT COUNT(*) FROM user WHERE email = ?";
    private static final String SELECT_MAX_RATING = "SELECT name FROM user WHERE rating = (SELECT MAX(rating) FROM user)";


    @Override
    public void save(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setDouble(4, user.getRating());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);

            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER);

             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                double rating = resultSet.getDouble("rating");
                users.add(User.builder()
                        .id(id)
                        .name(name)
                        .password(password)
                        .email(email)
                        .rating(rating)
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public User getUserById(int id) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                user = User.builder()
                        .name(name)
                        .email(email)
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return User.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .rating(resultSet.getDouble("rating"))
                        .build();
            }
        }
        return null;
    }

    @Override
    public boolean isEmailValid(String email) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMAIL_VALIDITY)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public String getUserWithHighestRating() {
        String userName = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MAX_RATING);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                userName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userName;
    }
}
