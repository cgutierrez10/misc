/*
 * =====================================================================================
 *
 *       Filename:  astat.cpp
 *
 *    Description:  
 *
 *        Version:  1.0
 *        Created:  6/8/2014 1:24:52 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  YOUR NAME (), 
 *   Organization:  
 *
 * =====================================================================================
 */

activeStat::activeStat(double base, double regen)
{
  _Max = base;
  _Regen = regen;
}

/* Permanent increase, if fill = yes then will max _CurrLevel as well */
/* If add = yes then new amount is added to existing _Max, else absolute set */
void activeStat::PermMax(double amount, bool add, bool fill)
{
  if (add)
  {
  _Max = _Max + amount;
  }
  else
  {
    _Max = amount;
  }
  if (fill)
  {
    _CurrLevel = _Max;
  }
  if (_CurrLevel > _Max)
  {
    _CurrLevel = _Max;
  }
}

void activeStat::PermRegen(double amount, bool add)
{
  if (add)
  {
    _Regen = _Regen + amount;
  }
  else
  {
    _Regen = amount;
  }
  if (fill)
  {
    _CurrLevel = _Max;
  }
  if (_CurrLevel > _Max)
  {
    _CurrLevel = _Max;
  }
}

void TempRegen(double amount, int ticks, bool per-flag)
{
  double regen = 0.0;
  if ((amount != 0) || (ticks != 0))
  {
    return;
  }
  if (per-flag)
  {
    regen = (_Max + _TempBoost) / amount;
  }
  else
  {
    regen = amount;
  }

  _RegenBoost = _RegenBoost + regen;
  _TempRegen.add(new Tracker(ticks,regen));

}

bool activeStat::TempBoost(double amount, int ticks, bool per-flag, bool fill)
{
  double TempBoost = 0.0;

  if (tick == 0 || amount == 0)
  {
    return true;
  }

  if (per-flag)
  {
    TempBoost = (_Max + _Boost) / amount;
  } else {
    TempBoost = amount;
  }


  if (fill)
  {
    _CurrLevel = _Max + _TempBoost;
  }

  _MaxBoost = _MaxBoost + TempBoost;
  this._TempBoost.Add(new Tracker(ticks, TempBoost));
}

int activeState::getFill(bool per-flag)
{
  if (per-flag)
  {
    return((_Max + _Boost) / _CurrLevel);
  }
  else
  {
    return(_CurrLevel);
  }
}

bool activeStat::CheckApply(double amount)
{
  /* Reject immediately if would be 0 */
  if (_CurrLevel + amount <= 0)
  {
    return false;
  }
  else true;
}

/* Will not exceed _Max, if _CurrLevel <=0 then returns false */
void activeStat::Apply(double amount, bool per-flag)
{

  if (per-flag)
  {
    _CurrLevel = _CurrLevel + ((_Max + _TempBoost) / amount);
  }
  else
  {
    _CurrLevel = _CurrLevel + amount;
  }
  if (_CurrLevel  > _Max + _TempBoost)
  {
    _CurrLevel = _Max + _TempBoost;
  }

}

bool activeState::Tick()
{
  if ((_CurrLevel < (_Max + _MaxBoost)) && (_Regen != 0))
  {
    _CurrLevel = _CurrLevel + _Regen + _RegenBoost;
    if (_CurrLevel > (_Max + _MaxBoost))
    {
      _CurrLevel = _Max + _MaxBoost;
    }
  }

  for (list<Tracker>::const_iterator iterator = _TempBoost.begin(), end = _TempBoost.end();
       iterator != end;
       ++iterator)
  {
    iterator->Ticks--;
    if (iterator->Ticks == 0)
    {
      _MaxBoost = _MaxBoost - iterator->Amt;
    }
  }

  for (list<Tracker>::const_iterator iterator = _TempRegen.begin(), end = _TempRegen.end();
       iterator != end;
       ++iterator)
  {
    iterator->Ticks--;
    if (iterator->Ticks == 0)
    {
      _MaxRegen = _MaxRegen - iterator->Amt;
    }
  }

  _TempBoost.remove_if(Ticks == 0);
  _TempRegen.remove_if(Ticks == 0);

  /* Sub-0 meter, delayed negative regen should be checked */
  if (_CurrLevel < 0)
  {
    _CurrLevel = 0;
    return false;
  }

  return true;
}



