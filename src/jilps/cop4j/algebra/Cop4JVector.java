package jilps.cop4j.algebra;

import java.util.List;

/**
 * @author Sebastian Riedel
 */
public interface Cop4JVector {

  void acceptOperator(Cop4JOperator visitor, int argumentIndex);

  Cop4JVector copy();

  boolean isBetween(double lower, Cop4JVector upper);

  Cop4JVectorView getView(List<Integer> indices);

  boolean geq(double lower);

  double getValue(int index);

  void timesAdd(double scalar, Cop4JVector vector);

  void setValue(int index, double value);

  int getDimension();

  void append(double... values);

  Iterable<Double> getValues();

  Iterable<? extends Cop4JVectorEntry> getNonZeroValues();

  void extend(int size);
}
