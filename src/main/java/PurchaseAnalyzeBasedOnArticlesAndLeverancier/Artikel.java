package PurchaseAnalyzeBasedOnArticlesAndLeverancier;

import java.util.Objects;

public class Artikel {

    String artikel;
    String artikelOmschrijving;

    public Artikel() {
    }

    public String getArtikel() {
        return artikel;
    }

    public void setArtikel(String artikel) {
        this.artikel = artikel;
    }

    public String getArtikelOmschrijving() {
        return artikelOmschrijving;
    }

    public void setArtikelOmschrijving(String artikelOmschrijving) {
        this.artikelOmschrijving = artikelOmschrijving;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artikel artikel1 = (Artikel) o;
        return Objects.equals(artikel, artikel1.artikel) &&
                Objects.equals(artikelOmschrijving, artikel1.artikelOmschrijving);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artikel, artikelOmschrijving);
    }

    @Override
    public String toString() {
        return "Artikel{" +
                "artikel='" + artikel + '\'' +
                ", artikelOmschrijving='" + artikelOmschrijving + '\'' +
                '}';
    }
}
