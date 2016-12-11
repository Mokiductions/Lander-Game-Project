/**
 * File: Test.java
 * Created 07-dic-2016 at 15:30:23
 */
package Servlets;

import db_models.Games;
import db_models.Users;
import db_services.GamesService;
import db_services.LoginsService;
import db_services.UsersService;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static javax.persistence.Persistence.createEntityManagerFactory;

/**
 *
 * @author Miquel Ginés Borràs
 * @mail ginesborrasm@gmail.com
 */
public class Test {

    private static EntityManager em;
    private static EntityManagerFactory emf;
    private static UsersService us;
    private static LoginsService ls;
    private static GamesService gs;
    private static String answer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            System.out.println(generateActCode());
        }
        
    }
    
    private static String generateActCode() {
        String code = "";
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    private void addLoginInfo(String usr) {
        Users user = us.getUserByUsr(usr);
        ls.addLogin(user);
    }

    private static void validLogin(String usr, String pwd) {
        // Habría que hashear la contraseña entrante y compararla con la BD, 
        // ya que allí se guardarán hasheadas.
        pwd = us.hashPassword(pwd);
        System.out.println(pwd);
    }

}
