/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import db_services.UsersService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static javax.persistence.Persistence.createEntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilities.MailSender;

/**
 *
 * @author gines
 */
public class Register extends HttpServlet {

    private EntityManager em;
    private EntityManagerFactory emf;
    private UsersService us;

    // Variables para el envío del mail de activación
    private MailSender sender;
    private final String username = "lander.user.services@gmail.com";
    private final String password = "Landergame1234";
    private String activationLink;

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
        em = emf.createEntityManager();
        us = new UsersService(em);
        String usr = request.getParameter("USR");
        String pwd = request.getParameter("PWD");
        String mail = request.getParameter("MAIL");
        String actCode = generateActCode();
        if (!us.existsUser(usr)) {
            us.addUser(usr, pwd, mail, actCode);
            answer = "1";
            // Cambiar la URL 'localhost' por la dirección del servidor al subirlo.
            activationLink = "http://localhost:8080/LanderProject/activation.jsp?USR="
                    + usr + "&ACT=" + actCode;
            sendActivationMail(usr, mail);
        } else {
            answer = "0";
        }
        // Preparar respuesta para el JSP
        response.setContentType("text/plain");
        response.getWriter().print(answer);
    }

    private String generateActCode() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    private void sendActivationMail(String usr, String destination) {
        sender = new MailSender(username, password);
        sender.setDestination(destination);
        sender.setSubject("Confirmación de registro en Lander Game");
        sender.setBody("¡Gracias por darse de alta en nuestro juego, " + usr + "!<br/><br/>"
                + "Su registro ha sido llevado a cabo correctamente, tan sólo "
                + "necesita activar su cuenta para comenzar a jugar.<br/><br/>"
                + "Para activar su cuenta haga click <a href=\"" + activationLink
                + "\" target=\"_blank\">aquí</a>.<br/><br/>Ojo no estrelles la nave... "
                + "Y que la fuerza te acompañe.");

        try {
            // Envía el correo
            sender.send();
        } catch (MessagingException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
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
