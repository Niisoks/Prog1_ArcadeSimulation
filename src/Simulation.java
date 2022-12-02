import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Simulation {

    public static void main(String[] args) throws Exception {

        File inputCustomerData = new File("customers.txt");
        File inputTransactionData = new File("transactions.txt");
        File inputGameData = new File("games.txt");

        Arcade newArcade = initialiseArcade("arcadeName",inputGameData,inputCustomerData);

        simulateFun(newArcade, inputTransactionData);
        for (int i = 0; i < newArcade.getCustomerCollection().size(); i++) {
            System.out.println(newArcade.getCustomerCollection().get(i));
        }
        for (int i = 0; i < newArcade.getArcadeGameCollection().size(); i++) {
            System.out.println(newArcade.getArcadeGameCollection().get(i));
        }

    }

    public static void simulateFun(Arcade arcade, File transactionFile) throws Exception {
        Scanner transactionScanner = new Scanner(transactionFile);
        transactionScanner.useDelimiter("\n");

        Scanner lineScanner;
        String line;

        String customerIntent, customerID, gameID, peakCheck, customerName, customerRole;
        int customerStartBalance, customerAge;
        String placeHolder;
        boolean isPeak;

        Customer currentCustomer;

        while(transactionScanner.hasNext()){
            line = transactionScanner.next();
            lineScanner = new Scanner(line);
            lineScanner.useDelimiter(",");
            customerIntent = lineScanner.next();
            customerID = lineScanner.next();
            if(customerIntent.equals("NEW_CUSTOMER")){
                customerName = lineScanner.next();
                placeHolder = lineScanner.next();
                if(!placeHolder.matches(".*[0-9].*")){
                    customerRole = placeHolder;
                    customerStartBalance = Integer.parseInt(lineScanner.next().trim());
                    customerAge = Integer.parseInt(lineScanner.next().trim());
                } else {
                    customerRole = "STANDARD";
                    customerStartBalance = Integer.parseInt(placeHolder.trim());
                    customerAge = Integer.parseInt(lineScanner.next().trim());
                }
                System.out.println("New customer created " + customerName + " with a balance of " + customerStartBalance + ", welcome! Enjoy your self!");
                currentCustomer = new Customer(customerID, customerName, customerAge, Customer.DiscountType.valueOf(customerRole),customerStartBalance);
               // System.out.println(currentCustomer);
                arcade.addCustomer(currentCustomer);
            }else if(customerIntent.equals("ADD_FUNDS")){
                for (int i = 0; i < arcade.getCustomerCollection().size(); i++) {
                    if(arcade.getCustomerCollection().get(i).getAccountID().equals(customerID)){
                        currentCustomer = arcade.getCustomerCollection().get(i);
                        currentCustomer.addFunds(Integer.parseInt(lineScanner.next().trim()));
                        break;
                    }
                }

            }else{
                gameID = lineScanner.next();
                peakCheck = lineScanner.next();
                isPeak = peakCheck.equals("PEAK");
                System.out.println("Transaction success status: " + arcade.processTransaction(customerID, gameID, isPeak) + ' ' + arcade.getCustomer(customerID).getName());
            }
        }
        Customer richieRich = arcade.findRichestCustomer();
        int[] countedGames = arcade.countArcadeGames();
        System.out.println("The richest customer is ... " + richieRich.getName() + " with a total balance of " + richieRich.getAccountBalance());
        System.out.println("The median game price is ... " + arcade.getMedianGamePrice());
        System.out.println("There are ... " + countedGames[0] + " Cabinet Games, " + countedGames[1] + " Active Games, and " + countedGames[2] + " VR games!");
        Arcade.printCorporateJargon();

        System.out.println("Todays total revenue is... " + (int) arcade.getArcadeRevenue());
    }

    public static Arcade initialiseArcade(String arcadeName, File gamesFile, File customerFile) throws InvalidCustomerException, FileNotFoundException, InvalidGameIDException {
        Arcade currentArcade = new Arcade(arcadeName);

        Scanner customerFileScanner = new Scanner(customerFile);
        customerFileScanner.useDelimiter("\n");


        Scanner lineScanner;
        String line;

        //----------------begin customer file scanning  ------------------------//

        String accountID, name;
        Customer.DiscountType discountType;
        int age, accountBalance;
        Customer currentCustomer;

        while(customerFileScanner.hasNext()){
            line = customerFileScanner.next();       // get the line that we're looking at
            lineScanner = new Scanner(line); // make a new scanner to split up line
            lineScanner.useDelimiter("#");
            accountID = lineScanner.next();
            name = lineScanner.next();
            accountBalance = Integer.parseInt(lineScanner.next().trim());
            age = Integer.parseInt(lineScanner.next().trim());
            if (lineScanner.hasNext()) {
                discountType = Customer.DiscountType.valueOf(lineScanner.next());
            } else {
                discountType = Customer.DiscountType.valueOf("STANDARD");
            }
            lineScanner.close();
            currentCustomer = new Customer(accountID, name, age, discountType, accountBalance);
            currentArcade.addCustomer(currentCustomer);
        }
        customerFileScanner.close();

        //----------------customer file scanning over ------------------------//

        Scanner gamesFileScanner = new Scanner(gamesFile);
        gamesFileScanner.useDelimiter("\n");
        Scanner newlineScanner;

        //----------------begin games file scanning --------------------------//

        String gameID, gameName, gameType, requiredController, canPayReward;
        int pricePerPlay, minAge;
        VirtualRealityGame.RequireControllers requireControllers = null;
        ArcadeGame newGame = null;

        while(gamesFileScanner.hasNext()){
            line = gamesFileScanner.next();       // get the line that we're looking at
            newlineScanner = new Scanner(line); // make a new scanner to split up line
            newlineScanner.useDelimiter("@");
            gameID = newlineScanner.next();
            gameName = newlineScanner.next();
            gameType = newlineScanner.next();
            pricePerPlay = Integer.parseInt(newlineScanner.next().trim());
            switch (gameType) {
                case "virtualReality" -> {
                    minAge = Integer.parseInt(newlineScanner.next().trim());
                    requiredController = newlineScanner.next();
                    switch (requiredController) {
                        case "headsetOnly" -> requireControllers = VirtualRealityGame.RequireControllers.valueOf("HEADSET");
                        case "headsetAndController" -> requireControllers = VirtualRealityGame.RequireControllers.valueOf("HEADSETANDCONTROLLER");
                        case "fullBodyTracking" -> requireControllers = VirtualRealityGame.RequireControllers.valueOf("FULLBODY");
                    }
                    newGame = new VirtualRealityGame(gameName, gameID, pricePerPlay, minAge, requireControllers);
                }
                case "cabinet" -> {
                    canPayReward = newlineScanner.next();
                    boolean rewardPayable = canPayReward.equals("yes");
                    newGame = new CabinetGame(gameName, gameID, pricePerPlay, rewardPayable);
                }
                case "active" -> {
                    minAge = Integer.parseInt(newlineScanner.next().trim());
                    newGame = new ActiveGame(gameName, gameID, pricePerPlay, minAge);
                }
            }
            newlineScanner.close();
            currentArcade.addArcadeGame(newGame);
        }
        gamesFileScanner.close();

        //----------------games file scanning over ------------------------//

        return currentArcade;
    }
}
