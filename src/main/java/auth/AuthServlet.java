package auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import modele.User;
import connexion.Connexion;

public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
    
        try {
            User user = User.authenticate(email, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("auth", "true");
                if(user.getIsAdmin() == 0) {
                    response.sendRedirect("AccueilServlet");
                } else {
                    response.sendRedirect("ListDepenseServlet");
                }
            } else {
                response.sendRedirect("index.jsp?error");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during authentication", e);
        } catch (Exception e) {
            throw new ServletException("Unexpected error during authentication", e);
        }
    }
}