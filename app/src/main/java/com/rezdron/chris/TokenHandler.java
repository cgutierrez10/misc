package com.rezdron.chris;

import com.rezdron.chris.agame.Token;

import java.util.Stack;

/**
 * Created by Chris on 1/9/2016.
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

    public static TokenHandler getInstance()
    {
        return instance;
    }

    public void addToken(Token newToken)
    {
        // Everything must tick, if it does not tick it cannot be culled
        tickable.push(newToken);

        // Everything should draw if it isn't visible why does it exist
        drawable.push(newToken);
        moveable.push(newToken);
        scoreable.push(newToken);
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
        cullList.empty();
    }

    public void tick() {
        for (Token element:tickable) {
            element.tick();
            if (element.canScore()) {
                score = element.getScore();
                scoreable.remove(element); // Removed here for simplicity
            }
            if (!element.getActive())
            {
                cullList.add(element);
            }
        }
        cullTokens();
    }
}
