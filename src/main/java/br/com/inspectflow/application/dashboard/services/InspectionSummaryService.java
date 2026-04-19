package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.InspectionSummaryDto;
import br.com.inspectflow.application.dashboard.dto.MonthlyCountDto;
import br.com.inspectflow.application.dashboard.ports.in.InspectionSummaryUseCase;
import br.com.inspectflow.domain.inspection.models.Inspection;
import br.com.inspectflow.domain.inspection.repositories.InspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InspectionSummaryService implements InspectionSummaryUseCase {
    private final InspectionRepository inspectionRepository;

    @Override
    @Cacheable(value = "dashboardInspections", key = "'summary'")
    public InspectionSummaryDto execute() {
        long totalInspections = inspectionRepository.count();
        List<Inspection> allInspections = inspectionRepository.findAll();

        Map<String, Long> monthlyCounts = allInspections.stream()
                .collect(Collectors.groupingBy(
                        inspection -> {
                            LocalDateTime date = inspection.getDate();
                            return date.getYear() + "-" + String.format("%02d", date.getMonthValue());
                        },
                        Collectors.counting()
                ));

        List<MonthlyCountDto> monthlyInspections = monthlyCounts.entrySet().stream()
                .map(entry -> {
                    String[] ym = entry.getKey().split("-");
                    return new MonthlyCountDto(Integer.parseInt(ym[0]), Integer.parseInt(ym[1]), entry.getValue());
                })
                .sorted(Comparator.comparing(MonthlyCountDto::year).thenComparing(MonthlyCountDto::month))
                .collect(Collectors.toList());

        return new InspectionSummaryDto(totalInspections, monthlyInspections);
    }
}
