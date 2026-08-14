package net.unays.planner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class Main {
    private Main() {}

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java net.unays.planner.Main <tasks.csv> <available-minutes>");
            System.exit(2);
        }
        int budget = Integer.parseInt(args[1]);
        List<Task> tasks = readTasks(Path.of(args[0]));
        Plan plan = Optimizer.optimize(tasks, budget);
        System.out.printf("Selected %d of %d tasks, using %d of %d minutes.%n",
                plan.selected().size(), tasks.size(), plan.usedMinutes(), budget);
        int start = 0;
        for (Task task : plan.selected()) {
            System.out.printf("%4d-%4d min  %-24s deadline=%d priority=%d%n",
                    start, start + task.durationMinutes(), task.id(), task.deadlineDay(), task.priority());
            start += task.durationMinutes();
        }
    }

    static List<Task> readTasks(Path path) throws IOException {
        List<Task> tasks = new ArrayList<>();
        int lineNumber = 0;
        for (String rawLine : Files.readAllLines(path)) {
            lineNumber++;
            String line = rawLine.trim();
            if (line.isEmpty() || line.startsWith("#") || line.toLowerCase().startsWith("id,")) continue;
            String[] columns = line.split(",", -1);
            if (columns.length != 4) throw new IllegalArgumentException("line " + lineNumber + " must have four columns");
            tasks.add(new Task(columns[0].trim(), Integer.parseInt(columns[1].trim()),
                    Integer.parseInt(columns[2].trim()), Integer.parseInt(columns[3].trim())));
        }
        return tasks;
    }
}
