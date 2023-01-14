package kitchenpos.application;

import kitchenpos.dao.MenuGroupDao;
import kitchenpos.dao.ProductDao;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.application.MenuGroupServiceTest.메뉴_그룹;
import static kitchenpos.application.MenuProductTest.메뉴_상품;
import static kitchenpos.application.ProductTest.상품;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@Transactional
@SpringBootTest
@DisplayName("메뉴 테스트 ")
public class MenuServiceTest {

    @Autowired
    private MenuGroupDao menuGroupDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MenuService menuService;

    @DisplayName("생성 성공")
    @Test
    void 생성_성공() {
        //given:
        final Menu 메뉴 = 메뉴("자메이카 통다리 1인 세트",
                BigDecimal.ONE,
                menuGroupDao.save(메뉴_그룹("추천 메뉴")).getId(),
                Arrays.asList(
                        메뉴_상품(productDao.save(상품("통다리", BigDecimal.ONE)).getId(), 5),
                        메뉴_상품(productDao.save(상품("콜라", BigDecimal.ONE)).getId(), 1)));
        //when:
        final Menu 저장된_메뉴 = menuService.create(메뉴);
        //then:
        assertThat(저장된_메뉴).isEqualTo(메뉴);
    }

    @DisplayName("생성 예외 - 메뉴의 가격이 0보다 작은 경우")
    @Test
    void 생성_예외_메뉴의_가격이_0보다_작은_경우() {
        //given:
        final Menu 메뉴 = 메뉴("자메이카 통다리 1인 세트",
                BigDecimal.valueOf(-1),
                menuGroupDao.save(메뉴_그룹("추천 메뉴")).getId(),
                Arrays.asList(
                        메뉴_상품(productDao.save(상품("통다리", BigDecimal.ONE)).getId(), 5),
                        메뉴_상품(productDao.save(상품("콜라", BigDecimal.ONE)).getId(), 1)));
        //when,then:
        assertThatIllegalArgumentException().isThrownBy(() -> menuService.create(메뉴));
    }

    @DisplayName("생성 예외 - 메뉴 그룹이 존재하지 않는 경우")
    @Test
    void 생성_예외_메뉴_그룹이_존재하지_않는_경우() {
        //given:
        final Menu 메뉴 = 메뉴("자메이카 통다리 1인 세트",
                BigDecimal.ONE,
                메뉴_그룹("추천 메뉴").getId(),
                Arrays.asList(
                        메뉴_상품(productDao.save(상품("통다리", BigDecimal.ONE)).getId(), 5),
                        메뉴_상품(productDao.save(상품("콜라", BigDecimal.ONE)).getId(), 1)));
        //when,then:
        assertThatIllegalArgumentException().isThrownBy(() -> menuService.create(메뉴));
    }

    @DisplayName("생성 예외 - 상품이 존재하지 않는 경우")
    @Test
    void 생성_예외_상품이_존재하지_않는_경우() {
        final Menu 메뉴 = 메뉴("자메이카 통다리 1인 세트",
                BigDecimal.ONE,
                menuGroupDao.save(메뉴_그룹("추천 메뉴")).getId(),
                Arrays.asList(
                        메뉴_상품(상품("통다리", BigDecimal.ONE).getId(), 5),
                        메뉴_상품(상품("콜라", BigDecimal.ONE).getId(), 1)));
        //when,then:
        assertThatIllegalArgumentException().isThrownBy(() -> menuService.create(메뉴));
    }

    @DisplayName("생성 예외 - 메뉴의 가격이 상품 목록 가격의 합보다 큰 경우")
    @Test
    void 생성_예외_메뉴의_가격이_상품_목록의_가격_합보다_큰_경우() {
        //given:
        final int expensivePrice = 99999;
        final Menu 메뉴 = 메뉴("자메이카 통다리 1인 세트",
                BigDecimal.valueOf(expensivePrice),
                menuGroupDao.save(메뉴_그룹("추천 메뉴")).getId(),
                Arrays.asList(
                        메뉴_상품(productDao.save(상품("통다리", BigDecimal.ONE)).getId(), 5),
                        메뉴_상품(productDao.save(상품("콜라", BigDecimal.ONE)).getId(), 1)));
        //when,then:
        assertThatIllegalArgumentException().isThrownBy(() -> menuService.create(메뉴));
    }

    @DisplayName("목록 조회 성공")
    @Test
    void 목록_조회_성공() {
        //given:
        final Menu 메뉴 = 메뉴("자메이카 통다리 1인 세트",
                BigDecimal.ONE,
                menuGroupDao.save(메뉴_그룹("추천 메뉴")).getId(),
                Arrays.asList(
                        메뉴_상품(productDao.save(상품("통다리", BigDecimal.ONE)).getId(), 5),
                        메뉴_상품(productDao.save(상품("콜라", BigDecimal.ONE)).getId(), 1)));
        //when:
        final Menu 저장된_메뉴 = menuService.create(메뉴);
        //then:
        assertThat(menuService.list()).contains(저장된_메뉴);
    }

    public static Menu 메뉴(String name, BigDecimal price, Long menuGroupId, List<MenuProduct> menuProducts) {
        return new Menu(name, price, menuGroupId, menuProducts);
    }
}
