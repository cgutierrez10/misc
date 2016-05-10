#ifndef __Activation
#define __Activation
#include "iactivation.h"
#endif

class Threshold : public IActivation
{
private:
	double LLimit;
	double ULimit;
	bool __IsDerived;
public:
	Threshold();
	Threshold(double, double);
	double Activation(double);
	double DerivedActivation(double);
};
