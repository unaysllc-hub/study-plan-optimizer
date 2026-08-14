package net.unays.planner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Optimizer {
    private Optimizer() {}

    public static Plan optimize(List<Task> tasks, int availableMinutes) {
        if (availableMinutes < 0) throw new IllegalArgumentException("available minutes cannot be negative");
        int count = tasks.size();
        int[][] best = new int[count + 1][availableMinutes + 1];

        for (int item = 1; item <= count; item++) {
            Task task = tasks.get(item - 1);
            for (int minutes = 0; minutes <= availableMinutes; minutes++) {
                best[item][minutes] = best[item - 1][minutes];
                if (task.durationMinutes() <= minutes) {
                    int candidate = best[item - 1][minutes - task.durationMinutes()] + task.value();
                    if (candidate > best[item][minutes]) best[item][minutes] = candidate;
                }
            }
        }

        List<Task> selected = new ArrayList<>();
        int remaining = availableMinutes;
        for (int item = count; item > 0; item--) {
            if (best[item][remaining] != best[item - 1][remaining]) {
                Task task = tasks.get(item - 1);
                selected.add(task);
                remaining -= task.durationMinutes();
            }
        }
        selected.sort(Comparator.comparingInt(Task::deadlineDay)
                .thenComparing(Comparator.comparingInt(Task::priority).reversed())
                .thenComparing(Task::id));
        int used = selected.stream().mapToInt(Task::durationMinutes).sum();
        return new Plan(selected, used, best[count][availableMinutes]);
    }
}
