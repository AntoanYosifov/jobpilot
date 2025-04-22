package com.antdevrealm.jobpilot.util;

import com.antdevrealm.jobpilot.model.dto.PaginatedResponse;
import org.springframework.data.domain.Page;

public class PaginationUtil {

    public static <T>PaginatedResponse<T> wrap(Page<T> page) {
        return new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
