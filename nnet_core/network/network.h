#include "stdio.h"
#include "armadillo"
#include "string.h"
//#include "savemaster.h"

using namespace arma;
using namespace std;

class Network // : public ISaveState
{
private:

protected:
	mat* Layers;
	mat* InVec;
	mat* OutVec;
	int Size;
public:
	Network();
	virtual ~Network();
	void InVectorWrite(mat);
	mat InVectorRead(); //Not implimented
	void OutVectorWrite(mat); //Not implimented
	mat OutVectorRead();

	//Below not implimented
	//Serialize matrix objects for load/save? Armadillo has method?
	void LoadNet(string); //Load matrices from file and separate
	void SaveNet(string); //Combine all matrices into nxn+2 (square plus in/out vectors) save
};
