// You are the manager of a clothing manufacturing factory with a production line of super sewing machines. The
// production line consists of n super sewing machines placed in a line. Initially, each sewing machine has a certain
// number of dresses or is empty.
// For each move, you can select any m (1 <= m <= n) consecutive sewing machines on the production line and pass
// one dress from each selected sewing machine to its adjacent sewing machine simultaneously.
// Your goal is to equalize the number of dresses in all the sewing machines on the production line. You need to
// determine the minimum number of moves required to achieve this goal. If it is not possible to equalize the number
// of dresses, return -1.
// Input: [2, 1, 3, 0, 2]
// Output: 5
// Example 1:
// Imagine you have a production line with the following number of dresses in each sewing machine: [2, 1, 3, 0, 2].
// The production line has 5 sewing machines.
// Here's how the process works:
// 1. Initial state: [2, 1, 3, 0, 2]
// 2. Move 1: Pass one dress from the second sewing machine to the first sewing machine, resulting in [2, 2, 2,
// 0, 2]
// 3. Move 2: Pass one dress from the second sewing machine to the first sewing machine, resulting in [3, 1, 2,
// 0, 2]
// 4. Move 3: Pass one dress from the third sewing machine to the second sewing machine, resulting in [3, 2, 1,
// 0, 2]
// 5. Move 4: Pass one dress from the third sewing machine to the second sewing machine, resulting in [3, 3, 0,
// 0, 2]
// 6. Move 5: Pass one dress from the fourth sewing machine to the third sewing machine, resulting in [3, 3, 1,
// 0, 1]
// After these 5 moves, the number of dresses in each sewing machine is equalized to 1. Therefore, the minimum
// number of moves required to equalize the number of dresses is 5.
// [
    public class question2a {

        public int minMovesToEqualize(int[] machines) {
            int totalDresses = 0;
            int numMachines = machines.length;
    
            // Calculate the total number of dresses in all machines
            for (int machine : machines) {
                totalDresses += machine;
            }
    
            // If the total number of dresses is not divisible by the number of machines, it's not possible to equalize
            if (totalDresses % numMachines != 0) {
                return -1;
            }
    
            // Calculate the target number of dresses each machine should have
            int targetDresses = totalDresses / numMachines;
            int moves = 0;
            int dressesMoved = 0;
    
            // Iterate through the machines to perform moves
            for (int i = 0; i < numMachines - 1; i++) {
                dressesMoved += machines[i];
                // Calculate the dresses needed to equalize machines up to this point
                int dressesNeeded = targetDresses * (i + 1);
                // Calculate the dresses that can be moved from the machines after this point
                int dressesAvailable = totalDresses - dressesMoved;
                // Calculate the dresses to move in this step
                int dressesToMove = Math.max(dressesNeeded - dressesMoved, 0);
                // Update moves required
                moves = Math.max(moves, dressesToMove);
            }
            return moves;
        }
    
        public static void main(String[] args) {
            question2a equalizer = new question2a();
            int[] machines = {2, 1, 3, 0, 2};
            System.out.println(equalizer.minMovesToEqualize(machines)); // Output: 5
        }
    }    