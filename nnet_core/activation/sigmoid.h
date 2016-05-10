#ifndef __Activation
#define __Activation
#include "iactivation.h"
#endif

class Sigmoid : public IActivation
{
public:
	Sigmoid() { __IsDerived = false; }
	double Activation(double);
	double DerivedActivation(double);
};
