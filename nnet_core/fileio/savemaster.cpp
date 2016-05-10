#include "savemaster.h"

SaveMaster::SaveMaster()
{
    SaveState = 0;
    LoadState = 0;
    objCounter = -1; // Preincrimented to start arrays from 0
    NumObjs = 5;
    //ctor
}

SaveMaster::~SaveMaster()
{
    //dtor
}

void SaveMaster::CommitSave(string FileName)
{
    if (SaveState == 2)
    {
        // Throw an exception later
        cout << "An error has occured within the save module, save cancelled" << endl;
        return;
    }
    ofstream SaveFile;
    SaveFile.open("temp.txt", ios::binary);
    cout << "Writing this to a file.\n";
    /*
    while (objects.size() > 0) {
        SaveFile.write(objects.pop_back(),1000);
    }*/
    MementoToken* it;
    int obj_size;
    int obj_type;
    while ( objects.size() > 0 )
    {
        it = objects.front();
        obj_size = it->GetSize();
        obj_type = it->GetType();
        SaveFile.write((char*) &obj_size, sizeof(obj_size));
        SaveFile.write((char*) &obj_type, sizeof(obj_size));
        SaveFile.write(it->GetObj(),it->GetSize());

        cout << " " << it->GetSize() << "\t" << it->GetType();
        if (it->GetType() == 0) { cout << "\t" << (int) *((int*) it->GetObj()) << endl; } else { cout << endl; }

        objects.pop_front();
    }

    SaveFile.close();
    SaveState = 1;

}

list<MementoToken*> SaveMaster::Load(string FileName)
{
    list<MementoToken*>* items = new list<MementoToken*>();
    int*  _size;
    int*  _type;
    void* _obj;
    ifstream SaveFile;
    SaveFile.open("temp.txt", ios::binary);
    do
    {
        _size = (int*) malloc(sizeof(int));
        _type = (int*) malloc(sizeof(int));
        SaveFile.read((char*)_size, 4);
        SaveFile.read((char*)_type, 4);
        _obj = (void*) malloc((int) *_size);
        SaveFile.read((char*)_obj, (int) *_size);
        items->push_back(new MementoToken((int) *_size,(int) *_type, (void*) _obj));
    } while (SaveFile.peek() != EOF);
    MementoToken* temp = items->back();
    return(*items);
}

void SaveMaster::AddToSave(MementoToken* object_ptr)
{
    objects.push_back(object_ptr);
}

/*
MementoToken::MementoToken(int obj_size, int obj_type, void* Obj)
{
    this->Size   = obj_size;
    this->Type   = obj_type;
    this->object = Obj;
}
*/
