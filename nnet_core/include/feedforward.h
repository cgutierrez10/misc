#pragma once
#include "network.h"
#ifndef __Activation
#define __Activation
#include "iactivation.h"
#endif

#include "savemaster.h"

using namespace std;

class FeedForward :
public Network
{

private:
	double Threshold1;
	double Threshold2;
	mat* Transforms;
    //mat *holder;
	IActivation* Activation;

public:
    class SaveToken
	{
	    public:
        SaveToken(int, int, int, double, double);
        private:
        ~SaveToken();
        double _Threshold1;
        double _Threshold2;
        int _Neurons;
        int _LayerCount;
        int _Size;
	};
	MementoToken* GetToken();

	int Neurons;
	int LayerCount;
	FeedForward(int, int, IActivation*);
	FeedForward(mat, mat, int, IActivation*);
	FeedForward(int, int, IActivation*, mat*); // Not implimented
	virtual ~FeedForward(void);
	mat Query(mat);
	void UpdateNeuron(int, int, int, double);
	void AddToNeuron(int, int, int, double);
	double GetInputElement(int, int);
	double GetOutputElement(int, int);
	mat GetOutputVector();
	void SetActivation(IActivation*);
	double GetNeuron(int, int, int);
	void SetThreshold(double);
	void SetThreshold(double, double);
	void PrintLayer(int);
	int Size();
	FeedForward(ifstream*);
	void SetTransforms(mat*);
	mat ReadTransform(int);
};

