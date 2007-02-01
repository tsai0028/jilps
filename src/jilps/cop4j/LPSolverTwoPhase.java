package jilps.cop4j;

import jilps.cop4j.algebra.Cop4JVector;

/**
 * @author Sebastian Riedel
 */
public class LPSolverTwoPhase implements LPSolver {

  private LP lp;
  private SimplexTableauSynchronizer tableauSynchronizer;
  private SimplexMethodPrimal primalSimplex = new SimplexMethodPrimal();
  private SimplexMethodDual dualSimplex = new SimplexMethodDual();

  private SimplexTableau tableau, modified;
  private boolean primalDual;

  public void setLP(LP lp) {
    this.lp = lp;

  }

  public LPSolverStatus solve() {
    tableau = tableauSynchronizer.getTableau();
    if (tableau.isSolutionPrimalFeasible() && tableau.isSolutionDualFeasible()) {
      return new LPSolverStatus(true, true);
    } else if (tableau.isSolutionPrimalFeasible()) {
      primalSimplex.solve(tableau);
      tableauSynchronizer.setTableau(tableau);
      return new LPSolverStatus(true, primalSimplex.getStatus().isProblemBounded(), true, true);
    } else if (tableau.isSolutionDualFeasible()) {
      dualSimplex.solve(tableau);
      tableauSynchronizer.setTableau(tableau);
      return new LPSolverStatus(true, true, true, dualSimplex.getStatus().isProblemFeasible());
    } else {
      if (primalDual) {
        makeDualFeasible();
        dualSimplex.solve(modified);
        restoreDualTableau();
        if (!dualSimplex.getStatus().isProblemFeasible()) {
          tableauSynchronizer.setTableau(tableau);
          return new LPSolverStatus(true, true, true, false);
        }
        primalSimplex.solve(tableau);
        tableauSynchronizer.setTableau(tableau);
        return new LPSolverStatus(true, primalSimplex.getStatus().isProblemBounded(), true, true);
      } else {
        makePrimalFeasible();
        primalSimplex.solve(modified);
        restorePrimalTableau();
        if (!primalSimplex.getStatus().isProblemBounded()) {
          tableauSynchronizer.setTableau(tableau);
          return new LPSolverStatus(true, false, true, true);
        }
        dualSimplex.solve(tableau);
        tableauSynchronizer.setTableau(tableau);
        return new LPSolverStatus(true, true, true, dualSimplex.getStatus().isProblemFeasible());
      }

    }

  }

  private void restorePrimalTableau() {
  }

  private void makePrimalFeasible() {

  }

  private void restoreDualTableau() {

  }

  private void makeDualFeasible() {
  }

  public Cop4JVector getSolution() {
    return tableau.getPrimalSolution();
  }

}
