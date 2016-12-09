package db_services;

import db_models.Games;
import db_models.Users;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Miquel Ginés Borràs
 * @mail ginesborrasm@gmail.com
 */
public class GamesService {

    private EntityManager em;

    public GamesService() {
        // Constructor vacío
    }

    public GamesService(EntityManager em) {
        this.em = em;
    }

    public void addGame(Users user, int score) {
        Games game = new Games();
        EntityTransaction trans = em.getTransaction();
        Calendar cal = Calendar.getInstance();
        Date loginTime = cal.getTime();
        game.setUsrId(user);
        game.setScore(score);
        game.setGameTime(loginTime);
        try {
            trans.begin();
            em.persist(game);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public List<Games> getTopScores() {
        List results = em.createNamedQuery("Games.findTopScores")
                .setMaxResults(10)
                .getResultList(); 
        return (List<Games>) results;
    }
    
    public List<Object[]> getMostPlayed() {
        List results = em.createNamedQuery("Games.findMostPlayed")
                .setMaxResults(10)
                .getResultList(); 
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).toString());
        }
        return (List<Object[]>) results;
    }
    
    public List<Object[]> lastActiveUsers() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -5);
        Date minAgo = cal.getTime();
        List results = em.createNamedQuery("Games.findLastActiveUsers")
                .setParameter("gameTime", minAgo)
                .setMaxResults(20)
                .getResultList();
        return (List<Object[]>) results;
    }
}
