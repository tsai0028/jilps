package jilps.cop4j;


import jilps.cop4j.algebra.Cop4JVector;
import jilps.cop4j.algebra.Cop4JVectorDense;

/**
 * @author Sebastian Riedel
 */
public class LPColumn {

  private Cop4JVector values;
  private double cost, upperBound,lowerBound;
  private boolean artificial;
  private String name;


  public LPColumn(Cop4JVector values, double lowerBound, double upperBound, double cost, boolean artificial) {
    this.values = values;
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.cost = cost;
    this.artificial = artificial;
  }


  public LPColumn(double lowerBound, double upperBound, double cost) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.cost = cost;
    values = new Cop4JVectorDense(0);
  }

  public String getName() {
    return name;
  }

  public boolean isArtificial() {
    return artificial;
  }

  public double getCost() {
    return cost;
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

  public LPColumn copy() {
    return new LPColumn(values.copy(),lowerBound, upperBound, cost, artificial);
  }
}
