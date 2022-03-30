package sample;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static sample.Main.*;
import static sample.Utils.*;

public final class Encrypt {
    List<String> lettersKeys = new LinkedList<>();
    List<Integer[][]> periodList;

    public Encrypt(List<Integer[][]> periodList) {
        this.periodList = periodList;
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

    private String buildEncryptedText(Controller controller, List<Integer[]> numbersFromPeriod){
        StringBuilder encryptedString = new StringBuilder();
        for (Integer[] arr: numbersFromPeriod) {
            displayInfo(Arrays.toString(arr).replaceAll("[^a-zA-Z0-9_-]", ""), controller);
            String str = (Encrypt.numbersToWord(arr).toString().replaceAll("[^a-zA-Z0-9_-]", ""));
            displayInfo(str, controller);
            encryptedString.append(str).append(" ");
        }
        displayInfo("Зашифрованный текст: " + encryptedString, controller);
        return encryptedString.toString();
    }

    public void encrypt(Controller controller) throws FileNotFoundException {
        displayInfo("Читаем цифры из периодов(горизонтально) и находим соответсвующие буквы в изначальных матрицах", controller);
        List<Integer[]> numbersFromPeriod = matrixListToArrList(periodList);
        saveEncryptedText(buildEncryptedText(controller, numbersFromPeriod), controller);
    }
}
