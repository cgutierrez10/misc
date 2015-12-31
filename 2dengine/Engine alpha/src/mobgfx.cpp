#include "mobgfx.h"

mobgfx::mobgfx(SDL_Renderer* screen)
{
    //ctor
    this->renderer = screen;
    // Load a resource
    SDL_Surface* temp;
    this->resource = SDL_CreateRGBSurface(0, 32,32, 24, 0xFF0000,0x00FF00,0x0000FF,0x000000FF);
    temp = IMG_Load("ALPH2.png");
    if (temp == NULL) {
        printf("Unable to load bitmap: %s\n", SDL_GetError());
    }

    //resource = SDL_CreateTextureFromSurface(screen, temp);

    // SDL 1.2 obs
    SDL_BlitSurface(temp,NULL,resource,NULL);
    //resource = SDL_DisplayFormat(temp);
    SDL_FreeSurface(temp);

    // SDL 1.2 obs ???
    //layer = SDL_DisplayFormatAlpha(screen);

    map_x = 0;
    map_y = 0;

}

mobgfx::~mobgfx()
{
    //dtor
}

void mobgfx::set_moblist(Mat<int>* mobs)
{
    this->moblist = mobs;
}

void mobgfx::redraw()
{
    SDL_Surface *temp = SDL_CreateRGBSurface(0, 640,480, 32, 0x000000FF,0x0000FF00,0x00FF0000,0xFF000000);
    SDL_UnlockSurface(temp);

    // If player moving past rubberband, blit entire surface one player movement aside, updating map_x, map_y
    // Player will always be the first column of the matrix
    if (moblist->at(1,1) < 160)
    {
        map_x = map_x - 8;
        moblist->at(1,1) = 160;
    }
    if (moblist->at(1,1) > 480)
    {
        map_x = map_x + 8;
        moblist->at(1,1) = 480;
    }
    if (moblist->at(1,2) < 96)
    {
        map_y = map_y - 8;
        moblist->at(1,2) = 96;
    }
    // Sprite is off center by 2 pixel vertical
    // This corresponds to the y > 9 tile in the mob rubberbanding logic
    if (moblist->at(1,2) > 288)
    {
        map_y = map_y + 8;
        moblist->at(1,2) = 288;
    }

    // Simplest case using 1x6 column for player gfx only
        SDL_Rect src, dest;
    // mobdraw, dest x, y, type, source x, y

    src.x = moblist->at(1,4);
    src.y = moblist->at(1,5);  // const for now, will use type later
    src.w = 39; // const for now
    src.h = moblist->at(1,6);

    dest.x = moblist->at(1,1) - 4;
    dest.y = moblist->at(1,2) + 2;
    dest.w = 39;
    dest.h = moblist->at(1,6);
/*
    src.x = 0;
    src.y = 0;
    src.w = 32;
    src.h = 32;

    dest.x = 320;
    dest.y = 240;
    dest.w = 32;
    dest.h = 32;
*/
    if (SDL_BlitSurface(this->resource,&src,temp,&dest) != 0)
    {
        printf("Mob blit failed: %s\n", SDL_GetError());
    }
    SDL_LockSurface(temp);
    SDL_DestroyTexture(this->layer);
    this->layer = SDL_CreateTextureFromSurface(this->renderer, temp);
    SDL_FreeSurface(temp);
}
