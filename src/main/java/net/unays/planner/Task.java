package net.unays.planner;

public record Task(String id, int durationMinutes, int deadlineDay, int priority) {
    public Task {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id cannot be blank");
        if (durationMinutes <= 0) throw new IllegalArgumentException("duration must be positive");
        if (deadlineDay < 0) throw new IllegalArgumentException("deadline day cannot be negative");
        if (priority < 1 || priority > 10) throw new IllegalArgumentException("priority must be between 1 and 10");
    }

    public int value() {
        int urgency = Math.max(1, 15 - deadlineDay);
        return priority * 100 + urgency;
    }
}
