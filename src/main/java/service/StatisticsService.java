package service;

import ENUMS.AreaCode;
import ENUMS.Status;
import model.AreaCodeStatistic;
import model.BusLine;
import repository.BusLineRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsService {

    public static Map<LocalDate, Long> getLinesCompletedLastMonth(String companyName, int weeks) {
        List<BusLine> busLines = BusLineRepository.getLineData(companyName);
        LocalDateTime queryDistance = LocalDateTime.now().minusWeeks(weeks);

        List<BusLine> filteredLines = busLines.stream()
                .filter(line -> line.getEndTime().isAfter(queryDistance) && "COMPLETED".equals(line.getStatus().name()))
                .toList();

        Map<LocalDate, Long> linesCompletedByDay = new HashMap<>();
        filteredLines.forEach(line -> {
            LocalDate endDate = line.getEndTime().toLocalDate();
            linesCompletedByDay.merge(endDate, 1L, Long::sum);
        });

        return linesCompletedByDay;
    }
    public static Map<Status, Integer> getSuccesPie(String companyName,int weeks){
        List<BusLine> busLines = BusLineRepository.getLineData(companyName);
        LocalDateTime queryDistance = LocalDateTime.now().minusWeeks(weeks);
        List<BusLine> filteredLines = busLines.stream()
                .filter(busLine -> busLine.getEndTime().isAfter(queryDistance))
                .toList();
        Map<Status, Integer> lineRatio = new HashMap<>();
        filteredLines.forEach(busLine ->{
            lineRatio.merge(busLine.getStatus(), 1, Integer::sum);
        });
        return lineRatio;
    }

    public static List<AreaCodeStatistic> getAreaCodeStatisticList(int weeks){
        List<AreaCodeStatistic> acsl = new ArrayList<>();
        for (int i = 1; i < 8;i++){
            acsl.add(BusLineRepository.getAreaCodeStatistics(AreaCode.fromCode(i), weeks));
        }
        return acsl;
    }
}

