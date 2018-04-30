/**
 * Student Number: 12252511
 * Name:Zhesi Ning
 */
package NegaScoutPVSPVR;

import java.util.Arrays;

public class Node {

    private Node[] daughters;
    private Node[] daughtersAfterReordering;
    private int evaluationScore;

    public Node(int evaluationScore) {
        this.evaluationScore = evaluationScore;
        this.daughters = null;
        this.daughtersAfterReordering = null;
    }

    public Node(int evaluationValue, Node[] daughters) {
        this.evaluationScore = evaluationValue;
        this.daughters = daughters;
        this.daughtersAfterReordering = Arrays.copyOf(daughters, daughters.length);
    }

    public Node[] daughters() {
        if (!isInterior()) {
            return null;
        }
        return Arrays.copyOf(daughters, daughters.length);
    }

    public boolean isInterior() {
        if (daughters == null || daughters.length == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void pvReordering(Node node) {
        if (daughters == null) {
            return;
        }
        daughtersAfterReordering = new Node[daughters.length];
        int index = 1;

        for (int i = 0; i < daughters.length; i++) {
            if (daughters[i] == node) {
                daughtersAfterReordering[0] = daughters[i];
            } else {
                daughtersAfterReordering[index++] = daughters[i];
            }
        }
    }

    public int evaluationValue() {
        return evaluationScore;
    }

    public Node[] daughtersAfterReordering() {
        if (!isInterior()) {

            return null;
        }

        return Arrays.copyOf(daughtersAfterReordering, daughtersAfterReordering.length);
    }

}
