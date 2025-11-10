package com.example.demo.Controller;

import com.example.demo.Models.User;
import com.example.demo.Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * ViewController - MVC Controller for Thymeleaf Views
 * 
 * Serves HTML pages with Model attributes for Thymeleaf templates
 * Provides complete CRUD operations through web UI
 * 
 * Endpoints:
 * - GET  /users          -> Display all users page
 * - GET  /users/find     -> Find user by ID
 * - POST /users/add      -> Add new user
 * - POST /users/update   -> Update existing user
 * - POST /users/delete   -> Delete user by ID
 * 
 * Note: REST API endpoints available at /api/* (see UserController.java)
 * 
 * @author BICT Spring Boot CRUD
 * @version 1.0
 */
@Controller
@RequestMapping("/users")
public class ViewController {

    @Autowired
    private userService userService;

    /**
     * Display all users page
     * Main landing page for CRUD operations
     * 
     * @param model Model to pass data to view
     * @return users.html template
     */
    @GetMapping
    public String showUsersPage(Model model) {
        try {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            model.addAttribute("totalUsers", users.size());
            return "users";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load users: " + e.getMessage());
            return "users";
        }
    }

    /**
     * Find user by ID
     * Handles search functionality from UI
     * 
     * @param id User ID to search
     * @param model Model to pass search result
     * @return users.html with search result
     */
    @GetMapping("/find")
    public String findUserById(@RequestParam(required = false) Integer id, Model model) {
        // Load all users for display
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        
        if (id != null) {
            try {
                User user = userService.getUserById(id);
                if (user != null) {
                    model.addAttribute("singleUser", user);
                    model.addAttribute("searchSuccess", "User found!");
                } else {
                    model.addAttribute("userNotFound", "User with ID " + id + " not found");
                }
            } catch (Exception e) {
                model.addAttribute("error", "Error searching user: " + e.getMessage());
            }
        }
        
        return "users";
    }

    /**
     * Add new user
     * Handles form submission for creating user
     * 
     * @param user User object from form
     * @param redirectAttributes Flash attributes for success message
     * @return Redirect to users page
     */
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.addUser(user);
            redirectAttributes.addFlashAttribute("successMessage", 
                "✅ User '" + user.getName() + "' added successfully!");
            return "redirect:/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "❌ Failed to add user: " + e.getMessage());
            return "redirect:/users";
        }
    }

    /**
     * Update existing user
     * Handles form submission for updating user data
     * 
     * @param id User ID to update
     * @param user Updated user object from form
     * @param redirectAttributes Flash attributes for success message
     * @return Redirect to users page
     */
    @PostMapping("/update")
    public String updateUser(@RequestParam int id, @ModelAttribute User user, 
                            RedirectAttributes redirectAttributes) {
        try {
            User updatedUser = userService.updateUser(id, user);
            if (updatedUser != null) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    "✅ User ID " + id + " updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "❌ User with ID " + id + " not found");
            }
            return "redirect:/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "❌ Failed to update user: " + e.getMessage());
            return "redirect:/users";
        }
    }

    /**
     * Delete user by ID
     * Handles form submission for deleting user
     * 
     * @param id User ID to delete
     * @param redirectAttributes Flash attributes for success message
     * @return Redirect to users page
     */
    @PostMapping("/delete")
    public String deleteUser(@RequestParam int id, RedirectAttributes redirectAttributes) {
        try {
            String result = userService.deleteUser(id);
            if (result.contains("successfully")) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    "✅ User ID " + id + " deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "❌ User with ID " + id + " not found");
            }
            return "redirect:/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "❌ Failed to delete user: " + e.getMessage());
            return "redirect:/users";
        }
    }

    /**
     * Show add user form (if needed separately)
     * Optional: For dedicated add user page
     */
    @GetMapping("/add-form")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    /**
     * Show update form with pre-filled data
     * Optional: For dedicated update user page
     */
    @GetMapping("/update-form")
    public String showUpdateForm(@RequestParam int id, Model model) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                model.addAttribute("user", user);
                return "update-user";
            } else {
                model.addAttribute("error", "User not found");
                return "redirect:/users";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error loading user: " + e.getMessage());
            return "redirect:/users";
        }
    }
}
