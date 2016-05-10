#include "backpropogation.h"

/*
  Output layer delta: output * (1-output) * (e - output) per neuron

    (transform neurons as appropriate to connect varied i/o layer sizes)

    Hidden layer delta: output * (1-output) * for(int k=0;k < neurons[i+1];k++){ sum+=delta[i+1][k]*layers[i+1][k][j]; }
    delta defined at output layer, layers are neuron weights

    apply update as: delta(neuron i,j) = rate * delta * (neuron i,j);
*/

BackPropogation::BackPropogation(int, int, IActivation*)
{

}


BackPropogation::~BackPropogation(void)
{
}

void BackPropogation::Train()
{

}

void BackPropogation::Train(mat Query, mat Expected)
{

}

void BackPropogation::SetRate(double NewRate)
{

}

void BackPropogation::SetExpected(mat input)
{

}
