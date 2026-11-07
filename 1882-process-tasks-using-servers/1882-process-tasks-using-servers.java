import java.util.*;

class Solution {
    private static class Server {
        int weight;
        int index;
        long freeTime;

        Server(int weight, int index, long freeTime) {
            this.weight = weight;
            this.index = index;
            this.freeTime = freeTime;
        }
    }

    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m = tasks.length;
        int[] ans = new int[m];

        // Free servers heap: sorted by weight (ascending), then index (ascending)
        PriorityQueue<Server> free = new PriorityQueue<>((a, b) -> {
            if (a.weight != b.weight) {
                return Integer.compare(a.weight, b.weight);
            }
            return Integer.compare(a.index, b.index);
        });

        // Used (busy) servers heap: sorted by freeTime (ascending), then weight, then index
        PriorityQueue<Server> used = new PriorityQueue<>((a, b) -> {
            if (a.freeTime != b.freeTime) {
                return Long.compare(a.freeTime, b.freeTime);
            }
            if (a.weight != b.weight) {
                return Integer.compare(a.weight, b.weight);
            }
            return Integer.compare(a.index, b.index);
        });

        // Initialize all servers as free at time 0
        for (int i = 0; i < n; i++) {
            free.offer(new Server(servers[i], i, 0));
        }

        long currentTime = 0;
        for (int i = 0; i < m; i++) {
            currentTime = Math.max(currentTime, i);

            // Move all servers that become free at or before `currentTime` back to `free`
            while (!used.isEmpty() && used.peek().freeTime <= currentTime) {
                free.offer(used.poll());
            }

            // If no servers are free, fast-forward time to the earliest server release time
            if (free.isEmpty()) {
                currentTime = used.peek().freeTime;
                while (!used.isEmpty() && used.peek().freeTime <= currentTime) {
                    free.offer(used.poll());
                }
            }

            // Assign the current task to the best available free server
            Server server = free.poll();
            ans[i] = server.index;
            server.freeTime = currentTime + tasks[i];
            used.offer(server);
        }

        return ans;
    }
}
