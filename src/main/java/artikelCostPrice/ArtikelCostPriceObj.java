package artikelCostPrice;

public class ArtikelCostPriceObj {


        String Artykul;
        String CenaSredniaWazona;
        String CenaPracy;
        String SumaObu;


    @Override
    public String toString() {
        return "ArtikelCostPriceObj{" +
                "Artykul='" + Artykul + '\'' +
                ", CenaSredniaWazona='" + CenaSredniaWazona + '\'' +
                ", CenaPracy='" + CenaPracy + '\'' +
                ", SumaObu='" + SumaObu + '\'' +
                '}';
    }

    public String getArtykul() {
        return Artykul;
    }

    public void setArtykul(String artykul) {
        Artykul = artykul;
    }

    public String getCenaSredniaWazona() {
        return CenaSredniaWazona;
    }

    public void setCenaSredniaWazona(String cenaSredniaWazona) {
        CenaSredniaWazona = cenaSredniaWazona;
    }

    public String getCenaPracy() {
        return CenaPracy;
    }

    public void setCenaPracy(String cenaPracy) {
        CenaPracy = cenaPracy;
    }

    public String getSumaObu() {
        return SumaObu;
    }

    public void setSumaObu(String sumaObu) {
        SumaObu = sumaObu;
    }

    public ArtikelCostPriceObj(String artykul, String cenaSredniaWazona, String cenaPracy, String sumaObu) {
        Artykul = artykul;
        CenaSredniaWazona = cenaSredniaWazona;
        CenaPracy = cenaPracy;
        SumaObu = sumaObu;
    }
}
