package jilps.cop4j;

import jilps.cop4j.algebra.Cop4JVector;
import jilps.cop4j.algebra.Cop4JMatrix;

import java.util.List;

/**
 * @author Sebastian Riedel
 */
public abstract class AbstractSimplexMethod implements SimplexMethod {

  protected Cop4JMatrix A;
  protected Cop4JVector x_B, z_N, l, u;
  protected List<Integer> basic, nonBasic;
  protected int enter, exit;
  protected double eps;
  protected SimplexTableau tableau;
  protected LP lp;
  protected LPSolverStatus status;

  protected boolean solverFinished, problemFeasible, problemBounded,
    solutionPrimalFeasible, solutionDualFeasible;


  public LPSolverStatus getStatus() {
    return status;
  }

  public SimplexTableau getTableau() {
    return tableau;
  }

  public void setTableau(SimplexTableau tableau) {
    this.tableau = tableau;
  }

  public void setLP(LP lp) {
    this.lp = lp;
  }

  public Cop4JVector getSolution() {
    return null;
  }
}
