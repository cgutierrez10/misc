/*
 * =====================================================================================
 *
 *       Filename:  meter_uifx.cpp
 *
 *    Description:  Horizontal Bar meter w/ decoration
 *                  Can easily add bool toggle for vertical display, if needed later
 *
 *        Version:  1.0
 *        Created:  6/15/2014 11:43:01 AM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  YOUR NAME (), 
 *   Organization:  
 *
 * =====================================================================================
 */
#include "meter_uifx.h"
#include "astat.h"


uiMeter::uiMeter(astat source)
{
  this.MeterSource = source;
}

// Almost certainly broken on every line
// does not return an sdl surface may not be able to
// the this.r's may be wrong
// this->color assumed an SDL_Color exists, may not?
void Draw()
{
  this->r.w = MeterSource.getFill(true); // Percentage meter 
  SDL_FillRect(gScreen, this.r, this->color);
  return;
}

// Fixed resize, new sizes define drawing box decor will also need resizing
void Resize(int newX, int newY)
{
  this->r.x = newX;
  this->r.y = newY;
  this->r.h = newY;
}

// Percentage rescale the ui element as a whole
void Rescale(double Scale)
{
  this->r.x = ceil(this->r.x * Scale/100);
  this->r.y = ceil(this->r.y * Scale/100);
  this->r.h = ceil(this->r.h * Scale/100);
}

// Stub
void ApplyDecor()
{
  // Stub at present, later may draw decor to a UI decor layer with alpha centers that fill with meter
}
