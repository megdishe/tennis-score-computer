package com.tennis.infra.console;

import com.tennis.application.SingleRoundUseCase;
import com.tennis.domain.exception.IncompleteGameRoundException;
import com.tennis.domain.exception.InvalidPointsSequenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConsoleApplicationTest {

    private SingleRoundUseCase singleRoundUseCase;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        singleRoundUseCase = mock(SingleRoundUseCase.class);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @ParameterizedTest
    @CsvSource({
            "'A B A B A B A', 'yes'",
            "'A A B B A', 'no'",
            "'B B B A A A A', 'yes'"
    })
    @DisplayName("Should process valid game rounds and prompt replay")
    void shouldProcessValidGameRoundsAndPromptReplay(String input, String playAgain) {
        System.setIn(new ByteArrayInputStream((input + System.lineSeparator() + playAgain + System.lineSeparator()).getBytes()));

        when(singleRoundUseCase.processGameRound(anyString())).thenReturn(List.of("Player A: 15", "Player B: 0"));

        new ConsoleApplication(singleRoundUseCase).run();

        assertThat(outputStream.toString()).contains("Welcome to Tennis Score Keeper!");
        assertThat(outputStream.toString()).contains("Thanks for playing!");
    }

    @Test
    @DisplayName("Should handle InvalidPointsSequenceException")
    void shouldHandleInvalidPointsSequenceException() {
        System.setIn(new ByteArrayInputStream(("X Y Z" + System.lineSeparator() + "no" + System.lineSeparator()).getBytes()));

        doThrow(new InvalidPointsSequenceException("Invalid sequence")).when(singleRoundUseCase).processGameRound(anyString());

        new ConsoleApplication(singleRoundUseCase).run();

        assertThat(outputStream.toString()).contains("Please Enter a valid sequence: Invalid sequence");
    }

    @Test
    @DisplayName("Should handle IncompleteGameRoundException")
    void shouldHandleIncompleteGameRoundException() {
        System.setIn(new ByteArrayInputStream(("A A B" + System.lineSeparator() + "no" + System.lineSeparator()).getBytes()));

        doThrow(new IncompleteGameRoundException()).when(singleRoundUseCase).processGameRound(anyString());

        new ConsoleApplication(singleRoundUseCase).run();

        assertThat(outputStream.toString()).contains("The game round did not reach a valid conclusion");
    }
}