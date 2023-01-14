package kitchenpos.menu.domain;

import kitchenpos.product.domain.Price;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private MenuName name;
    @Embedded
    private Price price;
    @Column(name = "menu_group_id")
    private Long menuGroupId;
    @Embedded
    private MenuProductBag menuProducts;

    private Menu(MenuName name, Price price, Long menuGroupId, MenuProductBag menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public static Menu of(MenuName name, Price price, Long menuGroupId, MenuProductBag menuProducts) {
        return new Menu(name, price, menuGroupId, menuProducts);
    }

    public static Menu of(String name, BigDecimal price, Long menuGroupId, MenuProductBag menuProducts) {
        return new Menu(MenuName.from(name), Price.from(price), menuGroupId, menuProducts);
    }

    protected Menu() {
    }

    public Long getId() {
        return id;
    }

    public MenuName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public MenuProductBag getMenuProducts() {
        return menuProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(name, menu.name) && Objects.equals(price.intValue(), menu.price.intValue())
                && Objects.equals(menuGroupId, menu.menuGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, menuGroupId, menuProducts);
    }

    public List<Long> productIds() {
        return this.menuProducts.productIds();
    }

    public void updateMenuToMenuProducts() {
        this.menuProducts.setMenuToMenuProducts(this);
    }
}
