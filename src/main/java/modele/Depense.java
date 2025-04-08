package modele;

import connexion.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mere.ClassMere;

public class Depense extends ClassMere {
    private int creditId;
    private double montant;
    private java.sql.Date date;

    public int getCreditId() {
        return creditId;
    }

    public void setCreditId(int creditId) {
        this.creditId = creditId;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }


    public List<Depense> getAll() throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            return getAll(connection);
        }
    }

    @Override
    public List<Depense> getAll(Connection connection) throws Exception {
        List<Depense> depenses = new ArrayList<>();
        String query = "SELECT * FROM depense";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Depense depense = new Depense();
                depense.setId(resultSet.getInt("id"));
                depense.setCreditId(resultSet.getInt("credit_id"));
                depense.setMontant(resultSet.getDouble("montant"));
                depense.setDate(resultSet.getDate("date"));
                depenses.add(depense);
            }
        }
        return depenses;
    }

    public Depense findById(int id) throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            return findById(connection, id);
        }
    }

    public Depense findById(Connection connection, int id) throws Exception {
        String query = "SELECT * FROM depense WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Depense depense = new Depense();
                    depense.setId(resultSet.getInt("id"));
                    depense.setCreditId(resultSet.getInt("credit_id"));
                    depense.setMontant(resultSet.getDouble("montant"));
                    depense.setDate(resultSet.getDate("date"));
                    return depense;
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
        String query = "INSERT INTO depense (credit_id, montant, date) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, this.creditId);
            statement.setDouble(2, this.montant);
            statement.setDate(3, this.date);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getInt(1);
                }
            }
        }
    }

    public void update() throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            update(connection);
        }
    }

    public void update(Connection connection) throws Exception {
        String query = "UPDATE depense SET credit_id = ?, montant = ?, date = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.creditId);
            statement.setDouble(2, this.montant);
            statement.setDate(3, this.date);
            statement.setInt(4, this.id);
            statement.executeUpdate();
        }
    }

    public void delete() throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            delete(connection);
        }
    }

    public void delete(Connection connection) throws Exception {
        String query = "DELETE FROM depense WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.id);
            statement.executeUpdate();
        }
    }

    public List<Depense> getAllWithPagination(int index, int count) throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            return getAllWithPagination(connection, index, count);
        }
    }

    public List<Depense> getAllWithPagination(Connection connection, int index, int count) throws Exception {
        List<Depense> depenses = new ArrayList<>();
        String query = "SELECT * FROM depense LIMIT ? OFFSET ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, count);
            statement.setInt(2, index);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Depense depense = new Depense();
                    depense.setId(resultSet.getInt("id"));
                    depense.setCreditId(resultSet.getInt("credit_id"));
                    depense.setMontant(resultSet.getDouble("montant"));
                    depense.setDate(resultSet.getDate("date"));
                    depenses.add(depense);
                }
            }
        }
        return depenses;
    }

    public List<Depense> getDepenseByDateGroupById(Connection connection ,java.sql.Date startDate, java.sql.Date endDate) throws Exception {
        List<Depense> depenses = new ArrayList<>();
        String query = "SELECT *, sum(montant) as total FROM depense WHERE date BETWEEN ? AND ? GROUP BY credit_id";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Depense depense = new Depense();
                    depense.setId(resultSet.getInt("id"));
                    depense.setCreditId(resultSet.getInt("credit_id"));
                    depense.setMontant(resultSet.getDouble("total"));
                    depense.setDate(resultSet.getDate("date"));
                    depenses.add(depense);
                }
            }
        }
        return depenses;
    }

    public List<Depense> getDepenseGroupById(Connection connection) throws Exception {
        List<Depense> depenses = new ArrayList<>();
        String query = "SELECT *, sum(montant) as total FROM depense GROUP BY credit_id";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Depense depense = new Depense();
                    depense.setId(resultSet.getInt("id"));
                    depense.setCreditId(resultSet.getInt("credit_id"));
                    depense.setMontant(resultSet.getDouble("total"));
                    depense.setDate(resultSet.getDate("date"));
                    depenses.add(depense);
                }
            }
        }
        return depenses;
    }
}
