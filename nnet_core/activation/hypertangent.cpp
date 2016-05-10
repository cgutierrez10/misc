#include "hypertangent.h"
#include "math.h"

double HyperTangent::Activation(double input)
{
	return(tanh(input));
}

double HyperTangent::DerivedActivation(double input) {
	double output;
	output = 1 - (tanh(input) * tanh(input));
	return(output);
}
