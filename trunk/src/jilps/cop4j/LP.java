package jilps.cop4j;

import jilps.cop4j.algebra.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * An LP represents a Linear Program of the form
 * <pre>
 * max: c x
 * -inf &lt;= a &lt;= A x &lt;= b &lt;= inf
 * -inf &lt;= l &lt;=   x &lt;= u &lt;= inf
 * </pre>
 * How this LP is constructed is up to the implementing classes and might differ for different types of LP (block
 * angular, one-zero, etc., network type).
 * <p/>
 * <p>The methods which return A,c,a,b,l and u all need to be called again when the LP is changed because they might not
 * be live-copies (i.e. they're not updated automatically) and become invalid. This might even be the case for changes
 * which are not obviously affecting the the value to be returned.
 *
 * @author Sebastian Riedel
 */

public class LP {

  private ArrayList<LPListener> listeners = new ArrayList<LPListener>();
  private ArrayList<LPRow> newRows = new ArrayList<LPRow>();
  private ArrayList<LPColumn> newColumns = new ArrayList<LPColumn>();

  private Cop4JLinAlgPackage linAlgPackage;
  private Cop4JVector columnUpperBounds, columnLowerBounds, rowUpperBounds, rowLowerBounds, costs;
  private Cop4JMatrix constraintMatrix;
  private HashMap<Integer, String> columnNames, rowNames;
  private HashMap<Integer, Boolean> artificials;

  private boolean updated;

  /**
   * Creates a new LP using the specified linear algebra package to create matrices etc.
   *
   * @param linAlgPackage the matrix package to use for creation of matrices etc.
   */
  public LP(Cop4JLinAlgPackage linAlgPackage) {
    this.linAlgPackage = linAlgPackage;
    columnLowerBounds = linAlgPackage.createVector(0);
    columnUpperBounds = linAlgPackage.createVector(0);
    rowLowerBounds = linAlgPackage.createVector(0);
    rowUpperBounds = linAlgPackage.createVector(0);
    costs = linAlgPackage.createVector(0);
    constraintMatrix = linAlgPackage.createMatrix(0, 0);
    columnNames = new HashMap<Integer, String>();
    rowNames = new HashMap<Integer, String>();
    artificials = new HashMap<Integer, Boolean>();
  }

  /**
   * An LP stores a number of rows, this method returns it.
   *
   * @return the number of rows
   */
  public int getNumRows() {
    return constraintMatrix.getNumRows() + newRows.size();
  }

  /**
   * An LP stores a number of columns, this method returns it.
   *
   * @return the number of columns
   */
  public int getNumColumns() {
    return constraintMatrix.getNumCols() + newColumns.size();
  }

  /**
   * The LP uses a linear algebra package to generate constraint matrices and vectors. This method returns it.
   *
   * @return this LP's linear algebra package
   */
  public Cop4JLinAlgPackage getLinAlgPackage() {
    return linAlgPackage;
  }

  /**
   * This returns the constraint matrix A. The returned matrix is owned by the LP and might be changed if the LP is
   * changed (i.e. it's a live version of A). However, this cannot be guaranteed so clients should call
   * getConstraintMatrix after the LP has been changed. Futhermore, it should not be changed externally.
   *
   * @return the constraint matrix A.
   */
  public Cop4JMatrix getConstraintMatrix() {
    if (updated) updateMatrixForm();
    return constraintMatrix;
  }

  /**
   * Returns a vector with lower bounds on the variables. Will be invalid after changes to the LP.
   *
   * @return the lower bounds l on columns/variables.
   */
  public Cop4JVector getColumnLowerBounds() {
    if (updated) updateMatrixForm();
    return columnLowerBounds;
  }

  /**
   * Returns a vector with upper bounds on the variables. Will be invalid after changes to the LP.
   *
   * @return the upper bounds l on columns/variables.
   */
  public Cop4JVector getColumnUpperBounds() {
    if (updated) updateMatrixForm();
    return columnUpperBounds;
  }

  /**
   * Each variable is associated with a cost in the objective function. This method returns a vector
   * with these costs in order of the corresponding variables.
   *
   * @return the cost vector.
   */
  public Cop4JVector getCosts() {
    if (updated) updateMatrixForm();
    return costs;
  }

  /**
   * Returns true if this object represents a maximization problem, false if it represents a minimization problem.
   *
   * @return true if this problem is a maximization problem, false if it is a minimization problem.
   */
  public boolean isMax() {
    return true;
  }


  public Cop4JVector getRowLowerBounds() {
    if (updated) updateMatrixForm();
    return rowLowerBounds;
  }

  public Cop4JVector getRowUpperBounds() {
    if (updated) updateMatrixForm();
    return rowUpperBounds;
  }


  public List<LPColumn> getColumns() {
    return newColumns;
  }

  public List<LPRow> getRows() {
    return newRows;
  }

  /**
   * Adds listener to this LP. It will be informed whenever this LP is changed.
   *
   * @param listener a LP listener.
   */
  public void addLPListener(LPListener listener) {
    listeners.add(listener);
  }


  /**
   * A Column is artificial if it is a unit column AND defined as artificial by
   * the client that added it.
   * @param index the index of the column
   * @return true iff the column is artificial
   */
  public boolean isArtificialColumn(int index){
    Boolean result = artificials.get(index);
    return result != null && result;
  }

  /**
   * Adds a row to this LP. Columns will be updated accordingly.
   *
   * @param row the row to add.
   */
  public void addRow(LPRow row) {
    newRows.add(row);
    int index = getNumRows();
    rowNames.put(index, "r" + index);
    updated = true;
    for (LPListener listener : listeners) listener.rowAdded(row);
  }

  /**
   * Adds a column to this LP. Rows will be updated accordingly.
   *
   * @param column the column to add.
   */
  public void addColumn(LPColumn column) {
    int index = getNumColumns();
    newColumns.add(column);
    if (column.getName() != null) columnNames.put(index, column.getName());
    else columnNames.put(index, "x" + index);
    if (column.isArtificial()) artificials.put(index,true);
    updated = true;
    for (LPListener listener : listeners) listener.columnAdded(column);
  }

  /**
   * Each column can have a name. This might be userdefined or generated automatically. This method returns
   * this name for a given column index
   *
   * @param column the index of the column
   * @return the name of the column
   */
  public String getColumnName(int column) {
    return columnNames.get(column);
  }

  /**
   * Each row can have a name. This might be userdefined or generated automatically. This method returns
   * this name for a given row index
   *
   * @param row the index of the row
   * @return the name of the row
   */
  public String getRowName(int row) {
    return rowNames.get(row);
  }

  private void updateMatrixForm() {
    int currentColumn = constraintMatrix.getNumCols();
    int currentRow = constraintMatrix.getNumRows();
    constraintMatrix.extend(newRows.size(), newColumns.size());
    columnLowerBounds.extend(newColumns.size());
    columnUpperBounds.extend(newColumns.size());
    costs.extend(newColumns.size());
    for (LPColumn column : newColumns) {
      columnLowerBounds.setValue(currentColumn, column.getLowerBound());
      columnUpperBounds.setValue(currentColumn, column.getUpperBound());
      costs.setValue(currentColumn, column.getCost());
      constraintMatrix.setColumn(currentColumn, column.getValues());
      ++currentColumn;
    }

    rowLowerBounds.extend(newRows.size());
    rowUpperBounds.extend(newRows.size());    
    for (LPRow row : newRows) {
      rowLowerBounds.setValue(currentRow, row.getLowerBound());
      rowUpperBounds.setValue(currentRow, row.getUpperBound());
      constraintMatrix.setRow(currentRow, row.getValues());
      ++currentRow;
    }
    newColumns.clear();
    newRows.clear();
    updated = false;
  }

  /**
   * Creates a deep copy of this LP and returns it.
   *
   * @return a deep copy of this LP.
   */
  public LP copy() {
    LP result = new LP(linAlgPackage);
    return result;
  }


}
