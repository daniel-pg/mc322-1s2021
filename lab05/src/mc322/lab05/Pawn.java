package mc322.lab05;

import java.util.Arrays;

public class Pawn extends Piece {
    Pawn(int owner, int[] pos, Board board) {
        super(owner, pos, board);
    }

    public boolean isValidMove(int[] dst) {
        // Avalia se já existe uma peça no local de destino
        if (this.board.getPiece(dst) != null) return false;

        int[] diff = {dst[0] - this.pos[0], dst[1] - this.pos[1]};
        int direction = (this.owner == 1) ? -1 : 1;

        // Avalia movimentos que não comem peças
        int[][] diffExpOneStep = {{direction, -1}, {direction, 1}};
        for (int[] diffExp : diffExpOneStep) {
            if (diff[0] == diffExp[0] && diff[1] == diffExp[1]) return true;
        }

        // Avalia movimentos que comem peças
        int[] eatenPos = new int[2];
        int[][] diffExpTwoStep = {{-2,-2}, {-2,2}, {2,-2}, {2,2}};
        for (int[] diffExp : diffExpTwoStep) {
            if (diff[0] == diffExp[0] && diff[1] == diffExp[1]) {
                Arrays.setAll(eatenPos, i -> this.pos[i] + diffExp[i] / 2);
                if(this.board.getPiece(eatenPos) != null) return true;
            }
        }

        return false;
    }

    public boolean hasValidMove() {
        int[] dst = new int[2];
        int[][] shiftTable = {{-2,-2}, {-2,2}, {2,-2}, {2,2}};

        for (int i = 0; i < 4; i++) {
            int finalI = i; // Vai entender as frescuras do Java...
            Arrays.setAll(dst, j -> this.pos[j] + shiftTable[finalI][j]);
            if (isValidMove(dst)) return true;
        }

        return false;
    }
}
