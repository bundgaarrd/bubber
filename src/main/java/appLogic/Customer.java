package appLogic;

public class Customer {
    private String name;
    private String email;
    private String phoneNumber;

    public Customer(String name, String email, String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public boolean verifyIsEmail(String email){

        if(!email.isEmpty()){
            return false;
        }
        if (!email.contains("@")) {
            return false;


        }
        if(!email.contains(".")){
            return false;
        }
        
        return true;
    

    }

}
