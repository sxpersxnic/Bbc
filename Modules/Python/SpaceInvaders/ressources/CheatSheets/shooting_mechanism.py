import pygame
import os

# Farben
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
RED = (255, 0, 0)
BLUE = (0, 0, 255)
GREEN = (0, 255, 0)

# Fenstergröße
WIDTH, HEIGHT = 800, 600

# Initialisierung
pygame.init()
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Space Invaders")
clock = pygame.time.Clock()

# Spieler
player = pygame.Rect(WIDTH // 2, HEIGHT - 50, 50, 50)
player_speed = 5

# Projektile
bullets = []
bullet_speed = 5

# Schussintervall
shoot_cooldown = 0

running = True

while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    keys = pygame.key.get_pressed()

    # Bewegung des Spielers
    if keys[pygame.K_LEFT] and player.left > 0:
        player.x -= player_speed
    if keys[pygame.K_RIGHT] and player.right < WIDTH:
        player.x += player_speed

    # Schießen
    if keys[pygame.K_SPACE] and shoot_cooldown == 0:
        bullet = pygame.Rect(player.centerx - 2, player.top, 4, 10)
        bullets.append(bullet)
        shoot_cooldown = 30  # Schussintervall in Frames

    # Bewegung der Projektile
    for bullet in bullets:
        bullet.y -= bullet_speed
        if bullet.bottom < 0:
            bullets.remove(bullet)

    # Schussintervall aktualisieren
    if shoot_cooldown > 0:
        shoot_cooldown -= 1

    # Zeichnen
    screen.fill(BLACK)
    pygame.draw.rect(screen, WHITE, player)
    for bullet in bullets:
        pygame.draw.rect(screen, RED, bullet)

    pygame.display.flip()
    clock.tick(60)

pygame.quit()
