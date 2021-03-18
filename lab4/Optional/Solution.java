import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Solution {
    public Problem p;
    public Map<Student,School> studentSchoolMap=new HashMap<>();

    public void setP(Problem p) {
        this.p = p;
    }

    public Problem getP() {
        return p;
    }

    public void setStudentSchoolMap(Map<Student, School> studentSchoolMap) {
        this.studentSchoolMap = studentSchoolMap;
    }

    public Map<Student, School> getStudentSchoolMap() {
        return studentSchoolMap;
    }

    public void calculateSolution(){
        Collections.sort(p.studentList, Comparator.comparing(Student::getScore));
        Collections.reverse(p.studentList);
        for(Student x : p.studentList)
            System.out.print(x.getNume()+" ");
        System.out.println();
        for(Student x : p.studentList) {
            int i=0;
            int j=0;
            School s1=new School();
            while (p.stdPref.get(x).get(i) != null){
                s1=p.stdPref.get(x).get(i);
                if(s1.capacity!=0)
                {studentSchoolMap.put(x,s1);
                 j=s1.getCapacity()-1;
                 p.stdPref.get(x).get(i).setCapacity(j);
                 break;
                }
                i++;}
        }
        for(Student x : p.studentList){
            System.out.println(x.getNume()+" "+studentSchoolMap.get(x).getNume());
        }
        /*
        Stream.of(studentSchoolMap.keySet().toString())
                .forEach(System.out::println);
        Stream.of(studentSchoolMap.values().toString())
                .forEach(System.out::println);

         */
    }
}
