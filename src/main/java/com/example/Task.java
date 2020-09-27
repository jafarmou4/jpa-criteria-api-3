package com.example;

import javax.persistence.*;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private long id;
    private String description;
    private boolean enabled;


    @ManyToOne
    @JoinColumn(name="employee_task_id")
    private Employee employee;

    public Task() {
    }

    public Task(String description, Employee employee, boolean enabled) {
        this.description = description;
        this.employee = employee;
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public Employee getEmployee() {
        return employee;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
//                ", supervisor='" + employee + '\'' +
                '}';
    }
}
