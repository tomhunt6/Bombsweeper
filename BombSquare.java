public class BombSquare extends GameSquare
{
    private GameBoard board;                            // Object reference to the GameBoard this square is part of.
    private boolean hasBomb;                            // True if this squre contains a bomb. False otherwise.
    
    private boolean visible;                            //Has sqaure been revealed
    private boolean flagged;                            //Is square currently flagged

    public static final int MINE_PROBABILITY = 8;

	public BombSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/blank.png");

        this.board = board;
        this.hasBomb = ((int) (Math.random() * MINE_PROBABILITY)) == 0;
        this.visible = false;
        this.flagged = false;        
    }   

    /**
	* Performs the right click action on the current 'BombSquare'
	*/
    public void rightClicked()
    {
        if(this.flagged==false){
            if(this.visible==false){
                this.setImage("images/flag.png");
                this.flagged=true;
                this.visible=true;
            }    
        }
        else{
            this.setImage("images/blank.png");
            this.flagged=false;
            this.visible=false;
        }
        
    } 

    /**
	* Performs the left click action on the current 'BombSquare'
	*/
    public void leftClicked()
    {
        if(this.flagged==false){
            if(hasBomb==true){
                this.setImage("images/bomb.png");
                this.visible=true;
            }
            else{
                this.search(this.getXLocation(), this.getYLocation());
            }
        }
        
    }

    /**
	* Returns the number of bombs surrounding the square given via the given co-ordinates
	* @param x the x co-ordinate of the square requested.
	* @param y the y co-ordinate of the square requested.
	*/
    public int returnNumBombsAround(int x, int y)
    {
        int counter=0;

        for(int i=-1;i<2;i++){
            for(int j=-1; j<2;j++){
                if(x+i>=0 && x+i<30 && y+j>=0 && y+j<30){
                    BombSquare bs = (BombSquare) board.getSquareAt(x+i, y+j);
                    if(bs.hasBomb==true){
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    /**
	* Returns the String of the image that sohuld be displayed given the number of surrounding bombs (only works for 0-8)
	* @param i the number of the desired image
	*/
    public String returnImageString(int i)
    {
        String s = "images/.png";
        s = s.substring(0, 7) + i + s.substring(7, s.length());
        return s;
    }

    /**
    * Performs the reveal/search function. It opens the square clicked, if square is empty 
    * (no adjacent bombs) will search for adjacent sqaures that are also empty and open them too.
	* @param x the x co-ordinate of the square requested.
	* @param y the y co-ordinate of the square requested.
	*/
    public void search(int x, int y)
    {
        if(x>=-1 && x<30 && y>=-1 && y<30){
            int bombs = this.returnNumBombsAround(x, y);
            if(bombs>0){
                this.setImage(this.returnImageString(bombs));
                this.visible=true;
            }
            if(bombs==0){
                if(this.visible==false){
                    this.setImage(this.returnImageString(bombs));
                    this.visible=true;

                    for(int i=-1; i<2; i++){
                        for(int j=-1; j<2; j++){
                            if(i!=0 || j!=0){
                                if(x+i>=0 && x+i<30 && y+j>=0 && y+j<30){
                                    BombSquare bs = (BombSquare) board.getSquareAt(x+i, y+j);
                                    if(bs.returnNumBombsAround(bs.getXLocation(), bs.getYLocation())==0){
                                        bs.search(x+i, y+j);
                                    }
                                    else{
                                        bs.setImage(this.returnImageString(bs.returnNumBombsAround(bs.getXLocation(), bs.getYLocation())));
                                        bs.visible=true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }

}
