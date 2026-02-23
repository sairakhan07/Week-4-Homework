
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

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
            System.out.println("Course: " + studentCourse.courseCode + " - " + studentCourse.courseName);
            System.out.println("Results:");

            for (int i = 0; i < studentCourse.moduleList.size(); i++) 
            {
                Module currentModule = studentCourse.moduleList.get(i);
                int currentMark = marks[i];
                String grade = currentModule.getLetterGrade(currentMark);

                System.out.println(currentModule.moduleName +" (" + currentModule.moduleCode + ") Mark: " + currentMark + "; Grade: " + grade);
            }
        } else 
        {
            System.out.println("Not enrolled in a course.");
        }

      
   }

   public void enrol(Course newCourse) //method that allows an object of the class Course to be passed in
   {
      this.studentCourse = newCourse; 
   }
   
   public static void main(String[] args)
      {
            Student student1 = new Student(123456, "Anna");//assigns name and id
            student1.printStudentValues();//calls printStudentValues to display name and id

            Course course1 = new Course("C021", "Biomedical Science");

            student1.enrol(course1);//links student with course
            student1.printStudentValues();//method now prints student and course
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

    public String LetterGrade(int mark) 
    {
        if (mark >= 70) 
        {
            return "A";
        } else if (mark >= 60) 
        {
            return "B";
        } else if (mark >= 50) 
        {
            return "C";
        } else if (mark >= 40) 
        {
            return "D";
        } else 
        {
            return "F";
        }
    }

    public void printModuleDetails() 
    {
        System.out.println("Module: " + moduleCode + ": " + moduleName);
    }
}