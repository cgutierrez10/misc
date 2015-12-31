/***************************************************************
 * Name:      leveleditorApp.cpp
 * Purpose:   Code for Application Class
 * Author:     ()
 * Created:   2015-01-17
 * Copyright:  ()
 * License:
 **************************************************************/

#include "leveleditorApp.h"

//(*AppHeaders
#include "leveleditorMain.h"
#include <wx/image.h>
//*)

IMPLEMENT_APP(leveleditorApp);

bool leveleditorApp::OnInit()
{
    //(*AppInitialize
    bool wxsOK = true;
    wxInitAllImageHandlers();
    if ( wxsOK )
    {
    	leveleditorFrame* Frame = new leveleditorFrame(0);
    	Frame->Show();
    	SetTopWindow(Frame);
    }
    //*)
    return wxsOK;

}
