package jilps.cop4j;

/**
 * @author Sebastian Riedel
 */
public interface SimplexMethod extends LPSolver{

  LPSolverStatus solve(SimplexTableau tableau);

}
