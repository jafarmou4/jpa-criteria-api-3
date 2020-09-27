package com.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    public Employee(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Employee(Collection<Task> tasks) {
        tasks.forEach(task -> this.tasks.add(task));
    }

    public Employee() {

    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public static Employee create(String name, Task... tasks) {
        Employee e = new Employee();
        e.name = name;
        if (tasks != null) {
            e.tasks = Arrays.asList(tasks);
        }
        return e;
    }

    public static Employee create(String name) {
        Employee e = new Employee();
        e.name = name;
        return e;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
