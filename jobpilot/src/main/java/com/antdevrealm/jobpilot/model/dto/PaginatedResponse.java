package com.antdevrealm.jobpilot.model.dto;

import java.util.List;

public record PaginatedResponse<T>(
   List<T> content,
   int page,
   int size,
   long totalElements,
   int totalPages
) {}
