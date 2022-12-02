package fileio;

public class Users {
    public Users(Users users) {
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    private Credentials credentials;
//    private String name;
//    private String password;
//    private String accountType;
//    private String country;
//    private int balance;
//
//    public Users(Credentials credentials) {
//        this.name = credentials.getName();
//        this.password = credentials.getPassword();
//        this.accountType = credentials.getAccountType();
//        this.country = credentials.getCountry();
//        this.balance = credentials.getBalance();
//    }


//    public Users(Users user) {
//        this.name = user.getName();
//        this.password = user.getPassword();
//        this.accountType = user.getAccountType();
//        this.country = user.getCountry();
//        this.balance = user.getBalance();
//    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getAccountType() {
//        return accountType;
//    }
//
//    public void setAccountType(String accountType) {
//        this.accountType = accountType;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public int getBalance() {
//        return balance;
//    }
//
//    public void setBalance(int balance) {
//        this.balance = balance;
//    }
//
//    @Override
//    public String toString() {
//        return "Users{" +
//                "name='" + name + '\'' +
//                ", password='" + password + '\'' +
//                ", accountType='" + accountType + '\'' +
//                ", country='" + country + '\'' +
//                ", balance=" + balance +
//                '}';
//    }
}