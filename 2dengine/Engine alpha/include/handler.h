/* Need include ifndef's */
// Will need to declare input in such a way it's UIState is accessable

class InputHandler :: public IHandle
{
  public:
    void mousemove(SDL_Event*, UISTate*);
    void mousebutton(SDL_Event*, UIState*);
    void key(SDL_Event*, UIState*);
  private:
//    struct UIState
//    {
//      int mousex;
//      int mousey;
//      int mousedown;
//      int hotitem;
//      int activeitem;
//    };
    map<SDL_Keysym,int> keybind;
};
