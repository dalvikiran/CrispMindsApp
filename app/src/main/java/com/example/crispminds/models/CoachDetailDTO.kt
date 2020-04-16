package com.example.crispminds.models

import java.io.Serializable

data class CoachListResponse(
    val success : SuccessDTO?,
    val error : ErrorDTO?,
    val totalCount : Long?,
    val CoachList : List<CoachDTO>?
) : Serializable


data class CoachDTO(
    val coachId : Long?,
    val coachName : String?,
    val coachIntro : String?,
    val aboutCoach : String?,
    val totalLikes : String?,
    val specializationNames : List<String>?,
    val certification : List<CertificationAndAwards>?,
    val meetings : List<Meeting>?,
    val subscriptions : List<Subscriptions>?,
    val testimonials : List<Testimonial>?,
    val aboutCoachVideoDto : VideoMasterDTO?,
    val coachVideos : List<VideoMasterDTO>?,
    val coachProfileDto : ImageDTO?
) : Serializable

data class Specialization(
    val specialization_content : String
): Serializable

data class Testimonial(
    val coachId : Long,
    val testimonialId : Long,
    val sequenceNo : Long,
    val testimonialText : String
): Serializable

data class CertificationAndAwards(
    val certificateName : String,
    val certificationId : Long,
    val coachId : Long,
    val dateOfCertification : String,
    val certification_and_awards_Pics : ImageDTO
): Serializable

data class Meeting(
    val coachId : Long,
    val meetingId : Long,
    val createdOn : String,
    val meetingType : String,
    val durationMinutes : String,
    val meetingDescription : String,
    val amount : String
): Serializable

data class Subscriptions(
    val coachId : Long,
    val amount : String,
    val createdOn : String,
    val durationMinutes : String,
    val subscriptionDescription : String,
    val subscriptionId : Long,
    val subscriptionType : String
): Serializable

data class CoachDetailResponse(
    val success : SuccessDTO?,
    val error : ErrorDTO?,
    val totalCount : Long?,
    val coach : CoachDTO?
) : Serializable
