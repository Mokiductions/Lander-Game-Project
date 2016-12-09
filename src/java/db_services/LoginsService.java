package db_services;

import db_models.Logins;
import db_models.Users;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Miquel Ginés Borràs
 * @mail ginesborrasm@gmail.com
 */
public class LoginsService {

    private EntityManager em;

    public LoginsService() {
        // Constructor vacío
    }

    public LoginsService(EntityManager em) {
        this.em = em;
    }

    public void addLogin(Users user) {
        Logins login = new Logins();
        EntityTransaction trans = em.getTransaction();
        Calendar cal = Calendar.getInstance();
        Date loginTime = cal.getTime();
        login.setUsrId(user);
        login.setLoginTime(loginTime);
        try {
            trans.begin();
            em.persist(login);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
        }
    }
}
