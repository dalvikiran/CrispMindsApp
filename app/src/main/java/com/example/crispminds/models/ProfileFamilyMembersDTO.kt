package com.example.crispminds.models

data class ProfileFamilyMembersDTO(
    var id: Long?,
    var acf: ProfileFamilyMembersDetailsDTO?
)

data class ProfileFamilyMembersDetailsDTO(
    val relation: String? = "Son",
    val points: String? = "150 points",
    val imageUrl: String? = "https://i7.pngguru.com/preview/37/640/852/computer-icons-social-media-cultural-diversity-clip-art-diversity.jpg"

)
