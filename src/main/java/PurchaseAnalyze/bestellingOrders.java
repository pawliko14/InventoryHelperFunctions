package PurchaseAnalyze;

public class bestellingOrders {

    private String leverancierOrdernummer;
    private double summaryValue;


    @Override
    public String toString() {
        return "bestellingOrders{" +
                "leverancierOrdernummer='" + leverancierOrdernummer + '\'' +
                ", summaryValue=" + summaryValue +
                '}';
    }

    public String getLeverancierOrdernummer() {
        return leverancierOrdernummer;
    }

    public void setLeverancierOrdernummer(String leverancierOrdernummer) {
        this.leverancierOrdernummer = leverancierOrdernummer;
    }

    public double getSummaryValue() {
        return summaryValue;
    }

    public void setSummaryValue(double summaryValue) {
        this.summaryValue = summaryValue;
    }

    public bestellingOrders(String leverancierOrdernummer, double summaryValue) {
        this.leverancierOrdernummer = leverancierOrdernummer;
        this.summaryValue = summaryValue;
    }
}
