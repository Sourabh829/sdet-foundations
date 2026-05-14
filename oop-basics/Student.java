public class Student {
    private String name;
    private int age;
    private double[] marks;

    public Student(String name, int age, double[] marks) {
        this.name = name;
        this.age = age;
        this.marks = marks;
    }

    public double getAverage() {
        double sum = 0;
        for (double mark : marks) {
            sum += mark;
        }
        return sum / marks.length;
    }

    public double getHighestMark() {
        double highest = marks[0];
        for (double mark : marks) {
            if (mark > highest) highest = mark;
        }
        return highest;
    }

    public String getGrade() {
        double avg = getAverage();
        if (avg >= 90) return "A";
        if (avg >= 75) return "B";
        if (avg >= 60) return "C";
        if (avg >= 50) return "D";
        return "F";
    }

    public void displayInfo() {
        System.out.println("Name  : " + name);
        System.out.println("Age   : " + age);
        System.out.printf("Average: %.2f%n", getAverage());
        System.out.println("Highest: " + getHighestMark());
        System.out.println("Grade  : " + getGrade());
        System.out.println("-------------------------");
    }

    public static void main(String[] args) {
        Student s = new Student("Alice", 20, new double[]{85, 92, 78, 90, 88});
        s.displayInfo();
        Student s2 = new Student("Ram", 32, new double[]{5, 0, 2, 6, 74});
        s2.displayInfo();
    }
}
