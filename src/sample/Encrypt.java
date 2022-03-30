package sample;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static sample.Utils.*;

public final class Encrypt {
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

    public void encrypt(Controller controller) throws FileNotFoundException {
        displayInfo("Читаем цифры из периодов(горизонтально) и находим соответсвующие буквы в изначальных матрицах", controller);
        List<Integer[]> numbersFromPeriod = matrixListToArrList(periodList);
        saveEncryptedText(buildEncryptedText(controller, numbersFromPeriod), controller);
    }
}
