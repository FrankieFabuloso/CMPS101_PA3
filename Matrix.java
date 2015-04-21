// ----------------------------------------------------------------------------
//  PA3
//  Francisco Rocha, CMPS 101 Fall 14
//   Matrix.java
//
//   A Matrix ADT that is in the data scturcture Array of Lists that does 
//   specified matric oporations.
//
//  note:
//  lots of help was given to me on changeEntry() during lab hours as well
//  as pseudocode from my fello peers.
//  mult() does not run on specified runtime needed but could not figure out
//      how to make it work becuase I ran out of time. It works just very slow on large matricies.
// ----------------------------------------------------------------------------
public class Matrix {
  
  private class Entry {
    int col;
    double val;
    Entry (int col, double val) {
      this.col = col;
      this.val = val;
    }
    
    public boolean equals(Object x) {
      return ((col == ((Entry) x).col) && (val == ((Entry) x).val));
    }
    
    public String toString() {
      return(" (" + col + ", " + val + ")");
    }
  }
  
  int size;
  int nnz; // The number of nonzero entries.
  List[] row; // Array of Lists (aka the rows of matrix)
  
  // Constructor ////////////////////////
  // Makes a new n x n zero Matrix. pre: n>=1 
  Matrix(int n) { 
    if (n < 1) {
      throw new RuntimeException("Size of matrix must greater than 0");
    }
    this.size = n;
    row = new List[n];
    for (int i = 0; i < n; i++) {
      row[i] = new List();
    }
  }
  
  // Access functions ///////////////////
  // getSize():
  // Returns n, the number of rows and columns of this Matrix 
  int getSize() { 
    return size;
  }
  
  // getNNZ():
  // Returns the number of non-zero entries in this Matrix
  int getNNZ() { 
    return nnz;
  }
  
  // equals():
  // overrides Object's equals() method
  public boolean equals(Object x) {
    if (getSize() == ((Matrix) x).getSize()) {
      boolean flag = true;
      int i = 0;
      while ((i < getSize()) && flag) {
        flag = (row[i].equals(((Matrix) x).row[i]));
        i++;
      }
      return flag;
    } else {
      return false;
    }
  }
  
  // ---------Manipulation procedures -----------
  // makeZero():
  // sets this Matrix to the zero state
  void makeZero() {
    for (int i = 0; i < getSize(); i++) {
      row[i].clear();
    }
    nnz = 0;
  }
  
  // copy():
  // returns a new Matrix having the same entries as this Matrix
  Matrix copy(){
    Entry E;
    Matrix temp = new Matrix( this.getSize() );
    temp.size = this.size;
    for(int i = 0; i < this.getSize() ; i++){
      row[i].moveTo(0);
      while ( this.row[i].getIndex() != -1){
        E = new Entry( ((Entry)(this.row[i].getElement())).col, ((Entry)(this.row[i].getElement())).val );
        temp.row[i].append(E);
        temp.nnz++;
        this.row[i].moveNext();
      }
    }
    return temp;
  }
  
  // changeEntry():
  // changes ith row, jth column of this Matrix to x 
  // pre: 1<=i<=getSize(), 1<=j<=getSize() 
  void changeEntry(int i, int j, double x) { 
    if ((i < 1) ||  (i > getSize())) { 
      throw new RuntimeException("Matrix index error: row number out of bounds with matrix size");
    } else if ((j < 1) || (j > getSize())) {
      throw new RuntimeException("Matrix index error: column number out of bounds with matrix size");
    }
    
    Entry ent = new Entry(j, x); // entry to be added 
    if (row[i - 1].isEmpty()) {  //add entry if nothing in this row
      if (x != 0) {
        row[i - 1].append(ent);
        nnz++;
      }
    } else if (j < ((Entry) row[i - 1].front()).col) { // if ent.col less than the one in row prepend
      if (x != 0) {
        row[i - 1].prepend(ent);
        nnz++;
      }
    } else if (j > ((Entry) row[i - 1].back()).col) { // else append
      if (x != 0) {
        row[i - 1].append(ent);
        nnz++;
      }
    } else {
      Entry prev;     // make new entry pointer
      row[i - 1].moveTo(0); // move to front of row to check where to insert/ change given entry
      while(row[i - 1].getIndex() != -1 && (((Entry) row[i - 1].getElement()).col != ent.col)) {
        prev = (Entry) row[i - 1].getElement();
        row[i - 1].moveNext();
        if (row[i - 1].getIndex() != -1) {
          if ((prev.col < j) && (((Entry) row[i - 1].getElement()).col > j)) {
            if (x != 0) {
              row[i - 1].insertBefore(ent);
              nnz++;
            }
          }
        }
      }
      if (row[i - 1].getIndex() != -1) {
        if (x != 0) {
          row[i - 1].insertBefore(ent);
          nnz++;
        }
        row[i - 1].delete(); // delete if replacing a col
        nnz--;
      }
    }
  }
  
  // scalarMult():
  // returns a new Matrix that is the scalar product of this Matrix with x 
  Matrix scalarMult(double x) {
    Matrix M = new Matrix(getSize());
    if (x != 0) {
      for (int i = 0; i < getSize(); i++) {
        row[i].moveTo(0);
        while (row[i].getIndex() != -1) {
          M.changeEntry(i + 1, ((Entry) row[i].getElement()).col, ((Entry) row[i].getElement()).val * x);
          row[i].moveNext();
        }
      }
    }
    return M;
  }
  
  // add():
  // returns a new Matrix that is the sum of this Matrix with M 
  // pre: getSize()==M.getSize() 
  Matrix add(Matrix M) { 
    if (getSize() != M.getSize()) { 
      throw new RuntimeException("Cannot add matrices of unequal sizes");
    }
    
    if( this == M ){              //if adding the same list to itself just multiply by 2
      return this.scalarMult(2.0);
    }
    Matrix matrix = new Matrix(getSize());
    double sum = 0;
    
    // same iteration algorithm through rows and cols as changeEntry
    for (int i = 0; i < getSize(); i++) {
      row[i].moveTo(0);
      M.row[i].moveTo(0);
      while (row[i].getIndex() != -1 || M.row[i].getIndex() != -1) {
        if (row[i].getIndex() == -1) {
          while (M.row[i].getIndex() != -1) {
            matrix.changeEntry(i + 1, ((Entry) M.row[i].getElement()).col, ((Entry) M.row[i].getElement()).val);
            M.row[i].moveNext();
          }
        } else if (M.row[i].getIndex() == -1) {
          while (row[i].getIndex() != -1) {
            matrix.changeEntry(i + 1, ((Entry) row[i].getElement()).col, ((Entry) row[i].getElement()).val);
            row[i].moveNext();
          }
        } else {
          if (((Entry) row[i].getElement()).col > ((Entry) M.row[i].getElement()).col) {
            matrix.changeEntry(i + 1, ((Entry) M.row[i].getElement()).col, ((Entry) M.row[i].getElement()).val);
            M.row[i].moveNext();
          } else if (((Entry) row[i].getElement()).col < ((Entry) M.row[i].getElement()).col) {
            matrix.changeEntry(i + 1, ((Entry) row[i].getElement()).col, ((Entry) row[i].getElement()).val);
            row[i].moveNext();
          } else {
            sum = ((Entry) row[i].getElement()).val + ((Entry) M.row[i].getElement()).val;
            matrix.changeEntry(i + 1,((Entry) row[i].getElement()).col, sum);
            row[i].moveNext();
            M.row[i].moveNext();
          }
        }
      }
    }
    return matrix;
  }
  
  // sub():
  // returns a new Matrix that is the difference of this Matrix with M 
  // pre: getSize()==M.getSize() 
  Matrix sub(Matrix M) { 
    if (getSize() != M.getSize()) { 
      throw new RuntimeException("Cannot subtract matrices of unequal sizes");
    }
    // uses scalarMult() to add() a (M*-1.0)
    Matrix matrix = new Matrix(getSize());
    matrix = this.add(M.scalarMult(-1.0));
    return matrix;
  }
  
  // transpose():
  // returns a new Matrix that is the transpose of this Matrix 
  Matrix transpose() { 
    Matrix M = new Matrix(getSize());
    
    // see piazza duscussion for pseudocode to this algorithm
    Entry ent;
    for (int i = 0; i < getSize(); i++) {
      row[i].moveTo(0);
      while (row[i].getIndex() != -1) {
        ent = (Entry) row[i].getElement();
        M.changeEntry(ent.col, i + 1, ent.val); // change entry col with row it is on
        row[i].moveNext();                      //move to next col in row
      }
    }
    return M;
  }
  
  // mult():
  // returns a new Matrix that is the product of this Matrix with M 
  // pre: getSize()==M.getSize() 
  Matrix mult(Matrix M) { 
    if (this.getSize() != M.getSize()) { 
      throw new RuntimeException("Cannot multiply matrices of unequal sizes");
    }
    Matrix transM = M.transpose(); //The transpose of M
    Matrix matrix = new Matrix(getSize());
    double x = 0;
    for (int i = 0; i < getSize(); i++) {
      for (int j = 0; j < getSize(); j++) {
        List row = this.row[i];
        List colm = transM.row[j];
        x = dot(row, colm);         // dot specified vectors (ith row, jth col)
        if (x != 0.0) {
          matrix.changeEntry(i + 1, j + 1, x);
        }
      }
    }
    return matrix;
  }
  
  // dot():
  // helper function for mult()
  // takes in two lists and multiplies their entry vals
  private static double dot(List A, List B){
    int aCol, bCol;
    double pVal, qVal, sum=0;
    A.moveTo(0);
    B.moveTo(0);
    while (A.getIndex() != -1 && B.getIndex() != -1){
      aCol = ((Entry)(A.getElement())).col;
      bCol = ((Entry)(B.getElement())).col;
      pVal = ((Entry)(A.getElement())).val;
      qVal = ((Entry)(B.getElement())).val;
      
      if(pVal == 0 && qVal == 0) { break; }
      if (aCol < bCol){ A.moveNext(); }
      else if (bCol < aCol){ B.moveNext(); }
      else{
        sum += pVal*qVal;
        A.moveNext();
        B.moveNext(); 
      }
    }
    return sum;
  } 
  
  // Other Functions ////////////////////////////////////////////////////
  public String toString() { 
    String output = "";
    for (int i = 0; i < getSize(); i++) {
      if (!row[i].isEmpty()) {
        output += (i + 1) + ":" + row[i].toString() + "\n";
      }
    }
    return output;
  }
  
}
