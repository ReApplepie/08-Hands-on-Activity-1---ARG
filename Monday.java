import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

interface Alarm {
    void setAlarm(String time);
    void showAlarm();
}

abstract class Weekday implements Alarm {
    String time;

    @Override
    public void setAlarm(String time) {
        this.time = time;
    }
}

public class Monday extends Weekday {
    private static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Manila");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

    @Override
    public void showAlarm() {
        try {
            LocalTime alarm = LocalTime.parse(time);
            LocalTime currentTime = ZonedDateTime.now(DEFAULT_ZONE).toLocalTime();
            System.out.println("\nSystem Time Zone: " + ZoneId.systemDefault());
            System.out.println("Alarm Time Zone: " + DEFAULT_ZONE);
            System.out.println("Current Time: " + currentTime.format(TIME_FORMATTER));
            System.out.println("Alarm Time: " + alarm.format(TIME_FORMATTER));

            if (alarm.isBefore(currentTime)) {
                System.out.println("\nAlarm is set for tomorrow!");
            } else {
                System.out.println("\nI'll wake you up later!");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Error: Failed to parse the alarm time. Please use the HH:MM format.\n");
        }
    }

    public static void main(String[] args) {
        Monday monday = new Monday();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter time for alarm in this format (HH:MM): ");
            String alarmTime = validateTimeInput(scanner);
            monday.setAlarm(alarmTime);
            monday.showAlarm();
        }
    }

    private static String validateTimeInput(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();

            if (input.matches("\\d{2}:\\d{2}")) {
                try {
                    String[] timeParts = input.split(":");
                    int hours = Integer.parseInt(timeParts[0]);
                    int minutes = Integer.parseInt(timeParts[1]);

                    if (hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60) {
                        return input;
                    } else {
                        System.out.println("Invalid time! Hours should be between 00 and 23, and minutes between 00 and 59.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter numeric values for hours and minutes.");
                }
            } else {
                System.out.println("Invalid format! Please enter the time in HH:MM format (e.g., 07:30).");
            }

            System.out.print("Try again: ");
        }
    }
}
