package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }



    @Test
    void getAllStudentsUsingFindAllMethod() {



        //when
        studentService.getAllStudents();

        //then
        verify(studentRepository).findAll();

    }

    @Test
    void canAddANewStudentAndItWillUseSaveMethod() {


        //given
        Student student = new Student();
        student.setEmail("berro.gonza2195@gmail.com");
        student.setGender(Gender.MALE);
        student.setName("Gonzalo");

        //when
        studentService.addStudent(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);


    }

    @Test
    void throwErrorWhenAddingStudentWithEmailAlreadyExisting() {


        //given
        Student student = new Student();
        student.setEmail("berro.gonza2195@gmail.com");
        student.setGender(Gender.MALE);
        student.setName("Gonzalo");

        given(studentRepository.selectExistsEmail(student.getEmail())).willReturn(true);

        //then
        assertThatThrownBy(() -> studentService.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");

    }

    @Test
    void deleteStudentMustUseDeleteById() {

        given(studentRepository.existsById(1l)).willReturn(true);

        studentService.deleteStudent(1l);

        verify(studentRepository).deleteById(1l);

    }

    @Test
    void deleteStudentThatNosExistsShouldReturnException() {

        given(studentRepository.existsById(1l)).willReturn(false);

        assertThatThrownBy(() -> studentService.deleteStudent(1l))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id 1 does not exists");

        verify(studentRepository, never()).deleteById(any());

    }
}