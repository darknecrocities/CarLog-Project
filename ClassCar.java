import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

class Car {
    private int yearModel;
    private String model;
    private double price;
    private int mileage;
    private String transmission;

    public Car(String model, int yearModel, String transmission, int mileage, double price) {
        this.model = model;
        this.yearModel = yearModel;
        this.transmission = transmission;
        this.mileage = mileage;
        this.price = price;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public int getYearModel() {
        return yearModel;
    }
    public void setYearModel(int yearModel) {
        this.yearModel = yearModel;
    }

    public String getTransmission() {
        return transmission;
    }
    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public int getMileage() {
        return mileage;
    }
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override  // i put this overdive to become template when its printing in view car method and also in HashMap :)
    public String toString() {
        return "Model: " + model + "\n\n\t- Model Year : " + yearModel
                                 + "\n\t- Transmission : " + transmission
                                 + "\n\t- Mileage : " + mileage + " miles"
                                 + "\n\t- Price : $" + price + "\n";
    }
}


class ClassCar {
    Scanner scan = new Scanner(System.in);
    private HashMap<String, ArrayList<Car>> carMap = new HashMap<>();
    
    public ClassCar() {
        carMap.put("Ford", new ArrayList<>(Arrays.asList(
                new Car("Raptor",2022, "A/T",  35000, 10000),
                new Car("Ranger" ,2023, "M/T", 40000, 15000),
                new Car("Fiesta", 2021, "A/T", 30000, 5000))));
        carMap.put("Honda", new ArrayList<>(Arrays.asList(
                new Car("Civic", 2022, "A/T", 0, 25000),
                new Car( "City", 2023, "M/T", 8000, 27000.0),
                new Car("HR-V", 2021,  "A/T", 25000, 23000.0))));
        carMap.put("Toyota", new ArrayList<>(Arrays.asList(
                new Car("Rush",2022, "A/T", 5363,  28000.0),
                new Car("Vios",2023, "M/T", 7363, 30000.0),
                new Car("Hilux", 2021, "A/T", 6009,  26000.0))));
        carMap.put("Mitsubishi", new ArrayList<>(Arrays.asList(
                new Car("Mirage", 2022, "A/T", 42000, 22000.0 ),
                new Car("Adventure", 2023,"M/T", 56740,  24000.0),
                new Car("Tamaraw", 2021, "A/T",60394, 20000.0 ))));
    }
    

    //[1] VIEW
    public void view() {
        for (Map.Entry<String, ArrayList<Car>> entry : carMap.entrySet()) {
            int i = 1;
            System.out.println(entry.getKey() + ":\n");

            for (Car car : entry.getValue()) {
                System.out.println("  "+(i++)+") "+car);
            }
        }
    }

    //[2] ADD
    public String add() {
    System.out.println("\nAdding a Car\nPlease enter the details below");
    Scanner scan = new Scanner(System.in);
    boolean flagger = true;
    List<Car> newlyAddedCars = new ArrayList<>(); // Declare newlyAddedCars list

    while (flagger) {
        int yearModel;
        String transmission;
        int mileage;
        double price;

        // CAR BRAND
        System.out.print("\nCar Brand: ");
        String brand = scan.nextLine().toLowerCase();
        String brand_caps = brand.substring(0, 1).toUpperCase() + brand.substring(1);
        brand = brand_caps;

        if (!carMap.containsKey(brand)) {
            carMap.put(brand, new ArrayList<>());
        } else {
            System.out.println("\n" + brand + " already exists!\n");
        }

        // CAR MODEL
        System.out.print("Car Model: ");
        String model = scan.next();
        String model_caps = model.substring(0, 1).toUpperCase() + model.substring(1);
        model = model_caps;

        // MODEL YEAR
        while (true) {
            try {
                System.out.print("Model Year: ");
                yearModel = scan.nextInt();
                scan.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid year.");
                scan.nextLine();
            }
        }

        // TRANSMISSION
        while (true) {
            System.out.print("Transmission Type [AT or MT]: ");
            transmission = scan.nextLine().toUpperCase();

            if (transmission.equals("AT") || transmission.equals("A/T")) {
                transmission = "A/T";
                break;
            } else if (transmission.equals("MT") || transmission.equals("M/T")) {
                transmission = "M/T";
                break;
            } else {
                System.out.println("Invalid transmission type! Please enter 'AT' or 'MT'.");
            }
        }

        // PRICE
        while (true) {
            try {
                System.out.print("Price: ");
                price = scan.nextDouble();
                scan.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid price.");
                scan.nextLine();
            }
        }

        // MILEAGE
        while (true) {
            try {
                System.out.print("Mileage: ");
                mileage = scan.nextInt();
                scan.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid mileage.");
                scan.nextLine();
            }
        }

        // Add the car to the map
        Car newCar = new Car(model, yearModel, transmission, mileage, price);
        carMap.get(brand).add(newCar);
        newlyAddedCars.add(newCar); // Add newly added car to the list

        System.out.println("Brand and Model Status: Created");

        // Prompt user to add another car
        System.out.print("\nDo you want to add another car? (Y/N) ");
        String addAnotherCar = scan.nextLine().toUpperCase();
        if (!addAnotherCar.equals("Y")) {
            flagger = false;
        }
    }

    // Save newly added car details to CSV file
    try {
        FileWriter csvWriter = new FileWriter("addedcars.csv", true); // Append mode
    
        for (Car car : newlyAddedCars) {
            String brand = ""; // Initialize brand variable
            for (Map.Entry<String, ArrayList<Car>> entry : carMap.entrySet()) {
                if (entry.getValue().contains(car)) {
                    brand = entry.getKey(); // Get the brand from the carMap
                    break;
                }
            }
            
            // Write car details to the CSV file
            csvWriter.append(brand).append(",");
            csvWriter.append(car.getModel()).append(",");
            csvWriter.append(Integer.toString(car.getYearModel())).append(",");
            csvWriter.append(car.getTransmission()).append(",");
            csvWriter.append(Integer.toString(car.getMileage())).append(",");
            csvWriter.append(Double.toString(car.getPrice())).append("\n");
        }
    
        csvWriter.flush();
        csvWriter.close();
    } catch (IOException e) {
        System.out.println("Error while writing to carData.csv: " + e.getMessage());
        }
    return null;
    }

    //[3] DELETE
    public void del() {
    Scanner scan = new Scanner(System.in);

    System.out.println("\nDeleting a Car\nEnter the details below");

    boolean flagger = true;

    while (flagger) {
        System.out.print("\nBrand: ");
        String brand = scan.nextLine().toLowerCase();

        String correctedBrand = brand.substring(0, 1).toUpperCase() + brand.substring(1);

        if (carMap.containsKey(correctedBrand)) {
            ArrayList<Car> cars = carMap.get(correctedBrand);

            // Display available cars for the specified brand
            System.out.println("\nAvailable cars in " + correctedBrand + ":\n");
            for (Car car : cars) {
                System.out.println(car);
            }

            while (true) {
                // Prompt user to enter the model of the car to delete
                System.out.print("\nEnter the car model to delete: ");
                String modelToDelete = scan.nextLine();

                // Iterate over the cars for the specified brand and delete the specified car if found
                boolean found = false;
                Iterator<Car> iterator = cars.iterator();

                while (iterator.hasNext()) {
                    Car car = iterator.next();
                    if (car.getModel().equalsIgnoreCase(modelToDelete)) {
                        // Remove the car from the carMap
                        iterator.remove();
                        System.out.println(correctedBrand+ " " + (modelToDelete.substring(0,1).toUpperCase()+modelToDelete.substring(1)) + " has been deleted from the list");
                        found = true;

                        // Write the deleted car data to 'deleteddata.csv'
                        try {
                            FileWriter csvWriter = new FileWriter("deleteddata.csv", true); // Append mode

                            // Write the deleted car data to the 'deleteddata.csv' file
                            csvWriter.append(correctedBrand).append(",");
                            csvWriter.append(car.getModel()).append(",");
                            csvWriter.append(Integer.toString(car.getYearModel())).append(",");
                            csvWriter.append(car.getTransmission()).append(",");
                            csvWriter.append(Integer.toString(car.getMileage())).append(",");
                            csvWriter.append(Double.toString(car.getPrice())).append("\n");

                            csvWriter.flush();
                            csvWriter.close();
                        } catch (IOException e) {
                            System.out.println("Error writing to deleteddata.csv: " + e.getMessage());
                        }     
                        break;
                    }
                }
                // Notify user if the specified car model was not found
                if (!found) {
                    System.out.println("\n" + (modelToDelete.substring(0,1).toUpperCase()+modelToDelete.substring(1)) + " not found under " + correctedBrand + "! Try Again!");
                } else {
                    break;
                }
            }

            // Prompt user to continue deleting cars or exit
            System.out.print("\nDo you want to delete another car (Y/N)? ");
            String choice = scan.nextLine().toUpperCase();
            if (!choice.equals("Y")) {
                flagger = false;
            }
        } else {
            System.out.println(correctedBrand + " not found in the list");
            }
        }
    }

    //[4] EDIT DETAILS
    public void editprice() {
    Scanner scan = new Scanner(System.in);

    //Prompt user to enter the brand
    System.out.println("\nEditing Car Price\nPlease enter the details below");
    boolean flagger = true;

    while (flagger) {
        System.out.print("\nBrand: ");
        String brand = scan.nextLine().toLowerCase();
        String correctedBrand = brand.substring(0, 1).toUpperCase() + brand.substring(1);

        if (carMap.containsKey(correctedBrand)) {

            ArrayList<Car> cars = carMap.get(correctedBrand);
            System.out.println("\nCar models and its prices under " + correctedBrand);

            // Display available car models and prices for the specified brand
            for (Car car : cars) {
                System.out.println(car.getModel() + " - $" + car.getPrice());
            }

            // Prompt user to enter the model of the car to edit the amount
            System.out.print("\nEnter the model to edit its price: ");
            String model = scan.nextLine();
            boolean found = false;

            for (Car car : cars) {
                if (car.getModel().equalsIgnoreCase(model)) {
                    double newPrice = 0;
                    boolean price_flagger = true;

                    while (price_flagger) {
                        try {
                            System.out.print("Enter " + correctedBrand + " " + model.substring(0, 1).toUpperCase() + model.substring(1) + "\'s new price: ");
                            newPrice = scan.nextDouble();
                            scan.nextLine();

                            if (newPrice <= 0) {
                                System.out.println("\nInvalid price!\n");
                            } else {
                                // Update the price of the specified car model
                                car.setPrice(newPrice);
                                System.out.println("\n" + correctedBrand + " " + model.substring(0, 1).toUpperCase() + model.substring(1) + "'s price has been updated.");
                                found = true;
                                price_flagger = false; // Exit the loop when a valid price is entered
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("\nInvalid input!\n");
                            scan.nextLine(); // Consume the newline character
                        }
                    }

                    // Update the price in the CSV file for this specific car model
                    try {
                        List<String> lines = Files.readAllLines(Paths.get("amountData.csv"));
                        List<String> updatedLines = new ArrayList<>();
                    
                        // Iterate over each line and update the price if it matches the car model
                        for (String line : lines) {
                            String[] parts = line.split(",");
                            if (parts.length >= 6 && parts[0].equalsIgnoreCase(correctedBrand) && parts[1].equalsIgnoreCase(model)) {
                                parts[5] = Double.toString(newPrice); // Update the price
                                line = String.join(",", parts);
                            }
                            updatedLines.add(line);
                        }
                    } catch (IOException e) {
                        System.out.println("Error while updating price in amountData.csv: " + e.getMessage());
                    }
                    

                    // Write the updated amount (price) to amountData.csv
                    try {
                        FileWriter csvWriter = new FileWriter("amountData.csv", true); // Append mode

                        // Write the updated amount (price) to the amountData.csv file
                        csvWriter.append(correctedBrand).append(",");
                        csvWriter.append(model).append(",");
                        csvWriter.append(Double.toString(newPrice)).append("\n");

                        csvWriter.flush();
                        csvWriter.close();
                    } catch (IOException e) {
                        System.out.println("Error while writing to amountData.csv: " + e.getMessage());
                    }
                    
                    // Ask user if they want to change price again
                    System.out.print("\nDo you want to change the price again? (Y/N): ");
                    String choice = scan.nextLine().toLowerCase();

                    if (choice.equals("n")) {
                        // Return to main menu
                        return;
                    } else if (!choice.equals("y")) {
                        System.out.println("Invalid choice. Returning to main menu.");
                        return;
                    }
                    
                    break; // No need to continue searching once the car model is found and updated
                }
            }
            if (!found) {
                System.out.println("Model not found for the specified brand.");
            }
        } else {
            System.out.println("Brand not found.");
            }
        }
    }

    //[5] EXIT
    public void clearAllData() {
        //clears data when exiting
        try {
            // Clear carData.csv
            FileWriter carDataWriter = new FileWriter("addedcars.csv");
            carDataWriter.close();
            
            // Clear deleteddata.csv
            FileWriter deletedDataWriter = new FileWriter("deleteddata.csv");
            deletedDataWriter.close();
            
            // Clear amountData.csv
            FileWriter amountDataWriter = new FileWriter("amountData.csv");
            amountDataWriter.close();
            
        } catch (IOException e) {
            
        }
    }

    // Menu method for GUI in our program
    public int menu() {
        System.out.println("\n--------------------  Menu ----------------------\n");
        System.out.println("[1] View Available Cars\n[2] Add a Car\n[3] Delete a Car\n[4] Edit Details\n[5] Exit");
        System.out.print("\nEnter a number: ");
        return scan.nextInt();
    }
    // brake method to use delay in our program or to confirm the promt input
    public String brake() {
        System.out.print("\nenter to go back menu");
        return scan.nextLine();
    }
}
