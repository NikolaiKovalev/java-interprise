package by.academy.it;


import java.io.Serializable;
import java.util.Date;

public class ExpensesDto implements Serializable {

    private int num;
    private Date payday;
    private int receiver;
    private double value;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getPayday() {
        return payday;
    }

    public void setPayday(Date payday) {
        this.payday = payday;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ExpensesDto{" +
                "num=" + num +
                ", payday=" + payday +
                ", receiver=" + receiver +
                ", value=" + value +
                '}';
    }
}
