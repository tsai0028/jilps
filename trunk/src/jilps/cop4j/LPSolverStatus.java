package jilps.cop4j;

/**
 * Created by IntelliJ IDEA. User: s0349492 Date: 10-Jan-2007 Time: 15:36:48
 */
public class LPSolverStatus {
  private boolean knowProblemBounded, knowProblemFeasible, problemFeasible, problemBounded;


  public LPSolverStatus(boolean problemBounded, boolean problemFeasible) {
    this.problemBounded = problemBounded;
    this.problemFeasible = problemFeasible;
    knowProblemBounded = true;
    knowProblemFeasible = true;
  }


  public LPSolverStatus(boolean knowProblemBounded,
                        boolean problemBounded,
                        boolean knowProblemFeasible,
                        boolean problemFeasible) {
    this.knowProblemBounded = knowProblemBounded;
    this.knowProblemFeasible = knowProblemFeasible;
    this.problemBounded = problemBounded;
    this.problemFeasible = problemFeasible;
  }

  public boolean doesKnowProblemBounded() {
    return knowProblemBounded;
  }

  public boolean doesKnowProblemFeasible() {
    return knowProblemFeasible;
  }

  public boolean isProblemBounded() {
    return problemBounded;
  }

  public boolean isProblemFeasible() {
    return problemFeasible;
  }
}
