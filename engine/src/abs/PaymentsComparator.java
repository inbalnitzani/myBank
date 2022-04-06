package abs;

import java.util.Comparator;

public class PaymentsComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Payment payment1 = (Payment) o1;
        Payment payment2 = (Payment) o2;
        if (payment1.getAmount() >= payment2.getAmount())
            return 1;
        else return 0;

    }
}
