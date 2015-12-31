#ifndef _Title
#define _Title
#include "title.h"
#endif

int current = 3;
int values = 3;
GLFont font;

Title::Title() {
	version = 1;
}

Title::~Title() {

}


void Title::curr_decrease() {
	if (current <= 0) { current = values; }
	else { current = current - 1; }
	printf("Current is: %d\n", current);
	menu();
}

void Title::curr_increase() {
	if (current >= values) { current = 0; }
	else { current = current + 1; }
	printf("Current is: %d\n", current);
	menu();
}

void Title::elect() {
	if (current == 3) { GameCore::Status::game_is_running(true); }
	if (current == 1) { GameCore::Status::game_close(true); }
	printf("Option %d selected.\n", current);
	//Stub, passes to specific menu routine
}

void Title::menu() {
	//Draw routine cannot call directly.
	//Create openGL menu interface here to simply draw 4 lines of text and pointer the selection
	//Menu upgrade, new design will auto place text elements based on data provided
	// Menu will auto-space elements and provide selector capability and route any non-assigned functions to a void
	string options[] = {"Quit", "Blank", "Play Game"}; //Bottom up list
	int num_options = 3;
	int tmargin = 200;
	int bmargin = 100;
	int line, i;
	i = 0;

	line = (int) ((640 - (tmargin + bmargin)) / num_options); //640 should be the X constant
	glMatrixMode(GL_MODELVIEW);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glLoadIdentity();
	if (!font.Create("timesnewroman.glf", 1)) {	printf("Times New Roman failed to load.\n"); }
	glColor3f(1.0F, 1.0F, 1.0F);
	font.Begin();
	//String array refuses to cooperate with options[i] format
	/*while (i < num_options) {
		printf("Element: %d String: %s\n", i, options[i]);
		//font.DrawString(options[i],0.0, 160, 199 + (i * 50));
		//font.DrawString(options[i],0.0, 160, (line * i) + bmargin);
		i++;
	}*/
	font.DrawString(options[0],1.0, 160, (line * 0) + bmargin);
	font.DrawString(options[1],1.0, 160, (line * 1) + bmargin);
	font.DrawString(options[2],1.0, 160, (line * 2) + bmargin);
	glPushMatrix();
	int y = ((current * line) - 15);
	glTranslated(150, y, 0);
	glBegin(GL_LINES);
	glColor3f(1.0F, 1.0F, 1.0F);
	glVertex2i(-10,0);
	glVertex2i(0,0);
	glVertex2i(-4,3);
	glVertex2i(0,0);
	glVertex2i(-4,-3);
	glVertex2i(0,0);
	glEnd();
	glPopMatrix();
	SDL_GL_SwapBuffers();
	font.Destroy();
}

int Title::input(Uint8 *key) {
	//printf("Keyhandling method entered\n");
	//key = SDL_GetKeyState(NULL);

	//Return value could be 'current' ? update current and return value on enter, -1 (quit) on q?

	SDL_PumpEvents();
	if (key[SDLK_q]) { printf("Escape pressed.\n"); return(-1); }
	if (key[SDLK_UP]) {
		if (current >= values) {printf("Up pressed\n");current = 1;} else {current = current++;}
	}
	if (key[SDLK_DOWN]) {
		if (current <= 1) {printf("Down pressed\n"); current = values;} else {current = current--;}
	}
	if (key[SDLK_RETURN]) { elect(); printf("Return pressed.\n"); Sleep(150);}
	Sleep(100);
	this->menu();
	return(0);
	//return(this->current);
}
