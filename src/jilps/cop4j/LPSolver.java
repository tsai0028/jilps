package jilps.cop4j;

import jilps.cop4j.algebra.Cop4JVector;

/**
 * @author Sebastian Riedel
 */
public interface LPSolver {


  void setLP(LP lp);

  LPSolverStatus solve();

  Cop4JVector getSolution();


}
