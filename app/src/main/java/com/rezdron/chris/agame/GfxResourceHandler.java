package com.rezdron.chris.agame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Chris on 1/12/2016.
 * This file handles loading all the game graphics and storing them internally
 * It will also provide calls to draw them onto a provided canvas
 * With specified location
 *
 * Like TokenHandler this is also a singleton should not waste effort re-loading graphics ever
 */
public class GfxResourceHandler {
    Map<String, Bitmap> gfxResource = new HashMap<>();
    Map<String, Float[]> texCoord = new HashMap<>();  // Resource name, resource x coord and resource height per frame
    private Context state;
    private static GfxResourceHandler instance = new GfxResourceHandler();
    private Integer loadCount = 0;
    private int loaded_textures = 0;
    //private Context appContext;

    public static GfxResourceHandler getInstance() { return instance; }

    public void resetContext(Context c)
    {
        state = c;
    }

    /* Debug function */
    public Context getState() { return state; }

    public Bitmap getRsx(String rq)
    {
        String request = "atlas";
        if (loadCount == 0)
        {
            renderTex();
        }
        if (gfxResource.containsKey(request)) {
            return gfxResource.get(request);
            //return true;
        }
        else {
            // Load the resource on demand
            // If things get choppy or need to be pre cached this function
            // Could be called in the load area to load the necessary graphics ahead of time
            try {
                Resources rsx = state.getResources();

                Log.d("Bitmap", String.valueOf(rsx.getIdentifier(request, "drawable", state.getPackageName())));
                //final BitmapFactory.Options options = new BitmapFactory.Options();
                //options.inScaled = false;	// No pre-scaling
                Bitmap loadRsx = BitmapFactory.decodeResource(rsx, rsx.getIdentifier(request, "drawable", state.getPackageName()));

                // Manual entry of resource id works, above state.getPackageName is the failpoint
                //0x7f020049 xml
                //0x7f02004a img
                if (loadRsx != null) {
                    //loaded_count++;
                    loadRsx.setDensity(Bitmap.DENSITY_NONE);
                    gfxResource.put(request, loadRsx);
                } else {
                    Log.d("Bitmap", "Load failed");
                }

                if (gfxResource.get(request) != null) {
                    return gfxResource.get(request);
                    //return true;
                } else {
                    Log.d("Bitmap", "Load failed returning null");
                    return null;
                }
            } catch(Exception e) { return null; }
        }
    }

    /*public Bitmap getRsx(String request)
    {
        if (gfxResource.containsKey(request)) {
            return gfxResource.get(request);
            //return true;
        }
        else {
            // Load the resource on demand
            // If things get choppy or need to be pre cached this function
            // Could be called in the load area to load the necessary graphics ahead of time
            try {
                Resources rsx = state.getResources();


                Log.d("State", state.getPackageName());
                Log.d("Resource", String.valueOf(rsx.getIdentifier(request, "drawable", state.getPackageName())));
                Bitmap loadRsx = BitmapFactory.decodeResource(rsx, rsx.getIdentifier(request, "drawable", state.getPackageName()));

                // Manual entry of resource id works, above state.getPackageName is the failpoint
                //0x7f020049 xml
                //0x7f02004a img
                if (loadRsx != null) {
                    //loaded_count++;
                    loadRsx.setDensity(Bitmap.DENSITY_NONE);
                    gfxResource.put(request, loadRsx);
                } else {
                    Log.d("Bitmap", "Load failed");
                }

                if (gfxResource.get(request) != null) {
                    return gfxResource.get(request);
                    //return true;
                } else {
                    Log.d("Bitmap", "Load failed returning null");
                    return null;
                }
            } catch(Exception e) { return null; }
        }
    }*/

    // When bitmaps are all loaded generate texture mark these textures available by adding
    // them to texcoords
    public void renderTex()
    {
        // For each bitmap in the image add to the large bitmap to render as texture
        // Add coordinates for texturing
        // Lastly send texture to renderer to bind

        // Maybe temp maybe permanent hardcoding spritesheet coords
        loadCount = 3;
        texCoord.put("sheep",new Float[] {0.0f,0.0f});
        texCoord.put("spider",new Float[] {1.0f / loadCount,0.0f});
        texCoord.put("player",new Float[] {2.0f / loadCount,0.0f});
    }


    public void blitAt(String request, int x, int y, int frame)
    {
        if (loadCount == 0)
        {
            getRsx("atlas");
        }
        // Implement a draw to the current context canvas of resource at location
        //Bitmap blit = getRsx(request);
        Float[] loc = texCoord.get(request);
        mglRender.getInstance().spriteBlit(x, y, 64, 64, loc[0], 0, frame, loadCount);
    }
}
