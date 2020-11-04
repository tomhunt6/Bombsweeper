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
            int a = this.returnNumBombsAround(this.getXLocation(), this.getYLocation());
            if(a>0)
            {
                this.setImage(this.returnImageString(a));
            }
            if(a==0)
            {
                this.blankSpace(this.getXLocation(), this.getYLocation());
            }
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
        return counter;
    }

    public String returnImageString(int i)
    {
        String s = "images/.png";
        s = s.substring(0, 7) + i + s.substring(7, s.length());
        return s;
    }

    public void blankSpace(int x, int y)
    {
        for(int i=-1; i<2; i++)
        {
            for(int j=-1; j<2; j++)
            {
                if(i!=0 || j!=0)
                {
                    if(x+i>=0 && x+i<30 && y+j>=0 && y+j<30)
                    {
                        BombSquare bs = (BombSquare) board.getSquareAt(x+i, y+j);
                        if(bs.returnNumBombsAround(x+i, y+j)==0)
                        {
                            this.revealBlankSpace(x+i, y+i);
                        }
                    }
                }

            }
        }
    }

    public void revealBlankSpace(int x, int y)
    {
        for(int i=-1; i<2; i++)
        {
            for(int j=-1; j<2; j++)
            {
                if(x+i>=0 && x+i<30 && y+j>=0 && y+j<30)
                {
                    BombSquare bs = (BombSquare) board.getSquareAt(x+i, y+j);
                    int numBombs = this.returnNumBombsAround(x+i, y+j);
                    bs.setImage(this.returnImageString(numBombs));
                }

            }
        }
    }

}
