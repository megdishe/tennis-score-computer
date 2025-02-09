package com.tennis.infra.console;

import com.tennis.application.SingleRoundUseCase;
import com.tennis.domain.exception.IncompleteGameRoundException;
import com.tennis.domain.exception.InvalidPointsSequenceException;

import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.trim;

public class ConsoleApplication {

    private final SingleRoundUseCase singleRoundUseCase;

    public ConsoleApplication(SingleRoundUseCase singleRoundUseCase) {
        this.singleRoundUseCase = singleRoundUseCase;
    }

    public void run() {
        try (var scanner = new Scanner(System.in)) {
            System.out.println("Welcome to Tennis Score Keeper!");
            boolean playAgain;
            do {
                System.out.println("Enter the sequence of points (A/B):");
                var input = scanner.hasNext() ? trim(scanner.nextLine()) : EMPTY;
                processInput(input);
                playAgain = scanner.hasNext() && "yes".equals(trim(scanner.nextLine()));
            } while (playAgain);
        }
        System.out.println("Game Over! Thanks for playing.");
    }

    private void processInput(String input) {
        try {
            singleRoundUseCase.processGameRound(input).forEach(System.out::println);
            System.out.println("""
                    Thanks for playing!
                    Do you want to play another round? (yes to continue, any other input to exit)
                    """);
        } catch (InvalidPointsSequenceException e) {
            System.out.println("Please Enter a valid sequence: " + e.getMessage());
        } catch (IncompleteGameRoundException e) {
            System.out.println("The game round did not reach a valid conclusion");
        }
    }
}
