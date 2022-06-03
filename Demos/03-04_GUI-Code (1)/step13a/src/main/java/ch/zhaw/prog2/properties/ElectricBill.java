package ch.zhaw.prog2.properties;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

//from  https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm
public class ElectricBill {


    public static void main(String[] args) {
        Bill electricBill = new Bill();
        electricBill.amountDueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldVal, Number newVal) {
                // here you could set information in the view of an application
                System.out.println("Electric bill has changed from "+oldVal+" to "+newVal );
            }
        });
        // changing the amount from initial value (0.0)
        electricBill.setAmountDue(100.0);
        // setting the same amount does not call the ChangeListener
        electricBill.setAmountDue(100.0);
        // changing the amount does call the ChangeListener again
        electricBill.setAmountDue(30);

    }


    static class Bill {
        // Instance variable to store the property
        private DoubleProperty amountDue = new SimpleDoubleProperty();

        // Getter for the property's value
        public final double getAmountDue() { return amountDue.get(); }

        // Setter for the property's value
        public final void setAmountDue(double value) { amountDue.set(value); }

        // Getter for the property itself
        public DoubleProperty amountDueProperty() { return amountDue; }
    }

}
