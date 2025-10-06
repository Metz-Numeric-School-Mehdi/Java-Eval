package com.example.controller;

import com.example.repository.TaskRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Page d'accueil avec statistiques
     */
    @GetMapping("/")
    public String home(Model model) {
        long totalUsers = userRepository.count();
        long totalTasks = taskRepository.count();
        long completedTasks = taskRepository.findByCompleted(true).size();
        long pendingTasks = taskRepository.findByCompleted(false).size();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalTasks", totalTasks);
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("pendingTasks", pendingTasks);
        model.addAttribute("recentTasks", taskRepository.findByCompleted(false));

        return "home";
    }
}