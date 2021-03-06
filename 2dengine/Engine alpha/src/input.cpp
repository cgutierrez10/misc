/*
 * =====================================================================================
 *
 *       Filename:  input.cpp
 *
 *    Description:  Input handling and parsing
 *               :  Can include an interface for game specific handling logic
 *
 *        Version:  1.0
 *        Created:  6/15/2014 12:16:30 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  YOUR NAME (),
 *   Organization:
 *
 * =====================================================================================
 */

#include "input.h"

Input::Input() {
}

/* Depreciated kept for reference only */
void Input::keyproc(SDL_Event* events, Mob* player) {
	//printf("Input module.\n");
	int vector[2] = {0,0};
	if (events->type == SDL_KEYDOWN) {
		//printf("Key press triggered.\n");
		switch (events->key.keysym.sym) { //Currently buggy drat
			case SDLK_UP:
				vector[1] = -1;
				break;
			case SDLK_LEFT:
				vector[0] = -1;
				break;
			case SDLK_DOWN:
				vector[1] = 1;
				break;
			case SDLK_RIGHT:
				vector[0] = 1;
				break;
			case SDLK_w:
				//fire = true;
				break;
			case SDLK_ESCAPE:
				//printf("Escape Key pressed.\n");
				GameCore::Status::game_is_running(false);
				break;
			default:
				break;
		}
	}
//	if (vector[0] == 0) { vector[0] = last_x; } else { last_x = vector[0]; }
//	if (vector[1] == 0) { vector[1] = last_y; } else { last_y = vector[1]; }
//	if ((events->type == SDL_KEYUP) && ((events->key.keysym.sym == SDLK_UP) || (events->key.keysym.sym == SDLK_DOWN))) { last_y = 0; }
//	if ((events->type == SDL_KEYUP) && ((events->key.keysym.sym == SDLK_LEFT) || (events->key.keysym.sym == SDLK_RIGHT))) { last_x = 0; }
	//if ((events->type == SDL_KEYUP) && (events->key.keysym.sym == SDLK_w)) { fire = false; }
	//if (fire) { Mob::Events::newbullet(); }
	if (vector[0] != 0 || vector[1] != 0) { player->change_move(vector[0], vector[1]); }
}

/*
int Input::inputEvent(SDL_Event* event)
{
  switch(event.type)
  {
    case SDL_MOUSEMOTION:
      this.mousemove(event);
      break;
    case SDL_MOUSEBUTTONDOWN:
      this.mousebutton(event);
      break;
    case SDL_MOUSEBUTTONUP:
      this.mousebutton(event);
      break;
    case SDL_KEYUP:
      this.key(event);
      break;
    case SDL_KEYDOWN:
      this.key(event);
      break;
    case SDL_QUIT:
      return(0);
    default:
      break;
  }
  return 0;
}
*/
