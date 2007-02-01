package jilps.cop4j;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: s0349492 Date: 12-Jan-2007 Time: 16:52:40
 */
public class SimplexTableauSynchronizerArtificialBasis implements SimplexTableauSynchronizer, LPListener {

  protected LP lp;
  protected SimplexTableau tableau;

  public SimplexTableauSynchronizerArtificialBasis(LP lp) {
    this.lp = lp;
    tableau = new SimplexTableau(lp);
    int index = 0;
    for (LPColumn column : lp.getColumns()) {
      if (column.isArtificial()) tableau.getBasicVariables().add(index++);
      else tableau.getNonBasicVariables().add(index++);
    }
    if (tableau.getBasicVariables().size() != lp.getNumRows())
      throw new IllegalArgumentException("");
  }

  public void setLP(LP lp) {
    this.lp = lp;
    lp.addLPListener(this);
  }

  public SimplexTableau getTableau() {
    return tableau;
  }

  public void setTableau(SimplexTableau tableau) {
    this.tableau = tableau;
  }

  public void columnAdded(LPColumn column) {
    if (column.isArtificial()) tableau.getBasicVariables().add(lp.getNumColumns()-1);
    else tableau.getNonBasicVariables().add(lp.getNumColumns()-1);
  }

  public void columnsAdded(List<LPColumn> columns) {

  }

  public void rowAdded(LPRow row) {
    
  }

  public void rowsAdded(List<LPRow> rows) {

  }
}
