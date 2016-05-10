#include "colerror.h"
#include "math.h"
#include "armadillo"

using namespace arma;

ColError::ColError()
{

}

ColError::~ColError()
{

}

mat ColError::CalcRMS(mat input, mat expected)
{
	//mat squared = new Mat<double>();
	//for (unsigned i = 0; i < input.n_cols; i++)
	//{
    //    squared += (as_scalar(input(i)) - as_scalar(expected(i)))*(as_scalar(input(i)) - as_scalar(expected(i)));
	//}
	//ErrorVal = sqrt(squared);
	mat percent;
	percent.resize(1,expected.n_cols);
	ErrorVal = (expected - input);
	for (uint i = 0; i < expected.n_cols; i++)
	{
		percent(0,i) = ErrorVal(0,i) / expected(0,i); // / ErrorVal(0,i);
	}
	ErrorVal = join_cols(ErrorVal,percent);
	return(ErrorVal);
}
