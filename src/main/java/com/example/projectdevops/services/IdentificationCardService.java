package com.example.projectdevops.services;

import com.example.projectdevops.exceptions.ResourceNotFoundException;
import com.example.projectdevops.models.IdentificationCard;
import com.example.projectdevops.models.Student;
import com.example.projectdevops.repositories.IdentificationCardRepository;
import com.example.projectdevops.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdentificationCardService {

    @Autowired
    IdentificationCardRepository idCardRepository;

    @Autowired
    StudentRepository studentRepository;

    public IdentificationCard createIdentificationCard(IdentificationCard idCard, Long studentId) throws ResourceNotFoundException {

        Optional<Student> studentData = this.studentRepository.findById(studentId);
        if(studentData.isPresent()) {
            Student student = studentData.orElseThrow(()-> new ResourceNotFoundException("Student not found"));
            idCard.setStudent(student);
            IdentificationCard createdIdCard = this.idCardRepository.save(idCard);
            return createdIdCard;
        }else{
        throw new  ResourceNotFoundException("No matching student, therefore can't create identification Card.");
    }

    }

    public IdentificationCard createIdentificationCard(IdentificationCard card) throws ResourceNotFoundException {

        IdentificationCard createdCard = this.idCardRepository.save(card);
        return createdCard;
    }

    public List<IdentificationCard> getAllIds() throws ResourceNotFoundException {

        List<IdentificationCard> idCards = this.idCardRepository.findAll();
        return idCards;
    }

    public IdentificationCard getIdCardById(Long idCardId) throws ResourceNotFoundException {

        Optional<IdentificationCard> idCardData = this.idCardRepository.findById(idCardId);
        if(idCardData.isPresent()){
            IdentificationCard idCard = idCardData.orElseThrow(()-> new ResourceNotFoundException("id card not found"));
            return idCard;
        }else{
            throw new  ResourceNotFoundException("Not id card matches.");
        }
    }
}
