/*
 * =====================================================================================
 *
 *       Filename:  handler.cpp
 *
 *    Description:  SDL events input handler
 *
 *        Version:  1.0
 *        Created:  6/15/2014 1:10:55 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  YOUR NAME (), 
 *   Organization:  
 *
 * =====================================================================================
 */


InputHandler::InputHandler()
{

}

void InputHandler::mousemove(SDL_Event* event, UIState* state)
{
  // update mouse position
  state.mousex = event.motion.x;
  state.mousey = event.motion.y;
}

void InputHandler::mousebutton(SDL_Event* event, UIState* state)
{
  // Toggle left, right, center button states on clicks
  if (event.button.button == 1)
    state.lb = (event.type == SDL_MOUSEBUTTONDOWN ? 1 : 0);
  if (event.button.button == 2)
    state.rb = (event.type == SDL_MOUSEBUTTONDOWN ? 1 : 0);
  if (event.button.button == 3)
    state.cb = (event.type == SDL_MOUSEBUTTONDOWN ? 1 : 0);
}


// Stub for now, will check keybind map as cases and then call the mapped functions
void InputHandler::key(SDL_Event* event)
{

}
