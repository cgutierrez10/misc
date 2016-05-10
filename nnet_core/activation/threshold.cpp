#include "threshold.h"

Threshold::Threshold(double upper, double lower)
{
	ULimit = upper;
	LLimit = lower;
	__IsDerived = false;
}

Threshold::Threshold()
{
	ULimit = 4;
	LLimit = -4;
	__IsDerived = 0;
}


double Threshold::Activation(double input)
{
	if (input > ULimit) {return(1.0);}
	if (input < LLimit) {return(-1.0);}
	return(0.0);
}

double Threshold::DerivedActivation(double input)
{
   return(0.0);
}
