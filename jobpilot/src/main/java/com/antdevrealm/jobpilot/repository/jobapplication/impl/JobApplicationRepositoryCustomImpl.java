package com.antdevrealm.jobpilot.repository.jobapplication.impl;


import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.model.entity.JobApplicationEntity;
import com.antdevrealm.jobpilot.repository.jobapplication.JobApplicationRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobApplicationRepositoryCustomImpl implements JobApplicationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<JobApplicationEntity> searchApplications(StatusEnum statusEnum, String companyName, String positionName, Pageable pageable) {
        StringBuilder selectJpql = getSelectJpql(statusEnum, companyName, positionName, pageable);

        TypedQuery<JobApplicationEntity> query = entityManager.createQuery(selectJpql.toString(), JobApplicationEntity.class);
        setQueryParameters(statusEnum, companyName, positionName, query);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<JobApplicationEntity> resultList = query.getResultList();

        StringBuilder countJpql = getCountJpql(statusEnum, companyName, positionName);
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql.toString(), Long.class);
        setQueryParameters(statusEnum, companyName, positionName, countQuery);

        Long total = countQuery.getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }

    private static void setQueryParameters(StatusEnum statusEnum, String companyName, String positionName, TypedQuery<?> query) {
        if (statusEnum != null) {
            query.setParameter("status", statusEnum);
        }
        if (companyName != null && !companyName.isEmpty()) {
            query.setParameter("company", companyName);
        }
        if (positionName != null && !positionName.isEmpty()) {
            query.setParameter("position", positionName);
        }
    }

    private static StringBuilder getSelectJpql(StatusEnum statusEnum, String companyName, String positionName, Pageable pageable) {
        StringBuilder jpql = new StringBuilder("SELECT ja FROM JobApplicationEntity ja WHERE 1=1");

        if(statusEnum != null) {
            jpql.append(" AND ja.status = :status");
        }
        if(companyName != null && !companyName.isEmpty()) {
            jpql.append(" AND LOWER(ja.company) LIKE LOWER(CONCAT('%', :company))");
        }
        if(positionName != null && !positionName.isEmpty()) {
            jpql.append(" AND LOWER(ja.position) LIKE LOWER(CONCAT('%', :position))");
        }

        jpql.append(" ORDER BY ");
        pageable.getSort().forEach(order -> {
            jpql.append("ja.").append(order.getProperty())
                    .append(order.isAscending() ? " ASC" : " DESC")
                    .append(", ");
        });

        jpql.setLength(jpql.length() - 2);

        return jpql;
    }

    private static StringBuilder getCountJpql(StatusEnum statusEnum, String companyName, String positionName) {
        StringBuilder jpql = new StringBuilder("SELECT COUNT(ja) FROM JobApplicationEntity ja WHERE 1=1");

        if (statusEnum != null) {
            jpql.append(" AND ja.status = :status");
        }
        if (companyName != null && !companyName.isEmpty()) {
            jpql.append(" AND LOWER(ja.company) LIKE LOWER(CONCAT('%', :company, '%'))");
        }
        if (positionName != null && !positionName.isEmpty()) {
            jpql.append(" AND LOWER(ja.position) LIKE LOWER(CONCAT('%', :position, '%'))");
        }

        return jpql;
    }
}
