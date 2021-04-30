package mc322.lab05;

public class Board {
    private int playerTurn;
    private Piece[][] boardMatrix;
    private final int boardSize;
    private final int pawnRanks;

    Board() {
        this(8, 3);
    }

    Board(int boardSize, int pawnRanks) {
        this.boardSize = boardSize;
        this.pawnRanks = pawnRanks;
        boardMatrix = new Piece[this.boardSize][this.boardSize];
        playerTurn = 1;     // Brancas: 1. Pretas: 2.

        int[] pos; // Vetor usado pra passar a coordenada da peça como parâmetro

        // Peças do jogador 1 (brancas)
        for (int row = this.boardSize - this.pawnRanks; row < this.boardSize; row++) {
            for (int col = 0; col < this.boardSize; col++) {
                if ((row + col) % 2 == 1) {
                    pos = new int[]{row, col};
                    boardMatrix[row][col] = new Pawn(1, pos, this);
                }
            }
        }

        // Peças do jogador 2 (pretas)
        for (int row = 0; row < this.pawnRanks; row++) {
            for (int col = 0; col < this.boardSize; col++) {
                if ((row + col) % 2 == 1) {
                    pos = new int[]{row, col};
                    boardMatrix[row][col] = new Pawn(2, pos, this);
                }
            }
        }
    }

    /**
     * Retorna uma representação do tabuleiro na forma de uma String, onde cada linha do tabuleiro é dividida por
     * uma quebra de linha. As letras minúsculas são peças comuns, e as letras maiúsculas são damas. A cor da peça é
     * indicada pelas letras 'P' (preta) ou 'B' (branca).
     */
    public String toString() {
        StringBuilder serialization = new StringBuilder();

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Piece piece = getPiece(new int[]{row, col});

                if (piece == null) {
                    // Espaço vazio
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

    /**
     * Acessa a peça do tabuleiro na posição especificada.
     * @param pos Vetor de (linha,coluna) da posição da peça.
     * @return O objeto da peça, ou null se não houver peça.
     */
    public Piece getPiece(int[] pos) {
        if (this.isOutOfBounds(pos))
            return null;
        else
            return this.boardMatrix[pos[0]][pos[1]];
    }

    private boolean isOutOfBounds(int[] pos) {
        return (pos[0] < 0 || pos[0] >= this.boardSize || pos[1] < 0 || pos[1] >= this.boardSize);
    }

    private void setPiece(int[] pos, Piece piece) {
        this.boardMatrix[pos[0]][pos[1]] = piece;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public boolean requestMove(int[] src, int[] dst) {
        if (this.isOutOfBounds(src)) return false;
        if (this.isOutOfBounds(dst)) return false;

        Piece piece = this.getPiece(src);
        if (piece == null) return false;

        int[][] path = piece.validateMove(dst);

        if (path == null) {
            return false;
        } else {
            executeMove(path);
            return true;
        }
    }

    private void executeMove(int[][] path) {
        Piece piece = this.getPiece(path[0]);

        // Remove a peça da posição original
        this.setPiece(path[0], null);

        // Remove uma peça que porventura tenha sido comida
        this.setPiece(path[path.length - 2], null);

        // Coloca a peça na posição destino, a promovendo se for o caso
        this.setPiece(path[path.length - 1], piece.isPromotable() ? piece : new Queen(piece));
    }

    private void changePlayerTurn() {
        this.playerTurn = (this.playerTurn - 1) % 2 + 1; // next_turn = (turn - bias) % no_players + bias
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

    public void printBoard() {
        // Imprime o tabuleiro na tela com a formatação adequada.
        String[] linhas = this.toString().split("\\n");

        for (int row = this.boardSize; row > 0; row--) {
            System.out.print(row);
            String linha = linhas[this.boardSize - row];
            for (int i = 0; i < linha.length(); i++) {
                System.out.print(" " + linha.charAt(i));
            }
            System.out.print("\n");
        }

        System.out.print(" ");
        for (int i = 0; i < this.boardSize; i++) {
            System.out.print(" " + Character.toString('a' + i));
        }
        System.out.println("\n");
    }
}
