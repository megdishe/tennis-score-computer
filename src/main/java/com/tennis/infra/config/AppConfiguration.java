package com.tennis.infra.config;

import com.tennis.application.SingleRoundUseCase;
import com.tennis.infra.console.ConsoleApplication;

public enum AppConfiguration {
    INSTANCE;

    private final ConsoleApplication consoleApplication;

    AppConfiguration() {
        final var singleRoundService = new SingleRoundUseCase();
        this.consoleApplication = new ConsoleApplication(singleRoundService);
    }

    public ConsoleApplication consoleApplication() {
        return this.consoleApplication;
    }
}
