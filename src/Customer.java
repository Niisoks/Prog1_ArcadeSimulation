public class Customer {

    enum DiscountType{
        STANDARD, STUDENT, STAFF
    }

    private String accountID;
    private String name;
    private int age;
    private DiscountType discountType;
    private double accountBalance;

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Customer(String accountID, String name, int age, DiscountType discountType, int accountBalance) throws InvalidCustomerException {
        this.accountID = accountID;
        this.name = name;
        this.age = age;
        this.discountType = discountType;
        this.accountBalance = accountBalance;
        if (accountID.length() != 6) {
            throw new InvalidCustomerException("Error: Customer ID must be 6 characters long");
        }
        if (accountBalance < 0) {
            throw new InvalidCustomerException("Error: Customer Balance can not go below 0");
        }
    }

    public Customer(String accountID, String name, int age, DiscountType discountType) {
        this.accountID = accountID;
        this.name = name;
        this.age = age;
        this.discountType = discountType;
        this.accountBalance = 0;
    }

    //testing start
//    public static void main(String[] args) throws InvalidCustomerException, InvalidGameIDException, AgeLimitException, InsufficientBalanceException {
//        Customer Jeff = new Customer("123456", "Jeff Bungole", 18, DiscountType.STUDENT, 1);
//        Customer Charlie = new Customer("654321", "Charlie Bagoon", 53, DiscountType.STAFF, 450);
//        System.out.println(Jeff.getAccountID() + Jeff.getName() + Jeff.getAge() + Jeff.getDiscountType() + Jeff.getAccountBalance());
//        Jeff.addFunds(1);
//        System.out.println(Jeff.getAccountBalance());
//        VirtualRealityGame UngaNight2 = new VirtualRealityGame("Unga Night In-Cave EXE: Plate", "UNDANITE89", 500, 18, VirtualRealityGame.RequireControllers.FULLBODY);
//        //CabinetGame UngaNight2 = new CabinetGame("Unga Night In-Cave EXE: Plate", "UNDANITE89", 800, true);
//        //ActiveGame UngaNight2 = new ActiveGame("Unga Night In-Cave EXE: Plate", "UNDANITE89", 800, 80);
//        System.out.println(Jeff.chargeAccount(UngaNight2, true));
//        System.out.println(Jeff.getAccountBalance());
//
//        System.out.println(Charlie.getAccountID() + Charlie.getName() + Charlie.getAge() + Charlie.getDiscountType() + Charlie.getAccountBalance());
//        System.out.println(Charlie.chargeAccount(UngaNight2, true));
//        System.out.println(Charlie.getAccountBalance());
//
//    }
    //testing end

    public void addFunds(int amount){
        if (amount > 0){
            System.out.println("current balance for "+ getName() + " is " + getAccountBalance() + "... Adding " + amount);
            setAccountBalance(getAccountBalance() + amount);
            System.out.println("success! new balance is " + getAccountBalance());
        }
        else{
            System.out.println("Can not add balance below 0!");
        }
    }

    public double chargeAccount(ArcadeGame gameBeingPlayed, boolean isPeak) throws InsufficientBalanceException, AgeLimitException {
        double newPrice;
        InsufficientBalanceException noBalance = new InsufficientBalanceException("Insufficient Balance, you have not been charged.", this.getName());
        if(gameBeingPlayed instanceof ActiveGame && ((ActiveGame) gameBeingPlayed).minimumAge <= getAge() || gameBeingPlayed instanceof CabinetGame) {
            switch (discountType) {
                case STAFF -> {
                    newPrice = Math.floor(gameBeingPlayed.calculatePrice(isPeak) - (gameBeingPlayed.calculatePrice(isPeak) * 10 / 100));
                    if (getAccountBalance() > newPrice) {
                        newPrice = getAccountBalance() - newPrice;
                        return (newPrice);
                    } else {
                        throw noBalance;
                    }
                }
                case STUDENT -> {
                    newPrice = Math.floor(gameBeingPlayed.calculatePrice(isPeak) - (gameBeingPlayed.calculatePrice(isPeak) * 5 / 100));
                    if (getAccountBalance() + 500 > newPrice) {
                        newPrice = getAccountBalance() - newPrice;
                        return (newPrice);
                    } else {
                        throw noBalance;
                    }
                }
                case STANDARD -> {
                    newPrice = gameBeingPlayed.calculatePrice(isPeak);
                    if (getAccountBalance() > newPrice) {
                        newPrice = getAccountBalance() - newPrice;
                        return (newPrice);
                    } else {
                        throw noBalance;
                    }
                }
            }
        }
        else{throw new AgeLimitException("Customer is too young for " + gameBeingPlayed.getGameName());}
        return (0);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "accountID='" + accountID + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", discountType=" + discountType +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
