# Neo-Veridia: Neon Shadows

Text-based adventure game written in Java.

## Story
You are a sentient machine trying to recover the Source Code stolen by Omni-Corp.
To win, you must explore the city, survive the guards, take the Source Code and reach the escape gate.

## Features
- Graph-based world with north, south, east and west movement
- Characters with health, strength, attack, defense and experience
- Weight-based inventory linked to character strength
- Weapons and shields with durability
- Cure, poison and power potions
- Containers that can be opened, closed, unlocked with a key or destroyed
- Turn-based combat with attack, defend, potion use and flee

## Main Commands
- `look`
- `status`
- `inventory`
- `north`, `south`, `east`, `west`
- `take <item>`
- `drop <item>`
- `equip <item>`
- `use <potion>`
- `open <container>`
- `close <container>`
- `unlock <container>`
- `search <container>`
- `destroy <container>`
- `talk <character>`
- `fight <character>`

## Run
Compile:

```bash
javac *.java
```

Start:

```bash
java Main
```
