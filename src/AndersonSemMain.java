import java.util.logging.Level;
import java.util.logging.Logger;

public class AndersonSemMain {

    public static void main(String[] args) {
        
        final Account a = new Account(1000);
        final Account b = new Account(4000);
        
        new Thread (() -> {
            try {
                transfer(a, b, 300);
            } catch (InsuffientFundsException ex) {
                Logger.getLogger(AndersonSemMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();  
        
        try {
            transfer(b, a, 500);
        } catch (InsuffientFundsException ex) {
            Logger.getLogger(AndersonSemMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static void transfer(Account acc_src, Account acc_dst, int amount) 
            throws InsuffientFundsException{
        if (acc_src.getBalance() < amount)
            throw new InsuffientFundsException();
        
        synchronized(acc_src){
            try {
                Thread.sleep(1000);
            synchronized(acc_dst){
            System.out.format("Balance before source = %d, balance destination = %d, amount = %d %n", acc_src.getBalance(), acc_dst.getBalance(), amount);
                
            acc_src.decBalance(amount);
            acc_dst.incBalance(amount);
        
            System.out.format("Balance after source = %d, balance destination = %d %n", acc_src.getBalance(), acc_dst.getBalance());        
            }
            } catch (InterruptedException ex) {
                Logger.getLogger(AndersonSemMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

private static class InsuffientFundsException extends Exception {

    public InsuffientFundsException() {
        }
    }
    
}
