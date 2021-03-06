/*******************************************************************
 * Impliments a gl draw routine for a bar meter
 * Will accept a decoration layer to apply over the meter
 * Will poll its meter tracking object (typically an astat)
 * Will return a glx layer
 ******************************************************************/
#IFNDEF _SDL
#INCLUDE "sdl.h"
#DEFINE _SDL


public class uiMeter : public iuifx
{
  public:
    uiMeter(astat);
    void Draw();
    void Resize(int,int);
    void Rescale(int);
    void ApplyDecor();
  private:
    astat MeterSource;
    SDL_Rect* r;
    SDL_Color color;
}

