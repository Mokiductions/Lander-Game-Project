package db_services;

import db_models.Users;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Miquel Ginés Borràs
 * @mail ginesborrasm@gmail.com
 */
public class UsersService {

    private EntityManager em;

    public UsersService() {
        // Constructor vacío
    }

    public UsersService(EntityManager em) {
        this.em = em;
    }

    public void addUser(String usr, String pwd, String mail, String actCode) {
        Users user = new Users();
        EntityTransaction trans = em.getTransaction();
        if (existsUser(usr)) {
            // Return error
            System.out.println("Error");
        } else {
            user.setUsr(usr);
            pwd = hashPassword(pwd);
            user.setPwd(pwd);
            user.setMail(mail);
            user.setActive(false);
            user.setActCode(actCode);
            try {
                trans.begin();
                em.persist(user);
                trans.commit();
            } catch (Exception e) {
                if (trans.isActive()) {
                    trans.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    public void activateUser(Users user) {
        EntityTransaction trans = em.getTransaction();
        user.setActive(true);
        user.setActCode("");
        try {
            trans.begin();
            em.persist(user);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
        }
    }

    public boolean existsUser(String usr) {
        List results = em.createNamedQuery("Users.findByUsr")
                .setParameter("usr", usr).getResultList();
        return results.size() > 0;
    }

    public String hashPassword(String pwd) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pwd.getBytes("UTF-8"));
            pwd = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsersService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UsersService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return pwd;
        }
    }

    public Users getUserByUsr(String usr) {
        List results = em.createNamedQuery("Users.findByUsr")
                .setParameter("usr", usr).getResultList();
        System.out.println(results.size());
        return (Users) results.get(0);
    }
}
