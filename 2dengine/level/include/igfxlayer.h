#ifndef IGFXLAYER_H
#define IGFXLAYER_H

#ifndef _SDL
#define _SDL
#include "sdl.h"
//#include "sdl_image.h"
#endif

#include <stdio.h>
#include <string>
#include <vector>

using namespace std;

class igfxlayer
{
    public:
        igfxlayer();
        virtual ~igfxlayer();

        virtual void redraw()=0;    // Blit the layer together
        void set_xy(int,int);
        int init_map(int*, int);
        SDL_Surface* getlayer();
        SDL_Surface* layer;

    protected:

        SDL_Surface* resource;
        int map_x; // Map max x
        int map_y; // Map max y
};

#endif // IGFXLAYER_H
