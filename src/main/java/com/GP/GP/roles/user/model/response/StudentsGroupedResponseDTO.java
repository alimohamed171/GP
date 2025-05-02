package com.GP.GP.roles.user.model.response;

import com.GP.GP.roles.user.model.dto.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentsGroupedResponseDTO {
    private List<StudentDto> oldStudents;
    private List<StudentDto> newStudents;
}
