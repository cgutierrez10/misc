#ifndef _SDLIncluded
#define _SDLIncluded
#include <windows.h>
#include <SDL.h>
#include <SDL_opengl.h>
#endif

class iHandler
{
  public:
    virtual void key(SDL_Event*) = 0;
    virtual void mousemove(SDL_Event*) = 0;
    virtual void mousebutton(SDL_Event*) = 0;
};
