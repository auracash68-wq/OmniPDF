package com.example.pdf

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.core.content.FileProvider
import com.example.model.DocumentTagType
import com.example.model.PdfDocumentItem
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID

object PdfEngine {

    private const val PAGE_WIDTH = 595  // Standard A4 width in points (72 dpi)
    private const val PAGE_HEIGHT = 842 // Standard A4 height in points (72 dpi)

    fun initializeSampleDocuments(context: Context): List<PdfDocumentItem> {
        val docsDir = File(context.filesDir, "documents").apply { mkdirs() }

        val doc1 = createSamplePdfFile(
            docsDir,
            "Q3_Financial_Audit_Report.pdf",
            "Q3 Financial Audit & Compliance Report",
            "Executive audit summary of quarterly earnings, ledger balancing, tax compliance, and revenue disclosures.",
            12
        )

        val doc2 = createSamplePdfFile(
            docsDir,
            "Architectural_Blueprint_v4.pdf",
            "Architectural Blueprint - Commercial Sector v4",
            "Auto-enhanced OCR scan. Structural floor plans, electrical conduit schematics, and load-bearing column tolerances.",
            3
        )

        val doc3 = createSamplePdfFile(
            docsDir,
            "Employment_Contract_Final.pdf",
            "Standard Executive Employment Agreement",
            "Encrypted document. Confidential non-disclosure, compensation schedules, and employment terms.",
            6
        )

        val doc4 = createSamplePdfFile(
            docsDir,
            "Client_NDA_Confidential_Signed.pdf",
            "Mutual Non-Disclosure Agreement",
            "Fully e-signed corporate confidentiality agreement executed between all primary counterparties.",
            4
        )

        val doc5 = createSamplePdfFile(
            docsDir,
            "Receipts_October_ScanBatch.pdf",
            "October Expense Receipts Batch",
            "Multi-page scanned expense vouchers, hotel folios, and airline stubs with OCR extraction.",
            8
        )

        val doc6 = createSamplePdfFile(
            docsDir,
            "Annual_Tax_Return_2024.pdf",
            "Annual Corporate Tax Filing Form 1120",
            "Encrypted tax return filing. Federal and state corporate schedules, deductor receipts, and audited filings.",
            18
        )

        val doc7 = createSamplePdfFile(
            docsDir,
            "Product_Roadmap_SlideDeck.pdf",
            "Product Strategy & Engineering Roadmap",
            "Converted presentation deck highlighting Q4 deliverables, cloud architecture upgrades, and AI engine rollout.",
            24
        )

        val doc8 = createSamplePdfFile(
            docsDir,
            "Medical_Insurance_Claim_Form.pdf",
            "Comprehensive Healthcare Claim Assessment",
            "Merged patient claim file comprising clinical notes, prescription invoices, and provider certification.",
            5
        )

        return listOf(
            PdfDocumentItem(
                id = "doc-1",
                title = "Client_NDA_Confidential_Signed.pdf",
                filePath = doc4.absolutePath,
                pageCount = 4,
                fileSizeString = "1.8 MB",
                timeString = "10:42 AM",
                tag = "E-Signed",
                tagType = DocumentTagType.TERTIARY,
                isStarred = true,
                dateGroup = "Today",
                description = "4 vector pages • Signed by Sarah Jenkins"
            ),
            PdfDocumentItem(
                id = "doc-2",
                title = "Receipts_October_ScanBatch.pdf",
                filePath = doc5.absolutePath,
                pageCount = 8,
                fileSizeString = "6.4 MB",
                timeString = "08:15 AM",
                tag = "OCR Ready",
                tagType = DocumentTagType.PRIMARY,
                isStarred = false,
                dateGroup = "Today",
                ocrExtracted = true,
                description = "8 pages • Searchable text extracted"
            ),
            PdfDocumentItem(
                id = "doc-3",
                title = "Annual_Tax_Return_2024.pdf",
                filePath = doc6.absolutePath,
                pageCount = 18,
                fileSizeString = "14.2 MB",
                timeString = "Oct 24",
                tag = "Encrypted (AES-256)",
                tagType = DocumentTagType.SECONDARY,
                isStarred = false,
                dateGroup = "Yesterday",
                isEncrypted = true,
                description = "Passcode Protected • 18 pages"
            ),
            PdfDocumentItem(
                id = "doc-4",
                title = "Product_Roadmap_SlideDeck.pdf",
                filePath = doc7.absolutePath,
                pageCount = 24,
                fileSizeString = "9.5 MB",
                timeString = "Oct 24",
                tag = "From PPTX",
                tagType = DocumentTagType.TERTIARY,
                isStarred = false,
                dateGroup = "Yesterday",
                description = "24 vector presentation slides"
            ),
            PdfDocumentItem(
                id = "doc-5",
                title = "Medical_Insurance_Claim_Form.pdf",
                filePath = doc8.absolutePath,
                pageCount = 5,
                fileSizeString = "840 KB",
                timeString = "Oct 18",
                tag = "Merged (3 files)",
                tagType = DocumentTagType.SECONDARY,
                isStarred = false,
                dateGroup = "Last Week",
                description = "Merged clinical and insurance file"
            ),
            PdfDocumentItem(
                id = "doc-home-1",
                title = "Q3_Financial_Audit_Report.pdf",
                filePath = doc1.absolutePath,
                pageCount = 12,
                fileSizeString = "4.2 MB",
                timeString = "2h ago",
                tag = "Signed",
                tagType = DocumentTagType.PRIMARY,
                isStarred = true,
                dateGroup = "Today",
                description = "Signed executive financial audit"
            ),
            PdfDocumentItem(
                id = "doc-home-2",
                title = "Architectural_Blueprint_v4.pdf",
                filePath = doc2.absolutePath,
                pageCount = 3,
                fileSizeString = "18.5 MB",
                timeString = "Yesterday",
                tag = "OCR Ready",
                tagType = DocumentTagType.TERTIARY,
                isStarred = false,
                dateGroup = "Yesterday",
                ocrExtracted = true,
                description = "High resolution CAD scans"
            ),
            PdfDocumentItem(
                id = "doc-home-3",
                title = "Employment_Contract_Final.pdf",
                filePath = doc3.absolutePath,
                pageCount = 6,
                fileSizeString = "1.1 MB",
                timeString = "3d ago",
                tag = "Encrypted",
                tagType = DocumentTagType.SECONDARY,
                isStarred = false,
                dateGroup = "Last Week",
                isEncrypted = true,
                description = "AES-256 Protected executive agreement"
            )
        )
    }

    private fun createSamplePdfFile(
        dir: File,
        fileName: String,
        heading: String,
        summary: String,
        pages: Int
    ): File {
        val file = File(dir, fileName)
        if (file.exists() && file.length() > 0) return file

        val pdfDoc = PdfDocument()
        for (p in 1..pages) {
            val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, p).create()
            val page = pdfDoc.startPage(pageInfo)
            val canvas = page.canvas

            // Background
            canvas.drawColor(Color.WHITE)

            val paint = Paint(Paint.ANTI_ALIAS_FLAG)

            // Header Banner bar
            paint.color = Color.parseColor("#4059AA")
            canvas.drawRect(0f, 0f, PAGE_WIDTH.toFloat(), 60f, paint)

            paint.color = Color.WHITE
            paint.textSize = 18f
            paint.isFakeBoldText = true
            canvas.drawText("OmniPDF Professional Document", 40f, 38f, paint)

            // Document Title
            paint.color = Color.parseColor("#131B2E")
            paint.textSize = 22f
            paint.isFakeBoldText = true
            canvas.drawText(heading, 40f, 120f, paint)

            paint.color = Color.parseColor("#6C7A71")
            paint.textSize = 12f
            paint.isFakeBoldText = false
            canvas.drawText("Page $p of $pages  •  Generated & Certified by OmniPDF Vault Engine", 40f, 145f, paint)

            // Horizontal rule
            paint.color = Color.parseColor("#DAE2FD")
            canvas.drawLine(40f, 165f, (PAGE_WIDTH - 40).toFloat(), 165f, paint)

            // Content text
            paint.color = Color.parseColor("#3C4A42")
            paint.textSize = 14f
            val words = summary.split(" ")
            var line = ""
            var yPos = 200f
            for (w in words) {
                if (paint.measureText("$line $w") < (PAGE_WIDTH - 80)) {
                    line = if (line.isEmpty()) w else "$line $w"
                } else {
                    canvas.drawText(line, 40f, yPos, paint)
                    yPos += 22f
                    line = w
                }
            }
            if (line.isNotEmpty()) {
                canvas.drawText(line, 40f, yPos, paint)
            }

            // Mock Data grid
            paint.color = Color.parseColor("#F2F3FF")
            canvas.drawRoundRect(40f, 300f, (PAGE_WIDTH - 40).toFloat(), 480f, 12f, 12f, paint)

            paint.color = Color.parseColor("#131B2E")
            paint.textSize = 13f
            paint.isFakeBoldText = true
            canvas.drawText("Section $p.0: Technical Record & Cryptographic Signature", 60f, 335f, paint)

            paint.isFakeBoldText = false
            paint.color = Color.parseColor("#3C4A42")
            canvas.drawText("• SHA-256 Hash Digest: a8f9c2d7e1b40284bfa6c9134d40", 60f, 370f, paint)
            canvas.drawText("• Security Classification: Enterprise Confidential", 60f, 395f, paint)
            canvas.drawText("• OCR Extraction Confidence: 99.8%", 60f, 420f, paint)
            canvas.drawText("• Vault Storage Status: 100% Synced to Cloud", 60f, 445f, paint)

            // Footer
            paint.color = Color.parseColor("#DAE2FD")
            canvas.drawLine(40f, (PAGE_HEIGHT - 60).toFloat(), (PAGE_WIDTH - 40).toFloat(), (PAGE_HEIGHT - 60).toFloat(), paint)

            paint.color = Color.parseColor("#6C7A71")
            paint.textSize = 10f
            canvas.drawText("CONFIDENTIAL & PROPRIETARY — OmniPDF Enterprise Document Suite", 40f, (PAGE_HEIGHT - 35).toFloat(), paint)
            canvas.drawText("Page $p", (PAGE_WIDTH - 80).toFloat(), (PAGE_HEIGHT - 35).toFloat(), paint)

            pdfDoc.finishPage(page)
        }

        try {
            FileOutputStream(file).use { out ->
                pdfDoc.writeTo(out)
            }
        } finally {
            pdfDoc.close()
        }

        return file
    }

    fun createPdfFromBitmaps(
        context: Context,
        bitmaps: List<Bitmap>,
        fileNameWithoutExt: String
    ): File {
        val safeName = fileNameWithoutExt.trim().replace(Regex("[^a-zA-Z0-9_\\-]"), "_")
        val docsDir = File(context.filesDir, "documents").apply { mkdirs() }
        val outputFile = File(docsDir, "${safeName}_${System.currentTimeMillis()}.pdf")

        val pdfDoc = PdfDocument()
        bitmaps.forEachIndexed { index, bmp ->
            val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, index + 1).create()
            val page = pdfDoc.startPage(pageInfo)
            val canvas = page.canvas

            canvas.drawColor(Color.WHITE)

            // Scale bitmap to fit nicely within margins
            val targetWidth = PAGE_WIDTH - 40f
            val targetHeight = PAGE_HEIGHT - 80f
            val scale = minOf(targetWidth / bmp.width, targetHeight / bmp.height)
            val scaledW = bmp.width * scale
            val scaledH = bmp.height * scale
            val left = (PAGE_WIDTH - scaledW) / 2f
            val top = 40f

            val destRect = Rect(left.toInt(), top.toInt(), (left + scaledW).toInt(), (top + scaledH).toInt())
            canvas.drawBitmap(bmp, null, destRect, null)

            // Page number footer
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.parseColor("#6C7A71")
                textSize = 10f
            }
            canvas.drawText("Page ${index + 1} of ${bitmaps.size} • Scanned with OmniPDF", 40f, PAGE_HEIGHT - 20f, paint)

            pdfDoc.finishPage(page)
        }

        FileOutputStream(outputFile).use { out ->
            pdfDoc.writeTo(out)
        }
        pdfDoc.close()
        return outputFile
    }

    fun mergePdfFiles(
        context: Context,
        files: List<File>,
        mergedTitle: String
    ): File {
        val docsDir = File(context.filesDir, "documents").apply { mkdirs() }
        val outputFile = File(docsDir, "${mergedTitle.replace(" ", "_")}_${System.currentTimeMillis()}.pdf")

        val newPdf = PdfDocument()
        var totalPageCount = 0

        for (file in files) {
            if (!file.exists()) continue
            try {
                val pfd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                val renderer = PdfRenderer(pfd)
                for (i in 0 until renderer.pageCount) {
                    totalPageCount++
                    val rendererPage = renderer.openPage(i)
                    val bmp = Bitmap.createBitmap(PAGE_WIDTH, PAGE_HEIGHT, Bitmap.Config.ARGB_8888)
                    rendererPage.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    rendererPage.close()

                    val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, totalPageCount).create()
                    val page = newPdf.startPage(pageInfo)
                    page.canvas.drawBitmap(bmp, 0f, 0f, null)
                    newPdf.finishPage(page)
                    bmp.recycle()
                }
                renderer.close()
                pfd.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (totalPageCount == 0) {
            // Fallback: create empty page
            val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create()
            val page = newPdf.startPage(pageInfo)
            page.canvas.drawColor(Color.WHITE)
            newPdf.finishPage(page)
        }

        FileOutputStream(outputFile).use { out ->
            newPdf.writeTo(out)
        }
        newPdf.close()

        return outputFile
    }

    fun signPdfWithSignature(
        context: Context,
        sourcePdf: File,
        signatureBitmap: Bitmap,
        outputTitle: String
    ): File {
        val docsDir = File(context.filesDir, "documents").apply { mkdirs() }
        val outputFile = File(docsDir, "${outputTitle.replace(" ", "_")}_Signed.pdf")

        val newPdf = PdfDocument()
        val pfd = ParcelFileDescriptor.open(sourcePdf, ParcelFileDescriptor.MODE_READ_ONLY)
        val renderer = PdfRenderer(pfd)

        for (i in 0 until renderer.pageCount) {
            val rendererPage = renderer.openPage(i)
            val bmp = Bitmap.createBitmap(PAGE_WIDTH, PAGE_HEIGHT, Bitmap.Config.ARGB_8888)
            rendererPage.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            rendererPage.close()

            val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, i + 1).create()
            val page = newPdf.startPage(pageInfo)
            val canvas = page.canvas
            canvas.drawBitmap(bmp, 0f, 0f, null)

            // Overlay signature on the last page or all pages
            if (i == renderer.pageCount - 1 || renderer.pageCount == 1) {
                // Signature box at bottom right
                val sigWidth = 160f
                val sigHeight = 70f
                val left = (PAGE_WIDTH - sigWidth - 50).toInt()
                val top = (PAGE_HEIGHT - sigHeight - 90).toInt()

                val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                paint.color = Color.parseColor("#006C49")
                paint.textSize = 10f
                canvas.drawText("Digitally Signed by Sarah Jenkins", left.toFloat(), top - 6f, paint)

                canvas.drawBitmap(signatureBitmap, null, Rect(left, top, (left + sigWidth).toInt(), (top + sigHeight).toInt()), null)

                // Timestamp
                paint.color = Color.GRAY
                paint.textSize = 8f
                canvas.drawText("Timestamp: ${System.currentTimeMillis()} (AES-256 Verified)", left.toFloat(), top + sigHeight + 14f, paint)
            }

            newPdf.finishPage(page)
            bmp.recycle()
        }

        renderer.close()
        pfd.close()

        FileOutputStream(outputFile).use { out ->
            newPdf.writeTo(out)
        }
        newPdf.close()

        return outputFile
    }

    fun addWatermarkToPdf(
        context: Context,
        sourcePdf: File,
        watermarkText: String,
        outputTitle: String
    ): File {
        val docsDir = File(context.filesDir, "documents").apply { mkdirs() }
        val outputFile = File(docsDir, "${outputTitle.replace(" ", "_")}_Watermarked.pdf")

        val newPdf = PdfDocument()
        val pfd = ParcelFileDescriptor.open(sourcePdf, ParcelFileDescriptor.MODE_READ_ONLY)
        val renderer = PdfRenderer(pfd)

        for (i in 0 until renderer.pageCount) {
            val rendererPage = renderer.openPage(i)
            val bmp = Bitmap.createBitmap(PAGE_WIDTH, PAGE_HEIGHT, Bitmap.Config.ARGB_8888)
            rendererPage.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            rendererPage.close()

            val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, i + 1).create()
            val page = newPdf.startPage(pageInfo)
            val canvas = page.canvas
            canvas.drawBitmap(bmp, 0f, 0f, null)

            // Draw semi-transparent diagonal watermark
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.parseColor("#44BA1A1A") // semi-transparent red
                textSize = 54f
                isFakeBoldText = true
                textAlign = Paint.Align.CENTER
            }

            canvas.save()
            canvas.rotate(-35f, PAGE_WIDTH / 2f, PAGE_HEIGHT / 2f)
            canvas.drawText(watermarkText.uppercase(), PAGE_WIDTH / 2f, PAGE_HEIGHT / 2f, paint)
            canvas.restore()

            newPdf.finishPage(page)
            bmp.recycle()
        }

        renderer.close()
        pfd.close()

        FileOutputStream(outputFile).use { out ->
            newPdf.writeTo(out)
        }
        newPdf.close()

        return outputFile
    }

    fun compressPdf(
        context: Context,
        sourcePdf: File,
        outputTitle: String
    ): File {
        val docsDir = File(context.filesDir, "documents").apply { mkdirs() }
        val outputFile = File(docsDir, "${outputTitle.replace(" ", "_")}_Compressed.pdf")

        val newPdf = PdfDocument()
        val pfd = ParcelFileDescriptor.open(sourcePdf, ParcelFileDescriptor.MODE_READ_ONLY)
        val renderer = PdfRenderer(pfd)

        for (i in 0 until renderer.pageCount) {
            val rendererPage = renderer.openPage(i)
            // Render at 60% resolution for compression
            val w = (PAGE_WIDTH * 0.7f).toInt()
            val h = (PAGE_HEIGHT * 0.7f).toInt()
            val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            rendererPage.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            rendererPage.close()

            val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, i + 1).create()
            val page = newPdf.startPage(pageInfo)
            page.canvas.drawBitmap(bmp, null, Rect(0, 0, PAGE_WIDTH, PAGE_HEIGHT), null)
            newPdf.finishPage(page)
            bmp.recycle()
        }

        renderer.close()
        pfd.close()

        FileOutputStream(outputFile).use { out ->
            newPdf.writeTo(out)
        }
        newPdf.close()

        return outputFile
    }

    fun renderFirstPageThumbnail(pdfFile: File): Bitmap? {
        if (!pdfFile.exists() || pdfFile.length() == 0L) return null
        return try {
            val pfd = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val renderer = PdfRenderer(pfd)
            if (renderer.pageCount == 0) {
                renderer.close()
                pfd.close()
                return null
            }
            val page = renderer.openPage(0)
            val bmp = Bitmap.createBitmap(160, 220, Bitmap.Config.ARGB_8888)
            page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            renderer.close()
            pfd.close()
            bmp
        } catch (e: Exception) {
            null
        }
    }

    fun sharePdfFile(context: Context, file: File) {
        if (!file.exists()) return
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share ${file.name}"))
    }
}
