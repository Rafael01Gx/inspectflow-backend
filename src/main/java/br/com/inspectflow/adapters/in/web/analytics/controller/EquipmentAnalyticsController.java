package br.com.inspectflow.adapters.in.web.analytics.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard/equipment-analytics")
@PreAuthorize("hasRole('GESTOR')")
@RequiredArgsConstructor
public class EquipmentAnalyticsController {
}
