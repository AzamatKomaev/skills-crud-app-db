package com.azamat_komaev.crudapp.model;

import java.util.Objects;

public class Skill {
    private Integer id;
    private String name;
    private Status status = Status.ACTIVE;

    public Skill() {}

    public Skill(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Skill{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", status=" + status +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill skill)) return false;
        return id.equals(skill.id) && Objects.equals(name, skill.name) && status == skill.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
