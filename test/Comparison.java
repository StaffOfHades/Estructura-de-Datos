package test;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by mauriciog on 9/14/16.
 */
public class Comparison {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int cont = 1;

        while (cont == 1) {
            System.out.println("Damos el tamaño de los arreglos");
            int number = scanner.nextInt();
            System.out.println("Arreglo 1");

            int[] array1 = new int[number];
            int[] array2 = new int[number];

            for (int i = 0; i < number; i++) {
                System.out.println("Dato " + i + ": ");
                array1[i] = scanner.nextInt();
            }

            System.out.println("Arreglo 2");

            for (int i = 0; i < number; i++) {
                System.out.println("Dato " + i + ": ");
                array2[i] = scanner.nextInt();
            }

            System.out.println("Los arreglos" + (compareInt(array1, array2) ? " " : " no ") + "son iguales");


            System.out.println("\n Desea Continuar? 1 == Si");
            cont = scanner.nextInt();
        }

    }

    public static boolean compareInt(int[] arreglo1, int[] arreglo2) {
        for (int i = 0; i < arreglo1.length; i++) {
            if (arreglo1[i] != arreglo2[i]) {
                return false;
            }
        }

        return true;
    }
}
