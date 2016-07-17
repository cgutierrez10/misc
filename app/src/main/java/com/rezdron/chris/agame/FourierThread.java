package com.rezdron.chris.agame;

import android.util.Pair;

import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Chris on 2/12/2016.
 * This'll be fun.
 * Fourier decomposition thread will accept vector<pair<x,y>> and return vector<pair<float,float>>
 * Which represents the fewest sine terms to match those x,y values
 * May need a special call to align existing area of high resolution to new values and keep sensible number of terms
 */


/**
 * #ifdef GL_ES
 * precision mediump float;
 * #endif
 *
 * //tigrou.ind@gmail.com 2012.11.22 (for gamedevstackexchange)
 *
 * uniform float time;
 * uniform vec2 mouse;
 * uniform vec2 resolution;
 *
 * void main( void ) {
 *
 * vec2 position = ( gl_FragCoord.xy / resolution.xy ) - mouse;
 *
 * float waves = sin(position.x*10.0)*0.05*sin(mouse[1]*5.0)   +  sin(position.x*10.0+1.3)*0.02*sin(mouse[0]*7.0);
 * float color = position.y < waves ?(waves-position.y)*20.0 : 0.0;
 * color = min(pow(color,0.5),1.0);
 * gl_FragColor = vec4( position.y < waves ? mix(vec3(0.59,0.63,0.86),vec3(0.19,0.24,0.51),color) : vec3(0,0,0), 1.0 );
 * }
 */
public class FourierThread extends Thread {
    // Maybe need to use a prime lookup table for simplicity
    // 11,13,17,19,23,29 should offer range from low end of 2.3 seconds (1,000 samples/second) to 12.5 seconds
    private Vector<Pair<Double,Float>> waveTerms;
    private Float blendval = 1.0f;   // Value of blending scale between begin/next begin
    private final int Length = 7163; // Length of series wavelength loop
    private boolean blend = false;
    private int xval = 0;  // Current x value over wavelength
    private int calcx = 0; // Calculate x step

    public FourierThread() {
        waveTerms.add(new Pair<>(13.0d,ContentGen.getInstance().getAmplitude(15.0f)));
        waveTerms.add(new Pair<>(17.0d,ContentGen.getInstance().getAmplitude(15.0f)));
        waveTerms.add(new Pair<>(19.0d,ContentGen.getInstance().getAmplitude(15.0f)));
        waveTerms.add(new Pair<>(13.0d,ContentGen.getInstance().getAmplitude(15.0f)));
        waveTerms.add(new Pair<>(17.0d,ContentGen.getInstance().getAmplitude(15.0f)));
        waveTerms.add(new Pair<>(19.0d,ContentGen.getInstance().getAmplitude(15.0f)));
    }

    // General steps
    // Start a new wave from randaddcords
    // As waterline reaches end of looping length
    // setCoords to end of existing wave
    // randaddcoords to new length

    // Get 3 random amplitudes, 3 random wavelengths
    // Capped/minned to set a reasonable max height and length

    // Determine the repeat point
    // Find 3 more waves, multiple from 0->1 from the 9/10th, 11/10th to end of the wave
    // while fading out other 1->0 9/10 ->11/10

    // Need to pop some values off the end
    public void setX(int newX) {
        xval = newX;
    }

    // Pop first 3 amplitudes and add 3 to end for next set
    // Call after blendval = 0
    private void shiftAmp()
    {
        waveTerms.remove(0);
        waveTerms.remove(0);
        waveTerms.remove(0);
        waveTerms.add(new Pair<>(13.0d,ContentGen.getInstance().getAmplitude(15.0f)));
        waveTerms.add(new Pair<>(17.0d,ContentGen.getInstance().getAmplitude(15.0f)));
        waveTerms.add(new Pair<>(19.0d,ContentGen.getInstance().getAmplitude(15.0f)));
        blendval = 1.0f;
        blend = false;
    }

    public float tan(int atX) {
        // Need to return tangent at given point, other functions can use this for jump physics
        // Stub as 0 for writing other functions around
        return 0.0f;
    }

    // This one will nearly always be doing nothing
    // Should buffer 80% or so of wave as renderable sprite coords, flat (gradient in shader) color
    // Can periodically refresh the gl to render additional as needed
    // Might create a large number of quads on screen?
    @Override
    public void run()
    {
        double waterline = 0;
        if (blend) {
            for (Pair<Double,Float> wave: waveTerms) {
                waterline = waterline + (wave.second * Math.sin(wave.first) * blendval);
                // Blend function for x vals
            }
        }
        else {
            Iterator<Pair<Double,Float>> itr = waveTerms.iterator();
            Pair<Double,Float> wave;
            for (int i = 0; i < 3; i++) {
                wave = itr.next();
                waterline = waterline + (wave.second * Math.sin(wave.first));
            }
            // Nonblend sum of first 3 terms
        }
    }

}
