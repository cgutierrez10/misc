#ifndef DECOR_H
#define DECOR_H

#include <igfxlayer.h>


class decor : public igfxlayer
{
    public:
        decor(SDL_Renderer*);
        virtual ~decor();
        void redraw();
    protected:
        Mat<int>* world;
    private:
};

#endif // DECOR_H
