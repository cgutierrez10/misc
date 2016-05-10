#include "armadillo"

using namespace arma;

class IActivation
{
protected:
	bool __IsDerived;
public:
	virtual ~IActivation() {};
	virtual double Activation(double)=0;
	virtual double DerivedActivation(double)=0;
	//virtual mat* Activation(mat*)=0;
	bool IsDerivable() { return __IsDerived; }
};
