package jilps.cop4j.algebra;

/**
 * @author Sebastian Riedel
 */
public class Cop4JVectorEntry {

  protected int index;
  protected double value;


  public Cop4JVectorEntry(int index, double value) {
    this.index = index;
    this.value = value;
  }

  public int getIndex(){
    return index;
  }
  public double getValue(){
    return value;
  }
}
