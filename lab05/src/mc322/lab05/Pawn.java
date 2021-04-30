package mc322.lab05;

import java.util.Arrays;

public class Pawn extends Piece {
    Pawn(int owner, int[] pos, Board board) {
        super(owner, pos, board);
    }

    public int[][] validateMove(int[] dst) {
        // Avalia se o destino é
        // Avalia se já existe uma peça no local de destino
        if (this.board.getPiece(dst) != null) {
            return null;
        }

        int[] diff = {dst[0] - this.pos[0], dst[1] - this.pos[1]};
        int direction = (this.owner == 1) ? -1 : 1;

        // Avalia movimentos que não comem peças
        int[][] diffExpOneStep = {{direction, -1}, {direction, 1}};
        for (int[] diffExp : diffExpOneStep) {
            if (diff[0] == diffExp[0] && diff[1] == diffExp[1]) {
                this.pos = dst;
                return new int[][]{this.pos, dst};
            }
        }

        // Avalia movimentos que comem peças
        int[] eatenPos = new int[2];
        int[][] diffExpTwoStep = {{-2,-2}, {-2,2}, {2,-2}, {2,2}};
        for (int[] diffExp : diffExpTwoStep) {
            if (diff[0] == diffExp[0] && diff[1] == diffExp[1]) {
                Arrays.setAll(eatenPos, i -> this.pos[i] + diffExp[i] / 2);
                if(this.board.getPiece(eatenPos) != null) {
                    this.pos = dst;
                    return new int[][]{this.pos, eatenPos, dst};
                }
            }
        }

        return null;
    }

    public boolean isPromotable() {
        boolean whitePromote = this.owner == 1 && this.pos[0] == 0;
        boolean blackPromote = this.owner == 2 && this.pos[0] == this.board.getBoardSize() - 1;
        return whitePromote || blackPromote;
    }
}
