package com.example.controller;

import com.example.model.Task;
import com.example.model.User;
import com.example.repository.TaskRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Affiche la liste de toutes les tâches
     */
    @GetMapping
    public String listTasks(Model model, @RequestParam(required = false) String filter) {
        if ("completed".equals(filter)) {
            model.addAttribute("tasks", taskRepository.findByCompleted(true));
            model.addAttribute("pageTitle", "Tâches terminées");
        } else if ("pending".equals(filter)) {
            model.addAttribute("tasks", taskRepository.findByCompleted(false));
            model.addAttribute("pageTitle", "Tâches en cours");
        } else {
            model.addAttribute("tasks", taskRepository.findAll());
            model.addAttribute("pageTitle", "Toutes les tâches");
        }
        model.addAttribute("currentFilter", filter);
        return "tasks/list";
    }

    /**
     * Affiche les détails d'une tâche spécifique
     */
    @GetMapping("/{id}")
    public String showTask(@PathVariable Long id, Model model) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            model.addAttribute("task", taskOpt.get());
            return "tasks/detail";
        } else {
            return "redirect:/tasks?error=TaskNotFound";
        }
    }

    /**
     * Affiche le formulaire de création d'une nouvelle tâche
     */
    @GetMapping("/new")
    public String newTaskForm(Model model, @RequestParam(required = false) Long userId) {
        Task task = new Task();
        if (userId != null) {
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isPresent()) {
                task.setUser(userOpt.get());
            }
        }
        model.addAttribute("task", task);
        model.addAttribute("users", userRepository.findAll());
        return "tasks/form";
    }

    /**
     * Traite la création d'une nouvelle tâche
     */
    @PostMapping
    public String createTask(@Valid @ModelAttribute Task task, BindingResult result, 
                           @RequestParam Long userId, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("users", userRepository.findAll());
            return "tasks/form";
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            task.setUser(userOpt.get());
            task.setCreatedAt(LocalDateTime.now());
            taskRepository.save(task);
            redirectAttributes.addFlashAttribute("successMessage", "Tâche créée avec succès!");
            return "redirect:/tasks";
        } else {
            result.rejectValue("user", "user.notfound", "Utilisateur non trouvé");
            model.addAttribute("users", userRepository.findAll());
            return "tasks/form";
        }
    }

    /**
     * Affiche le formulaire d'édition d'une tâche
     */
    @GetMapping("/{id}/edit")
    public String editTaskForm(@PathVariable Long id, Model model) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            model.addAttribute("task", taskOpt.get());
            model.addAttribute("users", userRepository.findAll());
            return "tasks/form";
        } else {
            return "redirect:/tasks?error=TaskNotFound";
        }
    }

    /**
     * Traite la modification d'une tâche
     */
    @PostMapping("/{id}")
    public String updateTask(@PathVariable Long id, @Valid @ModelAttribute Task task, 
                           BindingResult result, @RequestParam Long userId, Model model, 
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("users", userRepository.findAll());
            return "tasks/form";
        }

        Optional<Task> existingTaskOpt = taskRepository.findById(id);
        Optional<User> userOpt = userRepository.findById(userId);
        
        if (existingTaskOpt.isPresent() && userOpt.isPresent()) {
            Task existingTask = existingTaskOpt.get();
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setCompleted(task.getCompleted());
            existingTask.setDueDate(task.getDueDate());
            existingTask.setUser(userOpt.get());
            
            taskRepository.save(existingTask);
            redirectAttributes.addFlashAttribute("successMessage", "Tâche modifiée avec succès!");
            return "redirect:/tasks/" + id;
        } else {
            return "redirect:/tasks?error=TaskOrUserNotFound";
        }
    }

    /**
     * Bascule le statut de completion d'une tâche
     */
    @PostMapping("/{id}/toggle")
    public String toggleTaskCompletion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setCompleted(!task.getCompleted());
            taskRepository.save(task);
            redirectAttributes.addFlashAttribute("successMessage", 
                task.getCompleted() ? "Tâche marquée comme terminée!" : "Tâche marquée comme en cours!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Tâche non trouvée!");
        }
        return "redirect:/tasks";
    }

    /**
     * Supprime une tâche
     */
    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            taskRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Tâche supprimée avec succès!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Tâche non trouvée!");
        }
        return "redirect:/tasks";
    }
}