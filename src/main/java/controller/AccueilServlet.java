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

public class AccueilServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("auth") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Choisir</h1>");
        out.println("<p><a href='FormCreditServlet'>Creer un Credit</a></p>");
        out.println("<p><a href='FormDepenseServlet'>Creer une Depense</a></p>");
        out.println("</body></html>");
    }

   
}
