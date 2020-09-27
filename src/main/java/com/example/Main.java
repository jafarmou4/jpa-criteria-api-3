package com.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

class ExampleMain {
    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("example-unit");

    public static void main(String[] args) {
        try {
            persistEmployees();
            findEmployeesWithTasks();
            findEmployeesWithTasks2();
        } finally {
            entityManagerFactory.close();
        }
    }

    public static void persistEmployees() {


        Employee employee1 = Employee.create("Diana");
        Employee employee2 = Employee.create("Mike");
        Employee employee3 = Employee.create("Tim");
        Employee employee4 = Employee.create("Jack");

        Task task1 = new Task("Coding", employee1, true);
        Task task2 = new Task("Refactoring", employee2, false);
        Task task3 = new Task("Designing", employee2, true);
        Task task4 = new Task("Documentation", employee3, true);

        employee1.addTask(task1);
        employee2.addTask(task2);
        employee2.addTask(task3);
        employee3.addTask(task4);

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(employee1);
        em.persist(employee2);
        em.persist(employee3);
        em.persist(employee4);
//        em.persist(task1);
//        em.persist(task2);
//        em.persist(task3);
//        em.persist(task4);
        em.getTransaction().commit();
        em.close();

        System.out.println("-- Employee persisted --");
        System.out.println(employee1);
        System.out.println(employee2);
        System.out.println(employee3);
        System.out.println(employee4);
        System.out.println("\n\n");
    }

    private static void findEmployeesWithTasks() {
        System.out.println("-- find employees with tasks --");
        EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> employee = query.from(Employee.class);

        Join<Object, Object> join = employee.join(Employee_.TASKS);

        query.select(employee).distinct(true);
        query.where(cb
                .and(
//                        cb.equal(join.get("description"), "Designing"),
                        cb.equal(join.get("description"), "Refactoring")
                ));
        TypedQuery<Employee> typedQuery = em.createQuery(query);
        typedQuery.getResultList().forEach(System.out::println);
        System.out.println("\n\n");
    }

    private static void findEmployeesWithTasks2() {
        System.out.println("-- find employees with tasks 2 --");
        EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Employee> employee = query.from(Employee.class);

        Join<Object, Object> join = employee.join(Employee_.TASKS);

        Path<String> namePath = employee.get(Employee_.NAME);

        query.multiselect(employee.get(Employee_.ID), namePath, join.get("description"), join.get("enabled")).distinct(true);
        query.where(
                cb.and(
                    cb.equal(employee.get("name"), "Mike"),
                    cb.equal(join.get("enabled"), true)
                ));
        TypedQuery<Object[]> typedQuery = em.createQuery(query);
        typedQuery.getResultList().forEach(System.out::println);
        System.out.println("\n\n");
    }
}
