
import pygame
from settings import Settings
from ship import ship
import check_functions as gf
from  pygame.sprite import Group

def run_game():
    pygame.init()
    ai_serttings = Settings()
    screen = pygame.display.set_mode(
        (ai_serttings.screen_width, ai_serttings.screen_height))
    pygame.display.set_caption("Alien Invasion")
    bullets = Group()
    aliens = Group()

    Ship = ship(ai_serttings,screen)
    gf.creat_fleet(ai_serttings, Ship, screen, aliens)
    while True:

        gf.check_events(ai_serttings,screen,Ship,bullets)
        Ship.update()

        gf.update_bullets(aliens,bullets)
        gf.update_aliens(ai_serttings,aliens)
        gf.update_screen(ai_serttings,screen,aliens,Ship,bullets)

run_game()
