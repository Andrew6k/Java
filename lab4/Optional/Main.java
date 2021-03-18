import com.github.javafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args)
    {
        var students = IntStream.rangeClosed(0,3)
                .mapToObj(i->new Student("S"+i,i+7))
                .toArray(Student[]::new);
        var h =IntStream.rangeClosed(0,2)
                .mapToObj(i->new School("H"+i))
                .toArray(School[]::new);

        List<Student> studentList=new ArrayList<>();
        studentList.addAll(Arrays.asList(students));
        Collections.sort(studentList, Comparator.comparing(Student::getNume));
        Map<Student,List<School>> stdPref= new HashMap<>();
        stdPref.put(students[0],Arrays.asList(h[0],h[1],h[2]));
        stdPref.put(students[1],Arrays.asList(h[0],h[1],h[2]));
        stdPref.put(students[2],Arrays.asList(h[0],h[1]));
        stdPref.put(students[3],Arrays.asList(h[0],h[2]));
        System.out.println(stdPref.get(students[0]));
        studentList.stream()
                .filter(std -> stdPref.get(std).contains(h[0]))
                .forEach(System.out::println);

        Set<School> schoolSet=new TreeSet<>();
        schoolSet.add(h[0]);
        schoolSet.add(h[1]);
        schoolSet.add(h[2]);

        Map<School, List<Student>> schoolListMap = new TreeMap<>();
        schoolListMap.put(h[0],Arrays.asList(students[3],students[0],students[1],students[2]));
        schoolListMap.put(h[1],Arrays.asList(students[0],students[2],students[1]));
        schoolListMap.put(h[2],Arrays.asList(students[0],students[1],students[3]));
        Stream.of(schoolListMap.keySet().toString())
                .forEach(System.out::println);

        List<School> target=Arrays.asList(h[0],h[2]);
        List<Student> result= studentList.stream()
                .filter(std -> stdPref.get(std).containsAll(target))
                .collect(Collectors.toList());
        for(Student x : result)
            System.out.print(x.getNume()+" ");
        System.out.println();

        Set<School> result1=schoolSet.stream()
                .filter(std->schoolListMap.get(std).get(0).equals(students[0]))
                .collect(Collectors.toSet());
        for(School x : result1)
            System.out.print(x.getNume()+" ");
        System.out.println();



        Faker faker=new Faker();
        for(int i=0;i<=3;i++) {
            students[i].setNume(faker.name().firstName());
            System.out.print(students[i].getNume()+" ");
            System.out.print(students[i].getScore()+" ");
        }
        System.out.println();
        for(int i=0;i<=2;i++){
            h[i].setNume(faker.name().lastName());
            System.out.print(h[i].getNume()+ " ");
        }
        System.out.println();
        h[0].setCapacity(1);
        h[1].setCapacity(2);
        h[2].setCapacity(2);



        Problem p=new Problem();
        p.setSchoolSet(schoolSet);
        p.setStudentList(studentList);
        p.setSchoolListMap(schoolListMap);
        p.setStdPref(stdPref);
        Solution s=new Solution();
        s.setP(p);
        s.calculateSolution();
    }
}
