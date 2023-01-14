package kitchenpos.product.application;

import kitchenpos.product.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kitchenpos.product.domain.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(final Product request) {
        return productRepository.save(Product.of(request.getName(), request.getPrice()));
    }

    @Transactional(readOnly = true)
    public List<Product> list() {
        return productRepository.findAll();
    }
}
