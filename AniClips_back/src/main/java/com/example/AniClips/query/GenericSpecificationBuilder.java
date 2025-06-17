package com.example.AniClips.query;

import com.example.AniClips.util.SearchCriteria;
import jakarta.persistence.criteria.*;
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
            String key = criteria.key();
            String operation = criteria.operation();
            Object value = criteria.value();

            Predicate predicate = null;

            if (key.equalsIgnoreCase("valoracion")) {
                Subquery<Double> subquery = query.subquery(Double.class);
                Root<U> subRoot = (Root<U>) subquery.from(root.getModel().getBindableJavaType());
                Join<Object, Object> valoraciones = subRoot.join("valoraciones", JoinType.LEFT);

                subquery.select(builder.coalesce(builder.avg(valoraciones.get("puntuacion")), 0.0))
                        .where(builder.equal(subRoot.get("id"), root.get("id")));

                if (operation.equals(">") || operation.equals(">=")) {
                    predicate = builder.greaterThanOrEqualTo(subquery, Double.valueOf(value.toString()));
                } else if (operation.equals("<") || operation.equals("<=")) {
                    predicate = builder.lessThanOrEqualTo(subquery, Double.valueOf(value.toString()));
                } else if (operation.equals(":")) {
                    predicate = builder.equal(subquery, Double.valueOf(value.toString()));
                }
            }
            else if (operation.equals(">") || operation.equals(">=")) {
                Class<?> fieldType = root.get(key).getJavaType();
                if (fieldType == Integer.class) {
                    predicate = builder.greaterThanOrEqualTo(root.get(key).as(Integer.class), Integer.valueOf(value.toString()));
                } else if (fieldType == Double.class || fieldType == Float.class) {
                    predicate = builder.greaterThanOrEqualTo(root.get(key).as(Double.class), Double.valueOf(value.toString()));
                } else if (fieldType == Long.class) {
                    predicate = builder.greaterThanOrEqualTo(root.get(key).as(Long.class), Long.valueOf(value.toString()));
                }
            } else if (operation.equals("<") || operation.equals("<=")) {
                Class<?> fieldType = root.get(key).getJavaType();
                if (fieldType == Integer.class) {
                    predicate = builder.lessThanOrEqualTo(root.get(key).as(Integer.class), Integer.valueOf(value.toString()));
                } else if (fieldType == Double.class || fieldType == Float.class) {
                    predicate = builder.lessThanOrEqualTo(root.get(key).as(Double.class), Double.valueOf(value.toString()));
                } else if (fieldType == Long.class) {
                    predicate = builder.lessThanOrEqualTo(root.get(key).as(Long.class), Long.valueOf(value.toString()));
                }
            } else if (operation.equals(":")) {
                if (root.get(key).getJavaType() == String.class) {
                    String valor = value.toString().trim();
                    predicate = builder.like(builder.lower(root.get(key)), "%" + valor.toLowerCase() + "%");
                } else {
                    predicate = builder.equal(root.get(key), value);
                }
            }

            return predicate;
        };
    }
}