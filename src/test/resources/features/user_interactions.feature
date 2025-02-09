Feature: Console User Interactions

  Scenario: User plays a round where Player A wins and exits
    When the user plays a round with sequence "AAAA"
    And the user chooses to exit
    Then the console should display:
      | Player A: 15 / Player B: 0 |
      | Player A: 30 / Player B: 0 |
      | Player A: 40 / Player B: 0 |
      | Player A wins the game |
      | Game Over! Thanks for playing. |

  Scenario: User plays a round where Player B wins and exits
    When the user plays a round with sequence "BBBB"
    And the user chooses to exit
    Then the console should display:
      | Player A: 0 / Player B: 15 |
      | Player A: 0 / Player B: 30 |
      | Player A: 0 / Player B: 40 |
      | Player B wins the game |
      | Game Over! Thanks for playing. |

  Scenario: User plays a round, encounters Deuce, wins, then exits
    When the user plays a round with sequence "ABABABAA"
    And the user chooses to exit
    Then the console should display:
      | Player A: 15 / Player B: 0 |
      | Player A: 15 / Player B: 15 |
      | Player A: 30 / Player B: 15 |
      | Player A: 30 / Player B: 30 |
      | Player A: 40 / Player B: 30 |
      | Deuce |
      | Advantage Player A |
      | Player A wins the game |
      | Game Over! Thanks for playing. |

  Scenario: User plays a round, encounters Deuce, loses, then exits
    When the user plays a round with sequence "ABABABBB"
    And the user chooses to exit
    Then the console should display:
      | Player A: 15 / Player B: 0 |
      | Player A: 15 / Player B: 15 |
      | Player A: 30 / Player B: 15 |
      | Player A: 30 / Player B: 30 |
      | Player A: 40 / Player B: 30 |
      | Deuce |
      | Advantage Player B |
      | Player B wins the game |
      | Game Over! Thanks for playing. |

  Scenario: User enters invalid input and tries again
    When the user plays a round with sequence "XYYY"
    Then the console should display:
      | Please Enter a valid sequence: Points sequence must contain only 'A' and 'B' |

  Scenario: User plays an incomplete round and tries again
    When the user plays a round with sequence "AABB"
    Then the console should display:
      | The game round did not reach a valid conclusion |

  Scenario: User plays multiple rounds, then exits
    When the user plays a round with sequence "AAAA"
    And the user chooses to play again
    When the user plays a round with sequence "BBBB"
    And the user chooses to play again
    When the user plays a round with sequence "ABABABAA"
    And the user chooses to exit
    Then the console should display:
      | Player A wins the game |
      | Player B wins the game |
      | Player A wins the game |
      | Game Over! Thanks for playing. |
