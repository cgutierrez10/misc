#include "token.h"

MementoToken::MementoToken(int obj_size, int obj_type, void* Obj)
{
    this->Size   = obj_size;
    this->Type   = obj_type;
    this->object = Obj;
}
