package net.unays.planner;

import java.util.List;

public final class OptimizerTest {
    public static void main(String[] args) {
        choosesHighestValueCombinationWithinBudget();
        ordersSelectedTasksByDeadline();
        rejectsInvalidInput();
        System.out.println("All optimizer tests passed.");
    }

    static void choosesHighestValueCombinationWithinBudget() {
        List<Task> tasks = List.of(
                new Task("math-review", 30, 1, 8),
                new Task("science-lab", 50, 2, 10),
                new Task("reading", 20, 5, 3));
        Plan plan = Optimizer.optimize(tasks, 50);
        assert plan.usedMinutes() == 50;
        assert plan.selected().size() == 2;
        assert plan.selected().get(0).id().equals("math-review");
        assert plan.selected().get(1).id().equals("reading");
    }

    static void ordersSelectedTasksByDeadline() {
        Plan plan = Optimizer.optimize(List.of(
                new Task("later", 10, 4, 10),
                new Task("first", 10, 1, 2)), 20);
        assert plan.selected().get(0).id().equals("first");
    }

    static void rejectsInvalidInput() {
        boolean rejected = false;
        try {
            Optimizer.optimize(List.of(), -1);
        } catch (IllegalArgumentException expected) {
            rejected = true;
        }
        assert rejected;
    }
}
