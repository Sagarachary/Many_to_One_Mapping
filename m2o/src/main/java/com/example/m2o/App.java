package com.example.m2o;

import com.example.m2o.entity.Department;
import com.example.m2o.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class App {
	public static void main(String[] args) {
        // Build SessionFactory (reads src/main/resources/hibernate.cfg.xml)
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // create department
        Department dept = new Department("Research & Development");

        // create employees
        Employee e1 = new Employee("Asha Patel", "Developer");
        Employee e2 = new Employee("Rohit Singh", "Tester");
        Employee e3 = new Employee("Meera Iyer", "Team Lead");

        // set both sides using helper
        dept.addEmployee(e1);
        dept.addEmployee(e2);
        dept.addEmployee(e3);

        // Persist department, cascade will persist employees
        session.persist(dept);

        tx.commit();

        // Fetch and print to verify (optional)
        Session s2 = factory.openSession();
        Department fetched = s2.get(Department.class, dept.getId());
        System.out.println("Fetched department: " + fetched);
        if (fetched.getEmployees() != null) {
            fetched.getEmployees().forEach(emp -> System.out.println("  " + emp));
        }
        s2.close();

        session.close();
        factory.close();
    }
}
