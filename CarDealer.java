import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/*
 * Carreon, Jaycen
 * Jimenez, Graciella
 * Marquez, Jian Kalel
 * Parejas, Arron
 */


public class CarDealer {
    public static void main(String[] args) {

        try {
            FileWriter addname = new FileWriter("addedcars.csv", true);
            FileWriter deletedname = new FileWriter("deleteddata.csv", true);
            FileWriter amountname = new FileWriter("amountData.csv", true);

            Scanner scan = new Scanner(System.in);
            ClassCar car = new ClassCar();

            addname.append("ADDED CARS DATA\n");
            addname.flush();
            deletedname.append("DELETED CARS DATA\n");
            deletedname.flush();
            amountname.append("AMOUNT CARS DATA\n");
            amountname.flush();

            System.out.println("\n\tWelcome to the Dealership's LogBook!");

            while (true) {
                System.out.println("--------------------  Menu ----------------------");
                System.out.println("[1] View Available Cars");
                System.out.println("[2] Add a Car");
                System.out.println("[3] Delete a Car");
                System.out.println("[4] Edit Details");
                System.out.println("[5] Exit");
                System.out.print("\nEnter a number: ");

                int option = 0;

                try {
                    // Check if input is an integer
                    if (scan.hasNextInt()) {
                        option = scan.nextInt();
                        scan.nextLine();
                    } else {
                        System.out.println("Invalid input! Please enter a correct number.");
                        scan.nextLine();
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid option!");
                    scan.nextLine(); // Consume the invalid input
                    continue;
                }

                switch (option) {
                    case 1:
                        car.view();
                        car.brake();
                        break;
                    case 2:
                        car.add();
                        System.out.println("\n---- Updated List of Available Cars ----");
                        car.view();
                        car.brake();
                        break;
                    case 3:
                        car.del();
                        System.out.println("\n---- Updated List of Available Cars ----");
                        car.view();
                        car.brake();
                        break;
                    case 4:
                        car.editprice();
                        car.brake();
                        break;
                    case 5:
                        car.clearAllData();
                        System.out.println("\nThank You for Using Our Program!\n");
                        System.exit(0);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
