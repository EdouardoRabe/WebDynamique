package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import connexion.Connexion;
import modele.Depense;
import service.Service;
import modele.Credit;
import service.Service;

public class FormDepenseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("auth") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try (Connection connection = Connexion.getConnection()) {
            List<Credit> credits = new Credit().getAll(connection);
            out.println("<html><body>");
            out.println("<h1>Creer une Depense</h1>");
            out.println("<form method='post' action='FormDepenseServlet'>");
            out.println("<label for='credit_id'>Credit:</label>");
            out.println("<select id='credit_id' name='credit_id' required>");
            for (Credit credit : credits) {
                out.println("<option value='" + credit.getId() + "'>" + credit.getLibelle() + " (" + credit.getStartDate() + " - " + credit.getEndDate() + ")</option>");
            }
            out.println("</select><br>");
            out.println("<label for='amount'>Montant:</label>");
            out.println("<input type='number' id='amount' name='amount' step='0.01' required><br>");
            out.println("<label for='date'>Date:</label>");
            out.println("<input type='date' id='date' name='date' required><br>");
            out.println("<button type='submit'>Creer</button>");
            out.println("</form>");
            out.println("<p><a href='AccueilServlet'>Retour</a></p>");
            out.println("<p><a href='.'>Deconnexion</a></p>");

            out.println("</body></html>");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la recuperation des credits", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int creditId = Integer.parseInt(request.getParameter("credit_id"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        Date date = Date.valueOf(request.getParameter("date"));

        Depense depense = new Depense();
        depense.setCreditId(creditId);
        depense.setMontant(amount);
        depense.setDate(date);

        try (Connection connection = Connexion.getConnection()) {
            Service service = new Service();
            service.handleDepenseTransaction(connection, depense);
            response.sendRedirect("FormDepenseServlet");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'enregistrement de la depense", e);
        }
    }
}
