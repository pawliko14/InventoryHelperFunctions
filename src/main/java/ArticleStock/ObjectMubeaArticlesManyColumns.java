package ArticleStock;

import java.util.Objects;

public class ObjectMubeaArticlesManyColumns {

    private String articleNumber;
    private String articleDescription;
    private String unit;
    private String price;

    @Override
    public String toString() {
//        return "ObjectMubeaArticlesManyColumns{" +
//                "articleNumber='" + articleNumber + '\'' +
//                ", articleDescription='" + articleDescription + '\'' +
//                ", unit='" + unit + '\'' +
//                ", price='" + price + '\'' +
//                '}';

        return  "[" + articleNumber + " ; " + articleDescription + " ; " + unit + " ; " + price + "]";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectMubeaArticlesManyColumns that = (ObjectMubeaArticlesManyColumns) o;
        return Objects.equals(articleNumber, that.articleNumber) &&
                Objects.equals(articleDescription, that.articleDescription) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleNumber, articleDescription, unit, price);
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public String getUnit() {
        return unit;
    }

    public String getPrice() {
        return price;
    }

    public ObjectMubeaArticlesManyColumns(String articleNumber, String articleDescription, String unit, String price) {
        this.articleNumber = articleNumber;
        this.articleDescription = articleDescription;
        this.unit = unit;
        this.price = price;
    }
}
