#include "feedforward.h"
#include "armadillo"

using namespace arma;

class Hebbs
{
private:
	FeedForward* Network;
	double LearnRate;
public:
	Hebbs(FeedForward*, double);
	void Train();
	void Train(mat);
	void SetRate(double);
};
