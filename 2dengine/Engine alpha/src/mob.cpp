#ifndef _Mob
#define _Mob
#include "mob.h"
#endif

#ifndef _GameCore
#define _GameCore
#include "gamecore.h"
#endif


/*
	Ships work as self maintained bidirectional linked list

	Member functions
	Ctor
	Dtor

	Class with linked list structure within?
*/

/*
 * Mobs should work as a factory pattern
 * All should have a tick() function and a draw() function
 */



Mob::Mob() {
	//When class is called player element is initialized
	// Creating the first mob, player
  gfx = new Mat<int>();
	gfx->resize(1,6);

  mob_data* self = new mob_data();
  self->prev = self;
  self->next = self;

  first = self;
  last = self;
  player = self;

	self->x = 10;
	self->y = 5;
	//self->catagory = 2;
	self->type = 0;
	self->gfx = 1;
	//self->dead = 0;
	self->cur_frame = 1;
	self->ttl = 5;
	self->gfx_lock = false;
}


Mat<int>* Mob::update() {
	//Process move() per item, check element integrity, process drawmob() per item
	// Since it is called once per frame this also allows regeneration and lifespan updating
	//Currently has a bug regarding last item in list (typically bullets) either multiple or skip processing them
	//Very buggy! Fix here fix for good though

	// Start with simplest case
	// Tick the player and update player gfx
	mob_data* current;
	//mob_data* next;
	//next = first; //First element not 0th
	//new mob_data;

	int i;
	// Verify [current] is a valid object
	current = player;
	bool is_player;

/*
	gfx->at(1,1) = 32;
	gfx->at(1,2) = 32;
	gfx->at(1,3) = 0;
	gfx->at(1,4) = 0;
	gfx->at(1,5) = 64;
*/
    gfx->at(1,1) = (current->x * 32) + (8 * current->cur_frame * current->vec[0]);
    gfx->at(1,2) = (current->y * 32) + (8 * current->cur_frame * current->vec[1]);
    gfx->at(1,3) = current->type;
    gfx->at(1,4) = (current->cur_frame - 1) * 40;
    if (current->vec[0] == -1) { gfx->at(1,5) = 60; } // All elements here are in pixels
    if (current->vec[0] == 1) { gfx->at(1,5) = 120; } // All elements here are in pixels
    if (current->vec[1] == -1) { gfx->at(1,5) = 180; } // All elements here are in pixels
    if (current->vec[1] == 1) { gfx->at(1,5) = 0; } // All elements here are in pixels
    gfx->at(1,6) = 60;
    if (current == player) { is_player = true; } else { is_player = false; }
	tick(current, is_player);
    return gfx;

	/*
	do {
		//Next is current->next, if current is deleted next is still valid
		//
		current = next;
		next = current->next;
		tick(current); // Processes object 'frame'
		// Creates entry in gfx columns to draw sprite upper left corner at x, y, for resource frame*32 by height
		i++;
		gfx.column(i) = (current->x * 32, current->y * 32, current->type, current->cur_frame * 32, 64); // All elements here are in pixels



		//collision(current); //Flags elements for deletion
		//regen(current); //Regenerates energy to max as possible
		} else { delete current; }
	} while (next != last); //Current may be deleted preventing current == checks, last may change based on deletion
	/ *current = next;
	if (!current->dead) {
		move(current);
		collision(current); //Flags elements for deletion
		//regen(current); //Regenerates energy to max as possible
		drawship(current);
	} else { delete current; }* /
    */
}

void Mob::tick(mob_data* self, bool player)
{
	//This currently seems to delete second to last element under some circumstances?
	//if ((0 <= (self->y + self->vec[1])) && ((self->y + self->vec[1] <= 478) || (self->type != 0))) { self->y = self->y + self->vec[1]; } //Adding vertical check prevents off-screen spawning entries, later checks remove past-vertical elements
	//if ((0 <= (self->x + self->vec[0])) && ((self->x + self->vec[0]) <= 640)) { self->x = self->x + self->vec[0]; }
	//if (self->y >= 470 && self->type == 0 /*&& self != player*/) {self->dead = true; } //Bullet leaves top screen
	//if (self->y <= 10 && self->type == 1) { self->dead = true; } //Opponent escaped bottom of screen
    if (self->gfx_lock == true)
    {
        self->cur_frame++;
    }
    if (self->cur_frame == self->ttl)
    {
        self->gfx_lock = false;

        self->y = self->vec[1] + self->y;
        self->x = self->vec[0] + self->x;
        self->cur_frame = 1;
        self->vec[0] = 0;
        self->vec[1] = 0;
    }
    if (player)
    {
        if (self->y > 9) { self->y--;}
        if (self->y < 3)  { self->y++;}
        if (self->x > 15) { self->x--;}
        if (self->x < 5)  { self->x++;}
    }

	return;
}

void Mob::add_mob()
{
    mob_data* self = new mob_data();
	last->next = self;
	self->prev = last;
	last = self;
	self->next = self;
	elements++;
	return;
}
Mob::mob_data::mob_data() {
	//printf("Ship element initialized.\n");

}

Mob::mob_data::~mob_data() {
	//Each clause should be exclusive and thus prevent multiple attempts to resolve
	//printf("Ship delete.\n");
	//Last element, revert last back one element, clean up next link
//	if (this == player) { GameCore::Status::game_is_running(false); printf("Player die.\n"); }
//	if (this == last && this != first) { last = this->prev; last->next = last; }
	//printf("Line 1.\n");
	//Middle elements, link past, link behind, remove self
//	if (this != first) { this->prev->next = this->next; this->next->prev = this->prev; }
	//printf("Line 2.\n");
	//First element, incriment first one pointer, new element prev = first
//	if (this == first) { first = this->next; this->prev = this; }
	//printf("Line 3.\n");
//	elements--;
}

void Mob::change_move(int x, int y) {
    if (player->gfx_lock == false)
    {
        player->vec[0] = x;
        player->vec[1] = y;
        player->gfx_lock = true;
    }
	//printf("Final Vector: %d, %d\n", player->vec[0], player->vec[1]);

}

