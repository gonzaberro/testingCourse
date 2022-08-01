package com.example.demo.student;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;



    @Test
    void studentSearchedByEmailShouldReturnTrue() {

        //given
        Student student = new Student();

        student.setEmail("berro.gonza2195@gmail.com");
        student.setGender(Gender.MALE);
        student.setName("Gonzalo");

        studentRepository.save(student);

        //when
        Boolean exists = studentRepository.selectExistsEmail(student.getEmail());

        //then

        assertThat(exists).isTrue();

    }

    @Test
    void studentSearchedByEmailShouldReturnFalse() {


        //when
        Boolean exists = studentRepository.selectExistsEmail("berrogonzalo95@gmail.com");

        //then

        assertThat(exists).isFalse();

    }
}