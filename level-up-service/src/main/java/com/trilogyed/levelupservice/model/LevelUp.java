package com.trilogyed.levelupservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "LevelUp") //just in case we have some other "Invoice" entity in the future, this defines the name
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "level_up")
@Proxy(lazy=false)
public class LevelUp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer levelUpId;

    private Integer customerId;

    private Integer points;

    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate memberDate;

    public LevelUp(){}

    public LevelUp(Integer customerId, Integer points, @NotNull LocalDate memberDate) {
        this.customerId = customerId;
        this.points = points;
        this.memberDate = memberDate;
    }

    public LevelUp(Integer levelUpId, Integer customerId, Integer points, @NotNull LocalDate memberDate) {
        this.levelUpId = levelUpId
        this.customerId = customerId;
        this.points = points;
        this.memberDate = memberDate;
    }

    public Integer getLevelUpId() {
        return levelUpId;
    }

    public void setLevelUpId(Integer levelUpId) {
        this.levelUpId = levelUpId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDate getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(LocalDate memberDate) {
        this.memberDate = memberDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelUp levelUp = (LevelUp) o;
        return Objects.equals(levelUpId, levelUp.levelUpId) &&
                Objects.equals(customerId, levelUp.customerId) &&
                Objects.equals(points, levelUp.points) &&
                Objects.equals(memberDate, levelUp.memberDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelUpId, customerId, points, memberDate);
    }

    @Override
    public String toString() {
        return "LevelUp{" +
                "levelUpId=" + levelUpId +
                ", customerId=" + customerId +
                ", points=" + points +
                ", memberDate=" + memberDate +
                '}';
    }
}
