#include "../include/terrain.h"

terrain::terrain(SDL_Surface* screen)
{
    // Load a resource
    SDL_Surface* temp;
    temp = IMG_Load("err_ground.bmp");
    if (temp == NULL) {
        printf("Unable to load bitmap: %s\n", SDL_GetError());
        //return 1;
    }

    resource = SDL_DisplayFormatAlpha(temp);
    SDL_FreeSurface(temp);
    //layer = SDL_DisplayFormat(screen);
}

terrain::~terrain()
{
    //dtor
}

void terrain::redraw()
{
/*
    SDL_Rect src, dest;
    //If exists free sdl layer, then create layer large enough for entire map
    SDL_FreeSurface(layer);
    layer = SDL_CreateRGBSurface(SDL_HWSURFACE, world->n_rows * 32, world->n_cols * 32, 24, 0xFF0000,0x00FF00,0x0000FF,0x000000);
    // Linear gfx resource
    // 0 is first tile 1 is second etc across resource map
    printf("World size: %i by %i\n", world->n_rows, world->n_cols);

    for (unsigned i = 0; i < this->world->n_rows; i++)
    {
        for (unsigned j = 0; j < this->world->n_cols; j++)
         {
            src.x = 32 * this->world->at(j,i);
            src.y = 0;
            src.w = 32; //image->w;
            src.h = 32; //image->h;

            dest.x = (32 * i);
            dest.y = (32 * j);
            dest.w = 32;
            dest.h = 32;

            if (SDL_BlitSurface(this->resource,&src,this->layer,&dest) != 0)
            {
                printf("Tileblit failed: %s\n", SDL_GetError());
            }
        }
    }
    printf("Terrain surface size: %d by %d\n", this->layer->w, this->layer->h);
    */
}
