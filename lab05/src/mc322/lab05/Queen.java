package mc322.lab05;

import java.lang.reflect.Array;

public class Queen extends Piece {
    Queen(int owner, int[] pos, Board board) {
        super(owner, pos, board);
    }

    Queen(Piece pawn) {
        this(pawn.owner, pawn.pos, pawn.board);
    }

    public char toChar() {
        return (owner == 1) ? 'B' : 'P';
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

        /*
         * Realiza varredura ao longo da trajetória diagonal
         * A varredura se dá em sentido contrário: parte-se do destino em direção à rainha
         * Estas duas variáveis determinam a direção da varredura
         */
        int rowStep = (this.pos[0] > dst[0]) ? 1 : -1;
        int colStep = (this.pos[1] > dst[1]) ? 1 : -1;

        // Armazena o local atual da varredura, começando adjacente ao destino
        int[] position = new int[]{dst[0] + rowStep, dst[1] + colStep};

        Piece piece;
        int sequence = 0;
        while (position[0] != this.pos[0]) {
            piece = this.board.getPiece(position);
            if (piece == null) {
                sequence = 0;
            } else {
                if (piece.owner == this.owner) {
                    return null;
                } else {
                    sequence++;
                }
            }
            if (sequence == 2) {
                return null;
            }
            position[0] += rowStep;
            position[1] += colStep;
        }

        // Produz o vetor com a trajetória da rainha
        // Relembre que rowStep e colStep vão do destino à posição da rainha
        int length = Math.abs(this.pos[0] - dst[0]) + 1;
        int[][] trajectory = new int[length][2];
        for (int i = 0; i < length; i++) {
            trajectory[i][0] = this.pos[0] - (i * rowStep);
            trajectory[i][1] = this.pos[1] - (i * colStep);
        }

        return trajectory;
    }

    public boolean isPromotable(int[] target) {
        return false;
    }
}
