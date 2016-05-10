#include "parsefile.h"

// Depreciated

ParseFile::ParseFile()
{
    //ctor
    string filename = "input.txt";
    Source = new FileRead();
    bool eof;
    Source->NextLine(&eof); //Read, toss first line it's column header info
}

ParseFile::~ParseFile()
{
    //dtor
}

//This works AND it's fragile Yay!
// Needs to be rewritten to accept vector indicating columns to include/omit
// Needs to be able to insert static data column(s)
mat ParseFile::DataRow(bool* eof)
{
    string chunks;
    mat Out; //The columns must match the number of data elements

    Out.resize(1,7);
    stringstream line;
    line << Source->NextLine(eof);
    if (*eof != true)
    {
    getline(line, chunks, ','); //Toss first chunk it's date data and cannot be added to double matrix without modification
    getline(line, chunks, ',');
    Out.at(1,0) = atof(chunks.c_str());
    getline(line, chunks, ',');
    Out.at(1,1) = atof(chunks.c_str());
    getline(line, chunks, ',');
    Out.at(1,2) = atof(chunks.c_str());
    getline(line, chunks, ',');
    Out.at(1,3) = atof(chunks.c_str());
    getline(line, chunks, ',');
    Out.at(1,4) = atof(chunks.c_str());
    getline(line, chunks, ',');
    Out.at(1,5) = atof(chunks.c_str());
    getline(line, chunks, ',');
    Out.at(1,6) = atof(chunks.c_str());
    getline(line, chunks, ',');
    Out.at(1,7) = atof(chunks.c_str());
    }
    //Above code needs to append each column, the Out << format overwrites existing data
    //cout << "columns contains " << Out.n_cols << "elements.\n";
    return(Out);
}

//This will return an entire data file (minus first line, see ctor) as a n by 6 matrix
//Fragility notes, see datarow()
mat ParseFile::DataSet()
{
    mat Out;
    Out.resize(1,7);
    bool eof = false;
    //mat temp = DataRow(&eof);
    while (eof == false)
    {
        Out.insert_rows(Out.n_rows,DataRow(&eof));
    }
    Out.shed_row(0);
    //Out.shed_row((int) Out.n_rows-2);
    //Out.shed_row((int) Out.n_rows-1);
    //Out.print("Output from dataset:");
    Out.shed_col(0);
    return(Out);
}

mat ParseFile::RollingDataSet(int window, int rollover)
{
    mat Inputlist = DataSet();
    //Inputlist.print("Input to rolling dataset:");
    cout << "Input: " << Inputlist.n_rows << "x" << Inputlist.n_cols << endl;
    mat Outputlist;
    Outputlist.resize(Inputlist.n_rows, 0);
    cout << "Ouput: " << Outputlist.n_rows << "x" << Outputlist.n_cols << endl;
    for (int i = 0; i < window; i++)
    {
        Outputlist = join_rows(Outputlist, Inputlist);
        Inputlist.shed_row(0);
        Outputlist.shed_row(Outputlist.n_rows-1);
    }
    cout << "Ouput: " << Outputlist.n_rows << "x" << Outputlist.n_cols << endl;
    return(Outputlist);
}
