package com.envent.bottlesup.mvp.model.mycart;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ronem on 3/28/18.
 */

@Table(name = "food_cart")
public class FoodCart extends Model {
    private static final String CATEGORY_ID = "category_id";
    private static final String ITEM_ID = "item_id";
    private static final String REMARK = "remark";
    private static final String AND_CLAUSE = "=? AND ";

    @Column(name = CATEGORY_ID)
    private int categoryId;

    @Column(name = ITEM_ID)
    private int itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "menu_price")
    private double menuPrice;

    @Column(name = "bottles_up_price")
    private double bottlesUpPrice;

    @Column(name = "bottles_up_inclusive_price")
    private double bottlesUpInclusivePrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = REMARK)
    private String remark;

    public static List<FoodCart> getFoodCartItems() {
        return new Select().from(FoodCart.class).execute();
    }

    public static void deleteFoodsFromCart() {
        new Delete().from(FoodCart.class).execute();
    }

    public static FoodCart getFoodCart(int categoryId, int itemId, String remark) {
        return new Select()
                .from(FoodCart.class)
                .where(CATEGORY_ID
                                + AND_CLAUSE + ITEM_ID
                                + AND_CLAUSE + REMARK
                                + "=?",
                        categoryId, itemId, remark)
                .executeSingle();
    }

    public static void deleteCart(int categoryId, int itemId, String remark) {
        new Delete()
                .from(FoodCart.class)
                .where(CATEGORY_ID
                                + AND_CLAUSE + ITEM_ID
                                + AND_CLAUSE + REMARK
                                + "=?",
                        categoryId, itemId,remark)
                .execute();
    }

    public FoodCart() {
        super();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(double menuPrice) {
        this.menuPrice = menuPrice;
    }

    public double getBottlesUpPrice() {
        return bottlesUpPrice;
    }

    public void setBottlesUpPrice(double bottlesUpPrice) {
        this.bottlesUpPrice = bottlesUpPrice;
    }

    public double getBottlesUpInclusivePrice() {
        return bottlesUpInclusivePrice;
    }

    public void setBottlesUpInclusivePrice(double bottlesUpInclusivePrice) {
        this.bottlesUpInclusivePrice = bottlesUpInclusivePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FoodCart{" +
                "categoryId='" + categoryId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", menuPrice=" + menuPrice +
                ", bottlesUpPrice=" + bottlesUpPrice +
                ", bottlesUpInclusivePrice=" + bottlesUpInclusivePrice +
                ", quantity=" + quantity +
                ", remark=" + remark +
                '}';
    }
}