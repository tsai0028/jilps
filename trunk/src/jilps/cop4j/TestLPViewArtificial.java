package jilps.cop4j;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA. User: s0349492 Date: 12-Jan-2007 Time: 19:00:55
 */
public class TestLPViewArtificial extends LPTestCase {

  public void testLP1() {
    LPPrinter printer = new LPPrinter();

    LPViewArtificial view = new LPViewArtificial(lp1);

    LP result = view.getLP();

    assertEquals(5, result.getNumColumns());
    assertEquals(2, result.getNumRows());
    assertEquals(1.0, result.getConstraintMatrix().getValue(0, 3));

    assertTrue(result.isArtificialColumn(3));
    assertTrue(result.isArtificialColumn(4));
    
    printer.print(result, System.out);

  }

}
