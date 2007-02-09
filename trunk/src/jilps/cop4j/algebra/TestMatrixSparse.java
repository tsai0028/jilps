package jilps.cop4j.algebra;

import junit.framework.TestCase;

public class TestMatrixSparse extends TestCase {
	
	protected Cop4JMatrixSparse L, A, L2;
	protected void setUp() throws Exception {
		super.setUp();
		L = new Cop4JMatrixSparse(5,5);
		L.setColumn(0, new int[] {0,4}, new double[] {1,-1});
		L.setColumn(1, new int[] {1,2,3}, new double[] {1,-1,4});
		L.setColumn(2, new int[] {2,3,4}, new double[] {1,-2,-3});
		L.setColumn(3, new int[] {3}, new double[] {1});
		L.setColumn(4, new int[] {4}, new double[] {1});
		
		A = new Cop4JMatrixSparse(5,5);
		A.setColumn(0, new int [] {0,1,2}, new double[] {2,3,-1});
		A.setColumn(1, new int [] {1,3}, new double[] {1,-1});
		A.setColumn(2, new int [] {0,2,4}, new double[] {4,-1,1});
		A.setColumn(3, new int [] {1}, new double[] {1});
		A.setColumn(4, new int [] {0,2,3,4}, new double[] {-2,-2,-6,4});
		
		L2 = new Cop4JMatrixSparse(5,5);
		L2.setColumn(0, new int[] {0,1,2}, new double[] {1,1.5,-0.5});
		L2.setColumn(1, new int[] {1,3}, new double[] {1,-1});
		L2.setColumn(2, new int[] {2}, new double[] {1});
		L2.setColumn(3, new int[] {3}, new double[] {1});
		L2.setColumn(4, new int[] {4}, new double[] {1});
		
		
	}

	public void testLsolve() {
		Cop4JVectorSparse b = new Cop4JVectorSparse(5,new int[] {0,3,4}, new double[] {3.0,7.0,-2.0});
		Cop4JVector x = L.lsolve(b);
		assertEquals(3.0, x.getValue(0));
		assertEquals(0.0, x.getValue(1));
		assertEquals(0.0, x.getValue(2));
		assertEquals(7.0, x.getValue(3));
		assertEquals(1.0, x.getValue(4));
	}
	
	public void testLsolve2() {
		Cop4JVectorSparse b = new Cop4JVectorSparse(5,new int[] {0,2,4}, new double[] {4.0,-1.0,1.0});
		Cop4JVector x = L2.lsolve(b);
		assertEquals(4.0, x.getValue(0));
		assertEquals(-6.0, x.getValue(1));
		assertEquals(1.0, x.getValue(2));
		assertEquals(-6.0, x.getValue(3));
		assertEquals(1.0, x.getValue(4));
	}
	
	public void testLU() {
		Cop4JMatrixSparse[] LU = A.LU();
		assertEquals(2.0, LU[1].getValue(0, 0));
		assertEquals(0.0, LU[1].getValue(0, 1));
		assertEquals(4.0, LU[1].getValue(0, 2));
		assertEquals(0.0, LU[1].getValue(0, 3));
		assertEquals(-2.0, LU[1].getValue(0, 4));
		assertEquals(0.0, LU[1].getValue(1, 0));
		assertEquals(1.0, LU[1].getValue(1, 1));
		assertEquals(-6.0, LU[1].getValue(1, 2));
		assertEquals(1.0, LU[1].getValue(1, 3));
		assertEquals(3.0, LU[1].getValue(1, 4));
		assertEquals(0.0, LU[1].getValue(2, 0));
		assertEquals(0.0, LU[1].getValue(2, 1));
		assertEquals(1.0, LU[1].getValue(2, 2));
		assertEquals(0.0, LU[1].getValue(2, 3));
		assertEquals(-3.0, LU[1].getValue(2, 4));
		assertEquals(0.0, LU[1].getValue(3, 0));
		assertEquals(0.0, LU[1].getValue(3, 1));
		assertEquals(0.0, LU[1].getValue(3, 2));
		assertEquals(1.0, LU[1].getValue(3, 3));
		assertEquals(-21.0, LU[1].getValue(3, 4));
		assertEquals(0.0, LU[1].getValue(4, 0));
		assertEquals(0.0, LU[1].getValue(4, 1));
		assertEquals(0.0, LU[1].getValue(4, 2));
		assertEquals(7.0, LU[1].getValue(4, 3));
		assertEquals(-2.0, LU[1].getValue(4, 4));
		
	}

}
