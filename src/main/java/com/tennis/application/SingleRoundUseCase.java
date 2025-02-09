package com.tennis.application;

import com.tennis.domain.exception.IncompleteGameRoundException;
import com.tennis.domain.exception.InvalidPointsSequenceException;
import com.tennis.domain.model.Round;
import com.tennis.domain.model.Score;

import java.util.List;

import static com.tennis.domain.model.Round.initializeRound;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.containsOnly;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class SingleRoundUseCase {

    public List<String> processGameRound(final String pointsSequence) {
        final Round round = initializeRound();
        validatePointsSequence(pointsSequence);
        final String capitalizedPointSequence = capitalize(pointsSequence);
        capitalizedPointSequence.chars()
                .mapToObj(c -> (char) c)
                .forEach(round::registerPoint);
        if (round.isNotFinished()) {
            throw new IncompleteGameRoundException();
        }
        return round.scores().stream()
                .map(Score::scoreMessage)
                .toList();
    }

    private void validatePointsSequence(final String pointsSequence) {
        if (isBlank(pointsSequence)) {
            throw new InvalidPointsSequenceException("Invalid input: Sequence cannot be empty.");
        }
        if (!containsOnly(capitalize(pointsSequence), 'A', 'B')) {
            throw new InvalidPointsSequenceException("Points sequence must contain only 'A' and 'B'");
        }
    }

}
