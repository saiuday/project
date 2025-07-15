package utility;

import java.time.LocalTime;
import java.util.Set;

public enum TimeSlot {
    T10_00("10:00"),
    T12_00("12:00"),
    T14_00("14:00"),
    T16_00("16:00"),
    T18_00("18:00"),
    T20_00("20:00");

    private final String label;

    TimeSlot(String label) {
        this.label=label;
    }

    public String getLabel() {
        return label;
    }
    public static boolean isValid(String input){
        for(TimeSlot slot: values()){
            if (slot.label.equals(input)){
                return true;
            }
        }
        return false;
    }

    public static TimeSlot fromLabel(String input){
        for(TimeSlot slots:values()){
            if(slots.label.equals(input)){
                return slots;
            }
        }
        throw new IllegalArgumentException("Invalid Time"+input);
    }
}
