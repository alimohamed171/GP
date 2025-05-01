package com.GP.GP.utill;

public class Enums {
    public enum Gender { MALE, FEMALE, OTHER }
    public enum RoomType { SINGLE, DOUBLE, DORM }
    public enum RoomStatus { AVAILABLE, OCCUPIED, MAINTENANCE }
    public enum AppointmentType { ACADEMIC, HOUSING, FINANCIAL }
    public enum AppointmentStatus { SCHEDULED, COMPLETED, CANCELLED }
    public enum MealType { BREAKFAST, LUNCH, DINNER }
    public enum Religion {CHRISTIAN, MUSLIM, OTHER}
    public enum StudentType {LOCAL, EXPATRIATE}
    public enum AdmissionRequestStatues{ACCEPTED, REJECTED, UNDER_REVIEW}
    public enum AccommodationStatus {ACTIVE, TERMINATED, PENDING}
    public enum BuildingType { MALE, FEMALE }
    public enum SecurityCheckStatues{ACCEPTED, REJECTED, PENDING}
    public enum AnnualGrade {
        ACCEPTABLE,                         // مقبول
        CARRY_SUBJECTS,                     // محمل بمواد
        EXCUSED_ABSENCE,                    // غياب بعذر
        PROMOTED_WITH_MAIN_AND_SECONDARY,  // منقول بماده وماده ثانويه
        PASS,                               // ناجح
        GOOD,                               // جيد
        VERY_GOOD,                          // جيد جدا
        EXCELLENT,                          // ممتاز
        PROMOTED_WITH_ONE_SUBJECT,         // منقول بماده
        PROMOTED_WITH_TWO_SUBJECTS,        // منقول بمادتين
        FAIL,                               // راسب
        SECOND_ROUND,                       // دور تاني
        PROMOTED_WITH_TWO_AND_ONE_MINOR    // منقول بمادتين وماده فرعيه
    }
}
