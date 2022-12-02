import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Arcade {

    private String arcadeName;
    private double arcadeRevenue;
    private List<ArcadeGame> arcadeGameCollection = new ArrayList<>();
    private List<Customer> customerCollection = new ArrayList<>();


    public String getArcadeName() {
        return arcadeName;
    }

    public void setArcadeName(String arcadeName) {
        this.arcadeName = arcadeName;
    }

    public double getArcadeRevenue() {
        return arcadeRevenue;
    }

    public void setArcadeRevenue(double arcadeRevenue) {
        this.arcadeRevenue = arcadeRevenue;
    }

    public List<ArcadeGame> getArcadeGameCollection() {
        return arcadeGameCollection;
    }

    public void setArcadeGameCollection(List<ArcadeGame> arcadeGameCollection) {
        this.arcadeGameCollection = arcadeGameCollection;
    }

    public List<Customer> getCustomerCollection() {
        return customerCollection;
    }

    public void setCustomerCollection(List<Customer> customerCollection) {
        this.customerCollection = customerCollection;
    }

    public Arcade(String arcadeName) {
        this.arcadeName = arcadeName;
    }

    public void addCustomer(Customer newCustomer){
//        this.customerCollection.add(newCustomer);
        getCustomerCollection().add(newCustomer);
    }

    public void addArcadeGame(ArcadeGame newArcadeGame){
//        this.customerCollection.add(newCustomer);
        getArcadeGameCollection().add(newArcadeGame);
    }

    public Customer getCustomer(String customerID) throws InvalidCustomerException {
        for (int i = 0; i < getCustomerCollection().size(); i++) {
            if(getCustomerCollection().get(i).getAccountID().equals(customerID)){
                return getCustomerCollection().get(i);
            }
        }
        throw new InvalidCustomerException("Error: Customer not found");
    }

    public ArcadeGame getArcadeGame(String gameID) throws InvalidGameIDException {
        for (int i = 0; i < getArcadeGameCollection().size(); i++) {
            if(getArcadeGameCollection().get(i).getSerialNumber().equals(gameID)){
                return getArcadeGameCollection().get(i);
            }
        }
        throw new InvalidGameIDException("Error: Arcade game not found");
    }

    public boolean processTransaction(String customerID, String gameID, boolean peak) {
        try {
            Customer currentCustomer = getCustomer(customerID);
            double customerStartingBalance = currentCustomer.getAccountBalance();
            ArcadeGame currentGame = getArcadeGame(gameID);
            currentCustomer.setAccountBalance(currentCustomer.chargeAccount(currentGame, peak));
            setArcadeRevenue(getArcadeRevenue() + (customerStartingBalance - currentCustomer.getAccountBalance()));
            System.out.println(currentCustomer.getName() + " you have spent " + (currentCustomer.getAccountBalance() - customerStartingBalance) + " on " + currentGame.getGameName() + ", your account balance is now " + currentCustomer.getAccountBalance());
            return true;
        }
        catch (InvalidGameIDException | InvalidCustomerException | InsufficientBalanceException | AgeLimitException e){
            e.printStackTrace(System.out);
            return false;
        }
    }

    public int getMedianGamePrice(){
        int listSize = getArcadeGameCollection().size();
        ArrayList<Integer> tempList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            tempList.add((int)getArcadeGameCollection().get(i).getPricePerPlay());
        }
        int middleValue = listSize/2;
        Collections.sort(tempList);
        if(listSize % 2 == 0){
            int centerValue1 = tempList.get(middleValue-1);
            int centerValue2 = tempList.get(middleValue);
            return (centerValue1 + centerValue2)/2;
        }
        else{
            return tempList.get(middleValue);
        }
    }

    public int[] countArcadeGames(){
        int[] machines = {0, 0, 0};
        for (int i = 0; i < getArcadeGameCollection().size(); i++) {
            switch (getArcadeGameCollection().get(i).getClass().getSimpleName()) {
                case "CabinetGame" -> machines[0] = machines[0] + 1;
                case "ActiveGame" -> machines[1] = machines[1] + 1;
                case "VirtualRealityGame" -> machines[2] = machines[2] + 1;
            }
        }
        return machines;

    }

    public Customer findRichestCustomer(){
        Customer currentHighest = getCustomerCollection().get(0);
        for (int i = 0; i < getCustomerCollection().size(); i++) {
            if(getCustomerCollection().get(i).getAccountBalance() > currentHighest.getAccountBalance()) {
                currentHighest = getCustomerCollection().get(i);
            }
        }
        return currentHighest;
    }

    public static void printCorporateJargon(){
        System.out.println("GreedyJayInc. and ArcadeCorp do not take responsibility for any accidents or fits of rage that occur on the premises");
    }

    //testing start
//    public static void main(String[] args) throws InvalidCustomerException, InvalidGameIDException, AgeLimitException, InsufficientBalanceException {
//        Arcade Plongus = new Arcade("Plongus");
//        Customer Jeff = new Customer("123456", "Jeff Bungole", 18, Customer.DiscountType.STUDENT, 5);
//        Customer Jeff2 = new Customer("123333", "Jeff Bungole", 18, Customer.DiscountType.STUDENT, 14829);
//        Customer Jeff3 = new Customer("123999", "Jeff Bungole", 18, Customer.DiscountType.STUDENT, 932329);
//        ActiveGame MasterChefCollection = new ActiveGame("Ring: Master Chef Collection", "1HALCHEFCC", 300, 15);
//        VirtualRealityGame DingusWarrior = new VirtualRealityGame("DingusWarrior: tactical worm action", "DINGUS6942", 1000, 12, VirtualRealityGame.RequireControllers.HEADSET);
//        VirtualRealityGame DingusWarrior2 = new VirtualRealityGame("DingusWarrior2: sons of librarians", "DINGUS7356", 120, 15, VirtualRealityGame.RequireControllers.HEADSETANDCONTROLLER);
//        VirtualRealityGame DingusWarrior3 = new VirtualRealityGame("DingusWarrior3: dongus eater", "DINGUSTWIN", 200, 18, VirtualRealityGame.RequireControllers.FULLBODY);
//        CabinetGame UngaNight = new CabinetGame("Unga Night In-Cave", "UNDANITE12", 80, false);
//        CabinetGame UngaNight2 = new CabinetGame("Unga Night In-Cave EXE: Plate", "UNDANITE89", 80, true);
//        System.out.println(Plongus.getCustomerCollection());
//        Plongus.addCustomer(Jeff);
//        Plongus.addCustomer(Jeff2);
//        Plongus.addCustomer(Jeff3);
//        Plongus.addArcadeGame(MasterChefCollection);
//        Plongus.addArcadeGame(DingusWarrior);
//        Plongus.addArcadeGame(DingusWarrior2);
//        Plongus.addArcadeGame(DingusWarrior3);
//        Plongus.addArcadeGame(UngaNight);
//        Plongus.addArcadeGame(UngaNight2);
//        System.out.println(Plongus.getCustomerCollection());
//        System.out.println(Plongus.getCustomer("123999"));
//        System.out.println(Plongus.getArcadeGameCollection());
//        System.out.println(Plongus.getArcadeGame("DINGUSTWIN"));
//        System.out.println(Plongus.processTransaction("123456","DINGUS6942", true));
//        System.out.println(Plongus.getArcadeRevenue());
//        System.out.println(Plongus.findRichestCustomer());
//        System.out.println(Plongus.getMedianGamePrice());
//        System.out.println(Arrays.toString(Plongus.countArcadeGames()));
//        printCorporateJargon();
//    }
    //testing end

    @Override
    public String toString() {
        return "Arcade{" +
                "arcadeName='" + arcadeName + '\'' +
                ", arcadeRevenue=" + arcadeRevenue +
                ", arcadeGameCollection=" + arcadeGameCollection +
                ", customerCollection=" + customerCollection +
                '}';
    }
}
