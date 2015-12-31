/***************************************************************
 * Name:      leveleditorMain.h
 * Purpose:   Defines Application Frame
 * Author:     ()
 * Created:   2015-01-17
 * Copyright:  ()
 * License:
 **************************************************************/

#ifndef LEVELEDITORMAIN_H
#define LEVELEDITORMAIN_H

//(*Headers(leveleditorFrame)
#include <wx/sizer.h>
#include <wx/menu.h>
#include <wx/button.h>
#include <wx/scrolbar.h>
#include <wx/frame.h>
#include <wx/statusbr.h>
//*)

class leveleditorFrame: public wxFrame
{
    public:

        leveleditorFrame(wxWindow* parent,wxWindowID id = -1);
        virtual ~leveleditorFrame();

    private:

        //(*Handlers(leveleditorFrame)
        void OnQuit(wxCommandEvent& event);
        void OnAbout(wxCommandEvent& event);
        //*)

        //(*Identifiers(leveleditorFrame)
        static const long ID_BUTTON2;
        static const long ID_BUTTON4;
        static const long ID_BUTTON3;
        static const long ID_BUTTON1;
        static const long ID_SCROLLBAR1;
        static const long ID_SCROLLBAR2;
        static const long idMenuQuit;
        static const long idMenuAbout;
        static const long ID_STATUSBAR1;
        //*)

        //(*Declarations(leveleditorFrame)
        wxScrollBar* ScrollBar2;
        wxButton* Button4;
        wxButton* Button1;
        wxScrollBar* ScrollBar1;
        wxButton* Button2;
        wxButton* Button3;
        wxStatusBar* StatusBar1;
        //*)

        DECLARE_EVENT_TABLE()
};

#endif // LEVELEDITORMAIN_H
