package com.musinsa.shoppingcategory.respository.impl;

import com.musinsa.shoppingcategory.domain.Category;
import com.musinsa.shoppingcategory.respository.CustomCategoryRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.musinsa.shoppingcategory.domain.QCategory.*;

public class CustomCategoryRepositoryImpl implements CustomCategoryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomCategoryRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findCategory(String categoryId) {
        return jpaQueryFactory
                .selectFrom(category)
                .where(categoryIdEq(categoryId))
                .fetch();
    }

    private BooleanExpression categoryIdEq(String categoryId) {
        return categoryId.equals("ALL") ? category.parent.isNull() : category.id.eq(Long.parseLong(categoryId));
    }
}
