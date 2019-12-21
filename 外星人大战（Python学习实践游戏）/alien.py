import pygame
from pygame.sprite import Sprite
class Alien(Sprite):
    def __init__(self,ai_settings,screen):
        super().__init__()
        self.screen = screen
        self.ai_settings = ai_settings
        self.image = pygame.image.load('alien.bmp')
        self.rect = self.image.get_rect()
        self.rect.x = self.rect.width
        self.rect.y = self.rect.height
        self.x = float(self.rect.x)

    def blitme(self):
        self.screen.blite(self.image,self.rect)

    def update(self):
        self.x += (self.ai_settings.alien_speed_factor*self.ai_settings.fleet_direction)
        self.rect.x = self.x
    def check_edges(self):
        if self.rect.right>=self.screen.get_rect().right or self.rect.left<=0:
            return True
