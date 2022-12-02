public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(String message, String currentCustomer){
        super(" Customer name - " + currentCustomer + ' ' + message);
    }
}
