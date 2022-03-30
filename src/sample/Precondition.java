package sample;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static sample.Main.*;
import static sample.Utils.*;

public final class Precondition {
    private final String pathToFile;
    List<String> lettersKeys = new LinkedList<>();

    public Precondition(String pathToFile) {
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

    private void displayPeriods(List<Integer[][]> periodList, Controller controller){
        for (Integer[][] matrix: periodList) {
            Utils.printMatrix(controller, matrix);
        }
    }

    public void start(Controller controller, String action) throws FileNotFoundException {
        List<Integer[][]> periodList;
        String text2Encrypt = Utils.getTextFromDoc(pathToFile);

        displayInfo("Исходная матрица: ", controller);
        Utils.printMatrix(controller, matrix1, matrix2, matrix3);

        displayInfo("Текст для шифрования: " + text2Encrypt +"\n", controller);

        findNumber(text2Encrypt, matrix1, matrix2, matrix3);

        displayInfo("Координата буквы в матрице(номер матрицы - строка - колонка): ", controller);
        Utils.printMatrix(controller, toMatrix(lettersKeys));

        displayInfo("Разбивка матрицы на периоды: ", controller);
        periodList = period(toMatrix(lettersKeys));

        displayPeriods(periodList, controller);

        if ("encrypt".equals(action)) {
            new Encrypt(periodList).encrypt(controller);
        }
    }}

