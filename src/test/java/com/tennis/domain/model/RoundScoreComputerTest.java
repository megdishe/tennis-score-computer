package com.tennis.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RoundScoreComputerTest {

    static Stream<Arguments> scoringCases() {
        return Stream.of(
                // Regular scoring progression
                Arguments.of(0, 0, "Player A: 0 / Player B: 0"),
                Arguments.of(1, 0, "Player A: 15 / Player B: 0"),
                Arguments.of(2, 0, "Player A: 30 / Player B: 0"),
                Arguments.of(3, 0, "Player A: 40 / Player B: 0"),
                Arguments.of(0, 1, "Player A: 0 / Player B: 15"),
                Arguments.of(0, 2, "Player A: 0 / Player B: 30"),
                Arguments.of(0, 3, "Player A: 0 / Player B: 40"),

                // Deuce cases
                Arguments.of(3, 3, "Deuce"),
                Arguments.of(4, 4, "Deuce"),
                Arguments.of(5, 5, "Deuce"),

                // Advantage cases
                Arguments.of(4, 3, "Advantage Player A"),
                Arguments.of(3, 4, "Advantage Player B"),
                Arguments.of(5, 4, "Advantage Player A"),
                Arguments.of(4, 5, "Advantage Player B"),

                // Winning cases
                Arguments.of(4, 2, "Player A wins the game"),
                Arguments.of(2, 4, "Player B wins the game"),
                Arguments.of(5, 3, "Player A wins the game"),
                Arguments.of(3, 5, "Player B wins the game"),
                Arguments.of(6, 4, "Player A wins the game"),
                Arguments.of(4, 6, "Player B wins the game")
        );
    }

    @ParameterizedTest
    @MethodSource("scoringCases")
    @DisplayName("Should correctly compute all possible scoring states")
    void shouldComputeScoring(int playerA, int playerB, String expectedScore) {
        assertThat(RoundScoreComputer.getScore(playerA, playerB).scoreMessage()).isEqualTo(expectedScore);
    }

}
