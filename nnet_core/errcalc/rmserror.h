#include "math.h"
#include "armadillo"

using namespace arma;

class ErrorCalc {
public:
    ErrorCalc();
    ~ErrorCalc();
    double ErrorVal;
	double CalcRMS(mat, mat); //Calc error RMS of array of known length
	double GetError() { return ErrorVal; }
};
