#include "rdelta.h"


//Delta learning rule class
//Currently not working due to NaN and -1.#INF errors propogating through the matrices
//Will review more information to determine the intended learning rule and function before futher developing this.
//Situation improving with revised (now correct) algorithm


RDelta::RDelta(FeedForward* FeedForwardObj, double Lrate, int count)
{
    RCount = count;
	Network = FeedForwardObj;
	LearnRate = Lrate;
    RecurrentVector.zeros(1, Network->Neurons + RCount);
    cout << "Network->Neurons " << Network->Neurons << endl;
}

void RDelta::SetRate(double Lrate)
{
	LearnRate = Lrate;
}

//Review this code evaluate as potential source of NaN errors from transform matrix modifications
void RDelta::SetExpected(mat E)
{
	Expected = E;
}

//Goes to infs, errors from setexpected or query not from this chunk
//Giving results without inf/NaN errors
//Missing several columns zeroing out columns that should be giving results
void RDelta::Train()
{
    weight = 0.0;
    Error = 0.0;
	//(RecurrentVector.n_cols - (RCount + 1), RecurrentVector.n_cols - 1)
    RecurrentVector.cols(RecurrentVector.n_cols - RCount, RecurrentVector.n_cols - 1) = Normalize(RecurrentVector.cols(RecurrentVector.n_cols - (RCount + 1), RecurrentVector.n_cols - 1));

	for (int j = 0; j < (int) Expected.n_cols - RCount; j++)
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
void RDelta::Train(mat InputMatrixVector, mat ExpectedVector)
{
	for (int i = 0; i < (int) InputMatrixVector.n_rows; i++)
	{
	    RecurrentVector = join_rows(ExpectedVector.row(i),RecurrentVector.cols(RecurrentVector.n_cols - RCount, RecurrentVector.n_cols - 1));
		SetExpected(RecurrentVector);
		RecurrentVector = Network->Query(RecurrentVector);
		Train();
	}
}

mat RDelta::RecurrentValues()
{
    return(RecurrentVector.cols(RecurrentVector.n_cols - RCount, RecurrentVector.n_cols - 1));
}

//Trains with copy output range from previous run into current run, only valid with a sequence of more than 1 query
void RDelta::Train(mat InputMatrixVector, mat ExpectedVector, int RecurrentNeuronsCount)
{
    SetRecurrentCount(RecurrentNeuronsCount);
    Result.zeros(1,RecurrentNeuronsCount);
	for (int i = 0; i < (int) InputMatrixVector.n_rows; i++)
	{
	    ExpectedVector(1,span(ExpectedVector.n_cols-RecurrentNeuronsCount, RecurrentNeuronsCount)) = Result;
		SetExpected(ExpectedVector.row(i));
		Result = Network->Query(InputMatrixVector.row(i));
		Train();
	}
}

void RDelta::SetRecurrentCount(int count)
{
    RCount = count;
}

/*
mat RDelta::GetRVector(int start, int end)
{
    return RecurrentVector.cols(start,end);
}
*/

mat RDelta::GetRVector()
{
    return RecurrentVector.cols((Network->Neurons - RCount),(Network->Neurons - 1));
}

mat RDelta::Normalize(mat input)
{
    mat output;
    output.resize(1,RCount);
    double accum = 0.0;
    for (int j = 0; j < RCount; j++)
    {
        accum = accum + as_scalar(input.col(j));
    }
    accum = sqrt(accum);
    for (int j = 0; j < RCount; j++)
    {
        output(j) = as_scalar(input.col(j)/accum);
    }
    return(output);
}


RDelta::SaveToken::SaveToken(double learn, double error)
{
    LRate = learn;
    Err = error;
}

MementoToken* RDelta::GetToken()
{
    RecurrentVector.save("recmat", raw_binary);
    SaveToken* State = new SaveToken(LearnRate, Error);
    MementoToken* Mem = new MementoToken(sizeof(SaveToken), 33, &State);
    return(Mem);
}
