#ifndef FILEREAD_H
#define FILEREAD_H

#include <iostream>
#include <fstream>
#include <string>


//Base class, impliments underlying io and most basic parsing
//Read file from given input stream

using namespace std;

class FileRead
{
    public:
        FileRead(string);
        FileRead();
        virtual ~FileRead();
        string NextLine(bool*);
    protected:
    private:
        ifstream InFile;
};

#endif // FILEREAD_H
