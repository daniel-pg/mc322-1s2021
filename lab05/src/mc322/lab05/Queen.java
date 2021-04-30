package mc322.lab05;

import java.lang.reflect.Array;

public class Queen extends Piece {
    Queen(int owner, int[] pos, Board board) {
        super(owner, pos, board);
    }

    Queen(Piece pawn) {
        this(pawn.owner, pawn.pos, pawn.board);
    }

    public int[][] validateMove(int[] dst) {
        // Avalia se o local de destino está vago
        if (this.board.getPiece(dst) != null) {
            return null;
        }

        // Avalia se o movimento é diagonal
        if (Math.abs(pos[0] - dst[0]) != Math.abs(pos[1] - dst[1])) {
            return null;
        }

        // Variáveis para percorrer a diagonal-trajetória partindo do destino em direção à rainha
        int rowStep = (pos[0] > dst[0]) ? 1 : -1;
        int colStep = (pos[1] > dst[1]) ? 1 : -1;
        int row = dst[0] + rowStep;
        int col = dst[1] + colStep;
        int[] position;

        // Avalia se o destino é adjacente à rainha. Se for, retorna o vetor trajetória
        if (this.pos[0] == row) {
            this.pos = dst;
            return new int[][]{this.pos, dst};
        }

        // As seções abaixo são executadas caso o destino esteja a duas ou mais casas da rainha
        // Avalia se a penúltima casa da trajetória contém uma peça do jogador
        position = new int[]{row, col};
        Piece nextToLast = this.board.getPiece(position);
        if (nextToLast != null && nextToLast.owner == this.owner) {
            return null;
        }

        // Avalia se as outras casas da trajetória contém alguma peça
        row += rowStep;
        col += colStep;
        while(row != this.pos[0]) {
            position = new int[]{row, col};
            if (this.board.getPiece(position) != null) {
                return null;
            } else {
                row += rowStep;
                col += colStep;
            }
        }

        // Produz o vetor com a trajetória da rainha
        // Relembre que rowStep e colStep vão do destino à posição da rainha
        int length = this.pos[0] - dst[0] + 1;
        int[][] trajectory = new int[length][2];
        for (int i = 0; i < length; i++) {
            trajectory[i][0] = this.pos[0] - (i * rowStep);
            trajectory[i][1] = this.pos[1] - (i * colStep);
        }

        this.pos = dst;
        return trajectory;
    }

    public boolean isPromotable() {
        return false;
    }
}
