#ifndef SAVEMASTER_H
#define SAVEMASTER_H

#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <string>
#include <list>
#include "token.h"
using namespace std;

/*
//Impliments a memento interface
// Use, reserve types 0-15 for standard c++ types, 16+ for UD objects
class MementoToken
{
    private:
        int Size;
        int Type;
        void* object;
    public:
        MementoToken(int, int, void*);
        int   GetSize() { return Size;   };
        int   GetType() { return Type;   };
        char* GetObj()  { return (char*) object; };
};*/

//Redesign this as a memento type with keepers
//Then save the memento's to file or load and return as necessary
class SaveMaster
{
    public:
        /** Default constructor */
        SaveMaster();
        /** Default destructor */
        ~SaveMaster();
        list<MementoToken*> Load(string);
        void AddToSave(MementoToken*);
        int SaveStatus(); // Getter
        int LoadStatus(); // Getter
        int SaveLoadStatus(); // Checks if any activity is being performed
        void CommitSave(string);
    protected:
    private:
        list<MementoToken*> objects;
        int SaveState;
        int LoadState;
        int objCounter;
        int NumObjs;
        class SaveException { };
        class LoadException { };
};



#endif // SAVEMASTER_H
