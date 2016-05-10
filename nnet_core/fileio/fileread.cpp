#include "fileread.h"

FileRead::FileRead(string filename)
{
    InFile.open(filename.c_str(), ios::in);
    if (!InFile.is_open()) {} //Or throw exception

    //ctor
}

FileRead::FileRead()
{
    InFile.open("input.txt", ios::in);
    if (!InFile.is_open()) {} //Or throw exception

    //ctor
}

FileRead::~FileRead()
{
    InFile.close();
    //dtor
}

string FileRead::NextLine(bool* eof)
{
    string line;
    if (InFile.eof()) {*eof = true; InFile.seekg(1, ios::beg); return "";}
    else {getline(InFile,line);};
    return line;
}

/*void FileRead::Reset()
{
    InFile.seekg(0, ios::beg);
}*/
