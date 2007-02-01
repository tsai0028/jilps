package jilps.cop4j.algebra;

/**
 * @author Sebastian Riedel
 */
public abstract class AbstractCop4JMatrix implements Cop4JMatrix {

  public void setColumn(int index, Cop4JVector column) {
    int row = 0;
    for (double value : column.getValues()){
      setValue(row++,index,value);
    }
  }


  public void setRow(int index, Cop4JVector row) {
    int column = 0;
    for (double value : row.getValues()){
      setValue(index,column++, value);
    }

  }
}
