# Gremlins

Welcome to the Gremlins Game! This project is developed as part of my INFO1113 (Object Oriented Programming) assignment, using the Java programming language with the Processing library for graphics, and Gradle as the dependency manager.

## Table of Contents
- [Game Overview](#game-overview)
- [Features](#features)
- [Setup and Installation](#setup-and-installation)
- [Gameplay](#gameplay)
- [Extensions](#extensions)
- [Controls](#controls)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Game Overview
The Gremlins Game is a tile-based action game where players control a wizard who must navigate through levels filled with enemies called Gremlins. The goal is to reach the exit while avoiding enemies and their projectiles, casting spells to defeat them, and utilizing powerups.

## Features
- **Tile-based Map**: Navigate a 33x36 grid of tiles where the wizard must avoid Gremlins and find the exit to progress.
- **Gremlins**: Enemies that shoot slime and respawn after being hit.
- **Powerups**: Timed abilities that affect the player or enemies, such as speed boosts or invincibility.
- **Multiple Levels**: Progress through levels with increasing difficulty, each loaded from a configuration file.
- **Extensions**: The game includes an additional feature—portals, allowing the player to teleport across the map.

## Setup and Installation
1. Clone this repository:
   ```bash
   git clone https://github.com/ssko7098/Gremlins.git
   ```
2. Navigate to the project directory:
   ```bash
   cd Gremlins
   ```
3. Install dependencies using Gradle:
   ```bash
   gradle build
   ```
4. Run the game:
   ```bash
   gradle run
   ```

## Gameplay
The player controls a wizard who can move around the map and cast fireballs to destroy brick walls and defeat enemies. The goal is to reach the exit while avoiding enemies (Gremlins) that shoot slime projectiles. If the wizard is hit by a Gremlin or its projectile, they lose a life, and the level resets. The game ends when all lives are lost.

- **Map Layout**: Each level is defined by a configuration file (`config.json`), where the tiles represent different objects (walls, Gremlins, player spawn, and exit).
- **Gremlins**: Gremlins move randomly, shoot slime, and respawn after being hit by fireballs.
- **Powerups**: Powerups provide temporary effects, such as increased speed or invincibility.

## Extensions
This project includes an extension that introduces a new type of projectile: **Portals**. Portals allow the player to teleport between two locations on the map. The wizard can create portals using the `s` key, and once both portals are created, the player can teleport between them.

## Controls
- **Arrow Keys**: Move the wizard.
- **Space Bar**: Shoot fireballs.
- **S Key**: Shoot portal projectiles (extension feature).

## Testing
The project includes unit tests for various gameplay mechanics. To run the tests and generate a coverage report:
```bash
gradle test jacocoTestReport
```
Test cases cover over 90% of the code execution paths, including edge cases and game-specific mechanics like player movement and collision detection.

## Contributing
We welcome contributions to improve the game! Feel free to fork the repository and submit a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
