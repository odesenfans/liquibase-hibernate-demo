package com.bearmetaldev.hibernate.demo;

import com.bearmetaldev.hibernate.demo.entities.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateDemo {

    public static void main(String[] args) {
        String dbEngine = "postgres";
        if (args.length > 0) {
            dbEngine = args[0];
        }
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory(dbEngine).openSession()) {

            transaction = session.beginTransaction();

            // Add new Employee object
            int salary = 10000;     // Business is not so good lately
            Employee employee = new Employee("olivier.desenfans@bearmetaldev.com", "Olivier", "Desenfans", salary);

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
