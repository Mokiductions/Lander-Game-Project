/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import db_models.Games;
import db_models.Users;
import db_services.GamesService;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static javax.persistence.Persistence.createEntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gines
 */
public class TopScores extends HttpServlet {

    private EntityManager em;
    private EntityManagerFactory emf;
    private GamesService gs;
    private List<Games> games;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String answer;
        ServletContext ctx = getServletContext();
        emf = (EntityManagerFactory) ctx.getAttribute("emf");
        emf = createEntityManagerFactory("LanderProjectPU");
        em = emf.createEntityManager();
        gs = new GamesService(em);
        // Obtiene los datos de USR y PWD
        games = gs.getTopScores();
        answer = "<table class=\"data-table\">";
        answer += "<tr><th></th><th>Usuario</th><th>Punt.</th></tr>";
        int i = 1;
        for (Games game : games) {
            Users user = game.getUsrId();
            if (i % 2 == 0) {
                answer += "<tr class=\"table-row-even\">";
            } else {
                answer += "<tr>";
            }
            answer += "<td class=\"table-cell-number\">" + i + "</td>";
            answer += "<td class=\"table-cell-user\">" + user.getUsr() + "</td>";
            answer += "<td class=\"table-cell-data\">" + game.getScore() + "</td>";
            answer += "</tr>";
            i++;
        }
        answer += "</table>";
        // Preparar respuesta para el JSP
        response.setContentType("text/html");
        response.getWriter().print(answer);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
