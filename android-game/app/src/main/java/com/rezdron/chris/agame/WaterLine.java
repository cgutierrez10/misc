package com.rezdron.chris.agame;

import android.graphics.Canvas;
import android.util.Pair;

import java.util.Vector;

/**
 * Created by Chris on 2/11/2016.
 * This class will handle rendering of a water waveline and provide a boolean
 * above/below water at <x,y> function
 */
public class WaterLine {
    private static WaterLine instance = new WaterLine();
    private boolean initalized;

    // This contains the operating fourier sequences, first is amplitude second is frequency
    private Vector<Pair<Float,Float>> fourier;

    // Fourier thread will handle decompositions
    // Fourier decompose a set of random x,y coordinates
    // This approach may end up being too inefficient to use
    //
    public WaterLine()
    {

    }

    public static WaterLine getInstance()
    {
        return instance;
    }

    public void initalize()
    {
        // Call rand a few times to get a set of x,y points
        // Prepare fourier decomposition
    }

    public void extentWater()
    {
        // Call rand a few more times, take last few samples of existing line
        // Prepare new fourier decomposition to extend the line another length
        // Being able to extend the waterline will allow
        // using smaller set of sine sums and ability to prevent visible repetition
        // This may be costly run on a background thread?
    }

    public boolean isInit()
    {
        return initalized;
    }

    public void onDraw(Canvas canvas)
    {

    }
}
