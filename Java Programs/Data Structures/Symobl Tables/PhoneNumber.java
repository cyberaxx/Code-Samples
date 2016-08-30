public final class PhoneNumber {
  private final int countryCode;
  private final int areaCode;
  private final int extention;

  public PhoneNumber(int cc, int ac, int ext) {
    this.countryCode=cc;
    this.areaCode=ac;
    this.extention=ext;
  }

  @Override 
  public boolean equals(Object phoneNumber) {
    // check if this instance and phoneNumber refere to the same address in the memory:
    if(this==phoneNumber) return true;
    // if the given object phoneNumber is null
    if(phoneNumber==null) return false; 
    // check if both this object and the given object have same class type
    if(this.getClass()!=phoneNumber.getClass())  return false;

    // Cast the phoneNumber and compare its fields with this object
    PhoneNumber number=(PhoneNumber) phoneNumber;
    if(this.extention!=number.extention) return false;
    if(this.areaCode!=number.areaCode) return false;
    if(this.countryCode!=number.countryCode) return false;
    return true;
  }

}
