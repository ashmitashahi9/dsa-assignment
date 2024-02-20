// You are given a 2D grid representing a maze in a virtual game world. The grid is of size m x n and consists of
// different types of cells:
// 'P' represents an empty path where you can move freely. 'W' represents a wall that you cannot pass through. 'S'
// represents the starting point. Lowercase letters represent hidden keys. Uppercase letters represent locked doors.
// You start at the starting point 'S' and can move in any of the four cardinal directions (up, down, left, right) to
// adjacent cells. However, you cannot walk through walls ('W').
// As you explore the maze, you may come across hidden keys represented by lowercase letters. To unlock a door
// represented by an uppercase letter, you need to collect the corresponding key first. Once you have a key, you can
// pass through the corresponding locked door.
// For some 1 <= k <= 6, there is exactly one lowercase and one uppercase letter of the first k letters of the English
// alphabet in the maze. This means that there is exactly one key for each door, and one door for each key. The letters
// used to represent the keys and doors follow the English alphabet order.
// Your task is to find the minimum number of moves required to collect all the keys and reach the exit point. The
// exit point is represented by 'E'. If it is impossible to collect all the keys and reach the exit, return -1.
// Example:
// Input: grid = [ ["S","P","P","P"], ["W","P","P","E"], ["P","b","W","P"], ["P","P","P","P"] ]
// Input: grid = ["SPaPP","WWWPW","bPAPB"]
// Output: 8
// The goal is to Collect all key
import java.util.*;

public class question4a {
    public int minStepsToCollectKeys(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        // Find the starting position and count the total number of keys
        int totalKeys = 0;
        int startX = -1, startY = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startX = i;
                    startY = j;
                } else if (Character.isLowerCase(grid[i][j])) {
                    totalKeys++;
                }
            }
        }

        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        State startState = new State(startX, startY, new HashSet<>());
        queue.offer(startState);
        visited.add(startState);
        int steps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                State currState = queue.poll();
                int x = currState.x;
                int y = currState.y;
                Set<Character> keys = currState.keys;

                if (grid[x][y] == 'E' && keys.size() == totalKeys) {
                    return steps;
                }

                for (int k = 0; k < 4; k++) {
                    int nx = x + dx[k];
                    int ny = y + dy[k];

                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] != 'W') {
                        char cell = grid[nx][ny];
                        if (Character.isUpperCase(cell) && !keys.contains(Character.toLowerCase(cell))) {
                            // Need key to unlock the door
                            continue;
                        }

                        Set<Character> newKeys = new HashSet<>(keys);
                        if (Character.isLowerCase(cell)) {
                            newKeys.add(cell);
                        }

                        State nextState = new State(nx, ny, newKeys);
                        if (!visited.contains(nextState)) {
                            visited.add(nextState);
                            queue.offer(nextState);
                        }
                    }
                }
            }
            steps++;
        }

        return -1; // Unable to collect all keys and reach the exit
    }

    class State {
        int x, y;
        Set<Character> keys;

        public State(int x, int y, Set<Character> keys) {
            this.x = x;
            this.y = y;
            this.keys = new HashSet<>(keys);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, keys);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof State)) return false;
            State other = (State) obj;
            return x == other.x && y == other.y && keys.equals(other.keys);
        }
    }

    public static void main(String[] args) {
        question4a question4a = new question4a();
        char[][] grid1 = {
            {'S', 'P', 'P', 'P'},
            {'W', 'P', 'P', 'E'},
            {'P', 'b', 'W', 'P'},
            {'P', 'P', 'P', 'P'}
        };
        System.out.println(question4a.minStepsToCollectKeys(grid1)); // Output: 8

        char[][] grid2 = {
            {'S', 'P', 'a', 'P', 'P'},
            {'W', 'W', 'W', 'P', 'W'},
            {'P', 'A', 'P', 'B', 'P'}
        };
        System.out.println(question4a.minStepsToCollectKeys(grid2)); // Output: -1
    }
}
