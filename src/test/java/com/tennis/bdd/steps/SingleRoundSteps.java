package com.tennis.bdd.steps;

import com.tennis.application.SingleRoundUseCase;
import com.tennis.domain.exception.IncompleteGameRoundException;
import com.tennis.domain.exception.InvalidPointsSequenceException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import java.util.List;

public class SingleRoundSteps {

    private final SingleRoundUseCase singleRoundUseCase = new SingleRoundUseCase();
    private List<String> result;

    @When("the points sequence {string} is played in a new tennis game")
    public void thePointsSequenceIsPlayed(String sequence) {
        result = singleRoundUseCase.processGameRound(sequence);
    }

    @Then("the full score progression should be:")
    public void theFullScoreProgressionShouldBe(List<String> expectedScores) {
        Assertions.assertThat(result)
                .as("Expected a valid score progression but got null.")
                .isNotNull()
                .hasSize(expectedScores.size())
                .containsExactlyElementsOf(expectedScores);
    }

    @Then("a tennis round with sequence {string} should throw an InvalidPointsSequenceException with message {string}")
    public void aTennisRoundShouldThrowInvalidPointsSequenceException(String sequence, String expectedMessage) {
        Assertions.assertThatThrownBy(() -> singleRoundUseCase.processGameRound(sequence))
                .as("Expected InvalidPointsSequenceException")
                .isInstanceOf(InvalidPointsSequenceException.class)
                .hasMessage(expectedMessage);
    }

    @Then("a tennis round with sequence {string} should throw an IncompleteGameRoundException")
    public void aTennisRoundShouldThrowIncompleteGameRoundException(String sequence) {
        Assertions.assertThatThrownBy(() -> singleRoundUseCase.processGameRound(sequence))
                .as("Expected IncompleteGameRoundException")
                .isInstanceOf(IncompleteGameRoundException.class);
    }

}
