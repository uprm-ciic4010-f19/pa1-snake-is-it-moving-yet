package Game.Entities.Static;

import Game.Entities.Dynamic.Player;
import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Apple {

    private Handler handler;

    public int xCoord;
    public int yCoord;

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
    }
    
    //Implement rotten apple
    boolean appleRotten;
    public static boolean isGood() {
    	if(Player.steps < 240) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
}
