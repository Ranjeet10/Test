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

@Table(name = "drink_cart")
public class DrinkCart extends Model {
    private static final String CATEGORY_ID = "category_id";
    private static final String ITEM_ID = "item_id";
    private final String NAME = "name";
    private final String MENU_PRICE = "menu_price";
    private final String BOTTLES_UP_PRICE = "bottles_up_price";
    private final String BOTTLES_UP_INCLUSIVE_PRICE = "bottles_up_inclusive_price";
    private static final String DRINK_OPTION = "drink_option_id";
    private static final String MIXTURE = "mixture_id";
    private static final String REMARK = "remark";
    private final String QUANTITY = "quantity";

    private static final String AND_CLAUSE = "=? AND ";

    @Column(name = CATEGORY_ID)
    private int categoryId;

    @Column(name = ITEM_ID)
    private int itemId;

    @Column(name = NAME)
    private String name;

    @Column(name = MENU_PRICE)
    private double menuPrice;

    @Column(name = BOTTLES_UP_PRICE)
    private double bottlesUpPrice;

    @Column(name = BOTTLES_UP_INCLUSIVE_PRICE)
    private double bottlesUpInclusivePrice;

    @Column(name = DRINK_OPTION)
    private int drinkOptionId;

    @Column(name = "drink_option_name")
    private String drinkOption;

    @Column(name = MIXTURE)
    private int mixtureId;

    @Column(name = "mixture_name")
    private String mixture;

    @Column(name = QUANTITY)
    private int quantity;

    @Column(name = REMARK)
    private String remark;

    public static List<DrinkCart> getDrinkCartItems() {
        return new Select().from(DrinkCart.class).execute();
    }

    public static void deleteDrinksFromCart() {
        new Delete().from(DrinkCart.class).execute();
    }

    public static DrinkCart getDrinkCart(int categoryId, int drinkItemId, int drinkOption, int mixture, String remark) {
        return new Select()
                .from(DrinkCart.class)
                .where(CATEGORY_ID + AND_CLAUSE
                        + ITEM_ID + AND_CLAUSE
                        + DRINK_OPTION
                        + AND_CLAUSE + MIXTURE
                        + AND_CLAUSE + REMARK
                        + "=?", categoryId, drinkItemId, drinkOption, mixture, remark)
                .executeSingle();
    }

    public static void deleteCart(int categoryId, int itemId, int drinkOption, int mixture, String remark) {
        new Delete()
                .from(DrinkCart.class)
                .where(CATEGORY_ID
                        + AND_CLAUSE + ITEM_ID
                        + AND_CLAUSE + DRINK_OPTION
                        + AND_CLAUSE + MIXTURE
                        + AND_CLAUSE + REMARK
                        + "=?", categoryId, itemId, drinkOption, mixture, remark)
                .execute();
    }

    public DrinkCart() {
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

    public String getDrinkOption() {
        return drinkOption;
    }

    public void setDrinkOption(String drinkOption) {
        this.drinkOption = drinkOption;
    }

    public String getMixture() {
        return mixture;
    }

    public void setMixture(String mixture) {
        this.mixture = mixture;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDrinkOptionId() {
        return drinkOptionId;
    }

    public void setDrinkOptionId(int drinkOptionId) {
        this.drinkOptionId = drinkOptionId;
    }

    public int getMixtureId() {
        return mixtureId;
    }

    public void setMixtureId(int mixtureId) {
        this.mixtureId = mixtureId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "DrinkCart{" +
                "categoryId=" + categoryId +
                ", itemId=" + itemId +
                ", name='" + name + '\'' +
                ", menuPrice=" + menuPrice +
                ", bottlesUpPrice=" + bottlesUpPrice +
                ", bottlesUpInclusivePrice =" + bottlesUpInclusivePrice +
                ", drinkOptionId=" + drinkOptionId +
                ", drinkOption='" + drinkOption + '\'' +
                ", mixtureId=" + mixtureId +
                ", mixture='" + mixture + '\'' +
                ", quantity=" + quantity +
                ", remark=" + remark +
                '}';
    }
}
