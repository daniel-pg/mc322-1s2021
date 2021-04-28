package mc322.lab05;

public class Board {
    private int playerTurn;
    private Pawn[][] mtxPawn;
    private Queen[][] mtxQueen;

    final static int BOARD_SIZE = 8;
    final static int PAWN_RANKS = 3;

    Board() {
        playerTurn = 1;                                          // Brancas: 1. Pretas: 2.
        mtxPawn = new Pawn[BOARD_SIZE][BOARD_SIZE];
        mtxQueen = new Queen[BOARD_SIZE][BOARD_SIZE];

        int[] pos;

        // Peças do jogador 1 (brancas)
        for (int row = BOARD_SIZE - PAWN_RANKS; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if ((row + col) % 2 == 1) {
                    pos = new int[]{row, col};
                    mtxPawn[row][col] = new Pawn(1, pos, this);
                }
            }
        }

        // Peças do jogador 2 (pretas)
        for (int row = 0; row < PAWN_RANKS; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if ((row + col) % 2 == 1) {
                    pos = new int[]{row, col};
                    mtxPawn[row][col] = new Pawn(2, pos, this);
                }
            }
        }
    }

    public String toString() {
        StringBuilder serialization = new StringBuilder();
        int[] pos;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                pos = new int[]{row, col};
                Piece piece = getPiece(pos);
                if (piece == null) {
                    serialization.append('-');
                } else {
                    if (piece instanceof Pawn) {
                        serialization.append((piece.owner == 1) ? 'b' : 'p');
                    } else if (piece instanceof Queen) {
                        serialization.append((piece.owner == 1) ? 'B' : 'P');
                    }
                }
            }

            serialization.append('\n');
        }

        return serialization.toString();
    }

    public Piece getPiece(int[] pos) {
        Piece pawn = this.mtxPawn[pos[0]][pos[1]];
        Piece queen = this.mtxQueen[pos[0]][pos[1]];

        if (pawn != null) {
            return pawn;
        } else {
            return queen;
        }
    }

    public boolean movePiece(int[] src, int[] dst) {
        Piece piece = this.getPiece(src);

        // TODO: Checar limites do tabuleiro

        if (piece == null) {
            return false;
        }

        boolean success = piece.isValidMove(dst);
        if (success) {
            if (piece instanceof Pawn) {
                this.mtxPawn[src[0]][src[1]] = null;                    // Remove o peão da posição-fonte
                if (isLastRow(piece.owner, dst[0])) {
                    Queen newPiece = new Queen((Pawn) piece);
                    this.mtxQueen[dst[0]][dst[1]] = newPiece;           // Se o peão for promovido, adiciona rainha na posição-destino
                } else {
                    this.mtxPawn[dst[0]][dst[1]] = (Pawn) piece;        // Se não for promovido, adiciona peão na posição-destino
                }
            } else if (piece instanceof Queen) {
                this.mtxQueen[src[0]][src[1]] = null;                   // Remove a rainha da posição-fonte
                this.mtxQueen[dst[0]][dst[1]] = (Queen) piece;          // Adiciona rainha na posição-destino
            }
        }

        if (!piece.hasValidMove()) {
            this.changePlayers();
        }

        return success;
    }

    static boolean isLastRow(int owner, int row) {
        return owner == 1 && row == 0 || owner == 2 && row == BOARD_SIZE - 1;
    }

    public void changePlayers() {
        if (this.playerTurn == 1) {
            this.playerTurn = 2;
        } else {
            this.playerTurn = 1;
        }
    }

    public static int[] decodeCoord(String encodedCoord) {
        assert (encodedCoord.length() == 2);

        // Converte as coordenadas convencionais em índices da matriz, na qual 'a8' se torna (0,0).
        int[] coord = new int[2];
        coord[0] = Math.abs(encodedCoord.charAt(1) - '8');  // Linha
        coord[1] = encodedCoord.charAt(0) - 'a';            // Coluna
        return coord;
    }

    public static String encodeCoord(int[] coord) {
        return "" + Character.toString('a' + coord[1]) + ('8' - coord[0]);
    }

    public static void printBoard(String strBoard) {
        // Imprime o tabuleiro na tela com a formatação adequada.
        String[] linhas = strBoard.split("\\n");
        for (int row = Board.BOARD_SIZE; row > 0; row--) {
            System.out.print(row);
            String linha = linhas[Board.BOARD_SIZE - row];
            for (int i = 0; i < linha.length(); i++) {
                System.out.print(" " + linha.charAt(i));
            }
            System.out.print("\n");
        }

        System.out.print(" ");
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            System.out.print(" " + Character.toString('a' + i));
        }
        System.out.println("\n");
    }
}
