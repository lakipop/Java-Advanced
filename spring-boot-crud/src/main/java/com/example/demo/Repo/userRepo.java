package com.example.demo.Repo;

import com.example.demo.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepo  extends JpaRepository<User, Integer>
{
//no need anything. this is for use to JpaRepository  class default functions
Optional<User> findByName(String name);

}
