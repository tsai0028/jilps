package jilps.cop4j;

import jilps.cop4j.algebra.Cop4JMatrixView;
import jilps.cop4j.algebra.Cop4JVector;

/**
 * @author Sebastian Riedel
 */
public class SimplexMethodPrimal extends AbstractSimplexMethod {

  protected Cop4JMatrixView B, Bt, N;
  protected double t, s;
  protected PivotRule pivotRule;
  protected Cop4JVector delta_x_B, delta_z_N;

  public LPSolverStatus solve() {
    return null;
  }

  public LPSolverStatus solve(SimplexTableau tableau) {

    if (!tableau.isSolutionPrimalFeasible()) return new LPSolverStatus(false,false,false,false);

    B = tableau.getLp().getConstraintMatrix().getColumns(tableau.getBasicVariables());
    N = tableau.getLp().getConstraintMatrix().getColumns(tableau.getNonBasicVariables());
    x_B = tableau.getPrimalSolution().getView(tableau.getBasicVariables());
    z_N = tableau.getDualSolution().getView(tableau.getNonBasicVariables());

    while (!tableau.isSolutionDualFeasible()) {
      //pick entering variable
      enter = pivotRule.choose(tableau);
      //primal step direction
      delta_x_B = B.solve(N.getColumn(enter));
      //step length + leaving variable
      computePrimalStepLength();
      //if problem is unbounded we can stop here
      if (!problemBounded) return new LPSolverStatus(true,false,true,true);
      //dual step direction
      delta_z_N = N.timesTranspose(B.solveTranspose(exit, 1.0));
      //dual step size
      s = z_N.getValue(enter) / delta_z_N.getValue(enter);
      //update solution
      x_B.timesAdd(t, delta_x_B);
      x_B.setValue(exit, t);
      z_N.timesAdd(s, delta_z_N);
      z_N.setValue(enter, s);
      //update basis
      B.swapRows(exit, enter);
    }
    return new LPSolverStatus(true,true);
  }

  private void computePrimalStepLength() {

  }


}
