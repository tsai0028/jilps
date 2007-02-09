package jilps.cop4j.algebra;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class Cop4JMatrixSparse extends AbstractCop4JMatrix {
	private double[][] Ax;
	private int[][] Ai;
	private int numColumns, numRows;
	
	public Cop4JMatrixSparse(double[][] Ax, int[][] Ai, int numColumns, int numRows) {
		this.Ax = Ax;
		this.Ai = Ai;
		this.numColumns = numColumns;
		this.numRows = numRows;
	}
	
	public Cop4JMatrixSparse(int numColumns, int numRows) {
		this.Ax = new double[numColumns][0];
		this.Ai = new int[numColumns][0];
		this.numColumns = numColumns;
		this.numRows = numRows;
	}
	
	public void setColumn(int column, int[] indicies, double[] values) {
		this.Ax[column] = values;
		this.Ai[column] = indicies;
	}
	
	// lower triangular solve.  Ly=b solve for y (MATLAB y=L\b)
	public Cop4JVector lsolve(Cop4JVectorSparse rhs) {
		Stack<Integer> nonZeros = reach(rhs);
		Cop4JVectorSparse solution = (Cop4JVectorSparse)rhs.copy();
		//for (Integer j : nonZeros) {
		while (!nonZeros.empty()) {
			Integer j = nonZeros.pop();
			double solution_j = solution.getValue(j);
			for (int i = j+1; i < this.getNumCols(); i++) {
				//this is what I want to do!
				//b(i) = b(i) - L(i,j)*x(j)
				double value = solution.getValue(i) - this.getValue(i, j) * solution_j;
				solution.setValue(i, value);
			}
		}
		return solution;
	}
	
	private Stack<Integer> reach(Cop4JVectorSparse rhs) {
		HashSet<Integer> marked = new HashSet<Integer>();
		Stack<Integer> result = new Stack<Integer>();
		//for (Cop4JVectorEntry entry : rhs.getNonZeroValues()) {
		for (Integer i : rhs.getNonZeroIndicies()) {
			if (!marked.contains(i)) {
				depthFirstSearch(i, result, marked);
			}
		}
		return result;
	}
	
	private void depthFirstSearch(int j, Stack<Integer> result, HashSet<Integer> marked) {
		marked.add(j);
		for (int i = 0; i < Ai[j].length; i++) {
			if (!marked.contains(Ai[j][i])) {
				depthFirstSearch(Ai[j][i], result, marked);
			}
		}
		result.push(j);
	}
	
	public void extend(int numRows, int numColumns) {
		// TODO Auto-generated method stub

	}

	public Cop4JVector getColumn(int index) {
		return new Cop4JVectorSparse(this.getNumRows(), Ai[index], Ax[index]);
	}

	public Cop4JMatrixView getColumns(List<Integer> indices) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNumCols() {
		return numColumns;
	}

	public int getNumRows() {
		return numRows;
	}

	public Cop4JVector getRow(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public double getValue(int row, int column) {
		for (int i = 0; i < Ai[column].length; i++) {
			if (row == Ai[column][i]) {
				return Ax[column][i];
			}
		}
		return 0.0;
	}

	public void setValue(int row, int column, double value) {		
		//trival case where current value is new value
		if (this.getValue(row, column) == value) {
			return;
		}
		
		int rl = Ai[column].length; //current row length
		
		double[] rx = new double[rl+1]; //incase row is zero
		int[] ri = new int[rl+1]; //incase row is zero
		int j = 0; //this will point to rx/ri
		boolean done = false;
		for (int i = 0; i < rl; i++) {
			int index = Ai[column][i];
			double oldvalue = Ax[column][i];
			if (row == index) {
				Ax[column][i] = value;
				return;
			} else if (row > index) {
				ri[j] = index;
				rx[j] = oldvalue;
				j++;
			} else if (row < index) {
				if (!done) {
					ri[j] = row;
					rx[j] = value;
					j++;
					done = true;
				}
				ri[j] = index;
				rx[j] = oldvalue;
				j++;
			}
		}
		if (!done) {
			ri[j] = row;
			rx[j] = value;
			done = true;
		}
		Ai[column] = ri;
		Ax[column] = rx;
		
/*		//we must extend the row arrays (rx, ri)
		double[] rx = new double[rl+1];
		int[] ri = new int[rl+1];
		int j = 0;
		for (int i = 0; i < rl; i++) {
			if (Ai[column][i] < row) {
				
			}
		}
		System.arraycopy(Ai[column], 0, ri, 0, rl);
		System.arraycopy(Ax[column], 0, rx, 0, rl);
		rx[rl] = value;
		ri[rl] = row;
		Ai[column] = ri;
		Ax[column] = rx;*/
	}
	
	public Cop4JMatrixSparse[] LU() {
		Cop4JMatrixSparse L = Cop4JMatrixSparse.eye(this.getNumCols());
		Cop4JMatrixSparse U = Cop4JMatrixSparse.eye(this.getNumCols());
		for (int k = 0; k < this.getNumCols(); k++) {
			Cop4JVector x = L.lsolve((Cop4JVectorSparse)this.getColumn(k));
			for (int i = 0; i <= k; i++) {
				U.setValue(i, k, x.getValue(i));
			}
			double Ukk = U.getValue(k,k);
			for (int i = k; i < this.getNumCols(); i++) {
				double xi = x.getValue(i);
				L.setValue(i, k, xi/Ukk);
			}
		}
		return new Cop4JMatrixSparse[] {L,U};
	}

	public Cop4JVector solve(Cop4JVector rhs) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cop4JVector solveTranspose(int index, double value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cop4JVector times(Cop4JVector operand) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cop4JVector timesTranspose(Cop4JVector operand) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Cop4JMatrixSparse eye(int n) {
		Cop4JMatrixSparse eye = new Cop4JMatrixSparse(n,n);
		for (int i = 0; i < n; i++)
			eye.setColumn(i, new int[] {i}, new double[] {1.0});
		return eye;
	}

}
