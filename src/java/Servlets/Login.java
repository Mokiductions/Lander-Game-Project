/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import db_models.Users;
import db_services.LoginsService;
import db_services.UsersService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static javax.persistence.Persistence.createEntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PC_15
 */
public class Login extends HttpServlet {

    private EntityManager em;
    private EntityManagerFactory emf;
    private UsersService us;
    private LoginsService ls;

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
        PrintWriter out = response.getWriter();
        String answer;
        try {
            emf = createEntityManagerFactory("LanderProjectPU");
            em = emf.createEntityManager();

            // Obtiene los datos de USR y PWD
            String usr = request.getParameter("USR");
            String pwd = request.getParameter("PWD");

            // Comprobar si existe en la Base de Datos
            us = new UsersService(em);
            ls = new LoginsService(em);
            if (validLogin(usr, pwd)) {
                answer = "1";
                // Añade información sobre el inicio de sesión a la tabla de
                // logins
                addLoginInfo(usr);
            } else {
                answer = "0";
            }
            // Preparar respuesta para el JSP
            response.setContentType("text/plain");
            response.getWriter().print(answer);
        } finally {
            out.close();
        }
    }
    
    private void addLoginInfo(String usr) {
        Users user = us.getUserByUsr(usr);
        ls.addLogin(user);
    }
    
    private boolean validLogin(String usr, String pwd) {
        // Habría que hashear la contraseña entrante y compararla con la BD, 
        // ya que allí se guardarán hasheadas.
        pwd = us.hashPassword(pwd);
        Users user = us.getUserByUsr(usr);
        return pwd.equals(user.getPwd());
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
