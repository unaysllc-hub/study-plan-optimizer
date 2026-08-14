package net.unays.planner;

import java.util.List;

public record Plan(List<Task> selected, int usedMinutes, int totalValue) {
    public Plan {
        selected = List.copyOf(selected);
    }
}
