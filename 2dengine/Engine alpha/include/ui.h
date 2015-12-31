/*********************************************************************
 * Each instance of this class represents a single graphical layer
 *
 * Allows layout of graphical objects that do not move with 
 * the game field
 *
 * May implement part of a memento for sake of rapid ui mode 
 * switching such as inventory to main game
 *
 *********************************************************************/

public class UIfx : public igfxlayer
{
  public:
    UIfx(SDL_Surface*);
    virtual ~UIfx();
    int get_x() { return 0; };
    int get_y() { return 0; };
    int AddElement(iuifx,int,int, bool);
      // Accepts an interface iuifx class 
      // and calls it's draw function
      // placing it at x,y
      // Returns a unique id to 
      // address that object

    RmvElement(int);        // Removes the element number (int) 
                            // from display list and updates
    void redraw();          // Force redraw all elements
  private:
    list<iuifx.draw()> RenderFuncs;
    list<iuifx> Elements;
    SDL_Surface* StaticLayer; //Contains rendered layer of static elements 
}
