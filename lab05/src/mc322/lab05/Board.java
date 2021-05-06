package mc322.lab05;

public class Board {
    private Piece[][] boardMatrix;
    private final int boardSize;    // Altura/largura do tabuleiro quadrado.
    private final int pawnRanks;    // Número de fileiras com peões para cada jogador, ao início do jogo.
    private int playerTurn;         // Brancas: 1. Pretas: 2.

    /**
     * Construtor padrão, passa as dimensões padrão do tabuleiro caso nenhuma seja fornecida.
     */
    Board() {
        this(8, 3);
    }

    Board(int boardSize, int pawnRanks) {
        this.boardSize = boardSize;
        this.pawnRanks = pawnRanks;
        boardMatrix = new Piece[this.boardSize][this.boardSize];
        playerTurn = 1;

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
     * Requisita à peça na posição de origem que calcule um trajeto até o destino, e em caso de sucesso, executa o
     * movimento e retorna 'true'. Caso contrário, não realiza nenhuma ação e retorna 'false'.
     *
     * @param src Vetor de coordenadas da posição de origem do lance.
     * @param dst Vetor de coordenadas da posição de destino do lance.
     * @return Indica se o movimento foi bem-sucedido.
     */
    public boolean requestMove(int[] src, int[] dst) {
        if (this.isOutOfBounds(src) || this.isOutOfBounds(dst))
            return false;

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

    /**
     * Executa o movimento especificado no parâmetro, comendo as peças que forem necessárias no caminho.
     * @param path Vetor de posições que devem ser percorridas pela peça.
     */
    private void executeMove(int[][] path) {
        Piece piece = this.getPiece(path[0]);

        // Remove a peça da posição original e quaisquer peças que porventura tenham sido comidas
        for (int i = 0; i < path.length - 2; i++) {
            this.setPiece(path[i], null);
        }

        // Coloca a peça na posição destino, a promovendo se for o caso
        int[] dst = path[path.length - 1];
        this.setPiece(dst, piece.isPromotable(dst) ? new Queen(piece) : piece);
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
                    serialization.append('-');  // Espaço vazio
                } else {
                    serialization.append(piece.toChar());
                }
            }

            serialization.append('\n');
        }

        return serialization.toString();
    }

    /**
     * Imprime na saída padrão o estado atual do tabuleiro em texto formatado.
     */
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

    public void exportToFile(CSVHandling csv) {
        String[] outputLines = new String[boardSize * boardSize];
        Piece peca;
        char c;

        for (int col = 0; col < boardSize; col++) {
            for (int row = boardSize - 1; row >= 0; row--) {
                int[] coord = new int[]{row, col};
                peca = this.getPiece(coord);
                c = (peca == null) ? '_': peca.toChar();
                outputLines[col * boardSize + (boardSize - row - 1)] = encodeCoord(coord) + Character.toString(c);
            }
        }
        csv.exportState(outputLines);
    }

    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Acessa a peça do tabuleiro na posição especificada.
     * @param pos Vetor de (linha,coluna) da posição da peça.
     * @return O objeto da peça, ou null se não houver peça ou as coordenadas forem invalidas.
     */
    public Piece getPiece(int[] pos) {
        if (this.isOutOfBounds(pos))
            return null;
        else
            return this.boardMatrix[pos[0]][pos[1]];
    }

    private void setPiece(int[] pos, Piece piece) {
        this.boardMatrix[pos[0]][pos[1]] = piece;

        if (piece != null) {
            piece.setPosition(pos);
        }
    }

    private boolean isOutOfBounds(int[] pos) {
        return (pos[0] < 0 || pos[0] >= this.boardSize || pos[1] < 0 || pos[1] >= this.boardSize);
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
        return "" + Character.toString('a' + coord[1]) + Character.toString('8' - coord[0]);
    }
}
