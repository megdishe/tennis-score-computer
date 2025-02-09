package com.tennis.bdd.steps;

import com.tennis.application.SingleRoundUseCase;
import com.tennis.domain.exception.IncompleteGameRoundException;
import com.tennis.domain.exception.InvalidPointsSequenceException;
import com.tennis.infra.console.ConsoleApplication;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserInteractionsSteps {

    private final SingleRoundUseCase singleRoundUseCase = mock(SingleRoundUseCase.class);
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final StringBuilder userInputs = new StringBuilder();

    public UserInteractionsSteps() {
        System.setOut(new PrintStream(outputStream));
    }

    @When("the user plays a round with sequence {string}")
    public void theUserPlaysARoundWithSequence(String sequence) {
        userInputs.append(sequence).append(System.lineSeparator());
    }

    @And("the user chooses to play again")
    public void theUserChoosesToPlayAgain() {
        userInputs.append("yes").append(System.lineSeparator());
    }

    @And("the user chooses to exit")
    public void theUserChoosesToExit() {
        userInputs.append("no").append(System.lineSeparator());
    }

    @Then("the console should display:")
    public void theConsoleShouldDisplay(List<String> expectedOutputs) {
        System.setIn(new ByteArrayInputStream(userInputs.toString().getBytes()));

        when(singleRoundUseCase.processGameRound(anyString())).thenAnswer(invocation -> {
            String input = invocation.getArgument(0);
            return switch (input) {
                case "AAAA" -> List.of(
                        formatScore(15, 0),
                        formatScore(30, 0),
                        formatScore(40, 0),
                        "Player A wins the game"
                );
                case "BBBB" -> List.of(
                        formatScore(0, 15),
                        formatScore(0, 30),
                        formatScore(0, 40),
                        "Player B wins the game"
                );
                case "ABABABAA" -> List.of(
                        formatScore(15, 0),
                        formatScore(15, 15),
                        formatScore(30, 15),
                        formatScore(30, 30),
                        formatScore(40, 30),
                        "Deuce",
                        "Advantage Player A",
                        "Player A wins the game"
                );
                case "ABABABBB" -> List.of(
                        formatScore(15, 0),
                        formatScore(15, 15),
                        formatScore(30, 15),
                        formatScore(30, 30),
                        formatScore(40, 30),
                        "Deuce",
                        "Advantage Player B",
                        "Player B wins the game"
                );
                case "AABB" -> throw new IncompleteGameRoundException();
                case "XYYY" -> throw new InvalidPointsSequenceException("Please Enter a valid sequence: Points sequence must contain only 'A' and 'B'");
                default -> throw new InvalidPointsSequenceException("Invalid sequence");
            };
        });

        new ConsoleApplication(singleRoundUseCase).run();

        String actualConsoleOutput = outputStream.toString();
        for (String expectedOutput : expectedOutputs) {
            Assertions.assertThat(actualConsoleOutput).contains(expectedOutput);
        }
    }

    private String formatScore(int playerA, int playerB) {
        return String.format("Player A: %d / Player B: %d", playerA, playerB);
    }
}

