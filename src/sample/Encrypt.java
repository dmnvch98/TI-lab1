package sample;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static sample.Main.*;
import static sample.Utils.nextNumber;

public final class Encrypt {
    private final String pathToFile;
    List<String> lettersKeys = new LinkedList<>();

    public Encrypt(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    @SafeVarargs
    private <T> void findNumber(String phrase, T[][]... args){
        char[] phraseCharArr = phrase.toCharArray();
        for (char element: phraseCharArr) {
            find1(element, args);
        }
    }

    @SafeVarargs
    private <T> void find1(char letter, T[][]... args){
        boolean isFound = false;
        int iteration = 1;
        if (args.length != 0){
            for (T[][] element: args){
                if (!isFound){
                    isFound = find2(letter, iteration, element);
                    iteration++;
                } else return;
            }
        }
    }

    private <T> boolean find2(char letter, int iteration, T[][] matrix){
        int columns = matrix[0].length;
        int rows = matrix.length;
        for(int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if (matrix[i][j].equals(letter)) {
                    lettersKeys.add(("" + iteration + (i + 1) + (j + 1)));
                    return true;
                }
            }
        }
        return false;
    }

    private List<Integer[][]> period(Integer[][] matrix){
        List<Integer[][]> result = new LinkedList<>();
        int arrSize = matrix[0].length;
        for (int i = 0; i < arrSize; i+=5){
            Integer[][] tempMatrix = new Integer[3][nextNumber(i, arrSize)];
            for (int j = 0; j < 3; j++){
                tempMatrix[j] = Arrays.copyOfRange(matrix[j],i,nextNumber(i, arrSize));
            }
            result.add(tempMatrix);
        }
        return result;
    }

    private Integer[][] toMatrix(List<String> list){
        Integer[][] arr = new Integer[3][list.size()];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < list.size(); j++) {
                arr[i][j] = Character.getNumericValue(list.get(j).toCharArray()[i]);
            }
        }
        return arr;
    }

    private List<Integer[]> matrixListToArrList(List<Integer[][]> list){
        List<Integer[]> result = new ArrayList<>();
        for (Integer[][] el: list) {
            Integer[] tempArr = Stream.of(el).flatMap(Stream::of).toArray(Integer[]::new);
            result.add(tempArr);
        }
        return result;
    }

    private static List<String> numbersToWord(Integer[] arr){
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

    private static Character getLetter(int matrixNumber, int rowNumber, int columnNumber){
        return switch (matrixNumber) {
            case 1 -> matrix1[rowNumber][columnNumber];
            case 2 -> matrix2[rowNumber][columnNumber];
            case 3 -> matrix3[rowNumber][columnNumber];
            default -> ' ';
        };
    }

    public void encrypt(Controller controller) throws FileNotFoundException {
        StringBuilder textToDisplay = new StringBuilder();
        List<Integer[][]> periodList;
        String text2Encrypt = Utils.getTextFromDoc(pathToFile);

//        controller.setTextArea("sss");
        textToDisplay.append("Исходная матрица: \n");
        controller.setTextArea(textToDisplay.toString());
//        System.out.println("Исходная матрица: ");
        Utils.printMatrix(matrix1, matrix2, matrix3);

        System.out.println("Текст для шифрования: " + text2Encrypt +"\n");

        findNumber(text2Encrypt, matrix1, matrix2, matrix3);

        System.out.println("Координа буквы в матрице(номер матрицы - строка - колонка): ");
        Utils.printMatrix(toMatrix(lettersKeys));

        System.out.println("Разбивка матрицы на периоды: ");
        periodList = period(toMatrix(lettersKeys));
        for (Integer[][] matrix: periodList) {
            Utils.printMatrix(matrix);
        }

        System.out.println("Читаем цифры из периодов(горизонтально) и находим соответсвующие буквы в изначальных матрицах");
        List<Integer[]> numbersFromPeriod = matrixListToArrList(periodList);
        StringBuilder encryptedString = new StringBuilder();
        for (Integer[] arr: numbersFromPeriod) {
            System.out.println(Arrays.toString(arr).replaceAll("[^a-zA-Z0-9_-]", ""));
            String str = (Encrypt.numbersToWord(arr).toString().replaceAll("[^a-zA-Z0-9_-]", ""));
            System.out.println(str);
            encryptedString.append(str).append(" ");
        }

        try(FileWriter writer = new FileWriter("encrypted text.txt", false)){
            writer.write(String.valueOf(encryptedString));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
