public class ActiveGame extends ArcadeGame{

    protected int minimumAge;

    public ActiveGame(String gameName, String serialNumber, double pricePerPlay, int minimumAge) throws InvalidGameIDException {
        super(gameName, serialNumber, pricePerPlay);
        this.minimumAge = minimumAge;
        if (getSerialNumber().length() != 10) {
            throw new InvalidGameIDException("Error: The Active Game ID / Serial Number must be exactly 10 characters.");
        }
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    //testing start
//    public static void main(String[] args) throws InvalidGameIDException {
//        ActiveGame Ring = new ActiveGame("Ring: Combat devolved", "HALRINGCD8", 100, 12);
//        //ActiveGame Ring2 = new ActiveGame("Ring 2", "2HALCHEf", 3000000, 5);
//        ActiveGame MasterChefCollection = new ActiveGame("Ring: Master Chef Collection", "1HALCHEFCC", 300, 15);
//        System.out.println(Ring.getGameName());
//        System.out.println(Ring.calculatePrice(true));
//        System.out.println(MasterChefCollection.getGameName());
//        System.out.println(MasterChefCollection.calculatePrice(false));
//    }
    //testing end

    @Override
    public double calculatePrice(boolean peak){
        if(peak){
            return (int) getPricePerPlay();
        }
        else{
            //removes 20% off of the price when off peak
            return (int) Math.floor(getPricePerPlay() - (getPricePerPlay() * 20 / 100));
        }
    }

    @Override
    public String toString() {
        return "ActiveGame{" +
                "minimumAge=" + minimumAge +
                ", gameName='" + gameName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", pricePerPlay=" + pricePerPlay +
                '}';
    }
}
