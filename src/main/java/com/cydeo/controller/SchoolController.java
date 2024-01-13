package com.cydeo.controller;

import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TeacherDTO;
import com.cydeo.service.AddressService;
import com.cydeo.service.ParentService;
import com.cydeo.service.StudentService;
import com.cydeo.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SchoolController {

    //write a method for teachers and return list of teachers

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ParentService parentService;
    private final AddressService addressService;

    public SchoolController(TeacherService teacherService, StudentService studentService, ParentService parentService, AddressService addressService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.parentService = parentService;
        this.addressService = addressService;
    }

    @GetMapping("/teachers")
    public List<TeacherDTO> allTeachers(){
        return teacherService.findAll();
    }
    /*  7MIN
        create an endpoint for students, where json response includes
        "students are successfully retrieved." message
        code:200
        success:true
        and student data
     */

    @GetMapping("/students")
    public ResponseEntity<ResponseWrapper> readAllStudents(){

        return ResponseEntity.ok(new ResponseWrapper
                ("students are successfully retrieved."
                ,studentService.findAll()));
    }

    /*     7 MIN
           create a parents endpoint where status code is 202
           additional header has "Parent" , "Returned"
           and following body structure
           "parents are successfully retrieved." message
           code:202
           success: true
           and parent data.
     */

    @GetMapping("/parents")
    public ResponseEntity<ResponseWrapper> readAllParents(){

        return ResponseEntity
                            .status(HttpStatus.ACCEPTED)
                            .header("Parent","Returned")
                            .body(new ResponseWrapper(true,"parents are successfully retrieved.",
                                    HttpStatus.ACCEPTED.value(),parentService.findAll()));
    }

    /*  6MIN
        create an endpoint for individual address information
        /address/1 2 3
        return status code 200
        "address .. is successfully retrieved" message
        success true
        and address information
     */
    @GetMapping("/address/{id}")
    public ResponseEntity<ResponseWrapper> getAddressById(@PathVariable("id") Long id) throws Exception {

        //find the address to return
        AddressDTO addressDTO = addressService.findById(id);

        return ResponseEntity.ok(new ResponseWrapper("Address "+id+" is successfully retrieved",addressDTO));

    }

    /*
        HW
        create an endpoint to update individual address information
        return updated address directly.
     */
    @PutMapping("/address/{id}")
    public AddressDTO updateAddress(@PathVariable("id") Long id, @RequestBody AddressDTO addressDTO) throws Exception {
        addressDTO.setId(id);

        AddressDTO updateAddress = addressService.update(addressDTO);

        return updateAddress;
    }

     /*
        create an endpoint for creating teacher
        return Http status 201
        custom header "teacherId","idCreated"
        responseWrapper("Teacher is created",teacherInfo)
     */

    @PostMapping("/teachers")
    public ResponseEntity<ResponseWrapper> createTeacher(@Valid @RequestBody TeacherDTO teacherDTO){
        TeacherDTO teacher = teacherService.createTeacher(teacherDTO);

        ResponseWrapper responseWrapper = new ResponseWrapper(true,"Teacher is created."
                ,HttpStatus.CREATED.value(),teacher);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("teacherId",String.valueOf(teacher.getId()))
                .body(responseWrapper);

    }




}
