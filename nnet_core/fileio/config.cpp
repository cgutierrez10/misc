#include "config.h"


/*InputConfig::InputConfig()
{
    //ctor
    _Debug = true;
}*/

int InputConfig::ReadConfig(string filename)
{
    /*
        Begin reading lines from config file
        1: trainings
        2: input data file
        3: direction
        4: frames
        5: column definition vector
        6: column const values
        7: training method
            (dictates network type)
        8: recurrence values (or 0 if not recurrent)
        9: error method
            fixed for now
   */
    bool eof;
    int cfgcounter = 0;
    FileRead* Source = new FileRead(filename);
    string line;
    line = Source->NextLine(&eof);
    while (eof != true)
    {
    	line = Source->NextLine(&eof);
        //cout << line << endl;
        if (line.substr(0,1) == "#")
        {
            //cout << "Line contains comment skipping" << endl;
        }
        else
        {
            cfgcounter++;
            switch(cfgcounter)
            {
            case 1:
                cout << "Trainings: ";
				_train = atoi(line.c_str());
                break;
            case 2:
                cout << "Data file: ";
				_IFile = line;
                break;
            case 3:
                cout << "Frame direction: ";
				direction = atoi(line.c_str());
                break;
            case 4:
                cout << "Frame width: ";
				_Window = atoi(line.c_str());
                break;
            case 5:
                cout << "Parsing vector: ";
                break;
            case 6:
                cout << "Constant data: ";
                break;
            case 7:
                cout << "Training method: ";
                break;
            case 8:
                cout << "Recurrent neurons: ";
                break;
            case 9:
                cout << "Semi-constant neurons: ";
                break;
            case 10:
                cout << "Error Calculation method: ";
                break;
            case 11:
                cout << "Save file: ";
                break;
            default:
                eof = true;
                break;
            }
			cout << line << endl;
        }
        line.clear();
    }
    return 0;
}

InputConfig::InputConfig(vec columns, vec col_const)
{
    //ctor
    _Columns = columns;
    _ConstantColumns = col_const;
    _Debug    = true;
    colscount = 0;
    for (unsigned i = 0; i < _Columns.n_rows; i++)
    {
        if (_Columns.at(i) != 0)
        {
            colscount++;
        }
    }
}

// No constant columns, init to 0
InputConfig::InputConfig(vec columns)
{
    //ctor
    _Columns  = columns;
    _Debug    = true;
    colscount = 0;
    for (unsigned i; i < _Columns.n_rows; i++)
    {
        if (_Columns.at(i) != 0)
        {
            colscount++;
        }
    }
    _ConstantColumns.zeros(colscount);
}

InputConfig::~InputConfig()
{
    //dtor
}

mat InputConfig::GetInputMatrix()
{

    // Use filename, colscount, window to compile an input matrix
    // If these are not set return an exception
    bool eof = false;
    int nextcol;
	string chunks;
	int month;
	int day;
	int year;
	int date;
	string str_date;
    mat read_row;
    _InputMatrix.resize(0,colscount);
    read_row.resize(1,colscount);
    FileRead* Source = new FileRead();
    stringstream line;
    Source->NextLine(&eof);
    Source->NextLine(&eof);
    while (eof != true)
    {
		line.clear();
        line << Source->NextLine(&eof);
        nextcol = -1;
        // If colscount is longer than chunks extra columns will end up 0'd and skipped
        // If colscount is shorter than chunks new line will be read and excess data will be tossed
		// Not as foolproof as I have assumed if columns mismatch definition vectors line may over/underflow and set an eof bit or somesuch
        for (unsigned j = 0; j < _Columns.n_rows; j++)
            {
            if (_Columns.at(j) == 1) {
	            getline(line, chunks, ',');
                read_row.at(1,nextcol) = atof(chunks.c_str());
                nextcol++;
            }
            if (_Columns.at(j) == -1) {
                read_row.at(1,nextcol) = _ConstantColumns.at(j);
                nextcol++;
            }
            if (_Columns.at(j) == 99) {
            	getline(line, chunks, ',');
                // Special case, dates will be transformed into julian datecodes 4 digit year 3 digit day
				year = 0;
				day = 0;
				month = 0;
				if (chunks.length() == 10) {
					year = atoi(chunks.substr(0,4).c_str());
					day = atoi(chunks.substr(8,2).c_str());
					month = atoi(chunks.substr(5,2).c_str());
				} else {
					year = 1;
					day = 1;
					month = 1;
				}

				switch (month) {
					case  1: month = month + 0; break;
					case  2: month = month + 31; break;
					case  3: month = month + 59; break;
					case  4: month = month + 90; break;
					case  5: month = month + 120; break;
					case  6: month = month + 151; break;
					case  7: month = month + 181; break;
					case  8: month = month + 212; break;
					case  9: month = month + 243; break;
					case 10: month = month + 273; break;
					case 11: month = month + 304; break;
					case 12: month = month + 334; break;
					default: break;
				}
				// Leap year, if leap year and after day 59 then add one
				if (((year % 4) == 0) && (day > 60)) {
					day++;
				}
				date = (year * 1000) + month + day;
				read_row.at(1,nextcol) = double(date);
                nextcol++;
            }
            if (_Columns.at(j) == 0)  {
            	getline(line, chunks, ',');
                // Skip over these, non-numeric or outlying data
            }
        }
        //Out = join_cols(Out, read_row);
        _InputMatrix.insert_rows(_InputMatrix.n_rows, read_row);
        //_InputMatrix = join_cols(_InputMatrix, read_row);
        read_row.zeros(1,colscount);
    }
	_InputMatrix.shed_row(_InputMatrix.n_rows - 1);
	_InputMatrix.shed_row(_InputMatrix.n_rows - 1);
	    //read_row.row(read_row.n_rows-1).print();
	_InputMatrix.save("example2.csv", raw_ascii);
    if (_Debug) {cout << "Processing complete" << endl;}

    if (_Debug)
    {
        cout << "Completed dataset retrieval." << endl;
        //cout <<  "Expected matrix width: " << output.n_cols << endl;
    }

    _InputMatrix = RollingWindow(frames,direction);

    //if (_Debug)
    //{
        cout << "Completed compiling data frames." << endl;
        //cout <<  "Expected matrix width: " << output.n_cols << endl;
    //}
    //return(Out);

    return(_InputMatrix);
}

mat InputConfig::GetOutputMatrix()
{
    // Strip columns off to go from window to single frame
	//output = join_cols(output, _InputMatrix.cols(0,(_InputMatrix.n_cols / colscount));
	mat temp = _InputMatrix.cols(0,(_InputMatrix.n_cols / colscount));
	return(temp);
}

mat InputConfig::RollingWindow(mat Inputlist)
{
    mat Outputlist;
    Outputlist.resize(Inputlist.n_rows, 0);
    if (_Debug)
    {
        cout << "Input: " << Inputlist.n_rows << "x" << Inputlist.n_cols << endl;
        cout << "Ouput: " << Outputlist.n_rows << "x" << Outputlist.n_cols << endl;
    }

    // Try this more efficiently? Build the last row then pop the last window and row upwards rather than generating each in turn?
    for (int i = 0; i < colscount; i++)
    {
        Outputlist = join_rows(Outputlist, Inputlist);
        Inputlist.shed_row(0);
        Outputlist.shed_row(Outputlist.n_rows-1);
    }
    cout << "Ouput: " << Outputlist.n_rows << "x" << Outputlist.n_cols << endl;
    return(Outputlist);
}

// Take individual data colscount and create a matrix with colscount of sets per row, and a direction
// Presumes _InputWindow is configured at 1 frame per row currently
mat InputConfig::RollingWindow(int width, int direction)
{
    mat Outputlist;
    //Read file and compile colscount into list
    //Outputlist.resize(_InputMatrix.n_rows, (_InputMatrix.n_cols * colscount));
	// Append the rows of the matrix with an offset per append
	// Append the rows of the matrix with an offset per append

	for (int i = 0; i < width; i++)
	{
		Outputlist = join_rows(Outputlist,_InputMatrix);
		// Append end to beginning, then delete those rows off the end
		_InputMatrix = join_cols(_InputMatrix.rows(1,direction+1),_InputMatrix);
		_InputMatrix.shed_rows(_InputMatrix.n_cols - direction, _InputMatrix.n_cols);
	}
	_InputMatrix.shed_row(29);
	_InputMatrix.shed_row(22);
	_InputMatrix.shed_row(15);
	_InputMatrix.shed_row(8);
    return(Outputlist);
}
