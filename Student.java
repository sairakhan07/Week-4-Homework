import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

enum Grade 
{//enum = a user-defined data type consisting of a set of named constants
    A, B, C, D, F
}

public class Student 
{
   int id;
   String name;
   Course studentCourse;
   int[] marks;//array to store marks for each module

   Student(int id, String name) //constructor = special method called when class is created (same name); used to initialise attributes
   {//(int id, string name) = constructor parameters
      this.id = id;
      this.name = name;//object's variable = constructor parameter
      this.marks = new int[4];//hold 4 integers (one for each module)

      Random rand = new Random();
        for (int i = 0; i < marks.length; i++) //iterates through marks array and assigns random mark between 0 and 100 to each element
        {
            this.marks[i] = rand.nextInt(101); //generates random integer from 0 to 100 (inclusive) and assigns it to marks[i]
        }
   }

   public void printStudentValues()
   {
      System.out.println("Student ID: " + id);
      System.out.println("Student name: " + name); //calls id and name from main and displays them when called

      if (studentCourse != null) 
        {
            for (int i = 0; i < studentCourse.moduleList.size(); i++) //iterates through the moduleList of the student's course and prints the module name, mark, and grade for each module
            {
            Module currentModule = studentCourse.moduleList.get(i);
            int currentMark = marks[i];//gets the mark for the current module from the marks array
            
            // Receive the result as a Grade enum
            Grade grade = currentModule.LetterGrade(currentMark);
            
            System.out.println(currentModule.moduleName +  " | Mark: " + currentMark + "; Grade: " + grade); // Java automatically calls .toString() on enums
            }
        }
        else 
        {
            System.out.println("No course enrolled.");
        }     
   }

   public void enrol(Course newCourse) //method that allows an object of the class Course to be passed in
   {
      this.studentCourse = newCourse; 
   }
   
   public static void main(String[] args)
    {

          Random rand = new Random();//creates a Random object to generate random marks for students
            Course course1 = new Course("C021", "Biomedical Science");

            ArrayList<Student> studentList = new ArrayList<>(); //creates an ArrayList to hold Student objects; ArrayList is a resizable array that can hold objects of any type (in this case, Student)
            studentList.add(new Student(101256, "Anna"));
            studentList.add(new Student(126797, "Bob"));
            studentList.add(new Student(134890, "Charlie"));

            ArrayList<moduleMark> allResults = new ArrayList<>();//creates an ArrayList to hold moduleMark objects

            for (Student s : studentList) 
            {
               s.enrol(course1); //enrols each student in the course (links student with course)
               for (Module m : course1.moduleList) 
                {
                  int randomMark = rand.nextInt(101);//generates random mark for each module
                  allResults.add(new moduleMark(s, m, randomMark));//creates a new moduleMark object for each student-module combination and adds it to the allResults list
                }
           }

           System.out.println("--- ALL STUDENT RESULTS ---");
           for  (moduleMark record : allResults) //iterates through allResults and calls printResult method for each moduleMark object to display the student name, module name, mark, and grade
            {
              record.printResult();
            }

        if(!allResults.isEmpty()) //checks if allResults is not empty before calculating statistics to avoid errors
        {
            int sum = 0;//variable to hold the sum of all marks for calculating the mean
         moduleMark lowestMark = allResults.get(0);//initialises lowestMark and highestMark to the first record in allResults to start comparisons
         moduleMark highestMark = allResults.get(0);
        
         for (moduleMark record : allResults) //iterates through allResults to calculate the sum of marks and find the lowest and highest marks
         {
            sum += record.mark;//adds the mark of the current record to the sum variable

            if(record.mark < lowestMark.mark) 
            {
                lowestMark = record;//if the mark of the current record is less than the mark of lowestMark, update lowestMark to the current record
            }

            if(record.mark > highestMark.mark) 
            {
                highestMark = record;//if the mark of the current record is greater than the mark of highestMark, update highestMark to the current record
            }
         }

         double mean = (double) sum / allResults.size();//calculates the mean by dividing the sum of marks by the number of records in allResults (casts sum to double for accurate division)

         System.out.println("\n--- STATISTICS ---");
         System.out.println("Mean mark: " + mean);
         System.out.println("Lowest mark: " + lowestMark.mark);
         System.out.println("Highest mark: " + highestMark.mark);

        }

    }
}


class Course //not public because file name is "Student.java"; only student class public
{
      String courseCode;
      String courseName;
      ArrayList<Module> moduleList; //an attribute that is an instance of the ArrayList class

      public Course(String courseCode, String courseName) 
      {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.moduleList = new ArrayList<Module>(); //initialises moduleList as an empty ArrayList

        moduleList.add(new Module("Intro to Java", "PROG101"));
        moduleList.add(new Module("Database Systems", "DB202"));
        moduleList.add(new Module("Web Development", "WEB303"));
        moduleList.add(new Module("Professional Practice", "PROF404"));
      }

      public void printCourseValues()
   {
      System.out.println("Course code: " + courseCode);
      System.out.println("Course name: " + courseName); 

      System.out.println("Modules list:");
      for (Module m : moduleList) //for each loop to iterate through moduleList and print details of each module
      {
         m.printModuleDetails();//calls printModuleDetails method for each module in the list
      }
   }
}

class Module 
{
    String moduleName;
    String moduleCode;

    public Module(String moduleName, String moduleCode) 
    {
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
    }

  
    public Grade LetterGrade(int mark) //method that takes an integer mark and returns the corresponding Grade enum value based on the specified thresholds
    {
        if (mark >= 70) return Grade.A; 
        if (mark >= 60) return Grade.B;
        if (mark >= 50) return Grade.C;
        if (mark >= 40) return Grade.D;
        return Grade.F;
    }


    public void printModuleDetails() 
    {
        System.out.println("Module: " + moduleCode + ": " + moduleName);
    }
}

class  moduleMark //class to link a student, a module, and a mark together
{
    Student student;
    Module module;
    int mark;

    public moduleMark(Student student, Module module, int mark) 
    {
        this.student = student; 
        this.module = module;
        this.mark = mark;
    }

    public void printResult() 
    {
        Grade g = module.LetterGrade(mark); //calls LetterGrade method of the module to get the grade corresponding to the mark
        System.out.println("Student: " + student.name + "; Module: " + module.moduleName + "; Mark: " + mark + "; Grade: " + g);
    }
}
