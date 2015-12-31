#include "igfxlayer.h"

igfxlayer::igfxlayer()
{

}

igfxlayer::~igfxlayer()
{
    //dtor
}

SDL_Texture* igfxlayer::getlayer()
{
    return layer;
}

void igfxlayer::set_xy(int x,int y)
{
    map_x = x;
    map_y = y;
    return;
}

int igfxlayer::init_map(int* grid_map, int elements)
{
    if (elements != (map_x * map_y))
    {
        return -1; // Elements mismatch
    }
    else
    {
        //surface_map = grid_map;
    }
    return 0;
}
