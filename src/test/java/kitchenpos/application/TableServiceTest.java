package kitchenpos.application;

import kitchenpos.dao.OrderDao;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

import static kitchenpos.application.OrderServiceTest.주문;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@Transactional
@SpringBootTest
@DisplayName("주문 테이블 테스트")
public class TableServiceTest {

    public static final boolean 빈_상태 = true;
    public static final boolean 비어있지_않은_상태 = false;
    public static final int 두_명의_방문객 = 2;
    public static final int 한_명의_방문객 = 1;

    @Autowired
    private TableService tableService;

    @Autowired
    private OrderDao orderDao;

    @DisplayName("생성 성공")
    @Test
    void 생성_성공() {
        assertThat(tableService.create(주문_테이블(두_명의_방문객, 빈_상태))).isEqualTo(주문_테이블(두_명의_방문객, 빈_상태));
    }

    @DisplayName("목록 조회 성공")
    @Test
    void 목록_조회_성공() {
        //given:
        final OrderTable 주문_테이블 = tableService.create(주문_테이블(두_명의_방문객, 빈_상태));
        //when,then:
        assertThat(tableService.list()).contains(주문_테이블);
    }

    @DisplayName("빈 주문 테이블 변경 성공")
    @Test
    void 빈_주문_테이블_변경_성공() {
        //given:
        final OrderTable 주문_테이블 = tableService.create(주문_테이블(두_명의_방문객, 비어있지_않은_상태));

        orderDao.save(주문(
                주문_테이블.getId(),
                OrderStatus.COMPLETION.name(),
                LocalDateTime.now(),
                Collections.emptyList()));
        //when:
        final OrderTable 빈_자리_테이블 = tableService.changeEmpty(주문_테이블.getId(), 주문_테이블.changeEmpty(빈_상태));
        //then:
        assertThat(빈_자리_테이블.isEmpty()).isTrue();
    }

    @DisplayName("빈 테이블 변경 예외 - 주문 테이블이 존재하지 않는 경우")
    @Test
    void 빈_테이블_변경_예외_주문_테이블이_존재하지_않는_경우() {
        //given:
        final OrderTable 주문_테이블 = 주문_테이블(2, 빈_상태);
        //when,then:
        assertThatIllegalArgumentException().isThrownBy(
                () -> tableService.changeEmpty(주문_테이블.getId(), 주문_테이블.changeEmpty(빈_상태)));
    }

    @DisplayName("빈 테이블 변경 예외 - 주문 테이블의 주문 상태가 식사 상태인 경우")
    @Test
    void 빈_테이블_변경_예외_주문_상태가_식사_상태인_경우() {
        //given:
        final OrderTable 주문_테이블 = tableService.create(주문_테이블(두_명의_방문객, 비어있지_않은_상태));

        orderDao.save(주문(
                주문_테이블.getId(),
                OrderStatus.MEAL.name(),
                LocalDateTime.now(),
                Collections.emptyList()));
        //when,then:
        assertThatIllegalArgumentException().isThrownBy(
                () -> tableService.changeEmpty(주문_테이블.getId(), 주문_테이블.changeEmpty(빈_상태)));
    }

    @DisplayName("빈 테이블 변경 예외 - 주문 테이블의 주문 상태가 조리 상태인 경우")
    @Test
    void 빈_테이블_변경_예외_주문_상태가_조리_상태인_경우() {
        //given:
        final OrderTable 주문_테이블 = tableService.create(주문_테이블(두_명의_방문객, 비어있지_않은_상태));

        orderDao.save(주문(
                주문_테이블.getId(),
                OrderStatus.COOKING.name(),
                LocalDateTime.now(),
                Collections.emptyList()));
        //when,then:
        assertThatIllegalArgumentException().isThrownBy(
                () -> tableService.changeEmpty(주문_테이블.getId(), 주문_테이블.changeEmpty(빈_상태)));
    }

    @DisplayName("방문 손님 수 변경 성공")
    @Test
    void 방문_손님_수_변경_성공() {
        //given:
        final OrderTable 주문_테이블 = tableService.create(주문_테이블(두_명의_방문객, 비어있지_않은_상태));
        //when:
        final OrderTable 방문_손님_수가_변경_된_주문_테이블 = tableService.changeNumberOfGuests(
                주문_테이블.getId(), 주문_테이블.changeNumberOfGuest(한_명의_방문객));
        //then:
        assertThat(방문_손님_수가_변경_된_주문_테이블.getNumberOfGuests()).isEqualTo(한_명의_방문객);
    }

    @DisplayName("방문 손님 수 변경 예외 - 주문 테이블이 존재하지 않는 경우")
    @Test
    void 방문_손님_수_변경_예외_주문_테이블이_존재하지_않는_경우() {
        //given:
        final OrderTable 주문_테이블 = 주문_테이블(두_명의_방문객, 비어있지_않은_상태);
        //when,then:
        assertThatIllegalArgumentException().isThrownBy(() -> tableService.changeNumberOfGuests(
                주문_테이블.getId(), 주문_테이블.changeNumberOfGuest(한_명의_방문객)));
    }

    @DisplayName("방문 손님 수 변경 예외 - 주문 테이블이 비어 있는 경우")
    @Test
    void 방문_손님_수_변경_예외_주문_테이블이_비어_있는_경우() {
        //given:
        final OrderTable 주문_테이블 = tableService.create(주문_테이블(두_명의_방문객, 빈_상태));
        //when,then:
        assertThatIllegalArgumentException().isThrownBy(() -> tableService.changeNumberOfGuests(
                주문_테이블.getId(), 주문_테이블.changeNumberOfGuest(한_명의_방문객)));
    }

    public static OrderTable 주문_테이블(int numberOfGuest, boolean empty) {
        return OrderTable.ofNumberOfGuestsAndEmpty(numberOfGuest, empty);
    }
}
