package jilps.cop4j;

/**
 * @author Sebastian Riedel
 */
public interface SimplexTableauSynchronizer {
  
  /**
   * This method sets the LP the synchronizer will synchronize its tableau for. 
   * @param lp the LP this synchronizer should create tableaus for
   */
  void setLP(LP lp);

  /**
   * This method return tableau for the LP. The tableau becomes invalid
   * when the LP changes. In these cases this method must be called again
   * @return a valid tableau, might not be primal or dual feasible
   */
  SimplexTableau getTableau();

  /**
   * Sets the tableau for the current LP. The synchronizer owns this LP
   * from now on. Changes to the LP will be translated to changes on this tableau.
   * @param tableau
   */
  void setTableau(SimplexTableau tableau);

}
