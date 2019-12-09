package com.trilogyed.levelupservice.repository;

import com.trilogyed.levelupservice.model.LevelUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelUpRepository extends JpaRepository<LevelUp, Integer> {
//    public List<LevelUp> findAllByCustomerId(int id);

    public LevelUp findByCustomerId(int id);

}
