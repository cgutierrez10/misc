#ifndef TERRAIN_H
#define TERRAIN_H

#include <igfxlayer.h>
#include <level_data.h>

class terrain : public igfxlayer
{
    public:
        terrain(SDL_Renderer*);
        virtual ~terrain();
        void redraw();
    protected:
        Mat<int>* world;
    private:
        void parse_lvl(vector<xy_container>*);
};

#endif // TERRAIN_H
