package ru.example.edu.controller;

import org.springframework.web.bind.annotation.*;
import ru.example.edu.model.Student;
import ru.example.edu.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/student")
    public List<Student> getStudents() {
        return repository.findAll();
    }

    @PostMapping("/student")
    public long addStudent(@RequestParam String name, @RequestParam String email, @RequestParam int age){
        Student std = new Student(name, email, age);
        std = repository.save(std);

        return std.getId();
    }
    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable long id) {
        Student st = repository.findById(id).get();

        return st;
    }

    @PutMapping("/student/{id}")
    public Student updateStudent(@PathVariable long id, @RequestBody Student st) {
        Optional<Student> findStudent = repository.findById(id);
        if (findStudent.isPresent()) {
            Student student = findStudent.get();

            student.setName(st.getName());
            student.setEmail(st.getEmail());
            student.setAge(st.getAge());

            return repository.save(student);
        } else {
            return null;
        }
    }

    @DeleteMapping("/student/{id}")
    public void deleteStudent(@PathVariable long id) {
        repository.deleteById(id);
    }
}
