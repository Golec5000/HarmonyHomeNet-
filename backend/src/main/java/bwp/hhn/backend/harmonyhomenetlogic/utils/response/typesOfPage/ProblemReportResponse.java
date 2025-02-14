package bwp.hhn.backend.harmonyhomenetlogic.utils.response.typesOfPage;

import bwp.hhn.backend.harmonyhomenetlogic.utils.enums.Category;
import bwp.hhn.backend.harmonyhomenetlogic.utils.enums.ReportStatus;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ProblemReportResponse(
        Long id,
        String note,
        ReportStatus reportStatus,
        Category category,
        String userName,
        String apartmentAddress,
        Instant endDate
) {
}
