package mc322.lab05;

public abstract class Piece {
    protected int owner;
    protected int[] pos;
    protected Board board;

    Piece(int pieceOwner, int[] pos, Board board) {
        this.owner = pieceOwner;
        this.pos = pos;
        this.board = board;
    }

    public abstract char toChar();

    public void setPosition(int[] pos) {
        this.pos = pos;
    }

    public abstract int[][] validateMove(int[] dst);

    public abstract boolean isPromotable(int[] target);
}
