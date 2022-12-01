package main;

public class Homepage {

    private static Homepage instance = new Homepage();

    // private constructor so that we cannot instantiate the class
    private Homepage(){}

    public static Homepage getInstance(){
        return instance;
    }

}
