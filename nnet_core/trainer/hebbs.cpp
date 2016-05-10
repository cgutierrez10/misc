#include "hebbs.h"

Hebbs::Hebbs(FeedForward* FeedForwardObj, double Lrate)
{
	Network = FeedForwardObj;
	LearnRate = Lrate;
}

void Hebbs::SetRate(double Lrate)
{
	LearnRate = Lrate;
}

//This functions properly returned results will be trained for the most obvious pattern
void Hebbs::Train()
{
	double weight = 0.0;
	//Coactivation of neuron i and j increases strength
	for (int i = 0; i < Network->Neurons; i++)
	{
		for (int j = 0; j < Network->Neurons; j++) {
		    //weight never descends below 0
		    weight = Network->GetInputElement(i,0) * Network->GetOutputElement(j,0) * LearnRate;
		    Network->AddToNeuron(i,j,0, weight);
		}
	}

}

//Each training vector requires processing (query) the network
void Hebbs::Train(mat InputMatrixVector)
{
	for (int i = 0; i < (int) InputMatrixVector.n_rows; i++)
	{
		Network->Query(InputMatrixVector.row(i));
		Train();
	}
}
