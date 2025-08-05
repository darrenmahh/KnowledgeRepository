package DeepCopyTest;

class Address implements Cloneable {
    public String city;
    public Address(String city) { this.city = city; }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}