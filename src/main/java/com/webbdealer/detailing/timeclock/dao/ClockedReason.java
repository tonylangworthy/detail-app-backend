package com.webbdealer.detailing.timeclock.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clocked_reasons")
public class ClockedReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "clockedReason")
    @JsonIgnore
    private List<TimeClock> timeClocks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimeClock> getTimeclocks() {
        return timeClocks;
    }

    public void setTimeclocks(List<TimeClock> timeClocks) {
        this.timeClocks = timeClocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClockedReason that = (ClockedReason) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(timeClocks, that.timeClocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, timeClocks);
    }

    @Override
    public String toString() {
        return "ClockedReason{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
