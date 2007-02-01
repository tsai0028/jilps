package jilps.cop4j;

import jilps.cop4j.algebra.Cop4JVector;
import jilps.cop4j.algebra.Cop4JMatrix;
import jilps.cop4j.algebra.Cop4JVectorSparse;
import jilps.cop4j.algebra.Cop4JVectorEntry;

import java.util.HashMap;
import java.util.List;
import java.util.HashSet;

/**
 * returns an equivalent LP in which each constraint is an equality constraint and has an artificial/slack variable.
 * The derived LP is synchronized with this LP (changes to this LP will change the derived LP).
 * <pre>
 * max: c x
 *      A x  = 0
 *   0 &lt;= x &lt;= u &lt;= inf
 * </pre>
 */
public class LPViewArtificial implements LPView, LPListener {
  private LP parent, child;
  private HashMap<Integer, Double> deltas = new HashMap<Integer, Double>();
  private HashSet<Integer> signChange = new HashSet<Integer>();
  private HashMap<Integer, Integer> indexOffsets = new HashMap<Integer, Integer>();
  private int offset = 0;

  public LPViewArtificial(LP parent) {
    this.parent = parent;
    this.child = new LP(parent.getLinAlgPackage());
    Cop4JMatrix A = parent.getConstraintMatrix();
    Cop4JVector l = parent.getColumnLowerBounds();
    Cop4JVector u = parent.getColumnUpperBounds();
    Cop4JVector a = parent.getRowLowerBounds();
    Cop4JVector b = parent.getRowUpperBounds();
    Cop4JVector c = parent.getCosts();

    int newNumRows = A.getNumRows();

    for (int column = 0; column < A.getNumCols(); ++column) {

      double lValue = l.getValue(column);
      double uValue = u.getValue(column);


      if (lValue == Double.NEGATIVE_INFINITY && uValue != Double.POSITIVE_INFINITY){
        lValue = -uValue; 
        uValue = Double.POSITIVE_INFINITY;
        signChange.add(column);
      }
      if (lValue != 0 && lValue != Double.NEGATIVE_INFINITY){
        deltas.put(column, lValue);
        uValue-= lValue;
        lValue = 0;
      }

      child.addColumn(new LPColumn(lValue, uValue, c.getValue(column)));


    }
    for (int row = 0; row < A.getNumRows(); ++row) {
      double bValue = b.getValue(row);
      double aValue = a.getValue(row);
      Cop4JVector rowData = A.getRow(row);
      double offset = 0;
      for (Cop4JVectorEntry entry : rowData.getNonZeroValues()){
        int col = entry.getIndex();
        double value = entry.getValue();
        if (signChange.contains(col)) {
          rowData.setValue(col, -value);
          value = -value;
        }
        Double delta = deltas.get(col);
        if (delta != null){
          offset += value * delta;    
        }
      }
      aValue += offset;
      bValue += offset;
      child.addRow(new LPRow(rowData, bValue, bValue));
      child.addColumn(new LPColumn(new Cop4JVectorSparse(newNumRows, row, 1.0),
              0, -aValue + bValue, 0.0, true));
    }
    parent.addLPListener(this);
  }

  public LP getSource() {
    return parent;
  }

  public LP getLP() {
    return child;
  }

  public Cop4JVector toSourceSolution(Cop4JVector solution) {
    Cop4JVector result = parent.getLinAlgPackage().createVector(parent.getNumColumns());
    return result;
  }

  public void columnAdded(LPColumn column) {
    child.addColumn(column);
  }

  public void columnsAdded(List<LPColumn> columns) {
    for (LPColumn column : columns) columnAdded(column);
  }

  public void rowAdded(LPRow row) {
    //rescale 
    child.addRow(new LPRow(row.getValues(), 0, 0));
    child.addColumn(new LPColumn(new Cop4JVectorSparse(child.getNumRows(), child.getNumRows() - 1, -1.0),
            row.getLowerBound(), row.getUpperBound(), 0.0, true));
    ++offset;
  }

  public void rowsAdded(List<LPRow> rows) {
    for (LPRow row : rows) rowAdded(row);
  }
}


