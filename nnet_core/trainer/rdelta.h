#include "feedforward.h"
#include "armadillo"
//#include "savemaster.h"


using namespace arma;



class RDelta
{
private:
	FeedForward* Network;
	double LearnRate;
	mat Expected;
    double weight;
	double Error;
	mat Result;
	int RCount;

	mat RecurrentValues();
	mat Normalize(mat);
	// Impliment the initializer and destructors
 public:

    class SaveToken
	{
	    public:
	    SaveToken(double, double);
	    private:
        double LRate;
        double Err;
	};
    MementoToken* GetToken();
    mat RecurrentVector;
	RDelta(FeedForward*, double, int);
	void Train();
	void Train(mat, mat);
	void Train(mat, mat, int);
	void SetRate(double);
	void SetExpected(mat);
	void SetRecurrentCount(int);
	//void SetRVector(mat);
	mat GetRVector();
};
