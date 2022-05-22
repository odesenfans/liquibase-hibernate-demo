package com.bearmetaldev.hibernate.demo;

import com.bearmetaldev.hibernate.demo.entities.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateDemo {

    public static void main(String[] args) {
        String dbEngine = "h2";
        if (args.length > 0) {
            dbEngine = args[0];
        }
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory(dbEngine).openSession()) {

            transaction = session.beginTransaction();

            // Add new Employee object
            Employee employee = new Employee("olivier.desenfans@bearmetaldev.com", "Olivier", "Desenfans");

            session.persist(employee);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();

            if (transaction != null) {
                transaction.rollback();
            }
        }


        HibernateUtil.shutdown();
    }

}
