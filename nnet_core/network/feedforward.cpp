#include "feedforward.h"

//Modify this to reflect different dimensionality per layer, possibly including full data I/O for transforms and layers?
FeedForward::FeedForward(int neurons, int numlayers, IActivation* ActivationObj) {
	Layers = new mat[numlayers];
	for (int i = 0; i < numlayers; i++)
	{
		Layers[i] = randu<mat>(neurons,neurons);
	}

	InVec = new mat;
	OutVec = new mat;
	//The vectors below cannot handle larger hidden or output layers than input
	/*InVec->resize(numlayers+1, neurons);
	OutVec->resize(numlayers, neurons);*/
    InVec->resize(neurons, numlayers+1);
	OutVec->resize(neurons, numlayers);

	Neurons = neurons;
	LayerCount = numlayers;
	Activation = ActivationObj;
	Threshold1 = 0.0;
	Threshold2 = 0.0;
    //holder = new mat();
}

FeedForward::~FeedForward(void) {

}

void FeedForward::SetThreshold(double newval) {
	Threshold1 = newval;
	Threshold2 = newval;
}

void FeedForward::SetThreshold(double val1, double val2) {
	Threshold1 = val1;
	Threshold2 = val2;
}

void FeedForward::PrintLayer(int index) {
	Layers[index].print();
}

//Currently invalid for multilayer matrices, the transform matrix leaves large sparse vectors properly done would be ragged matrix
//Review transform matrix changes may be part responsible for NaN's in delta training
mat FeedForward::Query(mat QueryVector)
{
    InVec->zeros();
    OutVec->zeros();
	InVec->col(0) = QueryVector.row(0).t();
    mat *holder = new mat();

    holder->resize(Neurons,1);
	for (int i = 0; i < LayerCount; i++)
	{
		holder->col(0) = (Layers[i].t() * InVec->col(i));
		InVec->col(i+1) = holder->col(0);
 		OutVec->col(i) = holder->col(0);
	}

	delete(holder);
    return(OutVec[LayerCount-1].t());
}

double FeedForward::GetInputElement(int index, int layer) {
	return(as_scalar(InVec->at(layer, index)));
}

double FeedForward::GetOutputElement(int index, int layer) {
	return(as_scalar(OutVec->at(layer, index)));
}

mat FeedForward::GetOutputVector() {
	return(OutVec->col(LayerCount-1));
}

double FeedForward::GetNeuron(int i, int j, int k) {
	return(as_scalar(Layers[k].at(i,j)));
}

void FeedForward::UpdateNeuron(int i, int j, int k, double newval) {
	Layers[k](i,j) = newval;
}

void FeedForward::AddToNeuron(int i, int j, int k, double newval) {
	Layers[k](i,j) = Layers[k](i,j) + newval;
}

void FeedForward::SetActivation(IActivation* ActivationObj) {
	Activation = ActivationObj;
}


FeedForward::SaveToken::SaveToken(int netsize, int neurs, int numLayers, double thresh1, double thresh2)
{
    //cout << "Matrix size " << sizeof(temp1)*neurs*neurs << endl;
    _Size       = netsize;
    _LayerCount = numLayers;
    _Neurons    = neurs;
    _Threshold1 = thresh1;
    _Threshold2 = thresh2;

}

MementoToken* FeedForward::GetToken()
{
    Layers->save("matfile", raw_binary);
    SaveToken* State = new SaveToken(Neurons*Neurons*sizeof(double), Neurons, 1, Threshold1, Threshold2);
    MementoToken* Mem = new MementoToken(sizeof(SaveToken), 25, &State);
    return(Mem);
}
