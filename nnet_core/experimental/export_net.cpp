// Test Networks.cpp : main project file.

/*
    Notes
    Backpropogation requirements:
        error functions
        +   separate object to be coded
        activation functions
        *   inside each IActivation obj
        derived activation functions
        *   inside each IActivation object
        inter-layer mapping matrices
        +   Add into feedforward object



    Output layer delta: output * (1-output) * (e - output) per neuron

    (transform neurons as appropriate to connect varied i/o layer sizes)

    Hidden layer delta: output * (1-output) * for(int k=0;k < neurons[i+1];k++){ sum+=delta[i+1][k]*layers[i+1][k][j]; }
    delta defined at output layer, layers are neuron weights

    apply update as: delta(neuron i,j) = rate * delta * (neuron i,j);
*/
/* Useful snippet memory leak catching

#include <psapi.h>
#include "windows.h"

int memory;
PROCESS_MEMORY_COUNTERS memCounter;

GetProcessMemoryInfo(GetCurrentProcess(), &memCounter, sizeof(memCounter));
memory = memCounter.WorkingSetSize;

GetProcessMemoryInfo(GetCurrentProcess(), &memCounter, sizeof(memCounter));
if ( (unsigned) memory != memCounter.WorkingSetSize) { cout << "Memory leak in Training\noriginal memory: " << memory << " memory after call: " << memCounter.WorkingSetSize << "\n" << endl; }
for (int j = 0; j < (int) InVec->n_rows; j++) {			holder->at(j,0) = Activation->Activation(holder->at(j,0));
}
*/
/*
    Notes to fix
    prioritize
    1 impliment file reader functions
    2 Impliment activation over matrix functions as part of IActivation base class
    4 impliment network save to file/read from file utilizing feedforward new(net, trans) mode
*/

//#include "stdafx.h"
#include "stdio.h"
#include "time.h"
#include "savemaster.h"
#include "armadillo"
#include "parsefile.h"
#include "feedforward.h"
#include "linear.h"
//#include "memcheck.h"
//#include <psapi.h>
//#include "windows.h"


using namespace std;
using namespace arma;

int main(int argc, char* argv[])
{
	//srand( (unsigned) time(NULL) );
  mat input;
	mat output;
	mat temp;
	mat temp2;
	/* Only needed if new testing data */
  int colcount;
	int direction = 1;
	int frames = 5;
  // CFG may be invalid for this purpose
	// CFG should only be used when it may save input
  // At this stage the result should be entirely automated
  // from data in files
	cout.precision(3);

  SaveMaster* LoadNet = new SaveMaster();
  /******
   * Only want to load network itself, activation function and test data
   ******/

  /****** 
   * If token type = network
   * initialize a network object with a linear activation
   * feed values
   * can reassign activation if needed?
   ******/ 
  
  /* No tokens needed unless thresholds are required? */
  FeedForward* Net = new FeedForward(temp.n_rows,temp.n_cols,new Linear(), &temp);

  /******
   * Should not be defining this column read data
   * Should be able to import pre-formatted test data
   ******/
  // Need to define input as data read in from training set
  //input = new mat();
  if (!input.load("query.mat"))
  {
    cout << "Failed to load query data set.\n" << endl;
    return 0;
  }


  // These both include values determined from the columns configuration
  int Recurrent = input.n_cols;
  colcount = input.n_cols;
  mat ExpectedOut = mat(colcount,1);
  // Network will be defined based on existing saved network file
  // Not defined as calculated by training sets
  /*FeedForward* Net = new FeedForward((IO->GetCols() * IO->GetFrames()) + Recurrent,1, new Linear());*/


  int next = 0;
  int k = 0;
  int j;
  mat results;

	int rownum;
	do {
    /******
     * Add result to column along with any relevant extra data
     * ****/
    cout << "Check matrix element, x to quit: " << endl;
    cin >> rownum;
	  temp.resize(1,5);
	  temp2.resize(1,5);
	  int j = 0;
    cout << "Rownum: " << rownum << endl;
    /* Colvec not defined yet, what does it need to be? */
    /*for (uint i = 0; i < colvec.n_rows; i++)
    {
      if (as_scalar(colvec[i]) != 0)
      {
        //Skipping first column for debugging
        temp[1,j] = as_scalar(input.row(rownum).col(i));
        j++;
      }
    }*/
    j = 0;

		temp.print("Expected: ");
		/*for (uint i = 0; i < colvec.n_rows; i++)
		{
			if (as_scalar(colvec[i]) != 0)
			{
				temp2[1,j] = as_scalar(output(0,i));
				j++;
			}
		}*/
		j = 0;
	  	temp2.print("Result:");

    //Rating->CalcRMS(temp,temp2);
    //Rating->CalcRMS(input.row(rownum).cols(2,colcount-1), temp.cols(2,colcount-1));

    //cout << "Error: \n" << Rating->GetError() << endl;
	} while (rownum > 0);

    // Input read glitch takes same column repeatedly
    //input.save("example.csv", raw_ascii);

	cout << "Save Network?" << endl;
	string Saveresponse;
	cin.clear();
  	cin.ignore(1000,'\n');
	getline(cin, Saveresponse);
//	cin >> Saveresponse;
//	This version of program only reads existing network, no changes are made nothing to save
	/*
  if (Saveresponse == "y" || Saveresponse == "yes")
	{
	    //Collect objects that need saving
	    / *
	    DATA to be saved:
          //  main() [3 elements; 3 int]:
          //          column list

            feedforward() [4 elements; 1 obj, 1 obj_ptr, 2 int]:
                activation method
                dimensions
                network matrix [1 element; 1 double[] ]:
                    use built in save/load?

            training_method() [3 elements; 1 ptr, 2 int]:
                train rate
                network pointer
                recurrent neurons some modes
        * /
        //MementoToken* item;

        //item = NNet->GetToken();
        int frames = 5;
        MementoToken* item = new MementoToken(sizeof(frames),0,&frames); // Hardcoded value

	    string SaveFile;
	    cout << "Filename to save to?" << endl;
	    //cin >> SaveFile;
        SaveMaster* Save = new SaveMaster();

        Save->AddToSave(new MementoToken(sizeof(trainings),0,&trainings));  // trainings is type int
        Save->AddToSave(new MementoToken(sizeof(frames),0,&frames));        // Dataset
        Save->AddToSave(Net->GetToken());                                   // Network matrices
        Save->AddToSave(NNet->GetToken());                                  // Training rules
        cout << trainings << endl;
        cout << (int) NNet->GetToken()->GetObj() << endl;
        Save->CommitSave(SaveFile);

        // Load testing points
        list<MementoToken*> Tokens;
        Tokens = Save->Load("test.txt");
        MementoToken* elem;
        list<int> loadedInts;
        do
        {
        elem = Tokens.front();
        if (elem->GetType() == 0)
        {
            loadedInts.push_back((int) *((int*) elem->GetObj()));
            // Each object here is an individual data

        }
        if (elem->GetType() == 25)
        {
            FeedForward::SaveToken* Net_Token = (FeedForward::SaveToken*) elem->GetObj();
            // Pass token back to FForward
        }
        if (elem->GetType() == 33)
        {
            RDelta::SaveToken* Training_Token = (RDelta::SaveToken*) elem->GetObj();
            // Pass token back to RDelta

        }
        Tokens.pop_front();
        } while (!Tokens.empty());
        cout << "First int " << loadedInts.front() << endl;
        loadedInts.pop_front();
        cout << "Second int " << loadedInts.front() << endl;
	}
  */

    return 0;
}
