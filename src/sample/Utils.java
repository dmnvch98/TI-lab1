package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class Utils {
    public static String getTextFromDoc(String path) throws FileNotFoundException {
        File doc = new File(path);
        Scanner obj = new Scanner(doc);
        StringBuilder entryFile = new StringBuilder();
        while (obj.hasNextLine())
            entryFile.append(obj.nextLine());
        return entryFile.toString();
    }

    @SafeVarargs
    public static <T> void printMatrix(T[][]... args){
        if (args.length != 0){
            for (T[][] element: args){
                print(element);
            }
        }
    }

    private static <T> void print(T[][] matrix){
        int columns = matrix[0].length;
        int rows = matrix.length;
        for(int i = 0; i < rows; i ++){
            for (int j = 0; j < columns; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static int nextNumber(int i, int size){
        int period = 5;
        int dd = size - i;
        if (dd < period){
            return size;
        }
        return i+5;
    }

}
