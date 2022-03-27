package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    public static void saveEncryptedText(String encryptedString, Controller controller){
        String fileName = "Зашифрованный текст.txt";
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

    static int nextNumber(int i, int size){
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

}
