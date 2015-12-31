//#include "lvl_write.h"
//#include "include\lvl_write.h"
#include "C:\programming\2dengine\tools\include\lvl_write.h"
#include <stdio.h>
#include <stdlib.h>
#include <fstream>
#include <iostream>

using namespace std;

lvl_write::lvl_write() {
    this->level = new vector<xy_container>;
    // ctor
}

lvl_write::~lvl_write()
{
    //dtor
}


void lvl_write::pop_default()
{

    //header hdr_data;
    hdr_data.max_x = 1024;
    hdr_data.max_y = 1025;
    hdr_data.gfx_len = 1;
    hdr_data.lvl_len = 64;
    hdr_data.lvl_units = 16;
    hdr_data.special_len = 15;

    level->resize(4);
    //xy_container *units = new xy_container[4];
    //xy_container unit = units[0]; //level->at(0);
    xy_container *unit = level->data();
    unit->x = 128;
    unit->y = 1;
    unit->m_units = 0;
    unit->t_units = 1;
    //unit.t_data =
    //terrain_unit*
    unit->m_data = new (nothrow) mobject_unit[unit->m_units];
    if (unit->m_data == nullptr && unit->m_units != 0)
    {
        cout << "M_Data Alloc failed" << endl;
        return;
    } else if (unit->m_data != nullptr) {

    }
    unit->t_data = new (nothrow) terrain_unit[unit->t_units];
    if (unit->t_data == nullptr && unit->t_units != 0)
    {
        cout << "T_Data Alloc failed" << endl;
        return;
    } else if (unit->t_data != nullptr) {
        unit->t_data[0].gfx = 1;
        unit->t_data[0].physics = 0;
        unit->t_data[0].angle = 0;
        unit->t_data[0].opta = 0;
        unit->t_data[0].optb = 0;
    }
    cout << "Terrain units: " << unit->t_units << endl;
    cout << "Objects units: " << unit->m_units << endl;

    ++unit;
    //unit = level->at(1);
    unit->x = 10;
    unit->y = 90;
    unit->m_units = 1;
    unit->t_units = 1;
    unit->t_data = new (nothrow) terrain_unit[unit->t_units];
    if (unit->t_data == nullptr && unit->t_units != 0)
    {
        cout << "T_Data Alloc failed" << endl;
        return;
    } else if (unit->t_data != nullptr) {
    unit->t_data[0].gfx = 1;
    unit->t_data[0].physics = 0;
    unit->t_data[0].angle = 0;
    unit->t_data[0].opta = 0;
    unit->t_data[0].optb = 0;
    }
    unit->m_data = new (nothrow) mobject_unit[unit->m_units];
    if (unit->m_data == nullptr && unit->m_units != 0)
    {
        cout << "M_Data Alloc failed" << endl;
        return;
    } else if (unit->m_data != nullptr)  {
        unit->m_data[0].type = 1;
        unit->m_data[0].opta = 2;
        unit->m_data[0].optb = 4;
        unit->m_data[0].optc = 8;
    }
    cout << "Terrain units: " << unit->t_units << endl;
    cout << "Objects units: " << unit->m_units << endl;

    ++unit; // = units[2]; //level->at(0);
    //unit = level->at(2);
    unit->x = 54;
    unit->y = 45;
    unit->m_units = 2;
    unit->t_units = 1;
    unit->t_data = new (nothrow) terrain_unit[unit->t_units];
    if (unit->t_data == nullptr && unit->t_units != 0)
    {
        cout << "T_Data Alloc failed" << endl;
        return;
    } else if (unit->t_data != nullptr) {
    unit->t_data[0].gfx = 1;
    unit->t_data[0].physics = 2;
    unit->t_data[0].angle = 0;
    unit->t_data[0].opta = 0;
    unit->t_data[0].optb = 0;
    }

    unit->m_data = new (nothrow) mobject_unit[unit->m_units];
    if (unit->m_data == nullptr && unit->m_units != 0)
    {
        cout << "M_Data Alloc failed" << endl;
        return;
    } else if (unit->m_data != nullptr)  {
        unit->m_data[0].type = 3;
        unit->m_data[0].opta = 5;
        unit->m_data[0].optb = 7;
        unit->m_data[0].optc = 11;
    }
    cout << "Terrain units: " << unit->t_units << endl;
    cout << "Objects units: " << unit->m_units << endl;

    ++unit; // = units[3]; //level->at(0);
    //unit = level->at(3);
    unit->x = 90;
    unit->y = 90;
    unit->m_units = 3;
    unit->t_units = 4;
    unit->t_data = new (nothrow) terrain_unit[unit->t_units];
    if (unit->t_data == nullptr && unit->t_units != 0)
    {
        cout << "T_Data Alloc failed" << endl;
        return;
    } else if (unit->t_data != nullptr) {
    unit->t_data[0].gfx = 1;
    unit->t_data[0].physics = 0;
    unit->t_data[0].angle = 0;
    unit->t_data[0].opta = 456;
    unit->t_data[0].optb = 123;
    unit->t_data[1].gfx = 4;
    unit->t_data[1].physics = 2;
    unit->t_data[1].angle = 1;
    unit->t_data[1].opta = 0;
    unit->t_data[1].optb = 0;
    }
    unit->m_data = new (nothrow) mobject_unit[unit->m_units];
    if (unit->m_data == nullptr && unit->m_units != 0)
    {
        cout << "M_Data Alloc failed" << endl;
        return;
    } else if (unit->m_data != nullptr)  {
        unit->m_data[0].type = 12;
        unit->m_data[0].opta = 23;
        unit->m_data[0].optb = 44;
        unit->m_data[0].optc = 88;
    }
    cout << "Terrain units: " << unit->t_units << endl;
    cout << "Objects units: " << unit->m_units << endl;

}


void lvl_write::set_level(vector<xy_container> *savedata)
{
    pop_default();
}


void lvl_write::write_to(string filename)
{
    std::ofstream file(filename);

    //header hdr_data;
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

void lvl_write::read_from(string filename)
{
    std::ifstream file(filename);
    read_pod(file, hdr_data);
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
