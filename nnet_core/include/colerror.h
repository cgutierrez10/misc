#include "math.h"
#include "armadillo"

using namespace arma;

class ColError {
public:
    ColError();
    ~ColError();
    mat ErrorVal;
	mat CalcRMS(mat, mat); //Calc error RMS of array of known length
	mat GetError() { return ErrorVal; }
};
