package jilps.cop4j.algebra;

import java.util.*;

/**
 * Created by IntelliJ IDEA. User: s0349492 Date: 10-Jan-2007 Time: 19:53:29
 */
public class Cop4JVectorSparse extends AbstractCop4JVector implements Iterable<Double> {

  private HashMap<Integer, Cop4JVectorEntry> index2entry = new HashMap<Integer, Cop4JVectorEntry>();
  private int size;



  public Cop4JVectorSparse() {

  }

  public Cop4JVectorSparse(int size, int[] indices, double[] values){
    this.size = size;
    for (int i = 0; i < indices.length;++i)
      index2entry.put(indices[i],new Cop4JVectorEntry(indices[i],values[i]));
  }

  public Cop4JVectorSparse(int size, int index, double value){
    this.size = size;
    index2entry.put(index, new Cop4JVectorEntry(index,value));
  }


  public int getDimension() {
    return size;
  }

  public void append(double... values) {
    for (double value : values)
      index2entry.put(size, new Cop4JVectorEntry(size++,value));
  }


  public Iterable<Double> getValues() {
    return this;
  }

  public Iterator<Double> iterator() {
    return new Iterator<Double>() {
      int current = 0;
      public boolean hasNext() {
        return current < size;
      }

      public Double next() {
        Cop4JVectorEntry cop4JVectorEntry = index2entry.get(current++);
        return cop4JVectorEntry == null ? 0.0 : cop4JVectorEntry.value;
      }

      public void remove() {

      }
    };
  }

  public Iterable<Cop4JVectorEntry> getNonZeroValues() {
    return new Iterable<Cop4JVectorEntry>() {
      public Iterator<Cop4JVectorEntry> iterator() {
        return index2entry.values().iterator();
      }
    };
  }

  public List<Double> asList() {
    return null;
  }

  public Map<Integer, Double> asMap() {
    return null;
  }


  public double getValue(int index) {
    Cop4JVectorEntry entry = index2entry.get(index);
    return entry == null ? 0.0 : entry.value;
  }

  public void extend(int size) {
    this.size += size;
  }
}
