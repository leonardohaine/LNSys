/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;


import java.io.File;
import javax.swing.JOptionPane;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author Leonardo
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            File config = new File("d:\\hibernate.cfg.xml");
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            JOptionPane.showMessageDialog(null, "Erro ao conectar-se no banco de dados.\n"+ ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
