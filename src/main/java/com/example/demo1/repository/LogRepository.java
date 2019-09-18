package com.example.demo1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo1.domain.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {

	Optional<Log> findOneByName(String name);

	Optional<Log> findOneById(Integer id);
}
