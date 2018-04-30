/*
 * Name: Zhesi Ning
 * Student Number: 12252511
 */
package NegaScoutPVSPVR;

import java.util.OptionalDouble;
import java.util.stream.*;

public class Main {

    public static void main(String[] args) {

        step2test();
        System.out.println("-------------------------------------------------------");
        for (int treeHeight = 4; treeHeight <= 6; treeHeight++) {
            for (int branchingFactor = 3; branchingFactor <= 21; branchingFactor = branchingFactor + 3) {
                for (int approx = 0; approx <= 300; approx += 50) {
                    int sumABWithReordering = 0;
                    int sumAB = 0;
                    int length = 0;

                    int sumPVSWithReordering = 0;
                    int sumPVS = 0;

                    for (int i = 0; i < 25; i++) {

                        BuildATree newTree = new BuildATree(treeHeight, branchingFactor, approx);
                        Node root = newTree.newTree();
                        AB alphaBeta = new AB(treeHeight);

                        Pv pv = alphaBeta.search(root, false);  // ab without reordering

                        sumAB = +alphaBeta.noOfEvaluationsPerformed();

                        pv = alphaBeta.search(root, true);     // ab with reordering

                        sumABWithReordering = +alphaBeta.noOfEvaluationsPerformed();
                        length++;
                        Pvs pvs = new Pvs(treeHeight);
                        pv = pvs.search(root, false);
                        sumPVS = +pvs.noOfEvaluationsPerformed();
                        pv = pvs.search(root, true);
                        sumPVSWithReordering = +pvs.noOfEvaluationsPerformed();

                    }
                    double meanAB = sumAB / length;
                    double meanABReordering = sumABWithReordering / length;

                    double meanPVS = sumPVS / length;
                    double meanPVSReordering = sumPVSWithReordering / length;
                    System.out.println("Mean of static evaluations of 25 Alpha Beta searchs with height of "
                            + treeHeight + " and branching factor = " + branchingFactor + " and Approx= "
                            + approx + "(AB without/with reordering):  " + meanAB + "/" + meanABReordering
                            + "(PVS without/with reordering):  "
                            + meanPVS + "/" + meanPVSReordering);
                 

                }
            }
        }
    }

    private static void step2test() {

        {
            BuildATree testTree = new BuildATree(4, 2, 80);
            Node testRoot = testTree.newTree();
            AB ab = new AB(4);

            System.out.println("AlphaBeta search:");
            Pv pv = ab.search(testRoot, false);
            System.out.println("No of static evaluations Performed = " + ab.noOfEvaluationsPerformed());

            System.out.println("AlphaBeta search after PVR:");
            pv = ab.search(testRoot, true);
            System.out.println(pv);

            System.out.println("No of static evaluations Performed = " + ab.noOfEvaluationsPerformed());
            System.out.println("---------------------------------------");
            System.out.println();
            Pvs pvs = new Pvs(4);

            System.out.println("PVS search:");
            pv = pvs.search(testRoot, false);
            System.out.println("No of static evaluations Performed = " + pvs.noOfEvaluationsPerformed());

            System.out.println("PVS search after reordering:");
            pv = pvs.search(testRoot, true);
            System.out.println(pv);
            System.out.println("No of static evaluations Performed = " + pvs.noOfEvaluationsPerformed());
        }
    }
}
