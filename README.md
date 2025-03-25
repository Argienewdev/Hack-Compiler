# Hack Assembly Language Compiler (GUI Version)

## Application Overview
A simple graphical compiler that converts Hack Assembly Language (.asm) to Hack Machine Code (.hack)

The repo comes with test input files taken from https://www.nand2tetris.org/

It also comes with their compiled counterparts

## Important Assumption
⚠️ **The compiler assumes all input code is syntactically correct**  
(No validation for malformed instructions, symbols, or syntax errors)

## File Structure Conventions

programs/

├── input/ # Contains source assembly files (.asm)

│ └── [filename].asm

└── output/ # Contains compiled machine code files (.hack)

└── [filename].hack

## Naming Conventions

- **Source Files**: Must use `.asm` extension (e.g., `program.asm`)
- **Output Files**: Automatically generated with `.hack` extension, any file with the same name in the output folder will be replaced
- **Matching Names**: Input and output files share the same base name (e.g., `test.asm` → `test.hack`)

## Assembly Language Specifications

### Instruction Syntax

| Type          | Format               | Examples          |
|---------------|----------------------|-------------------|
| A-Instruction | `@value`             | `@42`, `@LOOP`    |
| C-Instruction | `dest=comp;jump`     | `M=D+1`, `D;JGT`  |

### Symbol Handling

- **Labels**: Enclosed in parentheses `(LABEL)`
- **Variables**: Prefixed with `@` (e.g., `@counter`)
- **Predefined Symbols**:
  - Memory segments: `SP`, `LCL`, `ARG`, `THIS`, `THAT`
  - I/O mappings: `SCREEN`, `KBD`
  - Virtual registers: `R0`-`R15`

### Code Formatting Rules

1. One instruction per line
2. Comments begin with `//`
3. Whitespace is ignored
4. Case-sensitive symbols

## How to Use
1. **Launch the Application**:
   ```bash
   java -jar HackCompiler.jar


## Requirements
- Java 8 or higher
- Hack Assembly source files following above conventions
