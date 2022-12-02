public class VirtualRealityGame extends ActiveGame{

    enum RequireControllers{
        HEADSET, HEADSETANDCONTROLLER, FULLBODY
    }

    private RequireControllers requireControllers;

    public VirtualRealityGame(String gameName, String serialNumber, double pricePerPlay, int minimumAge, RequireControllers requireControllers) throws InvalidGameIDException {
        super(gameName, serialNumber, pricePerPlay, minimumAge);
        setRequireControllers(requireControllers);
        if (getSerialNumber().length() != 10) {
            throw new InvalidGameIDException("Error: The Virtual Reality Game ID / Serial Number must be exactly 10 characters.");
        }
    }

    public RequireControllers getRequireControllers() {
        return requireControllers;
    }

    public void setRequireControllers(RequireControllers requireControllers) {
        this.requireControllers = requireControllers;
    }

    //testing start
//    public static void main(String[] args) throws InvalidGameIDException {
//        VirtualRealityGame DingusWarrior = new VirtualRealityGame("DingusWarrior: tactical worm action", "DINGUS6942", 100, 12, RequireControllers.HEADSET);
//        VirtualRealityGame DingusWarrior2 = new VirtualRealityGame("DingusWarrior2: sons of librarians", "DINGUS7356", 120, 15, RequireControllers.HEADSETANDCONTROLLER);
//        VirtualRealityGame DingusWarrior3 = new VirtualRealityGame("DingusWarrior3: dongus eater", "DINGUSTWIN", 200, 18, RequireControllers.FULLBODY);
//        //VirtualRealityGame DingusWarrior4 = new VirtualRealityGame("DingusWarrior4: guns of the dungils", "DINGUSBUTOLD", 2000000, 18, RequireControllers.FULLBODY);
//        System.out.println(DingusWarrior.getGameName());
//        System.out.println(DingusWarrior.calculatePrice(true));
//        System.out.println(DingusWarrior.calculatePrice(false));
//        System.out.println(DingusWarrior2.getGameName());
//        System.out.println(DingusWarrior2.calculatePrice(true));
//        System.out.println(DingusWarrior2.calculatePrice(false));
//        System.out.println(DingusWarrior3.getGameName());
//        System.out.println(DingusWarrior3.calculatePrice(true));
//        System.out.println(DingusWarrior3.calculatePrice(false));
//    }
    //testing end

    @Override
    public double calculatePrice(boolean peak){
        if(!peak){
            return switch (requireControllers) {
                case HEADSET -> (int) Math.floor(getPricePerPlay() - (getPricePerPlay() * 10 / 100));
                case FULLBODY -> (int) getPricePerPlay();
                case HEADSETANDCONTROLLER -> (int) Math.floor(getPricePerPlay() - (getPricePerPlay() * 5 / 100));
            };
        }
        return (int) getPricePerPlay();
    }

    @Override
    public String toString() {
        return "VirtualRealityGame{" +
                "minimumAge=" + minimumAge +
                ", gameName='" + gameName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", pricePerPlay=" + pricePerPlay +
                ", requireControllers=" + requireControllers +
                '}';
    }
}
