package service;

import POJO.PositionDetails;
import notionalCalculator.NotionalCalculator;
import notionalCalculator.OptionsAndEquitiesNotionalCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotionalService implements CustomSpringService<List<NotionalCalculator>, List<PositionDetails>> {

    @Override
    public List<NotionalCalculator> execute(List<PositionDetails> positionDetails) {
        List<NotionalCalculator> notionalCalculators = new ArrayList<>();
        for(PositionDetails positionDetail: positionDetails) {
            NotionalCalculator notionalCalculator = new OptionsAndEquitiesNotionalCalculator(positionDetail);
            notionalCalculators.add(notionalCalculator);
        }
        return notionalCalculators;
    }
}
