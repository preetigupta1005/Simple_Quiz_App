import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class SimpleApp {

    private static final HashMap<String, String> userData = new HashMap<>();
    private static String loggedInUser = null;
    private static boolean sessionActive = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        userData.put("admin", "password"); // Default user for testing

        while (true) {
            System.out.println("\n--- Welcome to the Simple App ---");
            if (loggedInUser == null) {
                System.out.println("1. Login\n2. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline

                if (choice == 1) {
                    login(scanner);
                } else if (choice == 2) {
                    System.out.println("Exiting... Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("1. Update Profile/Password\n2. Take Quiz\n3. Logout");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline

                if (choice == 1) {
                    updateProfile(scanner);
                } else if (choice == 2) {
                    takeQuiz(scanner);
                } else if (choice == 3) {
                    logout();
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        }
        scanner.close();
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userData.containsKey(username) && userData.get(username).equals(password)) {
            loggedInUser = username;
            sessionActive = true;
            System.out.println("Login successful. Welcome, " + loggedInUser + "!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void updateProfile(Scanner scanner) {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        userData.put(loggedInUser, newPassword);
        System.out.println("Password updated successfully.");
    }

    private static void takeQuiz(Scanner scanner) {
        String[] questions = {
            "What is the capital of France?\n1. Berlin\n2. Madrid\n3. Paris\n4. Rome",
            "What is 2 + 2?\n1. 3\n2. 4\n3. 5\n4. 6"
        };
        int[] answers = {3, 2};

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time is up! Auto-submitting your answers.");
                sessionActive = false;
                logout();
                System.exit(0);
            }
        };

        timer.schedule(task, 60000); // 60 seconds timer

        int score = 0;
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            System.out.print("Your answer: ");
            int answer = scanner.nextInt();

            if (answer == answers[i]) {
                score++;
            }
        }
        timer.cancel();
        System.out.println("Quiz completed. Your score: " + score + "/" + questions.length);
    }

    private static void logout() {
        System.out.println("Logging out...");
        loggedInUser = null;
        sessionActive = false;
    }
}
