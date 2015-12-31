#ifndef LVLFILEHANDLE_H
#define LVLFILEHANDLE_H

#include <string>
#include <vector>
#include "level_data.h"
//#include <boost\serialization\serialization.hpp>
#include <boost\archive\text_oarchive.hpp>
#include <boost\archive\text_iarchive.hpp>
#include <boost\serialization\vector.hpp>

class lvlfilehandle
{
    friend class boost::serialization::access;
    public:
        lvlfilehandle();
        virtual ~lvlfilehandle();
        void readmap(std::string); // string filename, will populate this->grid
        void writemap(std::string); // string filename, will populate this->grid
        std::vector<xy_container> get_map() {
            for (std::vector<xy_container>::iterator it = this->level.begin(); it != this->level.end(); ++it)
            {
                std::cout << it->t_units << std::endl;
            }
            return this->level; };


        /*template<class Archive>
        void serialize(Archive & ar, const unsigned int version)
        {
            ar & level;
        }
        */
    protected:
    private:
        std::vector<xy_container> level;
};


#endif // LVLFILEHANDLE_H
