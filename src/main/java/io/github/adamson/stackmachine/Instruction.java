package io.github.adamson.stackmachine;

public record Instruction(OP op, Integer arg) {
    public Instruction(OP op) {
        this(op, null);
    }
}
