package jilps.cop4j;

import junit.framework.TestCase;
import jilps.cop4j.algebra.Cop4JLinAlgPackageDense;
import jilps.cop4j.algebra.Cop4JVector;
import jilps.cop4j.algebra.Cop4JVectorDense;

/**
 * Created by IntelliJ IDEA. User: s0349492 Date: 12-Jan-2007 Time: 16:11:13
 */
public class TestSimplexMethodPrimal extends TestCase {

  protected SimplexMethodPrimal simplex = new SimplexMethodPrimal();

  public void test1() {
    /*
        max 1.0 x0 + 2.0 x1 + 3.0 x2
        x0 + x1  <= 1
        x1 + x2  <= 2
        x0 <= 1
        x1 <= 1
        x2 <= 1
    */
    LP lp = new LP(new Cop4JLinAlgPackageDense());
    lp.addColumn(new LPColumn(0.0, 1.0, 1.0));
    lp.addColumn(new LPColumn(0.0, 1.0, 2.0));
    lp.addColumn(new LPColumn(0.0, 1.0, 3.0));
    lp.addRow(new LPRow(new Cop4JVectorDense(1.0,1.0,0.0), Double.NEGATIVE_INFINITY, 1.0));
    lp.addRow(new LPRow(new Cop4JVectorDense(0.0,1.0,1.0), Double.NEGATIVE_INFINITY, 1.0));

    LPViewArtificial view = new LPViewArtificial(lp);
    SimplexTableauSynchronizer ts = new SimplexTableauSynchronizerArtificialBasis(view.getLP());

    simplex.solve(ts.getTableau());

    Cop4JVector solution = view.toSourceSolution(ts.getTableau().getPrimalSolution());

    System.out.println("solution = " + solution);

  }

}
