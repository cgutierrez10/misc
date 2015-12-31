#ifndef _stdinc
#define _stdinc
#include <stdio.h>
#include <string>
#include <utility>
using namespace std;
#endif

#ifdef main
#undef main
#endif

#ifndef _SDLincluded
#define _SDLincluded
#include <windows.h>
#include <GL\gl.h>
#include <GL\glu.h>
#include <SDL.h>
#endif

#define TITLE "Glenishmore"

#include "core.h"

class Elements;
class GameCore;


void collision(Elements);
void physics(Mob*);
void renderscene(SDL_Texture*, SDL_Texture*, SDL_Renderer*, SDL_Texture*, int, int);
//Meter shields;

int DrawImage( SDL_Surface*, char*, int, int);

int main(int argc, char **argv) {
//	int done;
	Mob* mobs;
  Input* UInput = new Input();

  // Replacing SDL1.2 with 2.0 compat
  SDL_Window *window = SDL_CreateWindow("Engine Alpha", SDL_WINDOWPOS_UNDEFINED,SDL_WINDOWPOS_UNDEFINED,640,480,SDL_WINDOW_OPENGL);
  SDL_Renderer *renderer = SDL_CreateRenderer(window, -1, 0);
  SDL_Texture *screen = SDL_CreateTexture(renderer, SDL_PIXELFORMAT_ARGB8888, SDL_TEXTUREACCESS_STATIC, 640,480);

  // Obs sdl1.2 ???
  //SDL_SetAlpha(screen,SDL_SRCALPHA,SDL_ALPHA_OPAQUE);

  if (screen == NULL)
  {
      cout << "screen failed to init" << endl;
      return 1;
  }

  const int FRAMES_PER_SECOND = 30;
  const int SKIP_TICKS = 2500 / FRAMES_PER_SECOND;
  srand(SDL_GetTicks()); //Initialize random
  int sleep_time = 0;
	SDL_Event events;
	mobs = new Mob();

  /* Prepare extra surfaces */

  // new terrain call has hardcoded images will segfault if path bad
  terrain* ground = new terrain(renderer);

  // Out for debug
  //decor* decoration = new decor(screen);

  // new mobgfx call has hardcoded images will segfault if path bad
  mobgfx* mobiles = new mobgfx(renderer);

  /* Initialize the extra surfaces */

  ground->set_xy(20,15);
  ground->redraw();

  // Out for debug
  //decoration->set_xy(20,15);
  //decoration->redraw();

  mobiles->set_moblist(mobs->update());


  GameCore::Status::game_is_running(true); // Starts game, menu bypass
	//intro.menu(); //First call initializes menu, keypresses subsequently refresh

    // SDL 1.2 obs, needs replacement
	//Uint8* key = SDL_GetKeyState(NULL);

	//Setup game elements prior to loop beginning

  // SDL_Getticks() returns the current number of milliseconds
  // that have elapsed since the system was started


  Uint32 next_game_tick = SDL_GetTicks();


  while( GameCore::Status::game_is_running() ) {

    // Mob handling list tick each item and draw
    mobiles->set_moblist(mobs->update());
    mobiles->redraw();

    // Grab terrain and draw all layers
    // SDL 2 version
    renderscene(ground->getlayer(), mobiles->getlayer(), renderer, screen, 0,0); //mobiles->get_x(), mobiles->get_y()); //Draw screen

    SDL_PollEvent(&events);
    UInput->keyproc(&events, mobs); // Input handling

    next_game_tick += SKIP_TICKS; //Beginning of game loop code
    sleep_time = next_game_tick - SDL_GetTicks();
    if( sleep_time >= 0 ) {
        Sleep( sleep_time );
    }
    else {
      printf("Lag clause entered.\n");
      printf("Sleep time: %d\n", sleep_time);
    }
  }



  printf("Game is exiting %d.\n", GameCore::Status::game_close());

	Sleep(750);
	return(0);


}



// Each screen will be composed of
// Ground Layer always a single unique gfx resource, updates only when player moved
// Static Layer, (plants buildings) non-overlappable ground layer modifications, updates when object evolves or player moves
// Mobs Layer, things which may move and may? overlap, updates when mobs mob
// UI Layer, This stuff must be on top no matter what it occludes, updates when player status changes

// Ground layer can be composed of single 2d array where each int specified a resource
// Same is viable for static layer
// Mobs layer may point to linked list of drawables for that point?
// UI layer will be it's own separate section it does not need the same looping
// 4 calls to prepare each surface, then one call to render scene?
void renderscene(SDL_Texture* terrain, /*SDL_Surface* decor,*/ SDL_Texture* mobiles, SDL_Renderer *renderer, SDL_Texture *screen, int x, int y) {

    // Blank buffer
    SDL_RenderClear(renderer);

    // Obs SDL 1.2
	//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //Clears buffers (clearscreen)

    //Terrain *should* be overdrawn

    SDL_Rect terrainview = { x, y, (640 + x), (480 + y)};
    // the &view needs to be adjusted to correspond to the mobs center & rubberband

    if (SDL_RenderCopy(renderer,terrain,&terrainview,NULL) != 0)
    {
        printf("RenderCopy failure: %s\n", SDL_GetError());
    }


    /*
    if (SDL_BlitSurface(decor, &terrainview, screen, NULL) != 0)
    {
        printf("Blit failure: %s\n", SDL_GetError());
    }
    */

    // The mobs layer overdraws by 1 or 2 tiles

    /* Something in here is broken, fails and prevents code from hitting the SDL_Flip below */

    SDL_Rect mobview = { 0, 0, 640, 480};

    if (SDL_RenderCopy(renderer,mobiles, &mobview, NULL) != 0)
    {
        printf("Blit failure: %s\n", SDL_GetError());
    }

    SDL_RenderPresent(renderer);

}



void physics(Mob *mobs) {
}

void collision(Mob *mobs) {
}
