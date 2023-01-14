package kitchenpos.menu.domain;

import kitchenpos.menu.domain.MenuGroup;
import kitchenpos.menu.domain.MenuGroupName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("메뉴 그룹 테스트")
public class MenuGroupTest {

    @DisplayName("생성 성공")
    @Test
    void 생성_성공() {
        final MenuGroupName name = MenuGroupName.from("추천 메뉴");
        assertThat(MenuGroup.from(name)).isEqualTo(MenuGroup.from(name));
    }
}
