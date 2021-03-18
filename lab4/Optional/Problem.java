import java.util.*;

public class Problem {
    public List<Student> studentList=new ArrayList<>();
    public Map<Student,List<School>> stdPref=new HashMap<>();
    public Set<School> schoolSet=new TreeSet<>();
    public Map<School,List<Student>> schoolListMap=new TreeMap<>();

    public List<Student> getStudentList() {
        return studentList;
    }

    public Map<School, List<Student>> getSchoolListMap() {
        return schoolListMap;
    }

    public Map<Student, List<School>> getStdPref() {
        return stdPref;
    }

    public Set<School> getSchoolSet() {
        return schoolSet;
    }

    public void setSchoolListMap(Map<School, List<Student>> schoolListMap) {
        this.schoolListMap = schoolListMap;
    }

    public void setStdPref(Map<Student, List<School>> stdPref) {
        this.stdPref = stdPref;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void setSchoolSet(Set<School> schoolSet) {
        this.schoolSet = schoolSet;
    }
}
