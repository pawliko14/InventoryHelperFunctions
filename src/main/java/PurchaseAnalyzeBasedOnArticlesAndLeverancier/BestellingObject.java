package PurchaseAnalyzeBasedOnArticlesAndLeverancier;

import java.math.BigDecimal;
import java.util.Objects;

public class BestellingObject {

    String leverancier;
    String articelcode;
    String artikelomschirjing;
    double besteld;
    double gelerved;
    double pricePerItem;
    BigDecimal total;
    String leveringsdatumReceptie;
    String Munt;


    @Override
    public String toString() {
        return "BestellingObject{" +
                "leverancier='" + leverancier + '\'' +
                ", articelcode='" + articelcode + '\'' +
                ", artikelomschirjing='" + artikelomschirjing + '\'' +
                ", besteld=" + besteld +
                ", gelerved=" + gelerved +
                ", pricePerItem=" + pricePerItem +
                ", total=" + total +
                ", leveringsdatumReceptie='" + leveringsdatumReceptie + '\'' +
                ", Munt='" + Munt + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestellingObject that = (BestellingObject) o;
        return Double.compare(that.besteld, besteld) == 0 &&
                Double.compare(that.gelerved, gelerved) == 0 &&
                Double.compare(that.pricePerItem, pricePerItem) == 0 &&
                Objects.equals(leverancier, that.leverancier) &&
                Objects.equals(articelcode, that.articelcode) &&
                Objects.equals(artikelomschirjing, that.artikelomschirjing) &&
                Objects.equals(total, that.total) &&
                Objects.equals(leveringsdatumReceptie, that.leveringsdatumReceptie) &&
                Objects.equals(Munt, that.Munt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leverancier, articelcode, artikelomschirjing, besteld, gelerved, pricePerItem, total, leveringsdatumReceptie, Munt);
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(double pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public String getMunt() {
        return Munt;
    }

    public void setMunt(String munt) {
        Munt = munt;
    }

    public BestellingObject() {

    }

    public String getLeverancier() {
        return leverancier;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public String getArticelcode() {
        return articelcode;
    }

    public void setArticelcode(String articelcode) {
        this.articelcode = articelcode;
    }

    public String getArtikelomschirjing() {
        return artikelomschirjing;
    }

    public void setArtikelomschirjing(String artikelomschirjing) {
        this.artikelomschirjing = artikelomschirjing;
    }

    public double getBesteld() {
        return besteld;
    }

    public void setBesteld(double besteld) {
        this.besteld = besteld;
    }

    public double getGelerved() {
        return gelerved;
    }

    public void setGelerved(double gelerved) {
        this.gelerved = gelerved;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getLeveringsdatumReceptie() {
        return leveringsdatumReceptie;
    }

    public void setLeveringsdatumReceptie(String leveringsdatumReceptie) {
        this.leveringsdatumReceptie = leveringsdatumReceptie;
    }

}
