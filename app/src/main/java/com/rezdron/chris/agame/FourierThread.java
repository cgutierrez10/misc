package com.rezdron.chris.agame;

import android.util.Pair;

import java.util.Vector;

/**
 * Created by Chris on 2/12/2016.
 * This'll be fun.
 * Fourier decomposition thread will accept vector<pair<x,y>> and return vector<pair<float,float>>
 * Which represents the fewest sine terms to match those x,y values
 * May need a special call to align existing area of high resolution to new values and keep sensible number of terms
 */
public class FourierThread extends Thread {
    private Vector<Pair<Integer,Integer>> matchCoords;
    private Vector<Pair<Float,Float>> sineTerms;
    private boolean pending;

    // General steps
    // Start a new wave from randaddcords
    // As waterline reaches end of looping length
    // setCoords to end of existing wave
    // randaddcoords to new length

    // add coords may never be used
    public void setCoords(Vector<Pair<Integer,Integer>> newCoord)
    {
        // This one will completely replace list
        matchCoords = newCoord;
        pending = true;
    }

    public void addCoords(Vector<Pair<Integer,Integer>> newCoord)
    {
        // This one will completely add the provided coords to the list
    }

    // Important, set must be a power of 2
    public void randAddCoords(Integer length)
    {
        // This one will add length of new <x,y> from randomizer to current list
        for (int i = 0; i < length; i++)
        {
            Pair<Integer,Integer> datapoint = new Pair<>(i * 50, ContentGen.getInstance().rng.nextInt(50));
            //matchCoords.add(new Pair(i * 50, ContentGen.getInstance().rng.nextInt(50)));
            matchCoords.add(datapoint);
        }
        pending = true;
    }

    // This one will nearly always be doing nothing
    @Override
    public void run()
    {
        // Run fourier decomposition if pending == true
        // Clear the coords list and pending = false;
        if (pending)
        {
            // Verify FFT function requirements met (total samples power of 2 among others)
            // Need an FFT function for uneven sampling

            // Last things to call on the way out
            setCoords(new Vector<Pair<Integer,Integer>>());
            pending = false;
        }
    }

}
