#ifndef _SDLincluded
#define _SDLincluded
#include <windows.h>
#include <SDL.h>
#include <SDL_opengl.h>
#include <SDL_image.h>
#endif

#ifndef _Mob
#define _Mob
#include "mob.h"
#endif

//class Mob;


class GameCore
{
	public:
		class Status {
		public:
			static bool game_is_running();
			static int game_is_running(bool);
			static bool game_close();
			static int game_close(bool);
		};
		GameCore();
	//static int keyproc(SDL_Event*); //Valid key input?
};

