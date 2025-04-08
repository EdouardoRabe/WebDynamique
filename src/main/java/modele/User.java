package modele;

import connexion.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mere.ClassMere;

public class User extends ClassMere {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int isAdmin;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public static User authenticate(String email, String password) throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            return authenticate(email, password, connection);
        }
    }

    public static User authenticate(String email, String password, Connection connection) throws Exception {
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setIsAdmin(resultSet.getInt("is_admin"));
                    return user;
                } else {
                    return null;
                }
            }
        }
    }

    public List<User> getAll() throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            return getAll(connection);
        }
    }

    public List<User> getAll(Connection connection) throws Exception {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                users.add(user);
            }
        }
        return users;
    }

    public User findById(int id) throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            return findById(connection , id);
        }
    }

    public User findById(Connection connection, int id) throws Exception {
        String query = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                } else {
                    return null;
                }
            }
        }
    }

    public void save() throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            save(connection);
        }
    }

    public void save(Connection connection) throws Exception {
        String query = "INSERT INTO user (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, this.firstName);
            statement.setString(2, this.lastName);
            statement.setString(3, this.email);
            statement.setString(4, this.password);
            statement.executeUpdate();
        }
    }

    public void update() throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            update(connection);
        }
    }

    public void update(Connection connection) throws Exception {
        String query = "UPDATE user SET first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, this.firstName);
            statement.setString(2, this.lastName);
            statement.setString(3, this.email);
            statement.setString(4, this.password);
            statement.setInt(5, this.id);
            statement.executeUpdate();
        }
    }

    public void delete() throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            delete(connection);
        }
    }

    public void delete(Connection connection) throws Exception {
        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.id);
            statement.executeUpdate();
        }
    }

    public List<User> getAllWithPagination(int index, int count) throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            return getAllWithPagination(connection,index, count);
        }
    }

    public List<User> getAllWithPagination(Connection connection ,int index, int count) throws Exception {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user LIMIT ? OFFSET ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, count);
            statement.setInt(2, index);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    users.add(user);
                }
            }
        }
        return users;
    }
}