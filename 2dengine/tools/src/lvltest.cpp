#include <iostream>
#include "include\lvl_write.h"
#include "..\..\level\include\lvlfilehandle.h"
#include "C:\programming\2dengine\level\src\lvlfilehandle.cpp"

using namespace std;

int main()
{
    //lvlfilehandle *level = new lvlfilehandle();
    //level->readmap("sample_in.lvl");

    // One use tools
    lvl_write *sample = new lvl_write();
    //sample->set_level();
    //sample->write_to("testout.out");
    sample->read_from("testout2.out");
    sample->write_to("testout3.out");
    //std::ifstream in ("testout.out");
    //read_pod(in, sample->hdr_data);

    //std::ofstream out ("testout2.out");
    //write_pod(out, sample->hdr_data);

}
