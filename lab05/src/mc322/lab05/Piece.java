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

    public abstract boolean isValidMove(int[] dst);

    public abstract boolean hasValidMove();
}
