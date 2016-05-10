/******
 * Prompt user for matrix size number of layers
 * Generates matrix of appropriate size and feedforward/layer configuration
 ******/

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
   * Only want to generate network itself and activation function
   * MxN from command line, filename
   * Linear activation only for now
   ******/

  /* No tokens needed unless thresholds are required? */
  FeedForward* Net = new FeedForward(temp.n_rows,temp.n_cols,new Linear(), &temp);


	cout << "Save Network?" << endl;
	string Saveresponse;
	cin.clear();
  	cin.ignore(1000,'\n');
	getline(cin, Saveresponse);
//	cin >> Saveresponse;
  if (Saveresponse == "y" || Saveresponse == "yes")
	{
	    //Collect objects that need saving
	    /*
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
        */
        //MementoToken* item;

        //item = NNet->GetToken();
        int frames = 5;
        MementoToken* item = new MementoToken(sizeof(frames),0,&frames); // Hardcoded value

	    string SaveFile;
	    cout << "Filename to save to?" << endl;
	    //cin >> SaveFile;
        SaveMaster* Save = new SaveMaster();

        //Save->AddToSave(new MementoToken(sizeof(trainings),0,&trainings));  // trainings is type int
        Save->AddToSave(new MementoToken(sizeof(frames),0,&frames));        // Dataset
        Save->AddToSave(Net->GetToken());                                   // Network matrices
        //Save->AddToSave(NNet->GetToken());                                  // Training rules
        //cout << trainings << endl;
        //cout << (int) NNet->GetToken()->GetObj() << endl;
        Save->CommitSave(SaveFile);

        // Load testing points
        /*list<MementoToken*> Tokens;
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
        */
	}

    return 0;
}
