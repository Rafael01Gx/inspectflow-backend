package br.com.inspectflow.adapters.in.web.analytics.controller;

import br.com.inspectflow.adapters.in.helpers.ExtractUserId;
import br.com.inspectflow.application.dashboard.dto.*;
import br.com.inspectflow.application.dashboard.ports.in.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dashboard/me")
@RequiredArgsConstructor
public class PersonalDashboardController {

    private final FullPersonalDashboardUseCase fullPersonalDashboard;
    private final FindRecentCompletedWorkOrdersByUserUseCase findRecentCompletedWorkOrdersByUser;
    private final FindPendingWorkOrdersByUserUseCase findPendingWorkOrdersByUser;
    private final FindWorkOrderTimelineByUserUseCase findWorkOrderTimelineByUser;
    private final PersonalActivityUseCase personalActivity;
    private final PersonalSummaryUseCase personalSummary;

    @GetMapping("/summary")
    public ResponseEntity<PersonalSummaryDto> getSummary(Authentication authentication) {
        UUID userId = ExtractUserId.fromAuthentication(authentication);
        return ResponseEntity.ok(personalSummary.execute(userId));
    }

    @GetMapping("/activity")
    public ResponseEntity<PersonalActivityResponse> getActivity(
            @RequestParam(defaultValue = "day") String groupBy, Authentication authentication
    ) {
        UUID userId = ExtractUserId.fromAuthentication(authentication);
        return ResponseEntity.ok(personalActivity.execute(userId, groupBy));
    }

    @GetMapping("/work-orders-timeline")
    public ResponseEntity<List<PersonalWorkOrderTimelineDto>> getWorkOrderTimeline(
            @RequestParam(defaultValue = "3") int months, Authentication authentication
    ) {
        UUID userId = ExtractUserId.fromAuthentication(authentication);
        int safeMonths = Math.min(months, 12);
        return ResponseEntity.ok(findWorkOrderTimelineByUser.execute(userId, safeMonths));
    }


    @GetMapping("/pending-orders")
    public ResponseEntity<List<PersonalWorkOrderSummaryDto>> getPendingOrders(Authentication authentication) {
        UUID userId = ExtractUserId.fromAuthentication(authentication);
        return ResponseEntity.ok(findPendingWorkOrdersByUser.execute(userId));
    }


    @GetMapping("/recent-completed")
    public ResponseEntity<List<PersonalWorkOrderSummaryDto>> getRecentCompleted(
            @RequestParam(defaultValue = "10") int limit, Authentication authentication
    ) {
        UUID userId = ExtractUserId.fromAuthentication(authentication);
        int safeLimit = Math.min(limit, 50);
        return ResponseEntity.ok(findRecentCompletedWorkOrdersByUser.execute(userId, safeLimit));
    }

    @GetMapping("/full")
    public ResponseEntity<PersonalDashboardFullDto> getFullDashboard(
            @RequestParam(defaultValue = "3") int months, Authentication authentication
    ) {
        UUID userId = ExtractUserId.fromAuthentication(authentication);
        return ResponseEntity.ok(fullPersonalDashboard.execute(userId, Math.min(months, 12)));
    }
}
