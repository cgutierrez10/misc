#include "delta.h"


//Delta learning rule class
//Currently not working due to NaN and -1.#INF errors propogating through the matrices
//Will review more information to determine the intended learning rule and function before futher developing this.
//Situation improving with revised (now correct) algorithm


Delta::Delta(FeedForward* FeedForwardObj, double Lrate)
{
	Network = FeedForwardObj;
	LearnRate = Lrate;
}

void Delta::SetRate(double Lrate)
{
	LearnRate = Lrate;
}

//Review this code evaluate as potential source of NaN errors from transform matrix modifications
void Delta::SetExpected(mat E)
{
	Expected = E;
}

//Goes to infs, errors from setexpected or query not from this chunk
//Giving results without inf/NaN errors
//Missing several columns zeroing out columns that should be giving results
void Delta::Train()
{
    weight = 0.0;
    Error = 0.0;

	for (int j = 0; j < (int) Expected.n_cols; j++)
	{
        Error = Expected.at(j) - Network->GetOutputElement(0,j);
        if (Error >= 0) { Error = (1 - (1 / Error)); }
        if (Error < 0) { Error = -(1 + (1 / Error)); }
        Error = Error * LearnRate;
		for (int i = 0; i < Network->Neurons; i++) {
			weight = Network->GetNeuron(i,j,0) * Error;
			Network->AddToNeuron(i,j,0,weight);
		}
	}
	//cout << "Total change this train: " << accumulator << endl;
}

//Each training vector requires processing (query) the network
void Delta::Train(mat InputMatrixVector, mat ExpectedVector)
{
	for (int i = 0; i < (int) InputMatrixVector.n_rows; i++)
	{
		SetExpected(ExpectedVector.row(i));
		Network->Query(InputMatrixVector.row(i));
		Train();
	}
}

//Trains with copy output range from previous run into current run, only valid with a sequence of more than 1 query
void Delta::TrainRecurrent(mat InputMatrixVector, mat ExpectedVector, int RecurrentNeuronsCount)
{
    Result.zeros(1,RecurrentNeuronsCount);
	for (int i = 0; i < (int) InputMatrixVector.n_rows; i++)
	{
	    ExpectedVector(1,span(ExpectedVector.n_cols-RecurrentNeuronsCount, RecurrentNeuronsCount)) = Result;
	    //ExpectedVector(1,span(ExpectedVector.n_cols-RecurrentNeuronsCount, RecurrentNeuronsCount)) = Result;
		SetExpected(ExpectedVector.row(i));
		//cout << "Entering query" << endl;
		Result = Network->Query(InputMatrixVector.row(i));
		Train();
	}
}
