//Obsolete?

#include "armadillo.h"
#include "FeedForward.h"
#include "IError.h"
#include "IActivation.h"

using namespace arma;

//Interface declares any child has requisite functions to be trained
class ITrainableFeedForwardNetwork : public FeedForward
{
protected:
	//Activation function object meeting IActivation
	//Error function object meeting IError
public:
	//Already inherates feedforward class
	virtual void Train();
	virtual void Train(mat);
}
