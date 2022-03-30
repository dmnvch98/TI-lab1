package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static sample.Main.*;

public final class Utils {
    public static String getTextFromDoc(String path) throws FileNotFoundException {
        File doc = new File(path);
        Scanner obj = new Scanner(doc);
        StringBuilder entryFile = new StringBuilder();
        while (obj.hasNextLine())
            entryFile.append(obj.nextLine());
        return entryFile.toString();
    }

    public static void saveEncryptedText(String encryptedString, Controller controller){
        String fileName = "результат.txt";
        try(FileWriter writer = new FileWriter(fileName, false)){
            writer.write(String.valueOf(encryptedString));
            displayInfo("Файл с результатом сохранен в " + fileName, controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SafeVarargs
    public static <T> void printMatrix( Controller controller, T[][]... args){
        if (args.length != 0){
            for (T[][] element: args){
                print(controller, element);
            }
        }
    }

    private static <T> void print(Controller controller, T[][] matrix){
        int columns = matrix[0].length;
        int rows = matrix.length;
        for(int i = 0; i < rows; i ++){
            for (int j = 0; j < columns; j++){
                System.out.print(matrix[i][j] + " ");
                controller.setTextArea(matrix[i][j] + " ");
            }
            System.out.println();
            controller.setTextArea("\n");
        }
        System.out.println();
        controller.setTextArea("\n");
    }

    public static int nextNumber(int i, int size){
        int period = 5;
        int dd = size - i;
        if (dd < period){
            return size;
        }
        return i+5;
    }

    static public void displayInfo(String text, Controller controller){
        System.out.println(text);
        controller.setTextArea(text + "\n");
    }

    public static  List<String> numbersToWord(Integer[] arr){
        List<String> result = new LinkedList<>();
        List<Character> charList = new LinkedList<>();
        for (int i = 0; i < arr.length; i+=3){
            int matrixNumber = arr[i];
            int rowNumber = arr[i+1];
            int columnNumber = arr[i+2];
            charList.add(getLetter(matrixNumber,rowNumber-1,columnNumber-1));
        }
        result.add(charList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining())
        );
        return result;
    }

    public static  Character getLetter(int matrixNumber, int rowNumber, int columnNumber){
        return switch (matrixNumber) {
            case 1 -> matrix1[rowNumber][columnNumber];
            case 2 -> matrix2[rowNumber][columnNumber];
            case 3 -> matrix3[rowNumber][columnNumber];
            default -> ' ';
        };
    }

    public static String buildEncryptedText(Controller controller, List<Integer[]> numbersFromPeriod){
        StringBuilder encryptedString = new StringBuilder();
        for (Integer[] arr: numbersFromPeriod) {
            displayInfo(Arrays.toString(arr).replaceAll("[^a-zA-Z0-9_-]", ""), controller);
            String str = (numbersToWord(arr).toString().replaceAll("[^a-zA-Z0-9_-]", ""));
            displayInfo(str, controller);
            encryptedString.append(str).append(" ");
        }
        displayInfo("Результат: " + encryptedString, controller);
        return encryptedString.toString();
    }

}
