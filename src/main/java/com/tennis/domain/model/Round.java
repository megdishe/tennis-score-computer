package com.tennis.domain.model;

import com.tennis.domain.exception.InvalidPointsSequenceException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public record Round(AtomicInteger firstPlayerPoints, AtomicInteger secondPlayerPoints, List<Score> scores) {

    public static Round initializeRound() {
        return new Round(new AtomicInteger(0), new AtomicInteger(0), new ArrayList<>());
    }

    public void registerPoint(Character player) {
        if (this.scores.stream().anyMatch(Score::isWin)) {
            throw new InvalidPointsSequenceException("The round is already finished.");
        }
        switch (player) {
            case 'A' -> this.firstPlayerPoints.incrementAndGet();
            case 'B' -> this.secondPlayerPoints.incrementAndGet();
            default -> throw new InvalidPointsSequenceException("Invalid player: " + player);
        }
        final var score = RoundScoreComputer.getScore(firstPlayerPoints.get(), secondPlayerPoints.get());
        this.scores.add(score);
    }

    public boolean isNotFinished() {
        return this.scores.stream()
                .noneMatch(Score::isWin);
    }
}