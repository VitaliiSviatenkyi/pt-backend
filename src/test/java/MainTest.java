import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    private static List<Main.Node> list;

    @BeforeAll
    public static void setUpList() {
        list = new ArrayList<>();
        list.add(Main.node(1));
        list.add(Main.node(2, new Main.Node[]{Main.node(3), Main.node(4)}));
        list.add(Main.node(5, new Main.Node[]{Main.node(6, new Main.Node[]{Main.node(7)})}));
    }

    @Test
    void getMeanValue() {
        //given
        double expected = 4.0;

        //when
        double actual = Main.getMeanValue(list);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void getMeanValueLessThanZero() {
        //given
        List<Main.Node> listLessZero = new ArrayList<>();
        listLessZero.add(Main.node(-5));
        listLessZero.add(Main.node(-10, new Main.Node[]{Main.node(-15)}));
        double expected = 0.0;

        //when
        double actual = Main.getMeanValue(listLessZero);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void getMeanValueByDepth() {
        //given
        double expected = 3.62;

        //when
        double actual = Main.getMeanValueByDepth(list);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void getMeanValueByDepthLessThanZero() {
        //given
        List<Main.Node> listLessZero = new ArrayList<>();
        listLessZero.add(Main.node(-5));
        listLessZero.add(Main.node(-10, new Main.Node[]{Main.node(-15)}));
        double expected = 0.0;

        //when
        double actual = Main.getMeanValueByDepth(listLessZero);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void calculateResult() {
        //given
        //when
        Main.Result actual = Main.calculateResult(list);

        //then
        assertEquals(28, actual.getSum());
        assertEquals(7, actual.getCount());
    }

    @Test
    void calculateResultByDepth() {
        //given
        double expectedSum = 25.37;
        double expectedCount = 7;

        //when
        Main.Result actual = Main.calculateResultByDepth(list, 0);

        //then
        assertEquals(expectedSum, actual.getSum());
        assertEquals(expectedCount, actual.getCount());
    }

    @ParameterizedTest
    @CsvSource({
            "3.62, 3.62110",
            "4.73, 4.72999",
            "0.00, 0.0004"
    })
    void roundToHundredths(double expected, double given) {
        //given
        //when & then
        assertEquals(expected, Main.roundToHundredths(given));
    }
}