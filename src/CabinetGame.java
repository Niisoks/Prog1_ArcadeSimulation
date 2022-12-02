public class CabinetGame extends ArcadeGame{

    private boolean canPayReward;

    public boolean isCanPayReward() {
        return canPayReward;
    }

    public void setCanPayReward(boolean canPayReward) {
        this.canPayReward = canPayReward;
    }

    public CabinetGame(String gameName, String serialNumber, double pricePerPlay, boolean canPayReward) throws InvalidGameIDException{
        super(gameName, serialNumber, pricePerPlay);
        this.canPayReward = canPayReward;
        if (getSerialNumber().length() != 10) {
            throw new InvalidGameIDException("Error: The Cabinet Game ID / Serial Number must be exactly 10 characters.");
        }
    }

    //testing start
//    public static void main(String[] args) throws InvalidGameIDException {
//        CabinetGame UngaNight = new CabinetGame("Unga Night In-Cave", "UNDANITE12", 80, false);
//        CabinetGame UngaNight2 = new CabinetGame("Unga Night In-Cave EXE: Plate", "UNDANITE89", 80, true);
//        //CabinetGame UngaNight3 = new CabinetGame("Unga Night In-Cave EXE: Plate[BONK]", "UNDANITE89LONG", 801, true);
//        System.out.println(UngaNight.getGameName());
//        System.out.println(UngaNight.calculatePrice(true));
//        System.out.println(UngaNight.getGameName());
//        System.out.println(UngaNight.calculatePrice(false));
//        System.out.println(UngaNight2.getGameName());
//        System.out.println(UngaNight2.calculatePrice(true));
//        System.out.println(UngaNight2.getGameName());
//        System.out.println(UngaNight2.calculatePrice(false));
//    }
    //testing end

    @Override
    public double calculatePrice(boolean peak){
        if(peak){
            return (int) getPricePerPlay();
        }
        else if(isCanPayReward()){
            //removes 20% off of the price when off peak and the machine can pay out rewards
            return (int) Math.floor(getPricePerPlay() - (getPricePerPlay() * 20 / 100));
        }
        else{
            return Math.floor(getPricePerPlay() - (getPricePerPlay() * 50 / 100));
        }
    }

    @Override
    public String toString() {
        return "CabinetGame{" +
                "gameName='" + gameName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", pricePerPlay=" + pricePerPlay +
                ", canPayReward=" + canPayReward +
                '}';
    }
}
