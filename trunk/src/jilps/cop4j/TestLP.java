package jilps.cop4j;

/**
 * @author Sebastian Riedel
 */
public class TestLP extends LPTestCase{

  public void testLP1(){

    LPPrinter printer = new LPPrinter();

    printer.print(lp1,System.out);
    
    assertEquals(3,lp1.getNumColumns());
    assertEquals(2,lp1.getNumRows());

    assertEquals(1.0,lp1.getConstraintMatrix().getValue(0,0));
    assertEquals(1.0,lp1.getConstraintMatrix().getRow(0).getValue(0));
    assertEquals(1.0,lp1.getConstraintMatrix().getColumn(0).getValue(0));

    assertEquals(1.0,lp1.getConstraintMatrix().getValue(0,1));
    assertEquals(1.0,lp1.getConstraintMatrix().getRow(0).getValue(1));
    assertEquals(1.0,lp1.getConstraintMatrix().getColumn(1).getValue(0));

    assertEquals(0.0,lp1.getConstraintMatrix().getValue(0,2));
    assertEquals(0.0,lp1.getConstraintMatrix().getRow(0).getValue(2));
    assertEquals(0.0,lp1.getConstraintMatrix().getColumn(2).getValue(0));

  }
}
