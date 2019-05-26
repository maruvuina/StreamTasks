package by.bsu.strems.item;


import java.util.List;

public class Item {
    private final long ID;
    private double price;
    private String name;
    private List<String> producingCountries;

    {
        ID = (long)(Math.random()*10000);
    }

    public Item(double price, String name) {
        this.price = price;
        this.name = name;
    }

    public Item(double price, String name, List<String> producingCountries) {
        this.price = price;
        this.name = name;
        this.producingCountries = producingCountries;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getID() {
        return ID;
    }

    public List<String> getProducingCountries() {
        return producingCountries;
    }

    public void setProducingCountries(List<String> producingCountries) {
        this.producingCountries = producingCountries;
    }

    @Override
    public String toString() {
        return "Item{" +
                "ID=" + ID +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", producingCountries=" + producingCountries +
                '}';
    }
}
