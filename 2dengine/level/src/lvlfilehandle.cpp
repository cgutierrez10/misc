#include "../include/lvlfilehandle.h"

#include <iostream>
#include <fstream>


using namespace std;

lvlfilehandle::lvlfilehandle()
{
    //ctor
}

lvlfilehandle::~lvlfilehandle()
{
    //dtor
}

void lvlfilehandle::writemap(string filename)
{
    std::ofstream file(filename);
    vector<xy_container>* level;
    header hdr_data;
    hdr_data.max_x = 128;
    hdr_data.max_y = 128;
    hdr_data.gfx_len = 0;
    hdr_data.lvl_units = 0;
    hdr_data.special_len = 0;

    terrain_unit* t_hold;
    mobject_unit* m_hold;
    int total = 0;
    int i = 0;
    xy_container unit = level->at(0);
    for (std::vector<xy_container>::iterator it = level->begin(); it != level->end(); ++it)
    {
        unit = level->at(i++);
        total =+ sizeof(xy_container);
        total =+ sizeof(terrain_unit) * unit.t_units;
        total =+ sizeof(mobject_unit) * unit.m_units;
        hdr_data.lvl_units++; // = i;
    }
    hdr_data.lvl_len = total; // calculate as sizeof lvl_units
    write_pod(file, hdr_data);
    cout << "units in level: " << hdr_data.lvl_units << endl;

    for (int i = 0; i < hdr_data.lvl_units; i++)
    {
        unit = level->at(i);
        //file.write("begin unit",10);
        t_hold = unit.t_data;
        m_hold = unit.m_data;
        unit.t_data = nullptr;
        unit.m_data = nullptr;
        write_pod(file, unit);
        //file.write("end unit",8);
        unit.t_data = t_hold;
        unit.m_data = m_hold;

        for (int j = 0; j < unit.t_units; j++)
        {
            write_pod(file, unit.t_data[j]);
        }
        for (int j = 0; j < unit.m_units; j++)
        {
            write_pod(file, unit.m_data[j]);
        }
    }
}

void lvlfilehandle::readmap(string filename)
{
    std::ifstream file(filename);
    header hdr_data;
    read_pod(file, hdr_data);
    vector<xy_container>* level;
    level->resize(hdr_data.lvl_units);
    xy_container *unit; // = level.data();
    terrain_unit *t_data;
    mobject_unit *m_data;

    for (int i = 0; i < hdr_data.lvl_units; i++)
    {
        unit = &level->at(i);
        read_pod(file,level->at(i));

        cout << "T_units: " << unit->t_units << " M_units: " << unit->m_units << endl;

        unit->t_data = new (nothrow) terrain_unit[unit->t_units];
        if (unit->t_data == nullptr && unit->t_units != 0)
        {
            cout << "T_Data Alloc failed" << endl;
            return;
        } else if (unit->t_data != nullptr) {
            for (int j = 0; j < unit->t_units; j++){
                read_pod(file,unit->t_data[j]);
            }
        }
        unit->m_data = new (nothrow) mobject_unit[unit->m_units];
        if (unit->m_data == nullptr && unit->m_units != 0)
        {
            cout << "M_Data Alloc failed" << endl;
            return;
        } else if (unit->m_data != nullptr)  {
            for (int j = 0; j < unit->m_units; j++) {
                read_pod(file,unit->m_data[j]);
            }
        }
        cout << "Terrain units: " << unit->t_units << endl;
        cout << "Objects units: " << unit->m_units << endl;
    }
}
