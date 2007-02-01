package jilps.cop4j.algebra;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: s0349492 Date: 10-Jan-2007 Time: 18:44:16
 */
public abstract class AbstractCop4JVector implements Cop4JVector {
  public void acceptOperator(Cop4JOperator visitor, int argumentIndex) {

  }

  public Cop4JVector copy() {
    return null;
  }

  public boolean isBetween(double lower, Cop4JVector upper) {
    return false;
  }

  public Cop4JVectorView getView(List<Integer> indices) {
    return null;
  }

  public boolean geq(double lower) {
    return false;
  }



  public void timesAdd(double scalar, Cop4JVector vector) {
        
  }

  public void setValue(int index, double value) {

  }


  @Override
  public String toString() {
    StringBuffer result = new StringBuffer();
    result.append("(");
    int index = 0;
    for (double v : getValues())
      if (index > 0) result.append(", ").append(v);
      else result.append(v);
    result.append(")");
    return result.toString();
  }
}
