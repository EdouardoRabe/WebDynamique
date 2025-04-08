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
import modele.Credit;
import modele.Depense;

public class ListDepenseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("auth") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String startDateParam = request.getParameter("start_date");
        String endDateParam = request.getParameter("end_date");
        Date startDate = startDateParam != null ? Date.valueOf(startDateParam) : null;
        Date endDate = endDateParam != null ? Date.valueOf(endDateParam) : null;
        try (Connection connection = Connexion.getConnection()) {
            out.println("<html><body>");
            out.println("<h3>Faire une recherche</h3>");
            out.println("<form method='get'>");
            out.println("<label for='start_date'>Date de debut:</label>");
            out.println("<input type='date' id='start_date' name='start_date'><br>");
            out.println("<label for='end_date'>Date de fin:</label>");
            out.println("<input type='date' id='end_date' name='end_date'><br>");
            out.println("<button type='submit'>Filtrer</button>");
            out.println("</form>");
            List<Depense> depenses;
            if (startDate != null && endDate != null) {
                if (startDate.after(endDate)) {
                   throw new ServletException("La date de debut ne peut pas etre apres la date de fin.");
                }
                depenses = new Depense().getDepenseByDateGroupById(connection, startDate, endDate);
                out.println("<h1>Liste des Depenses entre " + startDate + " et " + endDate + "</h1>");
            } else {
                out.println("<h1>Liste des Depenses sans filtre</h1>");
                depenses = new Depense().getDepenseGroupById(connection);
            }
            out.println("<table border='1'>");
            out.println("<tr><th>Libelle</th><th>Credit</th><th>Depense</th><th>Reste</th></tr>");
            for (Depense depense : depenses) {
                Credit credit = new Credit().findById(connection, depense.getCreditId());
                out.println("<tr>");
                out.println("<td>" + credit.getLibelle()+ "</td>");
                out.println("<td>" + credit.getDepart()+ "</td>");
                out.println("<td>" + depense.getMontant() + "</td>");
                out.println("<td>" + (credit.getDepart()-depense.getMontant()) + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<p><a href='.'>Deconnexion</a></p>");
            out.println("</body></html>");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la recuperation des depenses", e);
        }
    }
}