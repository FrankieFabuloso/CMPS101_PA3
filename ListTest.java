// ----------------------------------------------------------------------------
//  PA3:
//  Francisco Rocha, CMPS 101 Fall 14
//  ListTest.java
//
//  A test client for the List ADT
// ----------------------------------------------------------------------------

class ListTest{
  public static void main(String[] args){
    List A = new List();
    List B = new List();
    A.append(2);
    A.append(6);
    A.append(19);
    B.prepend(12);
  
    System.out.println("A = " + A);
    System.out.println("B = " + B);
    System.out.println("cursor A at index: "+ A.getIndex());
    
    B.prepend(3);
    A.prepend(7);
    
    System.out.println("A = " + A);
    System.out.println("B = " + B);
    A.moveTo(1);
    System.out.println("cursor A at index: "+ A.getIndex());
    System.out.println("cursor A = " +A.getElement());
    System.out.println("A = " + A);
    
    A.delete();
    System.out.println("A = " + A);
    
    A.deleteBack();
    B.deleteBack();
    System.out.println("A = " + A);
    System.out.println("B = " + B);
    System.out.println("cursor A at index: "+ A.getIndex());
    
    System.out.println(A.equals(B));
    
    A.clear();
    B.clear();
    
        
    System.out.println(A.equals(B));
    
    A.append(4);
    B.append(4);
    
    System.out.println(A.equals(B));
    System.out.println("cursor A at index: "+ A.getIndex());
    System.out.println("cursor B at index: "+ B.getIndex());
    B.append(78);
  
    System.out.println("back of list B: "+ B.back());
    //System.out.println("cursor A = " +A.getElement());
    
    
    A.append(2);
    B.append(6);
    A.append(19);
    B.append(4);
    
      
    System.out.println("A = " + A);
    System.out.println("B = " + B);
    B.moveTo(0);
    B.moveNext();

    
    System.out.println("cursor B at index " + B.getIndex());
    System.out.println("cursor B = " +B.getElement());
    System.out.println("A = " + A);
    System.out.println("B = " + B);
    
     System.out.println("cursor B index at = " + B.getIndex());

    /* for(int i=1; i<=10; i++){
     A.append(i);
     B.prepend(11-i);
     }
     System.out.println("A = " + A);
     System.out.println("B = " + B);
     
     for(int i=1; i<=6; i++){
     A.append(B.front());
     B.deleteFront();
     }
     System.out.println("A = " + A);
     System.out.println("B = " + B);
     List C = A.copy();
     System.out.println("C = " + C);
     System.out.println("A " + (A.equals(B)?"equals":"does not equal") + " B");
     System.out.println("A " + (A.equals(C)?"equals":"does not equal") + " C");
     */
  }
}


