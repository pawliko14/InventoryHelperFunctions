package PurchaseAnalyze;


import java.util.List;

public class bestellingOrdersSummary {

    String leverancier;
    int orderNummerCount;
    String  munt;
    double sumPricesOfAllOrders;
    List<bestellingOrders> listOfOrders;

    public bestellingOrdersSummary() {
    }

    public List<bestellingOrders> getListOfOrders() {
        return listOfOrders;
    }

    public void setListOfOrders(List<bestellingOrders> listOfOrders) {
        this.listOfOrders = listOfOrders;
    }

    public String getLeverancier() {
        return leverancier;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public int getOrderNummerCount() {
        return orderNummerCount;
    }

    public void setOrderNummerCount(int orderNummerCount) {
        this.orderNummerCount = orderNummerCount;
    }

    public String getMunt() {
        return munt;
    }

    public void setMunt(String munt) {
        this.munt = munt;
    }

    public double getSumPricesOfAllOrders() {
        return sumPricesOfAllOrders;
    }

    public void setSumPricesOfAllOrders(double sumPricesOfAllOrders) {
        this.sumPricesOfAllOrders = sumPricesOfAllOrders;
    }

    @Override
    public String toString() {
        return "bestellingOrdersSummary{" +
                "leverancier='" + leverancier + '\'' +
                ", orderNummerCount=" + orderNummerCount +
                ", munt='" + munt + '\'' +
                ", sumPricesOfAllOrders=" + sumPricesOfAllOrders +
                '}';
    }
}
