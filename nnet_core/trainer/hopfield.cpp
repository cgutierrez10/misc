#include "hopfield.h"

Hopfield::Hopfield(int neurons)
{
	//Define matrix and neurons
	Layers = new mat;
	Layers->resize(neurons,neurons);
	//Define vector sizes
	InVec = new mat;
	OutVec = new mat;
	InVec->resize(neurons,1);
	OutVec->resize(neurons,1);
	Size = neurons;
}
Hopfield::~Hopfield()
{

}

mat* Hopfield::Query(mat QueryVector)
{
	for (int i = 0; i < Size; i++) {
		OutVec->at(i,0) = dot(Layers->row(i), QueryVector);
	}
	return (OutVec);
}

void Hopfield::Train()
{
	if (InVec->n_rows != (unsigned int) Size) { cout << "Error supplied training vector is not size compatible with existing matrix."; return; }
	*Layers += (InVec->t() * *(InVec)) - eye<mat>(Size,Size);
}

void Hopfield::Train(mat NewVec)
{
	if (NewVec.n_rows != (unsigned int) Size) { cout << "Error supplied training vector is not size compatible with existing matrix."; return; }
	*Layers += (NewVec.t() * NewVec) - eye<mat>(Size,Size);
}
