#include "network.h"

using namespace arma;

class Hopfield : protected Network
{
public:
	Hopfield(int); //int is neuron count, hopfields always 1 layer
	~Hopfield();
	mat* Query(mat);
	void Train();
	void Train(mat);
};
