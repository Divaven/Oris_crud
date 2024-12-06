package ru.itis.repositories.impl;

import ru.itis.models.User;
import ru.itis.models.Weapon;
import ru.itis.repositories.WeaponRepositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WeaponRepositoriesImpl implements WeaponRepositories {
    private static final String URL = "jdbc:postgresql://localhost:5432/Valorant";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Arsen2005";


    private static final String INSERT_WEAPON = "INSERT INTO weapon (name, type, reload_cd, cost) VALUES (?, ?, ?, ?)";
    private static final String DELETE_WEAPON = "DELETE FROM weapon WHERE id = ?";
    private static final String UPDATE_WEAPON = "UPDATE weapon SET name = ?, type = ?, reload_cd = ?, cost = ? WHERE id = ?";
    private static final String SELECT_WEAPON = "SELECT * FROM weapon";

    @Override
    public void save(Weapon weapon) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_WEAPON);

            preparedStatement.setString(1, weapon.getName());
            preparedStatement.setString(2, weapon.getType());
            preparedStatement.setDouble(3, weapon.getReload_cd()) ;
            preparedStatement.setInt(4, weapon.getCost());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Weapon weapon) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_WEAPON);

            preparedStatement.setInt(1, weapon.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Weapon weapon) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_WEAPON);


            preparedStatement.setString(1, weapon.getName());
            preparedStatement.setString(2, weapon.getType());
            preparedStatement.setDouble(3, weapon.getReload_cd());
            preparedStatement.setInt(4, weapon.getCost());
            preparedStatement.setInt(5, weapon.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Weapon> getAll() {
        List<Weapon> weapons = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WEAPON);

             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                double reload_cd = resultSet.getDouble("reload_cd");
                int cost = resultSet.getInt("cost");
                weapons.add(Weapon.builder()
                        .id(id)
                        .name(name)
                        .type(type)
                        .reload_cd(reload_cd)
                        .cost(cost)
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return weapons;
    }
}
