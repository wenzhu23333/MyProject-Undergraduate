import sys
import pygame
from bullet import Bullet
from alien import Alien
def check_events(ai_settings,screen,ship,bullets):

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()
        elif event.type == pygame.KEYDOWN:
            check_keydown_events(event,ai_settings,screen,ship,bullets)
        elif event.type == pygame.KEYUP:
            check_keyup_events(event,ship)
        elif event.type == pygame.K_q:
            sys.exit()
def check_keydown_events(event,ai_settings,screen,ship,bullets):
    if event.key == pygame.K_RIGHT:
        ship.moving_right = True
    if event.key == pygame.K_LEFT:
        ship.moving_left = True
    elif event.key == pygame.K_SPACE:
        fire_bullets(ai_settings,screen,ship,bullets)

def check_keyup_events(event,ship):
    if event.key == pygame.K_RIGHT:
        ship.moving_right = False
    if event.key == pygame.K_LEFT:
        ship.moving_left = False

def update_screen(ai_settings,screen,aliens,ship,bullets):

    screen.fill(ai_settings.bg_color)
    ship.blitme()
    aliens.draw(screen)
    for bullet in bullets.sprites():
        bullet.draw_bullet()


    pygame.display.flip()

def update_bullets(aliens,bullets):
    bullets.update()
    for bullet in bullets.copy():
        if bullet.rect.bottom <= 0:
            bullets.remove(bullet)
    collisions = pygame.sprite.groupcollide(bullets,aliens,True,True)

def fire_bullets(ai_settings,screen,ship,bullets):
    if len(bullets) < ai_settings.bullets_allowed:
        new_bullet = Bullet(ai_settings, screen, ship)
        bullets.add(new_bullet)

def creat_fleet(ai_settings,ship,screen,aliens):
    alien = Alien(ai_settings,screen)
    alien_width = alien.rect.width
    alien_height = alien.rect.height
    available_space_x = ai_settings.screen_width-2*alien_width
    number_aliens_x = int(available_space_x/(2*alien_width))

    available_space_y = ai_settings.screen_height - 3*alien_height-ship.rect.height
    number_rows = int(available_space_y/(2*alien_height))

    for row_number in range(number_rows):
        for alien_number in range(number_aliens_x):
            alien = Alien(ai_settings,screen)
            alien.x = alien_width+2*alien_width*alien_number
            alien.rect.x = alien.x
            alien.rect.y = alien.rect.height+2*alien.rect.height*row_number
            aliens.add(alien)

def update_aliens(ai_settings,aliens):
    check_fleet_edges(ai_settings,aliens)
    aliens.update()

def check_fleet_edges(ai_settings,aliens):
    for alien in aliens.sprites():
        if alien.check_edges():
            change_fleet_direction(ai_settings,aliens)
            break

def change_fleet_direction(ai_settings,aliens):
    for alien in aliens.sprites():
        alien.rect.y += ai_settings.fleet_drop_speed
    ai_settings.fleet_direction *= -1

