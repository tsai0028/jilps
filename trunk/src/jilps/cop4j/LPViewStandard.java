package jilps.cop4j;

import jilps.cop4j.algebra.Cop4JVector;

/**
 * returns an equivalent LP in which each constraint is an equality constraint and
 * has an artificial/slack variable. The derived LP is synchronized with this LP
 * (changes to this LP will change the derived LP).
 * <pre>
 * max: c x
 *      A x  = 0
 *   0 &lt;= x &lt;= u &lt;= inf
 * </pre>
 */
public class LPViewStandard implements LPView {
  public LP getLP() {
    return null;
  }

  public LP getSource() {
    return null;
  }

  public Cop4JVector toSourceSolution(Cop4JVector solution) {
    return null;
  }
}
