// ----------------------------------------------------------------------------
//  PA3:
//  Francisco Rocha, CMPS 101 Fall 14
//
//  MatrixTest.java
//  a text client for the Matrix ADT
//  
// ----------------------------------------------------------------------------
public class MatrixTest {
  public static void main(String[] args) {
    Matrix A = new Matrix(100);
    Matrix B = new Matrix(100);
    Matrix C = new Matrix(100); // empty matrix
    
    A.changeEntry(1, 1, 1.0); A.changeEntry(1, 2, -1.0); A.changeEntry(1, 3, 99.0);
    A.changeEntry(2, 1, 2.0); A.changeEntry(2, 2, -2.0); A.changeEntry(2, 3, 77.0);
    A.changeEntry(3, 1, 3.0); A.changeEntry(3, 2, -3.0); A.changeEntry(3, 3, 88.0);
    
    B.changeEntry(1, 1, 9.0); B.changeEntry(1, 3, 900.0);
    B.changeEntry(2, 3, 33.0);
    B.changeEntry(3, 1, 6.0); B.changeEntry(3, 2, 6.0); B.changeEntry(3, 3, 6.0);
    
    
    
    System.out.println("A has " + A.getNNZ() + " non-zero entries:");
    System.out.println(A);
    System.out.println("B has " + B.getNNZ() + " non-zero entries:");
    System.out.println(B);
    
    System.out.println("C has " + C.getNNZ() + " non-zero entries:");
    System.out.println(C);
    System.out.println("(C empty matrix:\n");
    
    System.out.println("(5.0)*A =");
    System.out.println(A.scalarMult(5.0));
    
    System.out.println("(700.0)*B =");
    System.out.println(B.scalarMult(700.0));
    
    System.out.println("A+B:");
    System.out.println(A.add(B));
    
    System.out.println("A*C:");
    System.out.println(A.add(C));
    
    System.out.println("B-A:");
    System.out.println(B.sub(A));
    
    System.out.println("A tanspose: ");
    System.out.println(A.transpose());
    
    System.out.println("B transpose: ");
    System.out.println(B.transpose());
    
    System.out.println("A*B =");
    System.out.println(A.mult(B));
    
    System.out.println("B*B =");
    System.out.println(B.mult(B));
    
    System.out.println("A*C =");
    System.out.println(A.mult(C));
    
    A.changeEntry(3, 2, 12.0);
    A.changeEntry(3, 1, 10.0);
    A.changeEntry(3, 3, 11.0);
    System.out.println("A: " + A.getNNZ() + " non-zero entries:");
    System.out.println(A);
    System.out.println("Delete by insertion of x=0:");
    A.changeEntry(2, 1, 0);
    A.changeEntry(3, 1, 0);
    A.changeEntry(3, 2, 0);
    System.out.println("A: " + A.getNNZ() + " non-zero entries:");
    System.out.println(A);
    
    A.changeEntry(2, 1, 20.0);
    A.changeEntry(3, 2, 30.0);
    System.out.println("A: " + A.getNNZ() + " non-zero entries:");
    System.out.println(A);
    
    A.makeZero();
    System.out.println("makeZero A: " + A.getNNZ() + " non-zero entries:");
    System.out.println(A);
    
    System.out.println(" -------copy func------ ");
    C = B.copy();
    System.out.println("B: ");
    System.out.println(B);
    System.out.println("C: ");
    System.out.println(C);
    System.out.println("B equals C: " + B.equals(C));
    System.out.println("makeZero B:");
    B.makeZero();
    System.out.println("B equals C: " + B.equals(C));
    C.makeZero();
    System.out.println("makeZero C:");
    System.out.println("B equals C: " + B.equals(C));
  }
}
