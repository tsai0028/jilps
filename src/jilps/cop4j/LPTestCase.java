package jilps.cop4j;

import junit.framework.TestCase;
import jilps.cop4j.algebra.Cop4JLinAlgPackageDense;
import jilps.cop4j.algebra.Cop4JVectorDense;

/**
 * @author Sebastian Riedel
 */
public abstract class LPTestCase extends TestCase {
  protected LP lp1;


  protected void setUp() throws Exception {
    super.setUp();
    /*
        max 1.0 x0 + 2.0 x1 + 3.0 x2
        x0 + x1  <= 1
        x1 + x2  <= 2
        x0 <= 1
        x1 <= 1
        x2 <= 1
    */
    lp1 = new LP(new Cop4JLinAlgPackageDense());
    lp1.addColumn(new LPColumn(-1.0, 1.0, 1.0));
    lp1.addColumn(new LPColumn(Double.NEGATIVE_INFINITY, 1.0, 2.0));
    lp1.addColumn(new LPColumn(0.0, 1.0, 3.0));
    lp1.addRow(new LPRow(new Cop4JVectorDense(1.0,1.0,0.0), Double.NEGATIVE_INFINITY, 1.0));
    lp1.addRow(new LPRow(new Cop4JVectorDense(0.0,1.0,1.0), Double.NEGATIVE_INFINITY, 1.0));

  }
}
