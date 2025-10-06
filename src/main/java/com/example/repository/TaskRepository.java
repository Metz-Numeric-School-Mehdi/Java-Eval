package com.example.repository;

import com.example.model.Task;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    /**
     * Trouve toutes les tâches d'un utilisateur
     */
    List<Task> findByUser(User user);
    
    /**
     * Trouve toutes les tâches d'un utilisateur par son ID
     */
    List<Task> findByUserId(Long userId);
    
    /**
     * Trouve les tâches complétées
     */
    List<Task> findByCompleted(Boolean completed);
    
    /**
     * Trouve les tâches d'un utilisateur par statut de completion
     */
    List<Task> findByUserAndCompleted(User user, Boolean completed);
    
    /**
     * Trouve les tâches par titre (recherche insensible à la casse)
     */
    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Task> findByTitleContainingIgnoreCase(String title);
    
    /**
     * Compte le nombre de tâches par utilisateur
     */
    @Query("SELECT COUNT(t) FROM Task t WHERE t.user.id = ?1")
    Long countByUserId(Long userId);
    
    /**
     * Compte le nombre de tâches complétées par utilisateur
     */
    @Query("SELECT COUNT(t) FROM Task t WHERE t.user.id = ?1 AND t.completed = true")
    Long countCompletedByUserId(Long userId);
}