package jilps.cop4j.algebra;

import java.util.*;

/**
 * Created by IntelliJ IDEA. User: s0349492 Date: 10-Jan-2007 Time: 18:21:52
 */
public class Cop4JVectorDense extends AbstractCop4JVector implements Iterable<Double> {

  private double[] data;
  private int dimension;

  public Cop4JVectorDense(int capacity) {
    data = new double[capacity];
    dimension = 0;
  }

  public Cop4JVectorDense(double... data) {
    this.data = new double[data.length];
    System.arraycopy(data, 0, this.data, 0, data.length);
    dimension = data.length;
  }

  public Cop4JVectorDense(int from, int to, double... data) {
    this.data = new double[to - from];
    System.arraycopy(data, from, this.data, 0, this.data.length);
    dimension = this.data.length;
  }


  public int getDimension() {
    return dimension;
  }

  public void append(double... values) {
    if (dimension + values.length > data.length) {
      double[] newData = new double[dimension + values.length * 10];
      System.arraycopy(data,0,newData, 0, data.length);
    }
    for (double value : values) data[dimension++] = value;
  }


  public Iterable<Double> getValues() {
    return this;
  }

  public Iterable<? extends Cop4JVectorEntry> getNonZeroValues() {
    int index = 0;
    for (; index < dimension;++index){
      if (data[index] != 0) break;
    }
    final int first = index;
    return new Iterable<Cop4JVectorEntry>() {
      public Iterator<Cop4JVectorEntry> iterator() {
        return new Iterator<Cop4JVectorEntry>() {
          int current = first;
          public boolean hasNext() {
            return current < dimension;
          }

          public Cop4JVectorEntry next() {
            Cop4JVectorEntry result = new Cop4JVectorEntry(current,data[current]);
            ++current;
            for (;current < dimension; ++current)
              if (data[current] != 0) break;
            return result;
          }

          public void remove() {

          }
        };
      }
    };
  }

  public Iterator<Double> iterator() {
    return new Iterator<Double>() {
      int current = 0;
      public boolean hasNext() {
        return current < dimension;
      }

      public Double next() {
        return data[current++];
      }

      public void remove() {

      }
    };
  }

  public List<Double> asList() {
    ArrayList<Double> result = new ArrayList<Double>(dimension);
    for (int i = 0; i < dimension; ++i) result.add(data[i]);
    return result;
  }

  public Map<Integer, Double> asMap() {
    HashMap<Integer, Double> result = new HashMap<Integer, Double>();
    for (int i = 0; i < dimension; ++i) {
      double v = data[i];
      if (v != 0.0) result.put(i, v);
    }
    return result;
  }

  public void extend(int size) {
    double[] newData = new double[dimension + size];
    System.arraycopy(data,0,newData, 0, dimension);
    data = newData;
    this.dimension+=size;
  }

  public Cop4JVector copy() {
    return new Cop4JVectorDense(data);
  }

  public boolean isBetween(double lower, Cop4JVector upper) {
    if (upper instanceof Cop4JVectorDense) {
      Cop4JVectorDense dense = (Cop4JVectorDense) upper;
      for (int i = 0; i < dimension; ++i) {
        double value = data[i];
        if (value < lower || value > dense.data[i]) return false;
      }
      return true;
    } else
      return super.isBetween(lower, upper);
  }

  public Cop4JVectorView getView(List<Integer> indices) {
    return null;
  }

  public boolean geq(double lower) {
    for (int i = 0; i < dimension; ++i)
      if (data[i] < lower) return false;
    return true;
  }

  public double getValue(int index) {
    return data[index];
  }

  public void timesAdd(double scalar, Cop4JVector vector) {
    if (vector instanceof Cop4JVectorDense) {
      Cop4JVectorDense dense = (Cop4JVectorDense) vector;
      double[] otherData = dense.data;
      for (int i = dimension - 1; i >= 0; --i)
        data[i] += scalar * otherData[i];
    }
    else
      super.timesAdd(scalar,vector);
  }

  public void setValue(int index, double value) {
    data[index] = value;
  }


  public double[] getData() {
    return data;
  }


  @Override
  public String toString() {
    return Arrays.toString(data);
  }
}
