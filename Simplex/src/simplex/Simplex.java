package simplex;

import java.util.Scanner;

public class Simplex {

    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
//        double[][] table2 = {
        // z x1 x2 xf1 xf2 xf3 b
//            {1, -3, -5, 0, 0, 0, 0},
//            {0, 2, 4, 1, 0, 0, 10},
//            {0, 6, 1, 0, 1, 0, 20},
//            {0, 1, -1, 0, 0, 1, 30}};

        double[][] table = requestValues();
        printTable(table);
        simplex(table);
    }

    public static double[][] requestValues() {
        System.out.print("Insira o número de variáveis de decisão: ");
        int variables = s.nextInt();
        System.out.print("Insira o número de restrições: ");
        int restrictions = s.nextInt();

        double[][] table = new double[restrictions + 1][variables + restrictions + 2];
        fillTable(table, variables, restrictions);
        return table;
    }

    public static void fillTable(double[][] table, int variables, int restrictions) {
        table[0][0] = 1;
        for (int i = 0; i <= restrictions; i++) {
            if (i == 0) {
                System.out.println("Digite a função objetivo: ");
            } else {
                System.out.printf("Digite a restrição de número %d:\n", i);
                table[i][variables + i] = 1;
            }
            for (int j = 1; j <= variables; j++) {
                System.out.printf("x%d: ", j);
                table[i][j] = s.nextDouble();
            }
            if (i > 0) { 
                System.out.print("b: ");
                table[i][table[0].length - 1] = s.nextDouble();
            }
        }
    }

    public static void printTable(double[][] table) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                System.out.print(table[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void printLine(double[] line) {
        for (int i = 0; i < line.length; i++) {
            System.out.print(line[i] + "\t");
        }
    }

    private static boolean greatSolution(double[] line) {
        for (int i = 0; i < line.length; i++) {
            if (line[i] < 0) {
                return false;
            }
        }
        return true;
    }

    public static double[][] simplex(double[][] table) {
        final int LINES = table.length;
        final int COLUMNS = table[0].length;

        int countIterations = 0;
        while (!greatSolution(table[0])) {
            System.out.println("\nIteration number: " + (countIterations + 1));
            double baseVariable = 0;
            int columnBase = 0;
//            System.out.println("Great solution? " + greatSolution(table[0]));

            // Determinando a variavel que entrara na base
            for (int j = 0; j < COLUMNS; j++) {
                if (table[0][j] < baseVariable) {
                    baseVariable = table[0][j];
                    columnBase = j;
                    System.out.println("Base variable: " + baseVariable + " and column: " + columnBase);
                }
            }

            // Determinar a linha que sai na base
            double pivotD = table[1][COLUMNS - 1] / table[1][columnBase];
            int lineBase = 1;
            for (int i = 2; i < LINES; i++) {
                double aux = table[i][COLUMNS - 1] / table[i][columnBase];
                System.out.println("Aux: " + aux);
                if (aux < pivotD && aux > 0) {
                    pivotD = aux;
                    lineBase = i;
                }
            }
            System.out.println("Pivot determinant: " + pivotD);

            // Calculando a NLP
            double[] nlp = new double[COLUMNS];
            double pivot = table[lineBase][columnBase];
            for (int j = 0; j < COLUMNS; j++) {
                nlp[j] = table[lineBase][j] / pivot;
            }

            for (int i = 0; i < nlp.length; i++) {
                System.out.print(nlp[i] + "\t");
            }
            System.out.println("\n");

            // Calcular as outras linhas a partir da nova linha pivot
            double[][] newTable = table.clone();
            for (int i = 0; i < LINES; i++) {
                if (i != lineBase) {
                    double[] tempNlp = new double[COLUMNS];
                    for (int k = 0; k < tempNlp.length; k++) {
                        tempNlp[k] = nlp[k] * (table[i][columnBase] * (-1));
                    }
                    for (int j = 0; j < COLUMNS; j++) {
                        newTable[i][j] = table[i][j] + tempNlp[j];
                    }
                } else {
                    newTable[i] = nlp;
                }
            }
            table = newTable;
            System.out.println("New Table:");
            printTable(table);
            countIterations++;
        }
        return table;
    }

}
