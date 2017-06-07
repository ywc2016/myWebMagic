package db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public enum Client {
    ;
    private static Configuration cfg = new Configuration().configure();
    private static SessionFactory sessionFactory = cfg.buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void main(String[] args) {
    }


}