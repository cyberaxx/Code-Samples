public final class Student {
  private final String name;
  private final int section;

  public Student(String name, int section) {
    this.name=name;
    this.section=section;
  }

  @Override
  public boolean equals(Object student) {
    if(this==student) return true; // optimization: check if this and given object are the same (refer to the same address in the memory)
    if(student==null) return false; // if the given object is null => equals must return false
    if(this.getClass()!=student.getClass()) return false; // if this object and the given object are instances of the different class type (dynamic type?)
    Student that=(Student) student; // Casting
    
    // compare instance fields:
    if(!this.name.equals(that.name))  return false;
    if(this.section!=that.section)  return false;
    return true;
  } 
}
