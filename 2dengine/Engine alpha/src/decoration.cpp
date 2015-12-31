#include "decoration.h"
#include "math.h"

decor::decor(SDL_Renderer* screen)
{
    this->renderer = screen;
    layer = SDL_CreateTexture(screen, SDL_PIXELFORMAT_ARGB8888, SDL_TEXTUREACCESS_TARGET, NULL,NULL);
    world = new Mat<int>();
    world->load("map2.dat",csv_ascii);
    world->save("map2.out",raw_ascii);

    // Load a resource
    SDL_Surface* temp;
    temp = IMG_Load("decor.png");
    if (temp == NULL) {
        printf("Unable to load bitmap: %s\n", SDL_GetError());
        //return 1;
    }

    //resource = SDL_CreateTextureFromSurface(screen, temp);

    // SDL 1.2 obs needs replacement?
    //resource = SDL_DisplayFormat(temp);

    // SDL 1.2 obs, not sure needed change
    //SDL_SetColorKey(resource, SDL_SRCCOLORKEY, SDL_MapRGBA(resource->format, 0, 0, 0,255));

    SDL_FreeSurface(temp);

    // Likely depreciated
    //layer = SDL_DisplayFormat(screen);
    //layer = SDL_DisplayFormatAlpha(layer);
}

decor::~decor()
{
    //dtor
}

void decor::redraw()
{

    SDL_Rect src, dest;

    // SDL 1.2 obs, also moved to initalizer cannot rerender without destroying now
    //If exists free sdl layer, then create layer large enough for entire map
    //SDL_FreeSurface(layer);

    // SDL 1.2 obs, replacement line moved to initalizer
    SDL_Surface *temp = SDL_CreateRGBSurface(0, world->n_rows * 32, world->n_cols * 32, 24, 0xFF0000,0x00FF00,0x0000FF,0x000000FF);
    SDL_UnlockSurface(temp);
    //SDL_FillRect(layer, NULL, 0x000000FF);

    // Linear gfx resource
    // 0 is first tile 1 is second etc across resource map
    printf("World size: %i by %i\n", world->n_rows, world->n_cols);

    for (int i = 0; i < this->world->n_rows; i++)
    {
        for (int j = 0; j < this->world->n_cols; j++)
         {
            if (this->world->at(j,i) != 0)
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
                    printf("Decoration Tileblit failed: %s\n", SDL_GetError());
                }
            }
        }
    }
    SDL_LockSurface(temp);
    SDL_DestroyTexture(this->layer);
    this->layer = SDL_CreateTextureFromSurface(this->renderer, temp);
    SDL_FreeSurface(temp);


    // SDL 1.2 incompatible code
    //printf("Decoration surface size: %d by %d\n", this->layer->w, this->layer->h);
}
