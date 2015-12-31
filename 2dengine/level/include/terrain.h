#ifndef TERRAIN_H
#define TERRAIN_H

#include "../include/igfxlayer.h"


class terrain : public igfxlayer
{
    public:
        terrain(SDL_Surface*);
        virtual ~terrain();
        void redraw();
    protected:
//        Mat<int>* world;
    private:
};

#endif // TERRAIN_H
