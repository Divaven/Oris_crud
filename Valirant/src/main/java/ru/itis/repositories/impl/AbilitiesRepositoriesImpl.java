package ru.itis.repositories.impl;

import ru.itis.models.Abilities;
import ru.itis.models.User;
import ru.itis.repositories.AbilitiesRepositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbilitiesRepositoriesImpl implements AbilitiesRepositories {
    private static final String URL = "jdbc:postgresql://localhost:5432/Valorant";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Arsen2005";


    private static final String INSERT_ABILITY = "INSERT INTO abilities (name, agent_id, cooldown, description) VALUES (?, ?, ?, ?)";
    private static final String DELETE_ABILITY = "DELETE FROM abilities WHERE id = ?";
    private static final String UPDATE_ABILITY = "UPDATE abilities SET name = ?, agent_id = ?, cooldown = ?, description = ? WHERE id = ?";
    private static final String SELECT_ABILITY = "SELECT * FROM abilities";


    @Override
    public void save(Abilities ability) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ABILITY);

            preparedStatement.setString(1, ability.getName());
            preparedStatement.setInt(2, ability.getAgent_id());
            preparedStatement.setString(3, ability.getCooldown());
            preparedStatement.setString(4, ability.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Abilities ability) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ABILITY);

            preparedStatement.setInt(1, ability.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Abilities ability) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ABILITY);

            preparedStatement.setString(1, ability.getName());
            preparedStatement.setInt(2, ability.getAgent_id());
            preparedStatement.setString(3, ability.getCooldown());
            preparedStatement.setString(4, ability.getDescription());
            preparedStatement.setInt(5, ability.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Abilities> getAll() {
        List<Abilities> abilities = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ABILITY);

             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int agent_id = resultSet.getInt("agent_id");
                String cooldown = resultSet.getString("cooldown");
                String description = resultSet.getString("description");
                abilities.add(Abilities.builder()
                        .id(id)
                        .name(name)
                        .agent_id(agent_id)
                        .cooldown(cooldown)
                        .description(description)
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return abilities;
    }

}
