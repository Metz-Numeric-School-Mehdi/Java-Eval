package com.example.controller;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Affiche la liste de tous les utilisateurs
     */
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    /**
     * Affiche les détails d'un utilisateur spécifique
     */
    @GetMapping("/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            model.addAttribute("user", user);
            model.addAttribute("tasks", taskRepository.findByUserId(id));
            model.addAttribute("totalTasks", taskRepository.countByUserId(id));
            model.addAttribute("completedTasks", taskRepository.countCompletedByUserId(id));
            return "users/detail";
        } else {
            return "redirect:/users?error=UserNotFound";
        }
    }

    /**
     * Affiche le formulaire de création d'un nouvel utilisateur
     */
    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }

    /**
     * Traite la création d'un nouvel utilisateur
     */
    @PostMapping
    public String createUser(@Valid @ModelAttribute User user, BindingResult result, 
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/form";
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "email.exists", "Cet email est déjà utilisé");
            return "users/form";
        }

        userRepository.save(user);
        redirectAttributes.addFlashAttribute("successMessage", "Utilisateur créé avec succès!");
        return "redirect:/users";
    }

    /**
     * Affiche le formulaire d'édition d'un utilisateur
     */
    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable Long id, Model model) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "users/form";
        } else {
            return "redirect:/users?error=UserNotFound";
        }
    }

    /**
     * Traite la modification d'un utilisateur
     */
    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, @Valid @ModelAttribute User user, 
                           BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/form";
        }

        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            
            // Vérifier si l'email n'est pas déjà utilisé par un autre utilisateur
            if (!existingUser.getEmail().equals(user.getEmail()) && 
                userRepository.existsByEmail(user.getEmail())) {
                result.rejectValue("email", "email.exists", "Cet email est déjà utilisé");
                return "users/form";
            }

            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur modifié avec succès!");
            return "redirect:/users/" + id;
        } else {
            return "redirect:/users?error=UserNotFound";
        }
    }

    /**
     * Supprime un utilisateur
     */
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            userRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur supprimé avec succès!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Utilisateur non trouvé!");
        }
        return "redirect:/users";
    }
}