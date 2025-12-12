package net.javaguides.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Carga automáticamente hibernate.cfg.xml desde resources
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                System.err.println(" Error inicializando Hibernate:");
                e.printStackTrace();
                throw new ExceptionInInitializerError(e);
            }
        }
        return sessionFactory;
    }
    
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}