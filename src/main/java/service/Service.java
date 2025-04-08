package service;

import java.sql.Connection;
import java.sql.Date;
import modele.Credit;
import modele.Depense;

public class Service {

    public Service() {
    }

    public void handleDepenseTransaction(Connection connection, Depense depense) throws Exception {
        try {
            connection.setAutoCommit(false);
            Credit credit = new Credit();
            credit.setId(depense.getCreditId());
            credit = credit.findById(connection, depense.getCreditId());
            if (credit == null) {
                throw new Exception("Le credit specifie n'existe pas.");
            }
            if (depense.getDate().compareTo(credit.getEndDate()) > 0 || depense.getDate().compareTo(credit.getStartDate()) < 0) {
                throw new Exception("La date de la depense doit etre comprise entre la date de debut et la date de fin du credit.");
            }
            if (depense.getMontant() > credit.getMontant()) {
                throw new Exception("Le montant de la depense ne peut pas depasser le montant du credit.");
            }
            depense.save(connection);
            credit.setMontant(credit.getMontant() - depense.getMontant());
            credit.update(connection);

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
