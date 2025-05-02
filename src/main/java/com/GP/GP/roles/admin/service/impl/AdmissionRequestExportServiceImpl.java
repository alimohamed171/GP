package com.GP.GP.roles.admin.service.impl;

import com.GP.GP.entities.User;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.admin.models.dto.request.AdmissionRequestExportDtO;
import com.GP.GP.roles.admin.models.dto.request.AdmissionRequestFilterDTO;
import com.GP.GP.roles.admin.models.mapper.AdmissionRequestExportMapper;
import com.GP.GP.roles.admin.service.contracts.AdmissionRequestExportService;
import com.GP.GP.security.Role;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AdmissionRequestExportServiceImpl implements AdmissionRequestExportService {
    @Autowired
    private UserRepository admissionRequestRepository;

    @Autowired
    private AdmissionRequestExportMapper exportMapper;

    @Override
    public ByteArrayInputStream exportAllAdmissionRequestsToExcel() {

        List<User> requests = admissionRequestRepository.findByRole(Role.USER);

        List<AdmissionRequestExportDtO> dtos = exportMapper.toDtoList(requests);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Admission Requests");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("الاسم كامل");
            header.createCell(1).setCellValue("البريد الالكتروني");
            header.createCell(2).setCellValue("الكليه");
            header.createCell(3).setCellValue("الرقم القومي");
            header.createCell(4).setCellValue("رقم الهاتف");
            header.createCell(5).setCellValue("الكليه");
            header.createCell(6).setCellValue("السنه الدراسيه");
            header.createCell(7).setCellValue("تاريخ الميلاد");
            header.createCell(8).setCellValue("النوع");
            header.createCell(9).setCellValue("عنوان السكن");
            header.createCell(10).setCellValue("العنوان التفصيلي");
            header.createCell(11).setCellValue("محل الميلاد");
            header.createCell(12).setCellValue("النوع");
            header.createCell(13).setCellValue("الديانه");
            header.createCell(14).setCellValue("اسم الاب");
            header.createCell(15).setCellValue("الرقم القومى الاب");
            header.createCell(16).setCellValue("وظيفه الاب");
            header.createCell(17).setCellValue("رقم هاتف الاب");
            header.createCell(18).setCellValue("اسم الوصي");
            header.createCell(19).setCellValue("الرقم القومى الوصي");
            header.createCell(20).setCellValue("رقم هاتف الوصي");
            header.createCell(21).setCellValue("المعدل التراكمي");
            header.createCell(22).setCellValue("حاله الطلب");
            header.createCell(23).setCellValue("السكن في السنوات السابقة");
            header.createCell(24).setCellValue("الأسرة في الخارج");
            header.createCell(25).setCellValue("ذوي الاحتياجات الخاصة");
            header.createCell(26).setCellValue("المرحلة الثانوية");
            header.createCell(27).setCellValue("مجموع الثانوية العامة");
            header.createCell(28).setCellValue("رقم الجواز");
            header.createCell(29).setCellValue("جهة إصدار الجواز");
            header.createCell(30).setCellValue("الفحص الامني");

            int rowIdx = 1;
            for (AdmissionRequestExportDtO dto : dtos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(dto.getFullName());
                row.createCell(1).setCellValue(dto.getEmail());
                row.createCell(2).setCellValue(dto.getUniversityName());
                row.createCell(3).setCellValue(dto.getNationalId());
                row.createCell(4).setCellValue(dto.getMobileNumber());
                row.createCell(5).setCellValue(dto.getFaculty());
                row.createCell(6).setCellValue(dto.getLevel());
                row.createCell(7).setCellValue(dto.getDateOfBirth() != null ? dto.getDateOfBirth().toString() : "");
                row.createCell(8).setCellValue(dto.getStudentType() != null ? dto.getStudentType().toString() : "");
                row.createCell(9).setCellValue(dto.getResidenceAddress());
                row.createCell(10).setCellValue(dto.getDetailedAddress());
                row.createCell(11).setCellValue(dto.getPlaceOfBirth());
                row.createCell(12).setCellValue(dto.getGender() != null ? dto.getGender().toString() : "");
                row.createCell(13).setCellValue(dto.getReligion() != null ? dto.getReligion().toString() : "");
                row.createCell(14).setCellValue(dto.getFatherName());
                row.createCell(15).setCellValue(dto.getFatherNationalId());
                row.createCell(16).setCellValue(dto.getFatherOccupation());
                row.createCell(17).setCellValue(dto.getFatherPhoneNumber());
                row.createCell(18).setCellValue(dto.getGuardianName());
                row.createCell(19).setCellValue(dto.getGuardianNationalId());
                row.createCell(20).setCellValue(dto.getGuardianPhoneNumber());
                row.createCell(21).setCellValue(dto.getPreviousAcademicYearGpa() != null ? dto.getPreviousAcademicYearGpa().toString() : "");
                row.createCell(22).setCellValue(dto.getStatus() != null ? dto.getStatus().toString() : "");
                row.createCell(23).setCellValue(dto.getHousingInPreviousYears());
                row.createCell(24).setCellValue(dto.getFamilyAbroad() != null ? dto.getFamilyAbroad().toString() : "");
                row.createCell(25).setCellValue(dto.getSpecialNeeds() != null ? dto.getSpecialNeeds().toString() : "");
                row.createCell(26).setCellValue(dto.getSecondaryDivision());
                row.createCell(27).setCellValue(dto.getTotalGradesHighSchool() != null ? dto.getTotalGradesHighSchool().toString() : "");
                row.createCell(28).setCellValue(dto.getPassportNumber());
                row.createCell(29).setCellValue(dto.getPassportIssuingAuthority());
                row.createCell(30).setCellValue(dto.getSecurityCheck() != null ? dto.getSecurityCheck().toString() : "");

            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Failed to export Excel file", e);
        }
    }

    @Override
    public List<User> filterAdmissionRequests(AdmissionRequestFilterDTO filterDTO) {
        List<User> requests = admissionRequestRepository.findByRole(Role.USER);
        return requests.stream()
                .filter(user -> filterDTO.getStatus() == null || filterDTO.getStatus().contains(user.getStatus()))
                .filter(user -> filterDTO.getGender() == null || user.getGender() == filterDTO.getGender())
                .filter(user -> filterDTO.getUniversityName() == null || filterDTO.getUniversityName().contains(user.getUniversity().getName()))
                .filter(user -> filterDTO.getFaculty() == null || filterDTO.getFaculty().contains(user.getFaculty()))
                .filter(user -> filterDTO.getLevel() == null || filterDTO.getLevel().contains(user.getLevel()))
                .filter(user -> filterDTO.getSpecialNeeds() == null || user.getSpecialNeeds().equals(filterDTO.getSpecialNeeds()))
                .filter(user -> filterDTO.getStartDate() == null || !user.getCreatedAt().isAfter(filterDTO.getStartDate()))
                .filter(user -> filterDTO.getEndDate() == null || !user.getCreatedAt().isBefore(filterDTO.getEndDate()))
                .filter(user -> filterDTO.getStudentType() == null || user.getStudentType()==filterDTO.getStudentType())
                .filter(user -> filterDTO.getSecurityCheck() == null || filterDTO.getSecurityCheck().contains(user.getSecurityCheck()))
                .filter(user -> filterDTO.getHasPenalty() == null
                        || (filterDTO.getHasPenalty() ? user.getPenalties() != null && !user.getPenalties().isEmpty()
                        : user.getPenalties() == null || user.getPenalties().isEmpty()))
                .collect(Collectors.toList());
    }
    @Override
    public ByteArrayInputStream exportFilteredAdmissionRequestsToExcel(List<User> filteredRequests, List<String> selectedColumns) {
        List<AdmissionRequestExportDtO> dtos = exportMapper.toDtoList(filteredRequests);

        // Map of Arabic column headers
        Map<String, String> columnHeaders = Map.ofEntries(
                Map.entry("fullName", "الاسم كامل"),
                Map.entry("email", "البريد الالكتروني"),
                Map.entry("universityName", "الجامعة"),
                Map.entry("nationalId", "الرقم القومي"),
                Map.entry("mobileNumber", "رقم الهاتف"),
                Map.entry("faculty", "الكلية"),
                Map.entry("level", "السنة الدراسية"),
                Map.entry("dateOfBirth", "تاريخ الميلاد"),
                Map.entry("studentType", "النوع"),
                Map.entry("residenceAddress", "عنوان السكن"),
                Map.entry("detailedAddress", "العنوان التفصيلي"),
                Map.entry("placeOfBirth", "محل الميلاد"),
                Map.entry("gender", "النوع"),
                Map.entry("religion", "الديانه"),
                Map.entry("fatherName", "اسم الاب"),
                Map.entry("fatherNationalId", "الرقم القومى الاب"),
                Map.entry("fatherOccupation", "وظيفه الاب"),
                Map.entry("fatherPhoneNumber", "رقم هاتف الاب"),
                Map.entry("guardianName", "اسم الوصي"),
                Map.entry("guardianNationalId", "الرقم القومى الوصي"),
                Map.entry("guardianPhoneNumber", "رقم هاتف الوصي"),
                Map.entry("previousAcademicYearGpa", "المعدل التراكمي"),
                Map.entry("status", "حاله الطلب"),
                Map.entry("housingInPreviousYears", "السكن في السنوات السابقة"),
                Map.entry("familyAbroad", "الأسرة في الخارج"),
                Map.entry("specialNeeds", "ذوي الاحتياجات الخاصة"),
                Map.entry("secondaryDivision", "المرحلة الثانوية"),
                Map.entry("totalGradesHighSchool", "مجموع الثانوية العامة"),
                Map.entry("passportNumber", "رقم الجواز"),
                Map.entry("passportIssuingAuthority", "جهة إصدار الجواز"),
                Map.entry("securityCheck", "الفحص الامني")
        );

        // Default columns if none are selected
        List<String> defaultColumns = List.of(
                "fullName",
                "nationalId",
                "universityName",
                "fatherName",
                "fatherNationalId"
        );

        // Use selectedColumns or fallback to default
        List<String> columnsToExport = (selectedColumns == null || selectedColumns.isEmpty())
                ? defaultColumns
                : selectedColumns;

        // Map of value getters
        Map<String, Function<AdmissionRequestExportDtO, String>> valueGetters = Map.ofEntries(
                Map.entry("fullName", AdmissionRequestExportDtO::getFullName),
                Map.entry("email", AdmissionRequestExportDtO::getEmail),
                Map.entry("universityName", AdmissionRequestExportDtO::getUniversityName),
                Map.entry("nationalId", AdmissionRequestExportDtO::getNationalId),
                Map.entry("mobileNumber", AdmissionRequestExportDtO::getMobileNumber),
                Map.entry("faculty", AdmissionRequestExportDtO::getFaculty),
                Map.entry("level", AdmissionRequestExportDtO::getLevel),
                Map.entry("dateOfBirth", dto -> dto.getDateOfBirth() != null ? dto.getDateOfBirth().toString() : ""),
                Map.entry("studentType", dto -> dto.getStudentType() != null ? dto.getStudentType().toString() : ""),
                Map.entry("residenceAddress", AdmissionRequestExportDtO::getResidenceAddress),
                Map.entry("detailedAddress", AdmissionRequestExportDtO::getDetailedAddress),
                Map.entry("placeOfBirth", AdmissionRequestExportDtO::getPlaceOfBirth),
                Map.entry("gender", dto -> dto.getGender() != null ? dto.getGender().toString() : ""),
                Map.entry("religion", dto -> dto.getReligion() != null ? dto.getReligion().toString() : ""),
                Map.entry("fatherName", AdmissionRequestExportDtO::getFatherName),
                Map.entry("fatherNationalId", AdmissionRequestExportDtO::getFatherNationalId),
                Map.entry("fatherOccupation", AdmissionRequestExportDtO::getFatherOccupation),
                Map.entry("fatherPhoneNumber", AdmissionRequestExportDtO::getFatherPhoneNumber),
                Map.entry("guardianName", AdmissionRequestExportDtO::getGuardianName),
                Map.entry("guardianNationalId", AdmissionRequestExportDtO::getGuardianNationalId),
                Map.entry("guardianPhoneNumber", AdmissionRequestExportDtO::getGuardianPhoneNumber),
                Map.entry("previousAcademicYearGpa", dto -> dto.getPreviousAcademicYearGpa() != null ? dto.getPreviousAcademicYearGpa().toString() : ""),
                Map.entry("status", dto -> dto.getStatus() != null ? dto.getStatus().toString() : ""),
                Map.entry("housingInPreviousYears", AdmissionRequestExportDtO::getHousingInPreviousYears),
                Map.entry("familyAbroad", dto -> dto.getFamilyAbroad() != null ? dto.getFamilyAbroad().toString() : ""),
                Map.entry("specialNeeds", dto -> dto.getSpecialNeeds() != null ? dto.getSpecialNeeds().toString() : ""),
                Map.entry("secondaryDivision", AdmissionRequestExportDtO::getSecondaryDivision),
                Map.entry("totalGradesHighSchool", dto -> dto.getTotalGradesHighSchool() != null ? dto.getTotalGradesHighSchool().toString() : ""),
                Map.entry("passportNumber", AdmissionRequestExportDtO::getPassportNumber),
                Map.entry("passportIssuingAuthority", AdmissionRequestExportDtO::getPassportIssuingAuthority),
                Map.entry("securityCheck", dto -> dto.getSecurityCheck() != null ? dto.getSecurityCheck().toString() : "")
        );

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Admission Requests");

            // Create header row
            Row header = sheet.createRow(0);
            for (int i = 0; i < columnsToExport.size(); i++) {
                String key = columnsToExport.get(i);
                header.createCell(i).setCellValue(columnHeaders.getOrDefault(key, key));
            }

            // Fill data rows
            int rowIdx = 1;
            for (AdmissionRequestExportDtO dto : dtos) {
                Row row = sheet.createRow(rowIdx++);
                for (int i = 0; i < columnsToExport.size(); i++) {
                    String key = columnsToExport.get(i);
                    Function<AdmissionRequestExportDtO, String> getter = valueGetters.get(key);
                    row.createCell(i).setCellValue(getter != null ? getter.apply(dto) : "");
                }
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Failed to export Excel file", e);
        }
    }


    @Override
    public ByteArrayInputStream generateSecurityCheckTemplate() {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Admission Requests Security Check Template");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("اسم الطالب");
            header.createCell(1).setCellValue("الرقم القومي");
            header.createCell(2).setCellValue("الفحص الأمني");
            header.createCell(3).setCellValue("الملاحظات");

            Row exampleRow = sheet.createRow(1);
            exampleRow.createCell(0).setCellValue("بسنت حيدر محمد احمد");
            exampleRow.createCell(1).setCellValue("30210190104442");
            exampleRow.createCell(2).setCellValue("REJECTED"); // or REJECTED
            exampleRow.createCell(3).setCellValue("اسباب سريه للفايه");
            workbook.write(out);
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Failed to export Excel file", e);
        }

    }
}




