#ifndef LVL_WRITE_H
#define LVL_WRITE_H
#include <vector>
#include <string>
#include "..\..\level\include\level_data.h"

class lvl_write
{
    public:
        lvl_write();
        virtual ~lvl_write();
        //void write_to(std::string);
        void write_to(std::string);
        void read_from(std::string);
        void set_level(std::vector<xy_container>* = 0);
        std::vector<xy_container> *level;
        header hdr_data;
        void calc_hdr();
        void pop_default();
    protected:
    private:
};

#endif // LVL_WRITE_H
