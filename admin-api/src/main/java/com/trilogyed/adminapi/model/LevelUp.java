package com.trilogyed.adminapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "level_up")
public class LevelUp {
    /*
    !!!!!!!!!!!!!!!!!!!!!!!
        DO
            NOT
        FORGET
            TO
        GENERATE
            EQUALS
        TOSTRING
            AND 
        HASHCODE
    !!!!!!!!!!!!!!!!!!!!!!!
    */
    private Integer level_up_id;
    private Integer customer_id;
    private int points;
    @JsonFormat(pattern="dd-MM-yyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate member_date;
    //@OneToMany(mappedBy = note_id, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //private Set<Note> notes;

    public LevelUp() {
    }

    public LevelUp(Integer level_up_id, Integer customer_id, int points, LocalDate member_date) {
        this.level_up_id = level_up_id;
        this.customer_id = customer_id;
        this.points = points;
        this.member_date = member_date;
    }

    public LevelUp( Integer customer_id, int points, LocalDate member_date) {
        this.customer_id = customer_id;
        this.points = points;
        this.member_date = member_date;
    }

    @Override
    public String toString() {
        return "LevelUp{" +
                "level_up_id=" + level_up_id +
                ", customer_id=" + customer_id +
                ", points=" + points +
                ", member_date=" + member_date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelUp levelUp = (LevelUp) o;
        return points == levelUp.points &&
                Objects.equals(level_up_id, levelUp.level_up_id) &&
                customer_id.equals(levelUp.customer_id) &&
                member_date.equals(levelUp.member_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level_up_id, customer_id, points, member_date);
    }

    public Integer getlevel_up_id() {
        return level_up_id;
    }

    public void setlevel_up_id(Integer level_up_id) {
        this.level_up_id = level_up_id;
    }

    public Integer getcustomer_id() {
        return customer_id;
    }

    public void setcustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public int getpoints() {
        return points;
    }

    public void setpoints(int points) {
        this.points = points;
    }

    public LocalDate getmember_date() {
        return member_date;
    }

    public void setmember_date(LocalDate member_date) {
        this.member_date = member_date;
    }
}
