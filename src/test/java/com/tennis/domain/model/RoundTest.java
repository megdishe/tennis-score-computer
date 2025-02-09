package com.tennis.domain.model;

import com.tennis.domain.exception.InvalidPointsSequenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoundTest {

    private Round round;

    @BeforeEach
    void setUp() {
        round = Round.initializeRound();
    }

    @Test
    @DisplayName("Should initialize round with zero points")
    void shouldInitializeWithZeroPoints() {
        assertThat(round.firstPlayerPoints().get()).isEqualTo(0);
        assertThat(round.secondPlayerPoints().get()).isEqualTo(0);
        assertThat(round.scores()).isEmpty();
    }

    @Test
    @DisplayName("Should correctly register points for Player A")
    void shouldRegisterPointForPlayerA() {
        round.registerPoint('A');
        assertThat(round.firstPlayerPoints().get()).isEqualTo(1);
        assertThat(round.secondPlayerPoints().get()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should correctly register points for Player B")
    void shouldRegisterPointForPlayerB() {
        round.registerPoint('B');
        assertThat(round.firstPlayerPoints().get()).isEqualTo(0);
        assertThat(round.secondPlayerPoints().get()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should throw exception when registering point after game is won")
    void shouldThrowExceptionIfGameIsAlreadyWon() {
        // Simulate Player A winning
        round.registerPoint('A');
        round.registerPoint('A');
        round.registerPoint('A');
        round.registerPoint('A'); // A wins

        assertThatThrownBy(() -> round.registerPoint('A'))
                .isInstanceOf(InvalidPointsSequenceException.class)
                .hasMessage("The round is already finished.");
    }

    @Test
    @DisplayName("Should throw exception for invalid player input")
    void shouldThrowExceptionForInvalidPlayerInput() {
        assertThatThrownBy(() -> round.registerPoint('X'))
                .isInstanceOf(InvalidPointsSequenceException.class)
                .hasMessage("Invalid player: X");
    }

    @Test
    @DisplayName("Should correctly determine if the round is unfinished")
    void shouldReturnTrueIfRoundIsNotFinished() {
        round.registerPoint('A');
        round.registerPoint('B');
        assertThat(round.isNotFinished()).isTrue();
    }

    @Test
    @DisplayName("Should correctly determine if the round is finished")
    void shouldReturnFalseIfRoundIsFinished() {
        round.registerPoint('A');
        round.registerPoint('A');
        round.registerPoint('A');
        round.registerPoint('A');
        assertThat(round.isNotFinished()).isFalse();
    }
}
