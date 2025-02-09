package com.tennis.infra.console;

import com.tennis.application.SingleRoundUseCase;

public class ConsoleApplication {

    private final SingleRoundUseCase singleRoundUseCase;

    public ConsoleApplication(SingleRoundUseCase singleRoundUseCase) {
        this.singleRoundUseCase = singleRoundUseCase;
    }

    public void run() {
    }


}
