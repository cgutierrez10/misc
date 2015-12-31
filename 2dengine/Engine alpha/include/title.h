#ifndef _stdinc
#define _stdinc
#include <stdio.h>
#include <string>
#include <utility>
using namespace std;
#endif

#ifndef _SDLincluded
#define _SDLincluded
#include <windows.h>
#include <SDL.h>
#include <SDL_opengl.h>
#endif

#ifndef _GLtext
#define _GLtext
#include "glfont2.h"
using namespace glfont;
#endif

#ifndef _GameCore
#define _GameCore
#include "gamecore.h"
#endif

class GameCore;

/*
	This class should be a pretty generic header that can be used with different conforming cpp files to produce
	individual title screens with basic menus and interfaces with game core code easily.
*/

/*
	Code matching this class should draw a title screen, accept inputs with little or no additional headers or classes.
	It should always work and always match the API outlined here. The API may be extended to include additional options
	such as credits, options and such.

	Any game I write should be able to use any title.cpp I write without modification or changes.
*/

class Title
{
	private:
		void curr_increase();
		void curr_decrease();
		void elect();
	protected:

	public:
		int version;
		void set_values(int); //Allows setting number of options
		void menu(); //Displays menu, call to update on selection changes
		int input(Uint8*); //Valid key input?
		Title();
		~Title();
};
