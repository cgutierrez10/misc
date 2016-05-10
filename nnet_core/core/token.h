#ifndef TOKEN_H
#define TOKEN_H

#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <string>
#include <list>

using namespace std;

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
};



#endif // SAVEMASTER_H
