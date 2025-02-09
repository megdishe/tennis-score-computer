package com.tennis;

import com.tennis.infra.config.AppConfiguration;

public class TennisScoreComputerApp {
    public static void main(String[] args) {
        final AppConfiguration appConfiguration = AppConfiguration.INSTANCE;
        appConfiguration.consoleApplication().run();
    }
}