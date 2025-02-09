package com.tennis.domain.model;

import com.tennis.domain.exception.InvalidPointsSequenceException;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static java.lang.String.format;

public enum RoundScoreComputer {

    REGULAR(
            (firstPlayerPoints, secondPlayerPoints) -> Math.max(firstPlayerPoints, secondPlayerPoints) <= 3 && Math.min(firstPlayerPoints, secondPlayerPoints) < 3,
            (firstPlayerPoints, secondPlayerPoints) -> "Player A: " + pointToScore(firstPlayerPoints) + " / Player B: " + pointToScore(secondPlayerPoints),
            false),
    DEUCE(
            (firstPlayerPoints, secondPlayerPoints) -> firstPlayerPoints >= 3 && Objects.equals(secondPlayerPoints, firstPlayerPoints),
            (firstPlayerPoints, secondPlayerPoints) -> "Deuce",
            false),

    ADVANTAGE_A(
            (firstPlayerPoints, secondPlayerPoints) -> firstPlayerPoints == secondPlayerPoints + 1 && firstPlayerPoints >= 3,
            (firstPlayerPoints, secondPlayerPoints) -> "Advantage Player A",
            false),

    ADVANTAGE_B(
            (firstPlayerPoints, secondPlayerPoints) -> secondPlayerPoints == firstPlayerPoints + 1 && secondPlayerPoints >= 3,
            (firstPlayerPoints, secondPlayerPoints) -> "Advantage Player B",
            false),

    WIN_A(
            (firstPlayerPoints, secondPlayerPoints) -> firstPlayerPoints >= 4 && firstPlayerPoints >= secondPlayerPoints + 2,
            (firstPlayerPoints, secondPlayerPoints) -> "Player A wins the game",
            true),

    WIN_B(
            (firstPlayerPoints, secondPlayerPoints) -> secondPlayerPoints >= 4 && secondPlayerPoints >= firstPlayerPoints + 2,
            (firstPlayerPoints, secondPlayerPoints) -> "Player B wins the game",
            true);

    private final BiPredicate<Integer, Integer> condition;
    private final BiFunction<Integer, Integer, String> scoreFunction;
    private final boolean isWin;

    RoundScoreComputer(final BiPredicate<Integer, Integer> condition,
                       final BiFunction<Integer, Integer, String> scoreFunction, boolean isWin) {
        this.condition = condition;
        this.scoreFunction = scoreFunction;
        this.isWin = isWin;
    }

    public static Score getScore(final int firstPlayerPoint, final int secondPlayerPoint) {
        return Stream.of(RoundScoreComputer.values())
                .filter(roundScoreComputer -> roundScoreComputer.condition.test(firstPlayerPoint, secondPlayerPoint))
                .map(roundScoreComputer -> new Score(roundScoreComputer.scoreFunction.apply(firstPlayerPoint, secondPlayerPoint), roundScoreComputer.isWin))
                .findFirst()
                .orElseThrow(() -> new InvalidPointsSequenceException(format("Invalid point sequence: No matching tennis score state for the given points (A: %s, B: %s)", firstPlayerPoint, secondPlayerPoint)));
    }

    private static String pointToScore(int points) {
        return switch (points) {
            case 0 -> "0";
            case 1 -> "15";
            case 2 -> "30";
            case 3 -> "40";
            default -> "Invalid";
        };
    }
}
