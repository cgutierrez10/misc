#ifndef LEVEL_DATA_H_INCLUDED
#define LEVEL_DATA_H_INCLUDED

//#include <cstdint>
#include <fstream>
// File structure
// Header, total map size x,y
//         offset to beginning of map-tile data
//         offset to beginning of gfx resources
//         offset to any special elements (bytecode segments?)

// Map-tile grid
//   Map grid used for second layer (walkable) rendering
//   Gfx resource, surface angle?
//   Physics transparency mode?
//   Flex length, min 4 bytes, x,y,0,0
//   No hard max length likely not to exceed x,y,3,5 in practice

//   total length
//   x
//   y
//   1 byte, number of gfx terrain objects following, for this grid
//   per above, units of terrain
//   1 byte, number of mobjects following, for this grid
//   per above, complete valid mobjects

// Pointer(s) to graphics resources
// Entry exit zones?


template<typename T>
void write_pod(std::ofstream& out, T& t)
{
    out.write(reinterpret_cast<char*>(&t), sizeof(T));
}

template<typename T>
void read_pod(std::ifstream& in, T& t)
{
    in.read(reinterpret_cast<char*>(&t), sizeof(T));
}


class header
{
    public:
    int max_x; //Map x
    int max_y; // map y
    int lvl_units; //number of individual xy units contained
    int lvl_len; //total length of level (xy, terrain, mobject) data
    int gfx_len; //total length of png graphics resources in file
    int special_len; //length of any special data segment
};

class terrain_unit
{
    public:
    // gfx to apply
    int gfx;
    // physics mode
    int physics;
    // display|collision angle?
    int angle;
    // optional parameters (reserved bytes)?
    int opta;
    int optb;
};

class mobject_unit
{
    public:
    // mob type (spawners, individual units at x,y), type indicates gfx, mob parameters
    int type;
    // optional spawner, unit parameter 1
    // options 1 indicates spawn type (cap (x living from this spawner), finite(x produced total))
    int opta;
    // optional spawner, unit parameter 2
    // option 2 indicates spawn rate (ticks between spawn)

    int optb;
    // optional spawner, unit parameter 3
    // option 3 indicates spawn per rate (produce 1, 2 or more at a time)
    int optc;

    // Compiler bug(?) new[] bugs out if the class is too small, adding one more int seems to fix
    int test;
};

class xy_container
{
    public:
    int x;
    int y;
    int t_units;
    int m_units;
    terrain_unit *t_data;
    mobject_unit *m_data;
};



#endif // LEVEL_DATA_H_INCLUDED
