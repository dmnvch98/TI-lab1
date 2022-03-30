package sample;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static sample.Utils.*;

public class Decrypt {
    List<Integer[][]> periodList;

    public Decrypt(List<Integer[][]> periodList) {
        this.periodList = periodList;
    }

    private List<List<Integer>> matrixListToArrList(List<Integer[][]> list){
        List<List<Integer>> result = new ArrayList<>();
        for (Integer[][] el: list) {
            List<Integer> tempList = new LinkedList<>();
            for (int i = 0; i < el[0].length; i++){
                for (int j = 0 ; j < 3; j++){
                    tempList.add(el[j][i]);
                }
            }
            result.add(tempList);
        }
        return result;
    }

    private List <Integer[][]> decryptToMatrix(List<List<Integer>> list){
        List <Integer[][]> result = new LinkedList<>();
        for (List<Integer> el: list) {
            int columns = el.size()/3;
            int counter = 0;
            Integer[][] tempArr = new Integer[3][columns];
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < columns; j++){
                    tempArr[i][j] = el.get(counter++);
                }
            }
            result.add(tempArr);
        }
        return result;
    }

    public void decrypt(Controller controller){
        System.out.println("Читаем цифры из периодов(горизонтально) и находим соответсвующие буквы в изначальных матрицах");
        List<List<Integer>> numbersFromPeriod = matrixListToArrList(periodList);
        for (List<Integer> el: numbersFromPeriod) {
            displayInfo(el.toString(), controller);
        }
        List<List<Integer>> periodsNumbersDecrypt = matrixListToArrList(decryptToMatrix(numbersFromPeriod));
        List<Integer[]> forNextMethod = new LinkedList<>();
        forNextMethod.add(periodsNumbersDecrypt.stream()
                .flatMap(List::stream).toArray(Integer[]::new));
        saveEncryptedText(buildEncryptedText(controller, forNextMethod), controller);

    }
}
