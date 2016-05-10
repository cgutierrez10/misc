#include "feedforward.h"



class BackPropogation
{
private:
    FeedForward* Net;
public:
	BackPropogation(int, int, IActivation*);
	virtual ~BackPropogation(void);
	void Train();
	void Train(mat, mat);
	void SetRate(double);
	void SetExpected(mat);
};

