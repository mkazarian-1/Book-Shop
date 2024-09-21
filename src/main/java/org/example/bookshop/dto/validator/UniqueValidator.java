package org.example.bookshop.dto.validator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<Unique,String> {

    private final EntityManager entityManager;
    private Class<?> entityClass;
    private String fieldName;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String queryStr = "SELECT COUNT(e) FROM " + entityClass.getSimpleName()
                + " e WHERE e." + fieldName + " = :value";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("value", value);
        return (Long) query.getSingleResult() == 0;
    }
}
