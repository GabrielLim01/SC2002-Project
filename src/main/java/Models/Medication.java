package Models;

public class Medication {
    protected String name;
    protected int amount;

    public Medication(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void printMedication() {
        System.out.println(
            "\n" + this.name + ": " + this.amount
        );
    }
}