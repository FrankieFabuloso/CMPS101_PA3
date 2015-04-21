// ----------------------------------------------------------------------------
//  PA3:
// 
//  Francisco Rocha, CMPS 101 Fall 14
//  List.java
//  A List ADT of Objects rather than intergers for Matrix ADT
//  
// Large mount of editing done from pa1 due to indexing errors and nullPointers
// during specific manipulation procedures.
// Got help from Prof. Tantalo as well as TA's and students in lab sections and
// outside of class particularly in getIndex().
// ----------------------------------------------------------------------------
public class List {
  
  private class Node {
    Object data;
    Node next;
    Node prev;
    
    Node( Object data ) {
      this.data = data;
      next = prev = null;
    }
  }
  // Fields
  int index;
  private int length;
  private Node cursor;
  private Node front;
  private Node back;
  
  // Constructors
  List() {
    length = 0;
    index = -1;
    front = back = cursor = null;
  }
  
  
  // Access Functions //////////////////////////////////////////////////////////////////
  
// isEmpty(): 
// returns true if this is an empty queue, false otherwise
  boolean isEmpty(){ return length == 0;}
  
// getLength(): 
// returns number of elements in List
  int length(){ return length;}
  
//getIndex(): 
// returns the index of the cursor element in this list, or -1 if index is UD
  int getIndex(){
    if (cursor == null){return -1;}
    int index;
    Node temp = front;
    for( index = 1; index < length ; index++){
      if( temp == cursor ){return index;}
      else { temp = temp.next; }
    }
    return index;
  }
  
// Front(): 
//Pre: length()>0 
// returns front element in this queue 
  Object front(){
    if ( isEmpty() ) {
      throw new RuntimeException("List Error: front() called on an empty list");
    }
    return front.data;
  }
  
// Back():
// Returns back element in this List. 
//Pre: length()>0   
  Object back(){
    if ( isEmpty() ) {
      throw new RuntimeException("List Error: back() called on an empty list");
    }
    return back.data;
  }
  
// int getElement();
// Returns cursor element in this list  
  Object getElement(){
    if ( isEmpty() ) {
      throw new RuntimeException("List Error: getElement() called on an empty list");
    } else if ( getIndex() == -1 ) {
      throw new RuntimeException("List Error: getElement() called on an undefined cursor");
    }
    return cursor.data;
  }
  
  
// boolean equals(Object x)
// Returns true if this List and L are the same integer  
// sequence. The cursor is ignored in both lists. 
  public boolean equals(Object x){
    if (this.length() == ((List) x).length) { return true;}
    if ( this.length() == ((List) x).length() ) {
      boolean flag = true;
      Node tempN = this.front;
      Node LNode = ((List) x).front;
      
      while ((tempN.next != null) && flag) {
        flag = (tempN.data == LNode.data);
        tempN = tempN.next;
        LNode = LNode.next;
      }
      return flag;
    } else {
      return false;
    }
  }
  
// Manipulation Procedures ///////////////////////////////////////////////////////////
  
// clear()
  void clear(){
    length = 0;
    index = -1;
    cursor = null;
    front = null;
    back = null;
  }
  
// moveto() : 
// moves cursor to index location i by setting it to front initially
// then moving over i # of times
  void moveTo(int i){
    if ((0 <= i) && (i <= (length - 1))) {
      if ((length / 2) >= i) { // decide where to start counting from ie if front is closer to wated location
        cursor = front;
        index = 0;
        while (i != index) {
          moveNext();
        }
      } else { // or if the back is
        cursor = back;
        index = length - 1;
        while (i != index) {
          movePrev();
        }
      }
    } else {
      cursor = null;
      index = -1;
    }
  }
  
// If 0<getIndex()<=length()-1, moves the cursor one step toward the  
// front of the list. If getIndex()==0, cursor becomes undefined.  
// If getIndex()==-1, cursor remains undefined. This operation is  
// equivalent to moveTo(getIndex()-1).
  void movePrev(){  
    if ( isEmpty() ) {
      throw new RuntimeException("List Error: movePrev() called on an empty list");
    } else if ( getIndex() == -1 ) {
      throw new RuntimeException("List Error: movePrev() called on an undefined cursor");
    }
    
    if (cursor == front) { // cursor falls off if it is pointing at front
      index = -1;
      cursor = null;
    } else {
      cursor = cursor.prev;
      index--;
    }
  }
// If 0<=getIndex()<length()-1, moves the cursor one step toward the  
// back of the list. If getIndex()==length()-1, cursor becomes  
// undefined. If index==-1, cursor remains undefined. This  
// operation is equivalent to moveTo(getIndex()+1). 
  void moveNext() {
    if ( isEmpty() ) {
      throw new RuntimeException("List Error: moveNext() called on an empty list");
    } else if ( getIndex() == -1 ) {
      throw new RuntimeException("List Error: moveNext() called on an undefined cursor");
    }
    if (cursor == back) { // cursor falls off if it is pointing at back
      index = -1;
      cursor = null;
    } else {
      cursor = cursor.next;
      index++;
    }
  }
  
// prepend(): appends data to back of this queue
  void prepend(Object data) {
    Node N = new Node(data);
    if (this.isEmpty()) {front = back = N; }
    else {
      front.prev = N; ///assign new nodes prev pointer to point at front node
      N.next = front; // assign fronts next pointer to point at new node
      front = N; //reassign where front is pointing to 
      if (index >= 0) {
        index++;
      }
    }
    length++;
  }
  // append(): appends element from front of this queue
  void append(Object data){
    Node N = new Node(data);
    if (this.isEmpty()) {front = back = N; }
    else {
      back.next = N;
      N.prev = back;
      back = N;
    }
    length++;
  }
  
// insertBefore(Object data):
// Pre: length()>0, getIndex()>=0  
// Inserts new element before cursor element in this  
  void insertBefore(Object data){
    if ( isEmpty() ) {
      throw new RuntimeException("List Error: insertBefore() called on an empty list");
    } else if ( getIndex() == -1 ) {
      throw new RuntimeException("List Error: insertBefore() called on an undefined cursor");
    }
    Node before = new Node(data);
    before.prev = cursor.prev;
    before.next = cursor;
    if (cursor.prev != null) {
      cursor.prev.next = before;
    }
    cursor.prev = before;
    
    if (front == cursor) {
      front = before;
    }
    length++;
    index++;
  }
  
// Pre: length()>0, getIndex()>=0  
// Inserts new element after cursor element in this 
  void insertAfter(Object data) {
    if ( this.isEmpty() ) {
      throw new RuntimeException("List Error: insertAfter() called on an empty list");
    } else if ( getIndex() == -1 ) {
      throw new RuntimeException("List Error: insertAfter() called on an undefined cursor");
    }
    Node after = new Node(data); // make new node pointer 
    after.next = cursor.next;
    after.prev = cursor;
    if (cursor.next != null) { 
      cursor.next.prev = after;
    }
    cursor.next = after;
    if (back == cursor) {
      back = after;
    }
    length++;
  }
  
// Pre: length()>0  
// Deletes the front element in this List.
  void deleteFront() {
    if ( isEmpty() ) {
      throw new RuntimeException("List Error: deleteFront() called on an empty list");
    } 
    if (cursor == front) {
      cursor = null;
    }
    if (length > 1) {
      front.next.prev = null;
      front = front.next;
    } else {front = back = null;}
    
    length--;
    
    if (index >= 0) {index--;}
    if (cursor == front) {cursor = null;}
  }
  
//Pre: length()>0 
// Deletes the back element in this List.  
  void deleteBack() {
    if ( isEmpty() ) {
      throw new RuntimeException("List Error: deleteBack() called on an empty list");
    } 
    if (this.length() == 1) { // 
      index = -1;
      cursor = null;
    }
    
    if (this.length() > 1) {
      back.prev.next = null;
      back.prev = back;
    } else {
      front = back = null;
    }
    length--;
    
  }
  
  
// Pre: length()>0, getIndex()>=0 
// Deletes cursor element in this List. Cursor is undefined after this  
// operation. 
  void delete() {
    if ( isEmpty() ) {
      throw new RuntimeException("List Error: delete() called on an empty list");
    } else if ( getIndex() == -1 ) {
      throw new RuntimeException("List Error: delete() called on an undefined cursor");
    }
    
    Node F= cursor.next;
    Node B=cursor.prev;
    if (cursor == front) { deleteFront();}     // if cursor is pointing to front call deleteFront()
    else if (cursor == back) { deleteBack();}  // if cursor is pointing to back call deleteBack()
    
    else {                                     // else delete node by rearanging pointers 
      if (cursor.prev != null) {  
        B.next = F;
      }
      if (cursor.next != null) {
        F.prev = B;
      }
      index = -1;
      cursor = null;
      length--;
    }
  }
  
// Other Functions ////////////////////////////////////////////////////
  
  // toString():  overides Object's toString() method.
  public String toString() {
    Node N = this.front;
    String string = "";
    while (N != null) {
      string += N.data + " ";
      N = N.next;
    }
    return string;
  }
  /*copy(): returns a new List identical to this one.
   List copy() {
   List newL = new List();
   Node N = this.front;
   while (N != null) {
   newL.append(N.data);
   N = N.next;
   }
   return newL;
   }
   */
  
// List concat(List L){
}


