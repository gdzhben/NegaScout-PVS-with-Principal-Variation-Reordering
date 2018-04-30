/**
 * Student Number: 12252511
 * Name:Zhesi Ning
 */
package NegaScoutPVSPVR;

public class Pvs {

    private int height = 0;
    private int count = 0;

    public Pvs(int h) {
        this.height = h;
    }

    public Pv search(Node root, boolean PVR) {
        count = 0;
        Pv pv = null;
        for (int i = 0; i < height; i++) {
            pv = pvs(root, i, -10000, +10000, PVR);

            pv.setScore(-pv.getScore());
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

    public Pv pvs(Node node, int h, int a, int b, boolean PVR) {
        if (h == 0 || !node.isInterior()) {
            count++;
            return new Pv(-node.evaluationValue());
        }
        Node[] daughters;
        if (PVR == true) {
            daughters = node.daughtersAfterReordering();

        } else {
            daughters = node.daughters();
        }

        Node best;

        Pv score = pvs(daughters[0], h - 1, -b, -a, PVR);
        best = daughters[0];
        if (score.getScore() < b) {
            for (int i = 1; i < daughters.length; i++) {
                int LowerBound = a;
                if (score.getScore() > a) {
                    LowerBound = score.getScore();
                }
                int UpperBound = LowerBound + 1;

                Pv temp = pvs(daughters[i], h - 1, -UpperBound, -LowerBound, PVR);

                if (temp.getScore() >= UpperBound && temp.getScore() < b) {
                    temp = pvs(daughters[i], h - 1, -b, -temp.getScore(), PVR);
                }

                if (temp.getScore() > score.getScore()) {
                    score = temp;
                    best = daughters[i];
                } else if (temp.getScore() >= b) {
                    break;
                }
            }
        }
        return new Pv(-score.getScore(), score.getPv(), best);
    }

    public int noOfEvaluationsPerformed() {
        return count;
    }
}
