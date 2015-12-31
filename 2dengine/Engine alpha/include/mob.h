#ifndef _Mob
#define _Mob
#include "mob.h"
#endif

#ifndef _SDLincluded
#define _SDLincluded
#include <windows.h>
#include <SDL.h>
#include <SDL_opengl.h>
#endif

#ifndef _arma
#define _arma
#include "armadillo"
#endif

using namespace arma;

class Mob
{
private: //Self access only
protected: //Self and child only (mutational variables)
public: //Public (global) functions go here
	struct mob_data {
		int catagory, type; //These can be used to prefill some values
		int score, layer, weapon, strength, regen, fire_rate, motion; //Motion is one of several predefined types?
		bool vulnerable; //True, or false
		bool dead;
		bool gfx_lock;
		int x, y, gfx; //Necessary to draw and update location of object
		int ttl; //Frames in sprite
		int cur_frame; // Current frame of sprite
		int vec[2];
		mob_data* next;
		mob_data* prev;
		mob_data();
		~mob_data();
	};

/*
	static class Events {
		private:

			//static void collision(mob_data*); //Seeks collision pairs, calls damage() when found, deletes if necessary
			//static bool damage(mob_data*, mob_data*); //Applies damage to collision pairs, returns true to destroy false otherwise
		public:

			//static void spawn(int);
			//static void newbullet();
	};
*/

    Mat<int>* update(); //Traverse list handing out a move and draw per element removing loops from move and draw
	void change_move(int, int);
	void add_mob(); // Later include to definition type, location, etc
	mob_data* last;
    mob_data* first;
    mob_data* player;
    int elements;
    Mat<int>* gfx;
    static void tick(mob_data*, bool);	// Process 1 engine frame
	Mob(); //Constructor initializes player element of linked list


};
