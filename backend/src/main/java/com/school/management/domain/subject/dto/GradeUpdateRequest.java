package com.school.management.domain.subject.dto;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class GradeUpdateRequest {
    private BigDecimal rawScore;
}