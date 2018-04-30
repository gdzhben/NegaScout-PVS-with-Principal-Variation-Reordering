/**
 * Student Number: 12252511 Name:Zhesi Ning
 */
package NegaScoutPVSPVR;

import java.util.ArrayList;
import java.util.List;

public class AB {

    private int height;
    private int count = 0;

    public Pv search(Node root, boolean PVR) {
        count = 0;
        Pv pv = null;
        for (int i = 0; i < height; i++) {
            pv = aBsearch(root, i, -10000, +10000, PVR);
            if (PVR == true) {
                Node current = root;

                for (Node node : pv.getPv()) {

                    current.pvReordering(node);
                    current = node;

                }
            }
        }
        return pv;
    }

    public AB(int height) {
        this.height = height;
    }

    public Pv aBsearch(Node node, int h, int a, int b, boolean PVR) {
        if (h == 0 || !node.isInterior()) {
            count++;
            return new Pv(node.evaluationValue());
        }

        Node best = null;
        Pv pvOfBest = null;
        Node[] daughters;
        if (PVR == true) {
            daughters = node.daughtersAfterReordering();

        } else {
            daughters = node.daughters();
        }

        for (Node daughter : daughters) {
            Pv temp = aBsearch(daughter, h - 1, -b, -a, PVR);
            temp.setScore(-temp.getScore());

            if (temp.getScore() >= b) {
                return new Pv(b);
            }

            if (temp.getScore() > a) {
                pvOfBest = temp;
                best = daughter;
                a = temp.getScore();
            }
        }

        if (pvOfBest == null) {

            return new Pv(a);
        }

        return new Pv(pvOfBest.getScore(), pvOfBest.getPv(), best);
    }

    public int noOfEvaluationsPerformed() {
        return count;
    }
}

class Pv {

    private int evaluationScore;
    private List<Node> pvs = new ArrayList<>();

    @Override
    public String toString() {
        String opt = "";
        for (Node pv : pvs) {
            opt += pv.evaluationValue() + ">>>>>>>>>";
        }

        return String.format("--------Score = %1$d-----------"
                + "\n--------- Principal Variation  = [%2$s]---------", this.evaluationScore, opt);
    }

    public Pv(int evaluationValue) {
        this.evaluationScore = evaluationValue;
    }

    public Pv(int evaluationValue, List<Node> nodeValues, Node node) {
        this.evaluationScore = evaluationValue;
        this.pvs.add(node);
        this.pvs.addAll(nodeValues);
    }

    public List<Node> getPv() {
        return pvs;
    }

    public int getScore() {
        return evaluationScore;
    }

    public void setScore(int score) {
        evaluationScore = score;
    }

}
