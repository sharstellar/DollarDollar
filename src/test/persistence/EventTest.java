package persistence;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    private Event testEvent;
    private Date testDate;

    @BeforeEach
    public void runBefore() {
        testEvent = new Event("Saved current transaction list");
        testDate = Calendar.getInstance().getTime();
    }

//    @Test
//    public void testEvent() {
//        assertEquals("Saved current transaction list", testEvent.getDescription());
//        assertEquals(testDate, testEvent.getDate());
//    }

    @Test
    public void testToString() {
        assertEquals(testDate.toString() + "\n" + "Saved current transaction list",
                testEvent.toString());
    }
}
