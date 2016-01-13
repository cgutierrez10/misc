package com.rezdron.chris.agame;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Map;

import static android.support.v4.content.res.ResourcesCompat.*;

/**
 * Created by Chris on 1/12/2016.
 * This file handles loading all the game graphics and storing them internally
 * It will also provide calls to draw them onto a provided canvas
 * With specified location
 *
 * Like TokenHandler this is also a singleton should not waste effort re-loading graphics ever
 */
public class GfxResourceHandler {
    Map<String,Drawable> gfxResource = new HashMap<>();
    Resources context;
    private Context appContext;
    public GfxResourceHandler(Context c)
    {
        appContext = c;
        // What gfx size to pull? may not be needed best practices unclear
        DisplayMetrics metrics = new DisplayMetrics();
        /*
        if (metrics.densityDpi <= 160)
        {
            // Medium density minimum to support
        }
        else if (metrics.densityDpi == 240)
        {
            // High density
        }
        else if (metrics.densityDpi >= 360)
        {
            // XHigh density, highest that will be supported
        }*/
    }

    public void resetContext(Context c)
    {

    }

    public Drawable getRsx(String request)
    {
        if (gfxResource.containsKey(request)) {
            return gfxResource.get(request);
        }
        else
        {
            // Load the resource on demand
            // If things get choppy or need to be precached this function
            // Could be called in the load area to load the necessary graphics ahead of time
            gfxResource.put(request, ResourcesCompat.getDrawable(appContext.getResources(),[id lookup],null));
        }
    }
}
