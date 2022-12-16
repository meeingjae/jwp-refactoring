package kitchenpos.menu.application;

import kitchenpos.menu.domain.MenuGroup;
import kitchenpos.menu.domain.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional(isolation = READ_COMMITTED)
    public MenuGroup create(final MenuGroup menuGroup) {
        return menuGroupRepository.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> list() {
        return menuGroupRepository.findAll();
    }

    public void existsById(Long id) {
        checkNullId(id);
        checkExist(id);
    }

    private void checkNullId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("메뉴그룹 id 값은 null이 아니어야 합니다");
        }
    }

    private void checkExist(Long id) {
        if (!menuGroupRepository.existsById(id)) {
            throw new IllegalArgumentException("메뉴그룹이 존재하지 않습니다. id:" + id);
        }
    }
}