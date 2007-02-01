package jilps.cop4j;

import jilps.cop4j.algebra.Cop4JVector;

/**
 * Created by IntelliJ IDEA. User: s0349492 Date: 11-Jan-2007 Time: 15:41:29
 */
public interface LPView {
  LP getLP();
  LP getSource();

  Cop4JVector toSourceSolution(Cop4JVector solution);

}
