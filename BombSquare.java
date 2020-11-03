 public class BombSquare extends GameSquare
{
    private GameBoard board;                            // Object reference to the GameBoard this square is part of.
    private boolean hasBomb;                            // True if this squre contains a bomb. False otherwise.

	public static final int MINE_PROBABILITY = 10;

	public BombSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/blank.png");

        this.board = board;
        this.hasBomb = ((int) (Math.random() * MINE_PROBABILITY)) == 0;
    }   

    public void rightClicked()
    {
        this.setImage("images/flag.png");
    } 

    public void leftClicked()
    {
        if(hasBomb==true)
        {
            this.setImage("images/bomb.png");
        }
        else
        {
            this.setImage(this.returnImageString(this.returnNumBombsAround(this.getXLocation(), this.getYLocation())));
        }
    }

    public int returnNumBombsAround(int x, int y)
    {
        int counter=0;

        for(int i=-1;i<2;i++)
        {
            for(int j=-1; j<2;j++)
            {
                BombSquare bs = (BombSquare) board.getSquareAt(x+i, y+j);
                if(bs.hasBomb==true)
                {
                    counter++;
                }
                
            }
        }
        
        if(counter==0)
        {
            this.whiteSpace(x, y);
            return counter;
        }
        else{
            return counter;
        }

    }

    public String returnImageString(int i)
    {
        String s = "images/.png";
        s = s.substring(0, 7) + i + s.substring(7, s.length());
        return s;
    }

    public void whiteSpace(int x, int y)
    {
        if(this.returnNumBombsAround(x, y)==0)
        {
            for(int i=-1;i<2;i++)
            {
                for(int j=-1; j<2;j++)
                {
                    if(i==0||j==0)
                    {
                        BombSquare bs = (BombSquare) board.getSquareAt(x+i, y+j);
                        if(bs.returnNumBombsAround(x+i, y+j)==0)
                        {
                            this.whiteSpace(x+i, y+j);
                        }
                    }                    
                }
            }
        }
    }


}
