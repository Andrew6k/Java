public class School implements Comparable<School> {
    int capacity;
    String nume;
    School(){}
    School(String s){
        nume=s;
    }
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public int compareTo(School o) {
        if(o==null)
            return 0;
        return this.getNume().compareTo(o.getNume());
    }

    @Override
    public String toString() {
        return "School{" + nume + '\'' +
                '}';
    }
}