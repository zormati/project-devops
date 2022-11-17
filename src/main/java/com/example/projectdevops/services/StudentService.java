package com.example.projectdevops.services;

import com.example.projectdevops.models.Student;
import com.example.projectdevops.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectdevops.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public Student createStudent(Student student) throws ResourceNotFoundException {

        Student createdStudent = this.studentRepository.save(student);
        return createdStudent;
    }

    public List<Student> getAllStudents() throws ResourceNotFoundException {

        List<Student> students = this.studentRepository.findAll();
        return students;
    }

    public Student getStudentById(Long studentId) throws ResourceNotFoundException {

        Optional<Student> studentData = this.studentRepository.findById(studentId);
        if(studentData.isPresent()){
            Student student = studentData.orElseThrow(()-> new ResourceNotFoundException("student not found"));
            return student;
        }else{
            throw new  ResourceNotFoundException("Not student matches.");
        }
    }
}
