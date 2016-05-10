#ifndef CONFIG_H
#include "armadillo"
#include "token.h"
#include "savemaster.h"
#include "parsefile.h"
#include <iostream>
#include <fstream>
#include <string>
#define CONFIG_H




/*
Break into 2 objects
    THIS object handles data configuration and manipulation
    one object handles object configuration

Object purpose:
separare all configuration settings into an interface
provide minimum setup modes and fuller customizations
provide standardizable code for things such as
    frame source
    frame size
        data window
    basic data additions
        Append constant element to source


Private functions:
read from file
    column selector
data column append
window config
    frame append

public functions:
get networkobj*
get trainer*
get datamatrix*
set frame
set window
set const (column)
read from file (+mode)
read and set (one call config from file)
read and override (read from file selecting some portion of objects to not build use defaults instead)
reconfig obj (select one object to reconfigure, declare new network repoint trainer, open new trainer for given network provide new data set)

*/
using namespace arma;
using namespace std;


/*  Test with columns vector <0,1,1,1,1,0,0>
                            window 5
                            frames autoconfig
*/
class InputConfig
{
    public:
        /** Default constructor */
        //InputConfig();
        InputConfig(vec, vec);
        InputConfig(vec);
        /** Default destructor */
        virtual ~InputConfig();

        int  GetFrames() { return frames; }
        void SetFrames(int val) { frames = val; }
        void SetColumns(mat val) { _Columns = val; } // This has to be modified to update cols counter
        vec GetColumns() { return _Columns; }
        void SetConstCols(mat val) { _ConstantColumns = val; }
        int  GetCols() { return colscount; }
        int  GetDirection() { return direction; }
        void SetDirection(int val) { direction = val; }
        void SetFilename(string val) { _IFile = val; }
        int GetTrainings() { return _train; }
        mat  GetInputMatrix(); // Many values need to be set first and return some basic result otherwise?
        mat  GetOutputMatrix();
        mat  RollingWindow(mat);
        //mat  DataSet();
	int  ReadConfig(string);
    protected:
    private:
        // _Columns from data spliced with _ConstantColumns = 1 frame, repeat with _direction for mat _Window
        // _Window is 1 input vector stacked to make sequential input matrix
        int  frames;
        int colscount;
        vec  _Columns;
        vec  _ConstantColumns;
        int  direction; // +, - step size implicit
		int  _train;
        mat  _Window; // Input vector
        mat  _InputMatrix;
        bool _Debug;
        string _IFile;
        void CompileInputs(); // Requires filename and columns set, checks columns to see if constcols are required
        mat  RollingWindow(int, int);
};

#endif // CONFIG_H

/*
Network setup config
        void SetTrainer(mat val) { _Trainer = val; }

        // Set trainer to provided object depreciate after testing
        void SetTrainer(void* val, int val2) { return; }

        mat GetInputMatrix(); // Returns _InputsMatrix after compileinputs()

        //list<double> for Network, list<double> for training, int<double> for data
        int LoadByParams(list<double>, list<double>, list<double>);
        //MementoToken list, unpack then pass to LoadByParams
        // Will be depreciated, or loadbyfile() will be depreciated
        int LoadByObjs(list<MementoToken> objs);
        //Load by source files
        int LoadByFile(string);
        mat _Trainer; //single matrix
*/


/*


Use Case 1:
Load data from file
    send objects through config
        create and configured each object
    return code indicating successful configuration
    provides interfaces for object pointers
    PROBLEM:
        how can upper layers interface with the training objects without awareness of their types? polymorphism?
        Anything beyond train() and trainseries() should be utilized by config only?

Use Case 2:
    provide initial values for configurations
        frame sizes
            data window
        data source configurations
    with these values create objects and return success code
    make pointers to network, data and training objects available
    higher code should be able to cast objects back to expected types

Use Case 3:
    configure objects
    modify data sources at configuration level
        append data columns
        select data columns from source
        configure data window size
        define data input file
    return success code
    make pointers available to network and training data objects
*/
