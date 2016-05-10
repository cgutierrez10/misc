#include "sigmoid.h"
#include "math.h"

using namespace std;

double Sigmoid::Activation(double input)
{
	double output;
	output = 1.0 / (1.0 + exp(-1 * input));
	return(output);
}

double Sigmoid::DerivedActivation(double input)
{
   double s = Activation(input);
   return (s * (1 - s));
}
