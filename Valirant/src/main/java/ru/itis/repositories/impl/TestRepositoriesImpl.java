package ru.itis.repositories.impl;

import ru.itis.models.Test;
import ru.itis.models.User;
import ru.itis.repositories.TestRepositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestRepositoriesImpl implements TestRepositories {
    private static final String URL = "jdbc:postgresql://localhost:5432/Valorant";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Arsen2005";


    private static final String INSERT_TEST = "INSERT INTO test (problem, max_score, topic, time) VALUES (?, ?, ?, ?)";
    private static final String DELETE_TEST = "DELETE FROM test WHERE id = ?";
    private static final String UPDATE_TEST = "UPDATE test SET problem = ?, max_score = ?, topic = ?, time = ? WHERE id = ?";
    private static final String SELECT_TEST = "SELECT * FROM test";

    @Override
    public void save(Test test) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TEST);

            preparedStatement.setString(1, test.getProblem());
            preparedStatement.setInt(2, test.getMax_score());
            preparedStatement.setString(3, test.getTopic());
            preparedStatement.setInt(4, test.getTime());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Test test) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TEST);

            preparedStatement.setInt(1, test.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Test test) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TEST);

            preparedStatement.setString(1, test.getProblem());
            preparedStatement.setInt(2, test.getMax_score());
            preparedStatement.setString(3, test.getTopic());
            preparedStatement.setInt(4, test.getTime());
            preparedStatement.setInt(5, test.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Test> getAll() {
        List<Test> tests = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TEST);

             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String problem = resultSet.getString("problem");
                int max_score = resultSet.getInt("mac_score");
                String topic = resultSet.getString("topic");
                int time = resultSet.getInt("time");
                tests.add(Test.builder()
                        .id(id)
                        .problem(problem)
                        .max_score(max_score)
                        .topic(topic)
                        .time(time)
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tests;
    }
}
