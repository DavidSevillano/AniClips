package com.example.AniClips.query;

import com.example.AniClips.util.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@AllArgsConstructor
public abstract class GenericSpecificationBuilder<U> {
    private List<SearchCriteria> params;

    public Specification<U> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<U> result = build(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = result.and(build(params.get(i)));
        }

        return result;
    }

    private Specification<U> build(SearchCriteria criteria) {
        return (Root<U> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Predicate predicate = null;

            if (criteria.operation().equalsIgnoreCase(">")) {
                predicate = builder.greaterThanOrEqualTo(root.get(criteria.key()), criteria.value().toString());
            } else if (criteria.operation().equalsIgnoreCase("<")) {
                predicate = builder.lessThanOrEqualTo(root.get(criteria.key()), criteria.value().toString());
            } else if (criteria.operation().equalsIgnoreCase(":")) {
                if (root.get(criteria.key()).getJavaType() == String.class) {
                    String valor = criteria.value().toString().trim().toLowerCase();

                    predicate = builder.like(builder.lower(root.get(criteria.key())), "%" + valor + "%");
                } else {
                    predicate = builder.equal(root.get(criteria.key()), criteria.value());
                }
            }

            return predicate;
        };
    }
}
