package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import connexion.Connexion;
import modele.Credit;

public class FormCreditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("auth") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Creer un Credit</h1>");
        out.println("<form method='post' action='FormCreditServlet'>");
        out.println("<label for='libelle'>Libelle:</label>");
        out.println("<input type='text' id='libelle' name='libelle' required><br>");
        out.println("<label for='montant'>Montant:</label>");
        out.println("<input type='number' id='montant' name='montant' step='0.01' required><br>");
        out.println("<label for='start_date'>Date de debut:</label>");
        out.println("<input type='date' id='start_date' name='start_date' required><br>");
        out.println("<label for='end_date'>Date de fin:</label>");
        out.println("<input type='date' id='end_date' name='end_date' required><br>");
        out.println("<button type='submit'>Creer</button>");
        out.println("</form>");
        out.println("<p><a href='AccueilServlet'>Retour</a></p>");
        out.println("<p><a href='.'>Deconnexion</a></p>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String libelle = request.getParameter("libelle");
        double montant = Double.parseDouble(request.getParameter("montant"));
        Date startDate = Date.valueOf(request.getParameter("start_date"));
        Date endDate = Date.valueOf(request.getParameter("end_date"));

        if (startDate.after(endDate)) {
           throw new ServletException("La date de debut ne peut pas etre apres la date de fin.");
        }

        Credit credit = new Credit();
        credit.setLibelle(libelle);
        credit.setMontant(montant);
        credit.setStartDate(startDate);
        credit.setEndDate(endDate);

        try (Connection connection = Connexion.getConnection()) {
            credit.save(connection);
            response.sendRedirect("FormCreditServlet");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'enregistrement du credit", e);
        }
    }
}
