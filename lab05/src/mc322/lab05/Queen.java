package mc322.lab05;

import java.lang.reflect.Array;

public class Queen extends Piece {
    Queen(int owner, int[] pos, Board board) {
        super(owner, pos, board);
    }

    Queen(Pawn pawn) {
        this(pawn.owner, pawn.pos, pawn.board);
    }

    public boolean isValidMove(int[] dst) {
        // Avalia se o local de destino está vago
        if (this.board.getPiece(dst) != null) return false;

        // Avalia se o movimento é diagonal
        if (Math.abs(pos[0] - dst[0]) != Math.abs(pos[1] - dst[1])) return false;

        // Variáveis para percorrer a diagonal-trajetória partindo do destino em direção à rainha
        int rowStep = (pos[0] > dst[0]) ? 1 : -1;
        int colStep = (pos[1] > dst[1]) ? 1 : -1;
        int row = dst[0] + rowStep;
        int col = dst[1] + colStep;
        int[] position;

        // Avalia se o destino é adjacente à rainha
        if (this.pos[0] == row) return true;

        // Avalia se a penúltima casa da trajetória contém uma peça do jogador
        position = new int[]{row, col};
        Piece nextToLast = this.board.getPiece(position);
        if (nextToLast != null && nextToLast.owner == this.owner) return false;

        // Avalia se as outras casas da trajetória contém alguma peça
        row += rowStep;
        col += colStep;
        while(row != this.pos[0]) {
            position = new int[]{row, col};
            if (this.board.getPiece(position) != null) return false;
            row += rowStep;
            col += colStep;
        }

        return true;
    }

    public boolean hasValidMove() {
        int[] coordAtual = new int[2];
        return true;
    }
}
