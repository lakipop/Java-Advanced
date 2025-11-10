package com.example.demo.Controller;

import com.example.demo.DTO.userDTO;
import com.example.demo.Models.User;
import com.example.demo.Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private  userService userService;

@GetMapping("/allusers")
    public List<User> getAllUsers() {
    return userService.getAllUsers();
}

@GetMapping("alluserDTO")
    public List<userDTO> getAllUserDTO() {
    return userService.getAllUserDTO();
}

@GetMapping("allusers2")
    public List<userDTO> getAllUsers2() {
    return userService.getAllUsers2();
}

@GetMapping("findbyid/{id}")
    public User getUserById( @PathVariable int id) {
    return userService.getUserById(id);
}

@PostMapping("adduser")
    public userDTO addUser(@RequestBody User  user) {
    return userService.addUser(user);
}

@PutMapping("updateuser/{id}")
    public User updateUser (@PathVariable  int id, @RequestBody  User user) {
  return userService.updateUser(id,user);
}

@DeleteMapping("removeuser/{id}")
    public String removeUser (@PathVariable  int id) {
    return userService.deleteUser(id);
}

    @GetMapping("getbyname/name")
    public User getUserByName (@RequestParam String name){
        return userService.getUserByName(name);
    }
}

