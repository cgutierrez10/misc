#ifndef __Activation
#define __Activation
#include "iactivation.h"
#endif

class HyperTangent : public IActivation
{
public:
	HyperTangent() { __IsDerived = false; }
	double Activation(double);
	double DerivedActivation(double);
};
