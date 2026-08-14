# Study Plan Optimizer

A Java 21 command-line planner that selects the highest-value combination of study tasks within a fixed time budget. It uses dynamic programming rather than a simple greedy sort, then orders selected work by deadline and priority.

## Input

Create a CSV file with four columns:

```csv
id,durationMinutes,deadlineDay,priority
algebra-practice,35,1,9
science-report,60,2,10
history-reading,25,3,6
```

`deadlineDay` is the number of days from now. `priority` ranges from 1 to 10.

## Compile and run

```bash
mkdir -p build/classes
javac -d build/classes $(find src/main/java -name '*.java')
java -cp build/classes net.unays.planner.Main tasks.example.csv 90
```

## Algorithm

The optimizer solves a 0/1 knapsack problem where time is capacity and each task receives a value from priority and urgency. Its time complexity is `O(tasks * availableMinutes)`.

The output is a planning suggestion, not an academic judgment. Learners should adjust it for breaks, accessibility, teacher guidance and real deadlines.

## License

MIT License.
