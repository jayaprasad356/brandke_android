package Models;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import org.w3c.dom.Text;

public class CartModel {

    private int ProductImage;
    private String ProductName;
    private String ProductOfferPrice;
    private String ProductOriginalPrice;
    private String ProductQuantity;
    private CardView CvBtnDecrement;
    private CardView CvBtnIncrement;
    private String TvTotalCount;
    private TextView TvDelete;
    private TextView TvSaveForLater;

    public CartModel(int productImage, String productName, String productOfferPrice, String productOriginalPrice, String productQuantity, String tvTotalCount) {
        ProductImage = productImage;
        ProductName = productName;
        ProductOfferPrice = productOfferPrice;
        ProductOriginalPrice = productOriginalPrice;
        ProductQuantity = productQuantity;
        TvTotalCount = tvTotalCount;
    }

    public int getProductImage() {
        return ProductImage;
    }

    public void setProductImage(int productImage) {
        ProductImage = productImage;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductOfferPrice() {
        return ProductOfferPrice;
    }

    public void setProductOfferPrice(String productOfferPrice) {
        ProductOfferPrice = productOfferPrice;
    }

    public String getProductOriginalPrice() {
        return ProductOriginalPrice;
    }

    public void setProductOriginalPrice(String productOriginalPrice) {
        ProductOriginalPrice = productOriginalPrice;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public CardView getCvBtnDecrement() {
        return CvBtnDecrement;
    }

    public void setCvBtnDecrement(CardView cvBtnDecrement) {
        CvBtnDecrement = cvBtnDecrement;
    }

    public CardView getCvBtnIncrement() {
        return CvBtnIncrement;
    }

    public void setCvBtnIncrement(CardView cvBtnIncrement) {
        CvBtnIncrement = cvBtnIncrement;
    }

    public String getTvTotalCount() {
        return TvTotalCount;
    }

    public void setTvTotalCount(String tvTotalCount) {
        TvTotalCount = tvTotalCount;
    }

    public TextView getTvDelete() {
        return TvDelete;
    }

    public void setTvDelete(TextView tvDelete) {
        TvDelete = tvDelete;
    }

    public TextView getTvSaveForLater() {
        return TvSaveForLater;
    }

    public void setTvSaveForLater(TextView tvSaveForLater) {
        TvSaveForLater = tvSaveForLater;
    }
}
