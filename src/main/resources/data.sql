-- Insertion des utilisateurs
INSERT INTO users (name, email) VALUES ('Alice Martin', 'alice.martin@email.com');
INSERT INTO users (name, email) VALUES ('Bob Dupont', 'bob.dupont@email.com');
INSERT INTO users (name, email) VALUES ('Charlie Moreau', 'charlie.moreau@email.com');

-- Insertion des tâches
INSERT INTO tasks (title, description, completed, created_at, due_date, user_id) VALUES 
('Finir le projet Spring Boot', 'Terminer l''application de gestion de tâches avec toutes les fonctionnalités', false, CURRENT_TIMESTAMP, DATEADD('DAY', 7, CURRENT_TIMESTAMP), 1);

INSERT INTO tasks (title, description, completed, created_at, due_date, user_id) VALUES 
('Réviser pour l''examen', 'Réviser les concepts de Spring Boot et JPA', false, CURRENT_TIMESTAMP, DATEADD('DAY', 3, CURRENT_TIMESTAMP), 1);

INSERT INTO tasks (title, description, completed, created_at, user_id) VALUES 
('Faire les courses', 'Acheter des légumes et des fruits', true, CURRENT_TIMESTAMP, 1);

INSERT INTO tasks (title, description, completed, created_at, due_date, user_id) VALUES 
('Préparer la présentation', 'Créer les slides pour la présentation de mardi', false, CURRENT_TIMESTAMP, DATEADD('DAY', 2, CURRENT_TIMESTAMP), 2);

INSERT INTO tasks (title, description, completed, created_at, user_id) VALUES 
('Lire un livre', 'Lire le livre sur les microservices', false, CURRENT_TIMESTAMP, 2);

INSERT INTO tasks (title, description, completed, created_at, due_date, user_id) VALUES 
('Organiser la réunion', 'Planifier la réunion d''équipe pour discuter du nouveau projet', false, CURRENT_TIMESTAMP, DATEADD('DAY', 1, CURRENT_TIMESTAMP), 3);

INSERT INTO tasks (title, description, completed, created_at, user_id) VALUES 
('Nettoyer le bureau', 'Ranger et nettoyer l''espace de travail', true, CURRENT_TIMESTAMP, 3);