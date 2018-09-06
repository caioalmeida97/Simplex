package simplex;

import java.util.Scanner;

public class Simplex {

    public static void main(String[] args) {
//        Scanner s = new Scanner(System.in);
        // z x1 x2 xf1 xf2 xf3 b
        double[][] table = {
            {1, -3, -5, 0, 0, 0, 0},
            {0, 2, 4, 1, 0, 0, 10},
            {0, 6, 1, 0, 1, 0, 20},
            {0, 1, -1, 0, 0, 1, 30}};

        double baseVariable = 0;
        int columnBase = 0;
        //Determinando a variavel que entrara na base
        for (int j = 0; j < table[0].length; j++) {
            if(table[0][j] < baseVariable){
                baseVariable = table[0][j];
                columnBase = j;
                System.out.println("Base variable: " + baseVariable + " and column: " + columnBase);
            }
        }
        
        // Determinar a linha que sai na base
        double pivotD = 9999;
        int lineBase = 0;
        for(int i = 1; i < table.length; i++){         
            double aux = table[i][table[0].length - 1]/table[i][columnBase];
            System.out.println("Aux: " + aux);
            if(aux < pivotD && aux > 0){
                pivotD = aux;
                lineBase = i;
                System.out.println("Pivot determinant: " + pivotD);
            }
        }
        
        // Calculando a NLP
        double[] nlp = new double[table[0].length];
        double pivot = table[lineBase][columnBase];
        for (int j = 0; j < table[lineBase].length; j++) {
            nlp[j] = table[lineBase][j]/pivot;
        }
        
        for (int i = 0; i < nlp.length; i++) {
            System.out.print(nlp[i] + "\t");
        }
        System.out.println();
        
        // Calcular as outras linhas a partir da nova linha pivot
    }

}
