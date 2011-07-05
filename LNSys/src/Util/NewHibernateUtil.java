/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;


import Tela.Inicial;
import java.io.File;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author Leonardo
 */
public class NewHibernateUtil {

    private static final AnnotationConfiguration cfg =
                            new AnnotationConfiguration();
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml)
            // config file.
            cfg.configure("hibernate.cfg.xml");
                File arquiv = new File(cfg.getProperty("nome.banco"));
                cfg.setProperty("hibernate.connection.url","jdbc:postgresql:"+Inicial.IP+"/3050:"+arquiv.getAbsolutePath());
            sessionFactory = cfg.configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception.
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
