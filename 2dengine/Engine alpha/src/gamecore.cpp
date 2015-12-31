#ifndef _GameCore
#define _GameCore
#include "gamecore.h"
#endif


/*
	Game Core functions
	GameCore::Clock
	GameCore::Status
	GameCore::Input
*/

bool game_running;
bool game_exit;

void GameCore() {
	game_running = false;
	game_exit = false;
}


bool GameCore::Status::game_is_running() {
	return(game_running);
}

int GameCore::Status::game_is_running(bool val) {
	if (game_running = val) { return(1); }
	else {return(0);}
}

bool GameCore::Status::game_close() {
	return(game_exit);
}

int GameCore::Status::game_close(bool val) {
	if (game_exit = val) { return(1); }
	else {return(0);}
}

//Non-swappable alpha version
//Basic title screen keyparsing

int last_x = 0;
int last_y = 0;
bool fire = false;

