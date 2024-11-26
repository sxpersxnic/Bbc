-- -----------------------------------------------------
-- DML
-- -----------------------------------------------------

USE `ticketshop_v1_db` ;

DELETE FROM `ticketshop_v1_db`.`person_role` WHERE `person_id` != 0;
DELETE FROM `ticketshop_v1_db`.`role` WHERE id != 0;
DELETE FROM `ticketshop_v1_db`.`event` WHERE id != 0;
DELETE FROM `ticketshop_v1_db`.`person` WHERE id != 0;


INSERT INTO `ticketshop_v1_db`.`role` (`name`)
VALUES ('USER'),
       ('ADMIN'),
       ('MANAGER');

-- Admin: admin_password
-- Manager: manager_password
-- User: user_password
INSERT INTO `ticketshop_v1_db`.`person` (`email`, `password`)
VALUES ('admin@localhost', '$2a$10$wf0Rc/b/BEQ4KTkztm3Mce844YNjbU4J/LfbAwBJmGlqQzUVhzZmK'),
       ('eventmanagement@localhost', '$2a$10$T/pCAYbcccrsr1X4SyTFOO1xG3deUZ2ahO3NVWB3DBnMNYBDVkagm'),
       ('user@localhost', '$2a$10$uf8kZ4rtuybi5tjCXq4Oze6KCAvqTdPUJU5WsAPtfJzbW3ogZ7hDm');

INSERT INTO `ticketshop_v1_db`.`person_role` (`person_id`, `role_id`)
VALUES ((SELECT id FROM person WHERE email = 'user@localhost'), (SELECT id FROM role WHERE `name` = 'USER')),
       ((SELECT id FROM person WHERE email = 'admin@localhost'), (SELECT id FROM role WHERE `name` = 'USER')),
       ((SELECT id FROM person WHERE email = 'eventmanagement@localhost'), (SELECT id FROM role WHERE `name` = 'USER')),
       ((SELECT id FROM person WHERE email = 'admin@localhost'), (SELECT id FROM role WHERE `name` = 'ADMIN')),
       ((SELECT id FROM person WHERE email = 'eventmanagement@localhost'), (SELECT id FROM role WHERE `name` = 'MANAGER'));

SELECT p.email, GROUP_CONCAT(r.name SEPARATOR ', ') AS roles
FROM person p
JOIN person_role pr ON pr.person_id = p.id
JOIN role r ON r.id = pr.role_id
GROUP BY p.email
ORDER BY p.email;
        
SELECT * FROM `person`;
SELECT * FROM `role`;