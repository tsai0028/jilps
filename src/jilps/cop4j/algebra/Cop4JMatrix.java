package jilps.cop4j.algebra;

import java.util.List;

/**
 * @author Sebastian Riedel
 */
public interface Cop4JMatrix {
  Cop4JMatrixView getColumns(List<Integer> indices);

  int getNumCols();

  int getNumRows();

  double getValue(int row, int column);

  void setValue(int row, int column, double value);


  Cop4JVector getColumn(int index);

  Cop4JVector getRow(int index);

  Cop4JVector solve(Cop4JVector rhs);

  Cop4JVector solveTranspose(int index, double value);

  Cop4JVector times(Cop4JVector operand);  

  Cop4JVector timesTranspose(Cop4JVector operand);

  void extend(int numRows, int numColumns);

  void setColumn(int index, Cop4JVector column);

  void setRow(int index, Cop4JVector row);
}
