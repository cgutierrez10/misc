#ifndef MOBGFX_H
#define MOBGFX_H
#include <igfxlayer.h>

#ifndef _Mob
#define _Mob
#include "mob.h"
#endif



class mobgfx : public igfxlayer
{
    public:
        mobgfx(SDL_Renderer*);
        virtual ~mobgfx();
        void redraw();
        void set_moblist(Mat<int>*);
        int get_x() { return map_x; };
        int get_y() { return map_y; };
        // Redraw polls each mob* in turn
        // Each mob* adds itself to a list of items to draw
        // With x,y of screen + 2 tiles
        // And x,y of upper left pixel of current sprite to draw for it
        // Then redraw() renders this list as an alpha layer and returns
    protected:
        Mat<int>* moblist;

    private:
};

#endif // MOBGFX_H
