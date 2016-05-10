#ifndef __Activation
#define __Activation
#include "iactivation.h"
#endif


class Linear : public IActivation
{
    public:
        /** Default constructor */
        Linear() { __IsDerived = false; };
        /** Default destructor */
        ~Linear();
        double Activation(double);
        double DerivedActivation(double);
        bool IsDerivable() { return __IsDerived; }
    protected:
    private:
};
