package com.rezdron.chris.agame;

/**
 * Created by Chris on 1/9/2016.
 */
public class Token {
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
    Integer x;
    Integer y;

    Token(Integer height, Integer score)
    {
        // Register self with tokenhandler event/listener controller
        // Accept a motion function defined elsewhere and assigned in
        // Can always assume sidescrolling, will appear at rightmost edge at 'height'
        this.y = height;
        //this.x = [rightmost edge of screen]
        this.points = score;
        active = true;
    }

    public void onDraw()
    {
        // tokennotifier will call each onDraw()
        // in lifo order so older objects render above newer
    }

    public void onCollide()
    {
        // Only if type is player does collision change active state
    }

    public Integer getScore() { return this.points; }

    public boolean canScore() { return scoreable; }

    public boolean getActive() { return active; }

    public void tick()
    {
        // Handle score check if player is past this then score its value
        // Player is a static Token setup when game enters loading phase
        // Assuming 32pixel sprites + 12 more for wiggle space
        // When player is 2/3rds past object score it could potentially allow player to score
        // A point while also dying on it but not a problem
        if (Player.getX() < (this.x + 24))
        {
            scoreable = true;
        }
        // Once object is at screen edge flip activity
        if (this.x < -48) // Presuming 0 is leftmost pixel and approx 32pixel sprites with a little extra space
        {
            active = false;
        }
    }
}
