package com.example.demo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data   //using lombok create  getters setters and to-string
@AllArgsConstructor  //using lombok create all argument and no argument constructor
@NoArgsConstructor
public class User {
    @Id
    private int id;
    private String name;
    private int age;
}
