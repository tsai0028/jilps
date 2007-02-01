package jilps.cop4j;

import jilps.cop4j.algebra.Cop4JVector;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Sebastian Riedel
 */
public class SimplexTableau {


  private Cop4JVector primalSolution, dualSolution;
  private ArrayList<Integer> basicVariables, nonBasicVariables;
  private LP lp;


  public SimplexTableau(LP lp) {
    this.lp = lp;
    primalSolution = lp.getLinAlgPackage().createVector(lp.getNumColumns());
    dualSolution = lp.getLinAlgPackage().createVector(lp.getNumColumns());
    basicVariables = new ArrayList<Integer>(lp.getNumRows());
    nonBasicVariables = new ArrayList<Integer>(lp.getNumColumns() - lp.getNumRows());
  }

  public Cop4JVector getPrimalSolution() {
    return primalSolution;
  }

  public Cop4JVector getDualSolution() {
    return dualSolution;
  }

  public List<Integer> getBasicVariables() {
    return basicVariables;
  }

  public List<Integer> getNonBasicVariables() {
    return nonBasicVariables;
  }

  public boolean isSolutionPrimalFeasible() {
    return false;
  }

  public boolean isSolutionDualFeasible() {
    return false;
  }

  public double getPrimalObjective() {
    return 0.0;
  }

  public double getDualObjective() {
    return 0.0;
  }

  public LP getLp() {
    return lp;
  }


}
