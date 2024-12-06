package ru.itis.repositories.impl;

import com.sun.tools.attach.AgentLoadException;
import ru.itis.models.Agent;
import ru.itis.models.User;
import ru.itis.repositories.AgentRepositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgentRepositoriesImpl implements AgentRepositories {
    private static final String URL = "jdbc:postgresql://localhost:5432/Valorant";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Arsen2005";


    private static final String INSERT_AGENT = "INSERT INTO agent (name, role, description, difficulty) VALUES (?, ?, ?, ?)";
    private static final String DELETE_AGENT = "DELETE FROM agent WHERE id = ?";
    private static final String UPDATE_AGENT = "UPDATE user SET name = ?, role = ?, description = ?, difficulty = ? WHERE id = ?";
    private static final String SELECT_AGENT = "SELECT * FROM user";


    @Override
    public void save(Agent agent) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AGENT);

            preparedStatement.setString(1, agent.getName());
            preparedStatement.setString(2, agent.getRole());
            preparedStatement.setString(3, agent.getDescription());
            preparedStatement.setString(4, agent.getDifficulty());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Agent agent) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AGENT);

            preparedStatement.setInt(1, agent.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Agent agent) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AGENT);

            preparedStatement.setString(1, agent.getName());
            preparedStatement.setString(2, agent.getRole());
            preparedStatement.setString(3, agent.getDescription());
            preparedStatement.setString(4, agent.getDifficulty());
            preparedStatement.setInt(5, agent.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Agent> getAll() {
        List<Agent> agents = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AGENT);

             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");
                String description = resultSet.getString("description");
                String difficulty = resultSet.getString("difficulty");
                agents.add(Agent.builder()
                        .id(id)
                        .name(name)
                        .role(role)
                        .description(description)
                        .difficulty(difficulty)
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return agents;
    }

}
