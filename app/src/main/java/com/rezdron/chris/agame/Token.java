package com.rezdron.chris.agame;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Created by Chris on 1/9/2016.
 * Creates abstract class, only requires tick() to be filled in
 * May be possible to reduce this further tick() may not need to be specific ever
 */
public abstract class Token {
    // Each token should represent itself as an object that can be
    // Drawn on screen, render()
    // Provide a collision boundary for itself or not register for such, collision()
    // Have a movement pattern, tick()
    // Have a location within the game bounds, x,y duple
    // Have a state representing whether active or inactive

    // Representations include
    // A cloud, no collision boundary static linear motion pattern, active when on screen

    // A player, collision boundry gravity affected motion pattern ability to change direction, while active game persists

    // An obstacle, collidable, possibly delayed motion pattern, active while on screen
    // Collision detection will be collision by occlusion method each gfx item
    // Will have a second same sized element that is a black/white solid shape of the collision area

    Integer points;
    Boolean active;
    Boolean scoreable;
    Boolean collide;
    String gfx_type;
    public TokenPhysics phys;


    public Token(Integer score, TokenPhysics movement)
    {
        // Register self with tokenhandler event/listener controller
        // Accept a motion function defined elsewhere and assigned in
        // Can always assume sidescrolling, will appear at rightmost edge at 'height'
        phys = movement;
        //this.x = [rightmost edge of screen]
        this.points = score;
        active = true;
        collide = true;
        scoreable = false;

        // Don't store drawables on each token, save them in one place and call to draw on location as needed
        gfx_type = ""; // Identifier for gfx type and a matching collision shadow

        // Save a physics object which will take x,y,dvy,dvx and make x,y available
    }

    public abstract void onDraw();
    // tokennotifier will call each onDraw()
    // in lifo order so older objects render above newer
    // If physics object then also draw to collision canvas

    public abstract void deactivate();

    public void onCollide()
    {
        // Only if type is player does collision change active state
    }

    public Integer getScore() { return this.points; }

    public boolean canScore() { return scoreable; }

    public boolean getActive() { return active; }

    public Integer getX()
    {
        return phys.getX();
    }

    public Integer getY()
    {
        return phys.getY();
    }

    public abstract void tick();
    /*
    public void tick()
    {
        phys.tick();
        // Handle score check if player is past this then score its value
        // com.rezdron.chris.agame.Player is a static Token setup when game enters loading phase
        // Assuming 32pixel sprites + 12 more for wiggle space
        // When player is 2/3rds past object score it could potentially allow player to score
        // A point while also dying on it but not a problem
        // Not the place to implement this
        if (com.rezdron.chris.agame.Player.getX() < (phys.getX() + 24))
        {
            scoreable = true;
        }
        // Once object is at screen edge flip activity
        if (this.phys.getX() < -48) // Presuming 0 is leftmost pixel and approx 32pixel sprites with a little extra space
        {
            active = false;
        }
    }
    */
}
