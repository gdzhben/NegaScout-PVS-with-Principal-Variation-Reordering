/**
 * Student Number: 12252511
 * Name:Zhesi Ning
 */
package NegaScoutPVSPVR;

import java.util.Random;

public class BuildATree {

    private Random random;

    private int approx;
    private int MAX = 10001;
    private int heightH;
    private int branchingFactorB;

    public BuildATree(int tree_height, int branchingFactor, int approx) {
        this.heightH = tree_height;
        this.branchingFactorB = branchingFactor;
        this.approx = approx;
        random = new Random();
    }

    private int getRandomValue(int lowerBound, int upperBound) {                       //
        int value = random.nextInt(upperBound - lowerBound + 1);
        value = value + lowerBound;
        return value;
    }

    public Node newTree() {
        int T = getRandomValue(-2500, 2500);

        Node root = newTreeNode(heightH, T);
        return root;
    }

    private Node newTreeNode(int height, int T) {
        if (T == MAX || height == 0) {
            int E = T;                  // search-based value (T)
            return new Node(E);
        }

        int b = branchingFactorB;
        if (height != heightH) {
            b = getInteriorNodesBranchingFactor();
        }

        Node[] daughterArray = new Node[b];

        daughterArray[0] = newTreeNode(height - 1, -T);
        for (int i = 1; i < b; i++) {
            int daughter = getRandomValue(-T, MAX);
            daughterArray[i] = newTreeNode(height - 1, daughter);
        }
        Node temp = daughterArray[0];
        int ranPositionBestDaughter = getRandomValue(0, b - 1);

        daughterArray[0] = daughterArray[ranPositionBestDaughter];
        daughterArray[ranPositionBestDaughter] = temp;
        int E = T + getRandomValue(-approx, approx);   //estimated static evaluation value (E)
        return new Node(E, daughterArray);
    }

    private int getInteriorNodesBranchingFactor() {         //branching factor bf with 90% chance, bf+1 with 5% chance, bf-1 with 5% chance
        int bf;
        int i = random.nextInt(100);

        if (i < 90) {                                                    //b with 90% chance
            bf = branchingFactorB;
        } else if (i >= 90 && i < 95) {                                  //b+1 with 5% chance
            bf = branchingFactorB + 1;
        } else {
            bf = branchingFactorB - 1;                                 //b-1 with 5% chance
        }
        return bf;
    }

}
