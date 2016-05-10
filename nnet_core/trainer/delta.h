#include "feedforward.h"
#include "armadillo"


using namespace arma;

class Delta
{
private:
	FeedForward* Network;
	double LearnRate;
	mat Expected;
    double weight;
	double Error;
	mat Result;
public:
	Delta(FeedForward*, double);
	void Train();
	void Train(mat, mat);
	void SetRate(double);
	void SetExpected(mat);
	void TrainRecurrent(mat InputMatrixVector, mat ExpectedVector, int RecurrentNeuronsCount);
};
