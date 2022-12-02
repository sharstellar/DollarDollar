package persistence;

import model.Event;
import model.EventLog;
import model.Transaction;
import model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventLogTest {
    private Event e1;
    private Event e2;
    private Transaction t1;
    private Transaction t2;

    @BeforeEach
    public void loadEvents() {
        t1 = new Transaction(TransactionType.INCOME, 100);
        t2 = new Transaction(TransactionType.EXPENSE, 300);
        e1 = new Event(("Added Transaction: " + t1.getTransactionType()
                + " $" + t1.getTransactionAmount()));
        e2 = new Event(("Deleted Transaction: " + t2.getTransactionType()
                + " $" + t2.getTransactionAmount()));
        EventLog el = EventLog.getInstance();
        el.logEvent(e1);
        el.logEvent(e2);
    }

    @Test
    public void testLogEvent() {
        List<Event> l = new ArrayList<Event>();

        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }

        assertTrue(l.contains(e1));
        assertTrue(l.contains(e2));
    }

    @Test
    public void testClear() {
        EventLog el = EventLog.getInstance();
        el.clear();
        Iterator<Event> itr = el.iterator();
        assertTrue(itr.hasNext());   // After log is cleared, the clear log event is added
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }
}
