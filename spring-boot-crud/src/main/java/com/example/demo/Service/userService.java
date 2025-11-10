package com.example.demo.Service;

import com.example.demo.DTO.userDTO;
import com.example.demo.Models.User;
import com.example.demo.Repo.userRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional //use single ATOMIC  transaction as database operation. for adhere to the ACID properties.
public class  userService {

    @Autowired
    private userRepo userRepo;

  public List<User> getAllUsers() {
      return userRepo.findAll();
  }

  public List<userDTO> getAllUserDTO() {
      return userRepo.findAll().stream().map(user -> new userDTO(user.getId(), user.getName())).collect(Collectors.toList());
  }

public  List <userDTO> getAllUsers2() {
      List<User> Users = userRepo.findAll();
      List <userDTO> userDTOS = new ArrayList<>();
      for (User user : Users) {
          userDTOS.add(new userDTO(user.getId(), user.getName()));
      }
      return userDTOS;
}

public User getUserById(int id) {
      Optional<User> user = userRepo.findById(id);
      if (user.isPresent()) {
          return user.get();
      }
     else {
         throw  new RuntimeException("md");
      }
}

public userDTO addUser(User user) {
      userRepo.save(user);
      return new userDTO(user.getId(), user.getName());
}

public User updateUser(int id, User user) {
    Optional<User> userOptional = userRepo.findById(id);
    if (userOptional.isPresent()) {
       User existing = userOptional.get();
       existing.setName(user.getName());
       existing.setAge(user.getAge());
       return userRepo.save(existing);
    }
    else {
        throw new RuntimeException("user not found");
    }
  }
public String deleteUser(int id) {
      userRepo.deleteById(id);
      return "delete success";
}

public  User getUserByName (String name) {
      return  userRepo.findByName(name).orElseThrow(() -> new RuntimeException("user not found"));
}
}
