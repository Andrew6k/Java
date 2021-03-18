public class Student {
    String nume;
    int score;
    Student(String s){
        nume=s;
    }
    Student(String s, int sc){nume=s; score=sc;}
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                '}';
    }
}
