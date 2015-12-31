#include "terrain.h"

terrain::terrain(SDL_Renderer* screen)
{
    this->renderer = screen;
    this->resource = SDL_CreateRGBSurface(0, 32,64, 24, 0xFF0000,0x00FF00,0x0000FF,0x000000);
    world = new Mat<int>();
    world->zeros(200,200);
    //world->load("map.dat",csv_ascii);
    //world->save("map.out",raw_ascii);

    // Load a resource
    SDL_Surface* temp;
    temp = IMG_Load("Google-Drive-icon.png");
    if (temp == NULL) {
        printf("Unable to load bitmap: %s\n", SDL_GetError());
        //return 1;
    }

    SDL_BlitSurface(temp,NULL,resource,NULL);
    //resource = SDL_DisplayFormatAlpha(temp);
    SDL_FreeSurface(temp);
    //layer = SDL_DisplayFormat(screen);

}

terrain::~terrain()
{
    //dtor
}

void terrain::parse_lvl(vector<xy_container> *level)
{
    for (std::vector<xy_container>::iterator it=level->begin(); it!=level->end(); ++it)
    {
        if (it->t_units != 0)
        {
            world->at(it->x,it->y) = it->t_data[0].gfx;
        }
    }

}

void terrain::redraw()
{
    SDL_Rect src, dest;
    //If exists free sdl layer, then create layer large enough for entire map

    SDL_Surface *temp = SDL_CreateRGBSurface(0, world->n_rows * 32, world->n_cols * 32, 24, 0xFF0000,0x00FF00,0x0000FF,0x000000);
    SDL_UnlockSurface(temp);
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

            if (SDL_BlitSurface(this->resource,&src,temp,&dest) != 0)
            {
                printf("terrain blit failed: %s\n", SDL_GetError());
            }
        }
    }
    SDL_DestroyTexture(this->layer);
    SDL_LockSurface(temp);
    this->layer = SDL_CreateTextureFromSurface(this->renderer,temp);
    SDL_FreeSurface(temp);
    //printf("Terrain surface size: %d by %d\n", this->layer->w, this->layer->h);
}
