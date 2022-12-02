abstract class ArcadeGame {

    protected String gameName;
    protected String serialNumber;
    protected double pricePerPlay;

    public ArcadeGame(){}

    public ArcadeGame(String gameName, String serialNumber, double pricePerPlay) throws InvalidGameIDException {
        this.gameName = gameName;
        this.serialNumber = serialNumber;
        this.pricePerPlay = pricePerPlay;
        if (getSerialNumber().length() != 10)
            throw new InvalidGameIDException("Error: The Game ID / Serial Number must be exactly 10 characters.");
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getPricePerPlay() {
        return pricePerPlay;
    }

    public void setPricePerPlay(double pricePerPlay) {
        this.pricePerPlay = pricePerPlay;
    }

//    public static void main(String args[]){
//        try {
//            ArcadeGame pingosadventure = new ArcadeGame("pingos","121212asdasdsadasdaasd",1);
//            System.out.println(pingosadventure);
//        } catch (InvalidGameIDException e) {
//            e.printStackTrace();
//        }
//    }

    public double calculatePrice(boolean peak){
        return (int) this.pricePerPlay;
    }

}
