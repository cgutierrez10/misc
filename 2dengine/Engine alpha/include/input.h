#ifndef _SDLIncluded
#define _SDLIncluded
#include <windows.h>
#include <SDL.h>
#include <SDL_opengl.h>
#endif

#ifndef _InputInterface
#define _InputInterface
#include <ihandle.h>
#endif

#ifndef _Mob
#define _Mob
#include "mob.h"
#endif

#ifndef _GameCore
#define _GameCore
#include "gamecore.h"
#endif

class Input
{
  public:
    Input();
    void keyproc(SDL_Event*, Mob*);
    //void inputEvent(SDL_Event*);
    //void SetHandler(IHandle*);
  private:
    //void key(SDL_Event*, UIState*);         // Future use, will forward
                                            // through an interface
    //void mousemove(SDL_Event*, UIState*);   // Forwards mouse movement events
    //void mousebutton(SDL_Event*, UIState*); // Forwards a mouse button event
    //IHandle* _handler;
    struct UIState
    {
      int mousex;
      int mousey;
      int mousedown;
      int hotitem;
      int activeitem;
    };
    UIState inputstate;
};

