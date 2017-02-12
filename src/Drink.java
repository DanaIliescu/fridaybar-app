import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Dana on 30-Mar-16.
 */
public class Drink {
    private SimpleStringProperty name;
    private SimpleIntegerProperty price;
    private SimpleIntegerProperty quantity;
    private SimpleIntegerProperty volume;
    private SimpleDoubleProperty percentage;

    public Drink(String name, int price, int quantity, int volume, double percentage){
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleIntegerProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.volume = new SimpleIntegerProperty(volume);
        this.percentage = new SimpleDoubleProperty(percentage);
    }

    public Drink(){}

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public int getVolume() {
        return volume.get();
    }

    public SimpleIntegerProperty volumeProperty() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume.set(volume);
    }

    public double getPercentage() {
        return percentage.get();
    }

    public SimpleDoubleProperty percentageProperty() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage.set(percentage);
    }

    @Override
    public String toString(){
        return (getName() + " " + getPrice() + " " + getQuantity() + " "
                + getVolume() + " " + getPercentage());
    }
}
