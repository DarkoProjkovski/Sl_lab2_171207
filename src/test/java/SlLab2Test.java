import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SlLab2Test {

    public List<Time> createList(Time... elems){
        return new ArrayList<>(Arrays.asList(elems));
    }
    public List<Integer> createListInt(Integer... elems){
        return new ArrayList<>(Arrays.asList(elems));
    }

    @Test
    void functionEveryPath() {
        RuntimeException ex;

        //1,2,3.1,3.2,4,5,6,7,8,9,33
        ex=assertThrows(RuntimeException.class, () -> SlLab2.function(createList(new Time(-1,2,3))));
        assertTrue(ex.getMessage().contains("The hours are smaller than the minimum"));

        //1,2,3.1,3.2,4,5,6,7,8,11,12,33
        ex=assertThrows(RuntimeException.class, () -> SlLab2.function(createList(new Time(25,2,3))));
        assertTrue(ex.getMessage().contains("The hours are grater than the maximum"));

        //1,2,3.1,3.2,4,5,6,7,15,16,17,33
        ex=assertThrows(RuntimeException.class, () -> SlLab2.function(createList(new Time(12,-1,3))));
        assertTrue(ex.getMessage().contains("The minutes are not valid!"));

        //1,2,3.1,3.2,(4,5,6,7,15,16,18,19,20,23,24,31,3.3,3.2),32,33
        assertEquals(createListInt(43810,1),SlLab2.function(createList(new Time(12,10,10),new Time(0,0,1))));

        //1,2,3.1,3.2,4,5,6,7,15,16,18,19,21,22,33
        ex=assertThrows(RuntimeException.class, () -> SlLab2.function(createList(new Time(12,10,-1))));
        assertTrue(ex.getMessage().contains("The seconds are not valid"));

        //1,2,3.1,3.2,(4,5,6,7,15,25,26,27,31,3.3,3.2),32,33
        assertEquals(createListInt(86400),SlLab2.function(createList(new Time(24,0,0))));

        //1,2,3.1,3.2,4,5,6,7,,15,25,28,29,33
        ex=assertThrows(RuntimeException.class, () -> SlLab2.function(createList(new Time(24,59,59))));
        assertTrue(ex.getMessage().contains("The time is greater than the maximum"));
    }

    @Test
    void functionMultipleCondition(){
        RuntimeException ex;
        //(hr < 0 || hr > 24)
        //T || X
        ex=assertThrows(RuntimeException.class, () -> SlLab2.function(createList(new Time(-1,2,3))));
        assertTrue(ex.getMessage().contains("The hours are smaller than the minimum"));

        //F || T
        ex=assertThrows(RuntimeException.class, () -> SlLab2.function(createList(new Time(25,2,3))));
        assertTrue(ex.getMessage().contains("The hours are grater than the maximum"));
        //F || F
        assertEquals(createListInt(43810),SlLab2.function(createList(new Time(12,10,10))));

        //(min < 0 || min > 59)
        //T || X
        ex=assertThrows(RuntimeException.class, () -> SlLab2.function(createList(new Time(12,-1,10))));
        assertTrue(ex.getMessage().contains("The minutes are not valid!"));

        //F || T
        ex=assertThrows(RuntimeException.class, () -> SlLab2.function(createList(new Time(12,61,10))));
        assertTrue(ex.getMessage().contains("The minutes are not valid!"));
        //F || F
        assertEquals(createListInt(43810),SlLab2.function(createList(new Time(12,10,10))));

        //(hr == 24 && min == 0 && sec == 0)
        //F && X && X ne vozmozen slucaj ako se dade broj pogolem ili pomal od 24 ke se spravi vo (hr < 0 || hr > 24) ako se dade broj megju 0 i 24 togas e validno vreme
//        ex=assertThrows(RuntimeException.class, () -> SlLab2.function(createList(new Time(25,10,10))));
//        assertTrue(ex.getMessage().contains("The time is greater than the maximum"));

        //T && F && x
        ex=assertThrows(RuntimeException.class, () -> SlLab2.function(createList(new Time(24,10,10))));
        assertTrue(ex.getMessage().contains("The time is greater than the maximum"));

        //T && T && F
        ex=assertThrows(RuntimeException.class, () -> SlLab2.function(createList(new Time(24,0,10))));
        assertTrue(ex.getMessage().contains("The time is greater than the maximum"));

        //T && T && T
        assertEquals(createListInt(86400),SlLab2.function(createList(new Time(24,0,0))));
    }
}