package service;

import model.BusLine;
import repository.BusLineRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsService {

    public static Map<LocalDate, Long> getLinesCompletedLastMonth(String companyName) {
        List<BusLine> busLines = BusLineRepository.getLineData(companyName);
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        List<BusLine> filteredLines = busLines.stream()
                .filter(line -> line.getEndTime().isAfter(oneMonthAgo) && "COMPLETED".equals(line.getStatus().name()))
                .toList();

        Map<LocalDate, Long> linesCompletedByDay = new HashMap<>();
        filteredLines.forEach(line -> {
            LocalDate endDate = line.getEndTime().toLocalDate();
            linesCompletedByDay.merge(endDate, 1L, Long::sum);
        });

        return linesCompletedByDay;
    }
}
