package kitchenpos.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class TableGroup {
    private Long id;
    private LocalDateTime createdDate;
    private List<OrderTable> orderTables;

    public TableGroup(LocalDateTime createdDate, List<OrderTable> orderTables) {
        this.createdDate = createdDate;
        this.orderTables = orderTables;
    }

    public TableGroup() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<OrderTable> getOrderTables() {
        return orderTables;
    }

    public void setOrderTables(final List<OrderTable> orderTables) {
        this.orderTables = orderTables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TableGroup that = (TableGroup) o;
        return Objects.equals(orderTables, that.orderTables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderTables);
    }
}
