#ifndef PARSEFILE_H
#define PARSEFILE_H

#include "fileread.h"
#include "armadillo"
#include <iostream>
#include <fstream>

using namespace arma;

//This class needs to convert input values to a 0.0 to 1.0 range
//At this time, simply load data from file into matrix of values to use, no data modifications no extra data generation

/*
    Elements in each data item
    identifier, -1.0 to 1.0 range linear stepwise unit identifier per stock ticker?
    value, not needed?
    delta value, already -1.0 to 1.0 ranged
    high, value divided by high
    low, 1 - low divided by high
    volume, may not be relevant best ratio volume % of outstanding
    timestamp not necessary in recurrent network, maybe 0.0 to 1.0 indicating age?
        eg. 0 days to 5 years scaling linear from 1.0 (most recent) to 0.0 (historical)
    delta value, already in %

    output => -1.0 to 1.0 range result of given item expected movement
    will require recurrent neural network
*/

using namespace std;

class ParseFile
{
    public:
        /** Default constructor */
        ParseFile();
        /** Default destructor */
        virtual ~ParseFile();
        mat DataSet();
        mat RollingDataSet(int, int);
    protected:
    private:
    FileRead* Source;
    mat DataRow(bool*);
};

#endif // PARSEFILE_H
