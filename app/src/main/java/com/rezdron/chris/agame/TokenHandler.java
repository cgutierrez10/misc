package com.rezdron.chris.agame;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;

import java.util.Stack;

/**
 * Created by Chris on 1/9/2016.
 * small change to avoid unnecessary warning
 */
public class TokenHandler
{
    // TokenHandler class will create and manage tokens including renders and physics

    // Singleton
    private static TokenHandler instance = new TokenHandler();
    private TokenHandler() {}

    Stack<Token> drawable = new Stack<>();
    Stack<Token> moveable = new Stack<>();
    Stack<Token> tickable = new Stack<>();
    Stack<Token> collideable = new Stack<>();
    Stack<Token> scoreable = new Stack<>();
    Stack<Token> cullList = new Stack<>();
    Integer score = 0;
    Integer offscreen = 1000;

    public static TokenHandler getInstance()
    {
        return instance;
    }

    public void reset() {
        //Dump all the stacks preparing for a new game, create new player object
        drawable.clear();
        moveable.clear();
        tickable.clear();
        collideable.clear();
        scoreable.clear();
        cullList.clear();
        Log.d("token","Clearing tokens");
    }

    public void addToken(Token newToken)
    {
        // Everything must tick, if it does not tick it cannot be culled
        tickable.push(newToken);

        // Everything should draw if it isn't visible why does it exist
        if (newToken.name() == "bobber")
        {
            Log.d("token","Adding " + newToken.name() + " to drawable");
        }
        drawable.push(newToken);
        moveable.push(newToken);
        scoreable.push(newToken);

        if (newToken.collide) {
            collideable.push(newToken);
        }
    }

    private void cullTokens()
    {
        for (Token rmvElement:cullList) {
            // If an element ceases to exist at tick() then this is called to remove from all lists
            if (drawable.contains(rmvElement)) {
                drawable.remove(rmvElement);
            }
            if (moveable.contains(rmvElement)) {
                moveable.remove(rmvElement);
            }
            if (tickable.contains(rmvElement)) {
                tickable.remove(rmvElement);
            }
            if (collideable.contains(rmvElement)) {
                collideable.remove(rmvElement);
            }
        }

        if (!cullList.isEmpty())
        {
            Log.d("Token","Culled one or more tokens");
            cullList.removeAllElements();
        }

    }

    public void tick() {
        int speed = 10;
        for (Token element:tickable) {
            element.tick();
            element.shift(speed);
            if (element.getY() > offscreen)
            {
                element.deactivate();
            }
            if (element.canScore()) {
                score = element.getScore();
                scoreable.remove(element); // Removed here for simplicity
            }
            if (!element.getActive())
            {
                cullList.add(element);
            }
        }
        // Player moves after everything else and then physics cleanup
        Player.getInstance().tick();

        cullTokens();
        if (!Player.getInstance().getActive())
        {
            // Player b ded handle this with some cleanup and initialize a modeswitch to endscreen

        }

    }

    public int count() { return tickable.size(); }

    public void cullAt(int range)
    {
        offscreen = range;
    }

    public void draw() {
        // Definitely being called
        for (Token element:drawable) {
            //Log.d("token","Drawing " + element.name());
            element.onDraw();
        }
        // Player always draws and always draws on top
        Player.getInstance().onDraw();
        mglRender.setFlip();
    }

    public void collide() {
        // Definitely being called
        for (Token element:collideable) {
            Player.getInstance().collideWith(element);
        }
    }
}
