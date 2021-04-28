package mc322.lab05;

public class Board {
    private boolean playerTurn;
    private Pawn[][] mtxPawn;
    private Queen[][] mtxQueen;

    final static int BOARD_SIZE = 8;
    final static int PAWN_RANKS = 3;

    Board() {
        playerTurn = false; // False: Jogador 1;  True: Jogador 2
        mtxPawn = new Pawn[BOARD_SIZE][BOARD_SIZE];
        mtxQueen = new Queen[BOARD_SIZE][BOARD_SIZE];

        int[] position;

        // Peças do jogador 1 (brancas)
        for (int row = BOARD_SIZE - PAWN_RANKS; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if ((row + col) % 2 == 1) {
                    position = new int[]{row, col};
                    mtxPawn[row][col] = new Pawn(false, position, this);
                }
            }
        }

        // Peças do jogador 2 (pretas)
        for (int row = 0; row < PAWN_RANKS; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if ((row + col) % 2 == 1) {
                    position = new int[]{row, col};
                    mtxPawn[row][col] = new Pawn(false, position, this);
                }
            }
        }
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (mtxPawn[i][j] != null) {
                    ret.append(mtxPawn[i][j].pieceOwner ? 'b' : 'p');
                } else if (mtxQueen[i][j] != null) {
                    ret.append(mtxQueen[i][j].pieceOwner ? 'B' : 'P');
                } else {
                    ret.append('-');
                }
            }

            ret.append('\n');
        }

        return ret.toString();
    }

    public boolean movePiece(int[] src, int[] dst) {
        // TODO: Implementar
        return true;
    }
}
