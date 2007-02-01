package jilps.cop4j;

import jilps.cop4j.algebra.Cop4JVector;

/**
 * @author Sebastian Riedel
 */
public class LPRow {

  private Cop4JVector values;
  private double upperBound, lowerBound;


  public LPRow(Cop4JVector values, double lowerBound, double upperBound) {
    this.values = values;
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }

  public double getLowerBound() {
    return lowerBound;
  }

  public double getUpperBound() {
    return upperBound;
  }

  public Cop4JVector getValues() {
    return values;
  }

  public LPRow copy(){
    return new LPRow(values.copy(),lowerBound, upperBound);
  }

}
