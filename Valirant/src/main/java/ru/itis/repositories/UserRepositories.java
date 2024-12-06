package ru.itis.repositories;

import ru.itis.models.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepositories extends CrudRepositories<User> {
    User getUserById(int id);
    User getUserByEmail(String email) throws SQLException;
    boolean isEmailValid(String email);
    String getUserWithHighestRating();
}
