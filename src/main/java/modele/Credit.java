package modele;

import connexion.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mere.ClassMere;

public class Credit extends ClassMere {
    private String libelle;
    private double montant;
    private double depart;
    private java.sql.Date startDate;
    private java.sql.Date endDate;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getMontant() {
        return montant;
    }

    public void setDepart(double depart) {
        this.depart = depart;
    }

    public double getDepart() {
        return depart;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public java.sql.Date getStartDate() {
        return startDate;
    }

    public void setStartDate(java.sql.Date startDate) {
        this.startDate = startDate;
    }

    public java.sql.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.sql.Date endDate) {
        this.endDate = endDate;
    }

    public static boolean authenticate(String libelle, double montant) throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            return authenticate(libelle, montant, connection);
        }
    }

    public static boolean authenticate(String libelle, double montant, Connection connection) throws Exception {
        String query = "SELECT * FROM credit WHERE libelle = ? AND montant = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, libelle);
            statement.setDouble(2, montant);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public List<Credit> getAll() throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            return getAll(connection);
        }
    }

    public List<Credit> getAll(Connection connection) throws Exception {
        List<Credit> credits = new ArrayList<>();
        String query = "SELECT * FROM credit";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Credit credit = new Credit();
                credit.setId(resultSet.getInt("id"));
                credit.setLibelle(resultSet.getString("libelle"));
                credit.setMontant(resultSet.getDouble("montant"));
                credit.setDepart(resultSet.getDouble("depart"));
                credit.setStartDate(resultSet.getDate("start_date"));
                credit.setEndDate(resultSet.getDate("end_date"));
                credits.add(credit);
            }
        }
        return credits;
    }

    public Credit findById(int id) throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            return findById(connection, id);
        }
    }

    public Credit findById(Connection connection, int id) throws Exception {
        String query = "SELECT * FROM credit WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Credit credit = new Credit();
                    credit.setId(resultSet.getInt("id"));
                    credit.setLibelle(resultSet.getString("libelle"));
                    credit.setMontant(resultSet.getDouble("montant"));
                    credit.setDepart(resultSet.getDouble("depart"));
                    credit.setStartDate(resultSet.getDate("start_date"));
                    credit.setEndDate(resultSet.getDate("end_date"));
                    return credit;
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
        String query = "INSERT INTO credit (libelle, montant, start_date, end_date, depart) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, this.libelle);
            statement.setDouble(2, this.montant);
            statement.setDate(3, this.startDate);
            statement.setDate(4, this.endDate);
            statement.setDouble(5, this.montant);
            statement.executeUpdate();
        }
    }

    public void update() throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            update(connection);
        }
    }

    public void update(Connection connection) throws Exception {
        String query = "UPDATE credit SET libelle = ?, montant = ?, start_date = ?, end_date = ? , depart = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, this.libelle);
            statement.setDouble(2, this.montant);
            statement.setDate(3, this.startDate);
            statement.setDate(4, this.endDate);
            statement.setDouble(5, this.depart);
            statement.setInt(6, this.id);
            statement.executeUpdate();
        }
    }

    public void delete() throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            delete(connection);
        }
    }

    public void delete(Connection connection) throws Exception {
        String query = "DELETE FROM credit WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.id);
            statement.executeUpdate();
        }
    }

    public List<Credit> getAllWithPagination(int index, int count) throws Exception {
        try (Connection connection = Connexion.getConnection()) {
            return getAllWithPagination(connection, index, count);
        }
    }

    public List<Credit> getAllWithPagination(Connection connection, int index, int count) throws Exception {
        List<Credit> credits = new ArrayList<>();
        String query = "SELECT * FROM credit LIMIT ? OFFSET ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, count);
            statement.setInt(2, index);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Credit credit = new Credit();
                    credit.setId(resultSet.getInt("id"));
                    credit.setLibelle(resultSet.getString("libelle"));
                    credit.setMontant(resultSet.getDouble("montant"));
                    credit.setDepart(resultSet.getDouble("depart"));
                    credit.setStartDate(resultSet.getDate("start_date"));
                    credit.setEndDate(resultSet.getDate("end_date"));
                    credits.add(credit);
                }
            }
        }
        return credits;
    }
}
