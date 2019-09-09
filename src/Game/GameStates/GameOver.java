package Game.GameStates;

import java.awt.Graphics;

import Game.Entities.Dynamic.Player;
import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameOver extends State {

    private UIManager uiManager;

    public GameOver(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        uiManager.addObjects(new UIImageButton(56, (223+(64+16))+(64+16), 128, 64, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
            
        }));
        




    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        
        
        
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.GameOver,0,0,800,800,null);
        uiManager.Render(g);

    }
}
