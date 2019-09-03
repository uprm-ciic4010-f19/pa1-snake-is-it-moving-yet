package Game.Entities.Dynamic;

import Main.Handler;
import MyPackage.RandomColor;
import Game.Entities.Static.Apple;
import Game.GameStates.GameState;
import Game.GameStates.State;
import Main.GameSetUp;
import java.util.ArrayList;
import java.util.Arrays;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;


/**
 * Created by AlexVR on 7/2/2018.
 */
//Comitting and Pushing through Eclipse
//lol020202
public class Player {

    public int lenght;
    public boolean justAte;
    private Handler handler;

    public int xCoord;
    public int yCoord;

    public int moveCounter;

    public String direction;//is your first name one?

    public Player(Handler handler){
        this.handler = handler;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        direction= "Right";
        justAte = false;
        lenght= 1;

    }
    
    boolean isRottenApple;
    double speed = 0.0;
    public void tick(){
        moveCounter++;
        if(moveCounter>=5 + speed) {
            checkCollisionAndMove();
            //appendArrayCheck();
            isRottenApple = rottenApple();
            moveCounter=0;
        }
        if(direction != "Down") {
        	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
        		direction="Up";
        	}   
        }
        if(direction != "Up") {
        	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
                direction="Down";
            }
        }
        if(direction != "Right") {
        	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
                direction="Left";
            }
        }
        if(direction != "Left") {
        	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)){
                direction="Right";
            }
        }
        
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_P)){
            //Increases snake's speed
        	speed--;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_M)){
            //Decreases snake's speed
        	speed++;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)){
            //Adds a piece of tail
        	debugEat();
        }
        
        //reStarts game if snake become headless or score goes into negative
        if(lenght == 0 || currScore < 0) {
        	lenght = 1;
        	currScore = 0;
        	kill();
        }

    }
    public static int steps = 0; //records amount of steps before eating an apple (to implement rotten apple)
    public void checkCollisionAndMove(){
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        int x = xCoord;
        int y = yCoord;
        switch (direction){
            case "Left":
                if(xCoord==0){
                    xCoord = handler.getWorld().GridWidthHeightPixelCount-1; //TP to right
                }else{
                    xCoord--;
                    steps++;
                }
                break;
            case "Right":
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                   xCoord = 0; //TP to left
                }else{
                    xCoord++;
                    steps++;
                }
                break;
            case "Up":
                if(yCoord==0){
                    yCoord = handler.getWorld().GridWidthHeightPixelCount-1; //TP to bottom
                }else{
                    yCoord--;
                    steps++;
                }
                break;
            case "Down":
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    yCoord = 0; //TP to top
                }else{
                    yCoord++;
                    steps++;
                }
                break;
        }
        handler.getWorld().playerLocation[xCoord][yCoord]=true;


        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            if(isRottenApple) {
            	badEat();
            }else {
            	Eat();
            }
            System.out.println("Steps: " + steps); //prints out steps for debug purposes
            System.out.println("Length: " + lenght); //prints out length for debug purposes
            System.out.println("Speed: " + speed); //prints out speed for debug purposes
            steps = 0;
        }

        if(!handler.getWorld().body.isEmpty()) {
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
        }

    }
    
    //Records coordinates in two different arrays, x and y
    /*public ArrayList<Integer> arrayCheckXY;
    public ArrayList<Integer> currentXY;
    public String arrayCheckOnX[] = new String[] {};
    public String arrayCheckOnY[] = new String[] {};
    public int positionOfArray = 0;
    int sizeOfX = 0;
    int sizeOfY = 0;
    int sizeOfXY = 0;
    int z, f;
    public void appendArrayCheck() {
    	 String x = Integer.toString(handler.getWorld().body.getFirst().x);
    	 String y = Integer.toString(handler.getWorld().body.getFirst().y);
    	 z = handler.getWorld().body.getFirst().x;
    	 f = handler.getWorld().body.getFirst().y;
    	 
    	 arrayCheckOnX[positionOfArray] = x;
    	 arrayCheckOnY[positionOfArray] = y;
    	 positionOfArray++;
    	 
    	 sizeOfX = arrayCheckOnX.length;
    	 sizeOfY = arrayCheckOnY.length;
    	 
    	 checkDeath();
    	 
    }
    
    public void checkDeath() {
    	String a = Integer.toString(handler.getWorld().body.getFirst().x);
    	String b = Integer.toString(handler.getWorld().body.getFirst().y);
    	currentXY.add(z, f);
    	//int x = Array.getInt(currentXY, 0);
    	//int y = Array.getInt(currentXY, 1);
    	String xCompare, yCompare;
    	for(int i = sizeOfX - lenght; i < sizeOfX - 1; i++) {
    		xCompare = arrayCheckOnX[i];
    		if(a == xCompare) {	
    			for(int j = sizeOfY - lenght; j < sizeOfY - 1; j++) {
    				yCompare = arrayCheckOnY[j];
    				if(b == yCompare) {
    					kill();
    				}
    			}
    		}
    	}
    }*/
    
    
    
    public void render(Graphics g,Boolean[][] playeLocation){
        
        
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                //g.setColor(new Color(128,0,128)); //Changes color of snake to green
                g.setColor(RandomColor.randColor); //Changes color of snake to a random one

                if(playeLocation[i][j]||handler.getWorld().appleLocation[i][j]){
                    g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
                }

            }
        }


    }

    private static double currScore = 0.0;
    public void Eat(){
        lenght++;
        Tail tail= null;
        handler.getWorld().appleLocation[xCoord][yCoord]=false;
        handler.getWorld().appleOnBoard=false;
        switch (direction){
            case "Left":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                        }
                    }

                }
                break;
            case "Right":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=0){
                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                        }
                    }

                }
                break;
            case "Up":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
            case "Down":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=0){
                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        } System.out.println("Tu biscochito");
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
        }
        handler.getWorld().body.addLast(tail);
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
        speed = speed - 0.25; //Increases speed when apple is eaten by 0.05
        setCurrScore(getCurrScore() + Math.sqrt(2 * getCurrScore() + 1)); //Increases current score by equation
        System.out.println("Score: " + getCurrScore()); //Prints score to console while we get it to render on screen
    }
    
    //BadEat function when apple is rotten
    public void badEat(){
        lenght--;
        Tail tail= null;
        handler.getWorld().appleLocation[xCoord][yCoord]=false;
        handler.getWorld().appleOnBoard=false;
        switch (direction){
            case "Left":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                        }
                    }

                }
                break;
            case "Right":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=0){
                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                        }
                    }

                }
                break;
            case "Up":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
            case "Down":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=0){
                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        } System.out.println("Tu biscochito");
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
        }
        handler.getWorld().body.remove(tail);
        handler.getWorld().playerLocation[tail.x][tail.y] = false;
        speed = speed + 0.25; //Decreases speed when apple is eaten by 0.05
        setCurrScore(getCurrScore() - Math.sqrt(2 * getCurrScore() + 1)); //Increases current score by equation
        System.out.println("Score: " + getCurrScore()); //Prints score to console while we get it to render on screen
    }

    public void debugEat(){
        lenght++;
        Tail tail= null;
        switch (direction){
            case "Left":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                        }
                    }

                }
                break;
            case "Right":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=0){
                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                        }
                    }

                }
                break;
            case "Up":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
            case "Down":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=0){
                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        } System.out.println("Tu biscochito");
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
        }
        handler.getWorld().body.addLast(tail);
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
    }
    
    public void kill(){
        lenght = 0;
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

                handler.getWorld().playerLocation[i][j]=true;
                reStart();	
            }
        }
    }
   
    //reStart function within Player.java
    public State gameState;
    public void reStart() {
    	gameState = new GameState(handler);
    }
    
    //Implements rotten apple
    public boolean rottenApple() {
    	
    	//Apple.setColor(Color.RED);
    	
    	if(steps >= 240) {
    		return true;
    	}else {
    		return false;
    	}
    }

    public boolean isJustAte() {
        return justAte;
    }

    public void setJustAte(boolean justAte) {
        this.justAte = justAte;
    }

	public static double getCurrScore() {
		return currScore;
	}

	public void setCurrScore(double currScore) {
		Player.currScore = currScore;
	}
}
