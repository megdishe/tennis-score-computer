Feature: Single Round Tennis Game Scoring

  Scenario: Player A wins with a normal sequence
    When the points sequence "AAABBA" is played in a new tennis game
    Then the full score progression should be:
      | Player A: 15 / Player B: 0  |
      | Player A: 30 / Player B: 0  |
      | Player A: 40 / Player B: 0  |
      | Player A: 40 / Player B: 15 |
      | Player A: 40 / Player B: 30 |
      | Player A wins the game      |

  Scenario: Player B wins after a deuce and advantage
    When the points sequence "ABABABBB" is played in a new tennis game
    Then the full score progression should be:
      | Player A: 15 / Player B: 0  |
      | Player A: 15 / Player B: 15 |
      | Player A: 30 / Player B: 15 |
      | Player A: 30 / Player B: 30 |
      | Player A: 40 / Player B: 30 |
      | Deuce                       |
      | Advantage Player B          |
      | Player B wins the game      |

  Scenario: A very complex round that ends with Player A winning
    When the points sequence "ABABABBAABBAAA" is played in a new tennis game
    Then the full score progression should be:
      | Player A: 15 / Player B: 0  |
      | Player A: 15 / Player B: 15 |
      | Player A: 30 / Player B: 15 |
      | Player A: 30 / Player B: 30 |
      | Player A: 40 / Player B: 30 |
      | Deuce                       |
      | Advantage Player B          |
      | Deuce                       |
      | Advantage Player A          |
      | Deuce                       |
      | Advantage Player B          |
      | Deuce                       |
      | Advantage Player A          |
      | Player A wins the game      |

  Scenario Outline: Invalid input should throw an exception
    Then a tennis round with sequence "<sequence>" should throw an InvalidPointsSequenceException with message "<message>"
    Examples:
      | sequence | message                                       |
      | AXBB     | Points sequence must contain only 'A' and 'B' |
      |          | Invalid input: Sequence cannot be empty.      |

  Scenario Outline: Incomplete tennis round should be rejected
    Then a tennis round with sequence "<sequence>" should throw an IncompleteGameRoundException

    Examples:
      | sequence |
      | AABB     |
      | AA       |
      | ABB      |
      | AABBA    |
      | BBB      |
      | ABABAB   |