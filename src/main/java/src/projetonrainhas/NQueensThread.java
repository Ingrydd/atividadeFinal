package src.projetonrainhas;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NQueensThread {
    int total = 0;

    public static void main(String[] args) {
        int n = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o tamanho do tabuleiro (valor de N): ");
        while (true) {
            try {
                n = Integer.parseInt(scanner.nextLine());
                if (n < 1) {
                    System.out.println("Por favor, digite um número maior que 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }

        if (n > 3) {
            // Cria uma instancia de NQueensThread e resolve o problema
            NQueensThread nq = new NQueensThread();
            nq.solve(n);
            System.out.printf("Fim da execução com %d rainhas.\n", n);
        } else {
            System.out.println("Nenhuma possibilidade encontrada.");
        }

        scanner.close();
    }

    public void solve(int n) {
        // para executar em paralelo, iremos criar uma thread pool
        // uma thread pool é um conjunto de threads que podem ser reutilizadas
        // para executar tarefas em paralelo.
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int col = 0; col < n; col++) {
            final int startCol = col;
            executor.submit(() -> {
                Set<Integer> cols = new HashSet<>();
                Set<Integer> posDiag = new HashSet<>();
                Set<Integer> negDiag = new HashSet<>();

                int[] board = new int[n];
                board[0] = startCol;
                cols.add(startCol);
                posDiag.add(startCol);
                negDiag.add(-startCol);

                backtrack(board, 1, n, cols, posDiag, negDiag);
            });
        }

        // Encerra a thread pool
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Espera todas as threads terminarem
        }
    }

    private void backtrack(int[] board, int row, int n, Set<Integer> cols, Set<Integer> posDiag, Set<Integer> negDiag) {
        // Se todas as rainhas foram colocadas, imprime a solucao
        if (row == n) {
            printSolution(board);
            return;
        }

        for (int col = 0; col < n; col++) {
            int posD = row + col;
            int negD = row - col;

            // Verifica se a coluna ou as diagonais ja estao ocupadas
            if (cols.contains(col) || posDiag.contains(posD) || negDiag.contains(negD)) {
                continue;
                // nao executa as lihas ou comando abaixo do if
            }

            // Coloca a rainha na posicao (row, col)
            board[row] = col;
            cols.add(col);
            posDiag.add(posD);
            negDiag.add(negD);

            // Avanca para a proxima linha
            backtrack(board, row + 1, n, cols, posDiag, negDiag);

            // Remove a rainha da posicao (row, col) e remove as restricoes
            cols.remove(col);
            posDiag.remove(posD);
            negDiag.remove(negD);
        }
    }

    // é importante esse método ser sincronizado para evitar que duas threads
    // imprimam ao mesmo tempo
    private synchronized void printSolution(int[] board) {
        total++;
        int n = board.length;
        System.out.printf("Solucao %d:\n", total);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i] == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
