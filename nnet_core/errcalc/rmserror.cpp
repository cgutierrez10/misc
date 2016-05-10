#include "rmserror.h"
#include "math.h"
#include "armadillo"

using namespace arma;

ErrorCalc::ErrorCalc()
{

}

ErrorCalc::~ErrorCalc()
{

}

double ErrorCalc::CalcRMS(mat input, mat expected)
{
	double squared = 0.0;
	for (unsigned i = 0; i < input.n_cols; i++)
	{
        squared += (as_scalar(input(i)) - as_scalar(expected(i)))*(as_scalar(input(i)) - as_scalar(expected(i)));
	}
	ErrorVal = sqrt(squared);
	return(ErrorVal);
}
