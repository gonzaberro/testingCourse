package com.example.demo.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        studentController = new StudentController(studentService);
    }

    @Test
    void whenGetAllStudentsIShouldCallGetAllStudentsServiceMethod(){


        studentController.getAllStudents();

        verify(studentService).getAllStudents();

    }

    @Test
    void whenaddStudentIShouldCallAddStudentServiceMethod(){


        studentController.addStudent(any());

        verify(studentService).addStudent(any());

    }

    @Test
    void whenDeleteStudentIShouldCallDeleteStudentServiceMethod() {

        studentController.deleteStudent(any());

        verify(studentService).deleteStudent(any());


    }
}