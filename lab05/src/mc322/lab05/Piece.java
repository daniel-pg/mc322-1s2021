package mc322.lab05;

public abstract class Piece {
    protected boolean pieceOwner;
    protected int[] position;
    protected Board board;

    Piece(boolean pieceOwner, int[] position, Board board) {
        this.pieceOwner = pieceOwner;
        this.position = position;
        this.board = board;
    }

    public abstract boolean movePiece(int[] src, int[] dst);

    public abstract boolean isValidMove(int[] src, int[] dst);

}
