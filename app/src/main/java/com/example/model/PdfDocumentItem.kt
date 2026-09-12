package com.example.model

data class PdfDocumentItem(
    val id: String,
    val title: String,
    val filePath: String? = null,
    val pageCount: Int,
    val fileSizeString: String,
    val timeString: String,
    val tag: String,
    val tagType: DocumentTagType = DocumentTagType.PRIMARY,
    val isStarred: Boolean = false,
    val dateGroup: String = "Today",
    val isEncrypted: Boolean = false,
    val description: String? = null,
    val ocrExtracted: Boolean = false
)

enum class DocumentTagType {
    PRIMARY,    // Green / OCR Ready / Signed
    SECONDARY,  // Blue / Encrypted / Merged
    TERTIARY    // Orange / E-Signed / Converted
}
