package jilps.cop4j.algebra;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: s0349492 Date: 10-Jan-2007 Time: 17:54:54
 */
public class Cop4JMatrixDense extends AbstractCop4JMatrix {

  private double[] rowFormat, columnFormat;
  private int numRows, numColumns;

  public Cop4JMatrixDense(int rowCapacity, int columnCapacity) {
    rowFormat = new double[rowCapacity * columnCapacity];
    columnFormat = new double[rowCapacity * columnCapacity];
    numRows = 0;
    numColumns = 0;
  }

  public Cop4JMatrixDense(int numRows, int numColumns, double... rowData) {
    this.numColumns = numColumns;
    this.numRows = numRows;
    rowFormat = new double[rowData.length];
    System.arraycopy(rowData, 0, rowFormat, 0, rowData.length);
    columnFormat = new double[rowData.length];
    int index = 0;
    for (int row = 0; row < numRows; ++row)
      for (int col = 0; col < numColumns; ++col)
        columnFormat[col * numRows + row] = rowData[index++]; 
  }

  public Cop4JMatrixView getColumns(List<Integer> indices) {
    return null;
  }

  public int getNumCols() {
    return numColumns;
  }

  public int getNumRows() {
    return numRows;
  }

  public double getValue(int row, int column) {
    return rowFormat[row * numColumns + column];
  }


  public void setValue(int row, int column, double value) {
    rowFormat[row * numColumns + column] = value;
    columnFormat[column * numRows + row] = value;
  }

  public Cop4JVector getColumn(int index) {
    return new Cop4JVectorDense(index * numRows, (index + 1) * numRows, columnFormat);
  }

  public Cop4JVector getRow(int index) {
    return new Cop4JVectorDense(index * numColumns, (index + 1) * numColumns, rowFormat);
  }

  public Cop4JVector solve(Cop4JVector rhs) {
    return null;
  }

  public Cop4JVector solveTranspose(int index, double value) {
    return null;
  }

  public Cop4JVector times(Cop4JVector operand) {
    if (operand instanceof Cop4JVectorDense) {
      Cop4JVectorDense dense = (Cop4JVectorDense) operand;
      double[] result = new double[numRows];
      double[] otherData = dense.getData();
      int index = 0;
      for (int row = 0; row < numRows; ++ row){
        result[row] = 0;
        for (int column = 0; column < numColumns; ++column)
          result[row] += rowFormat[index++] * otherData[column];
      }
      return new Cop4JVectorDense(result);
    }
    return null;
  }

  public Cop4JVector timesTranspose(Cop4JVector operand) {
    return null;
  }

  public void extend(int numRows, int numColumns) {
    int newNumRows = this.numRows + numRows;
    int newNumCols = this.numColumns + numColumns;
    double[] newRowFormat = new double[newNumCols * newNumRows];
    double[] newColumnFormat = new double[newNumCols * newNumRows];
    int srcPointer = 0;
    int dstPointer = 0;
    for (int row = 0; row < this.numRows; ++row){
      System.arraycopy(rowFormat,srcPointer,newRowFormat, dstPointer,rowFormat.length);
      srcPointer += this.numRows;
      dstPointer += newNumRows;
    }
    srcPointer = 0;
    dstPointer = 0;
    for (int col = 0; col < this.numColumns; ++col){
      System.arraycopy(columnFormat,srcPointer,newColumnFormat, dstPointer,columnFormat.length);
      srcPointer += this.numColumns;
      dstPointer += newNumCols;
    }
    rowFormat = newRowFormat;
    columnFormat = newColumnFormat;
    this.numRows = newNumRows;
    this.numColumns = newNumCols;

  }


}
