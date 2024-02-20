// b)
// You are provided with balanced binary tree with the target value k. return x number of values that are closest to the
// given target k. provide solution in O(n)
// Note: You have only one set of unique values x in binary search tree that are closest to the target.
// Input:
// K=3.8
// x=2
// Output: 4,5

import java.util.*;

// Define a class for tree nodes
class TreeNode {
    int val; // Value of the node
    TreeNode left, right; // Pointers to left and right children

    // Constructor to initialize the node with a value
    public TreeNode(int val) {
        this.val = val;
        left = right = null; // Initialize left and right pointers to null
    }
}

// Main class to find closest values in BST
public class question4b {

    // Method to find x closest values to the target in a BST
    public static List<Integer> closestValues(TreeNode root, double target, int x) {
        List<Integer> result = new ArrayList<>(); // Create a list to store the result
        inorderTraversal(root, result); // Perform inorder traversal to get sorted list of values

        int left = 0, right = result.size() - 1; // Initialize pointers for binary search
        // Binary search to find x closest values
        while (right - left + 1 > x) { // Keep looping until we have more than x elements
            double diffLeft = Math.abs(result.get(left) - target); // Calculate difference between target and leftmost element
            double diffRight = Math.abs(result.get(right) - target); // Calculate difference between target and rightmost element
            // Move the pointer that corresponds to the element with larger difference
            if (diffLeft > diffRight) {
                left++; // Move left pointer to the right
            } else {
                right--; // Move right pointer to the left
            }
        }

        // Return sublist of elements from left to right pointers
        return result.subList(left, right + 1);
    }

    // Method to perform inorder traversal of the BST and populate the result list
    private static void inorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) return; // Base case: if node is null, return
        inorderTraversal(root.left, result); // Traverse left subtree recursively
        result.add(root.val); // Add current node's value to the result list
        inorderTraversal(root.right, result); // Traverse right subtree recursively
    }

    // Main method to test the functionality
    public static void main(String[] args) {
        // Create a sample binary search tree
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(9);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(12);

        double target = 3.8; // Specify the target value
        int x = 2; // Specify the number of closest values required

        // Find the x closest values to the target in the BST
        List<Integer> closest = closestValues(root, target, x);

        // Print the closest values
        System.out.println("Closest values to " + target + " are: " + closest);
    }
}