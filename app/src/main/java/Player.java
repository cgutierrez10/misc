import com.rezdron.chris.agame.PhysVert;
import com.rezdron.chris.agame.Token;

/**
 * Created by Chris on 1/14/2016.
 * Carries player token specific data including special physics functions
 */
public class Player extends Token {
    TokenPhysics phys;


    Player()
    {
        super(32,0);
        phys = new PhysVert(32,64,(float)0.0,(float)0.0);
    }

    public void setAccel(Integer x, Integer y)
    {
        dvx = dvx + x;
        dvy = dvy + y;
    }

    @override
    public void tick()
    {
        // Player always subject to gravity
        // 8px = 1m
        // Assuming gravity (in water) acceleration of about
        // 5.4m/s^2, a^2t + bt + c, x = c dvx = b, 5.4 = a
        phys.tick();
        // human interaction will alter dvy
        // Player token has no other physics functions
        // Map will handle the x axis  of background and other tokens will advance towards player manually

    }

    public Integer getX()
    {
        return phys.getX();
    }

    public Integer getY()
    {
        return phys.getY();
    }
}
