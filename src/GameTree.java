import java.util.ArrayList;
import java.util.List;

class GameTree { // TIC TAC TOE
    static class Node {
        char[][] board;
        List<Node> children;
        int value;

        Node(char[][] board) {
            this.board = board;
            this.children = new ArrayList<>();
            this.value = 0;
        }
    }

    Node root;

    public GameTree(char[][] board) {
        root = new Node(board);
    }

    // Check if the game is over
    private boolean isGameOver(char[][] board) {
        // Check for a win or a full board
        return checkWin(board, 'X') || checkWin(board, 'O') || isBoardFull(board);
    }

    // Check if the board is full
    private boolean isBoardFull(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    // Check if a player has won
    private boolean checkWin(char[][] board, char player) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    // Generate all possible moves for the current board state
    private List<char[][]> generateMoves(char[][] board, char player) {
        List<char[][]> moves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    char[][] newBoard = copyBoard(board);
                    newBoard[i][j] = player;
                    moves.add(newBoard);
                }
            }
        }
        return moves;
    }

    // Copy the board
    private char[][] copyBoard(char[][] board) {
        char[][] newBoard = new char[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, 3);
        }
        return newBoard;
    }

    // Build the game tree
    public void buildTree(Node node, char player) {
        if (isGameOver(node.board)) {
            node.value = evaluateBoard(node.board);
            return;
        }
        List<char[][]> moves = generateMoves(node.board, player);
        for (char[][] move : moves) {
            Node child = new Node(move);
            node.children.add(child);
            buildTree(child, (player == 'X') ? 'O' : 'X');
        }
    }

    // Evaluate the board
    private int evaluateBoard(char[][] board) {
        if (checkWin(board, 'X')) return 10;
        if (checkWin(board, 'O')) return -10;
        return 0;
    }

    // Minimax algorithm
    public int minimax(Node node, boolean isMax) {
        if (node.children.isEmpty()) {
            return node.value;
        }
        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (Node child : node.children) {
                best = Math.max(best, minimax(child, false));
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (Node child : node.children) {
                best = Math.min(best, minimax(child, true));
            }
            return best;
        }
    }

    public static void main(String[] args) {
        char[][] board = {
                {'X', 'O', 'X'},
                {' ', 'O', ' '},
                {' ', ' ', ' '}
        };

        GameTree gameTree = new GameTree(board);
        gameTree.buildTree(gameTree.root, 'X');

        System.out.println("Best move value: " + gameTree.minimax(gameTree.root, true));
    }
}