/********************************************************************
 * This file will include functionality for active stats
 * Active stats are factors such as stamina hit points and magic.
 *
 *  A hunger or thirst stat could also be included depending on the 
 *  needs of the game design.
 *******************************************************************/

/* Provides class which impliments a single meter */
/* Each meter can be polled for absolute or percent fullness to be used for drawing UI elements elsewhere */

public class activeStat
{
  public:
    activeStat(double, double);
    void PermMax(double, bool, bool);
    void PermRegen(double, bool);
    bool TempRegen(double,int, bool);
    bool TempBoost(double, int, bool, bool);
    void Apply(double);
    bool CheckApply(double);
    int getFill(bool);
    void Tick();          // Can be used for regeneration


  private:
    class Tracker
    {
      public:
        Tracker(int,double);
        int Ticks;
        double Amt;
    }
    double _CurrLevel;
    double _MaxBase;
    double _RegenBase;
    double _MaxBoost;
    double _RegenBoost;
    List<Tracker> _TempRegen;
    List<Tracker> _TempBoost;

}
