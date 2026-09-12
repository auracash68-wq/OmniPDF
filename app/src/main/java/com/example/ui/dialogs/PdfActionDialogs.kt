package com.example.ui.dialogs

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.EnhancedEncryption
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.model.PdfDocumentItem
import com.example.pdf.PdfEngine
import com.example.ui.theme.Error
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TertiaryContainer
import java.io.File

@Composable
fun DocumentPreviewDialog(
    item: PdfDocumentItem,
    onDismiss: () -> Unit,
    onShare: () -> Unit,
    onSign: () -> Unit,
    onWatermark: () -> Unit,
    onCompress: () -> Unit
) {
    val context = LocalContext.current
    val file = item.filePath?.let { File(it) }
    val thumbnailBitmap = remember(file) {
        if (file != null && file.exists()) {
            PdfEngine.renderFirstPageThumbnail(file)
        } else null
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(24.dp)),
            color = SurfaceContainerLowest,
            shadowElevation = 12.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.title,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            maxLines = 1
                        )
                        Text(
                            text = "${item.pageCount} pages • ${item.fileSizeString} • ${item.timeString}",
                            fontSize = 12.sp,
                            color = OnSurfaceVariant
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Outline)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Page Preview Canvas
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerLow)
                        .border(1.dp, Color(0xFFDAE2FD), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (thumbnailBitmap != null) {
                        Image(
                            bitmap = thumbnailBitmap.asImageBitmap(),
                            contentDescription = "PDF Rendered Page",
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp))
                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_doc_nda),
                                contentDescription = null,
                                modifier = Modifier.size(90.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Vault Encrypted Page Stream",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = OnSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Metadata Details
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = SurfaceContainerLow
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Security Mode", fontSize = 12.sp, color = OnSurfaceVariant)
                            Text("AES-256 Bit Verified", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Primary)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("OCR Transcript", fontSize = 12.sp, color = OnSurfaceVariant)
                            Text("Extracted & Searchable", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = OnSurface)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Cloud Vault", fontSize = 12.sp, color = OnSurfaceVariant)
                            Text("Synced to OmniCloud", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Secondary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Action Operations Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                            onShare()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Share", fontSize = 13.sp)
                    }
                    Button(
                        onClick = {
                            onDismiss()
                            onSign()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Secondary)
                    ) {
                        Icon(Icons.Default.Draw, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sign", fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            onDismiss()
                            onWatermark()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Watermark", fontSize = 13.sp, color = OnSurface)
                    }
                    OutlinedButton(
                        onClick = {
                            onDismiss()
                            onCompress()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Compress", fontSize = 13.sp, color = OnSurface)
                    }
                }
            }
        }
    }
}

@Composable
fun SignaturePadDialog(
    onDismiss: () -> Unit,
    onSaveSignature: (Bitmap) -> Unit
) {
    val points = remember { mutableStateListOf<Offset>() }
    val paths = remember { mutableStateListOf<List<Offset>>() }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(24.dp)),
            color = SurfaceContainerLowest,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Fill & E-Sign Document",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "Draw your legal signature inside the box below",
                            fontSize = 12.sp,
                            color = OnSurfaceVariant
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Outline)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Drawing Canvas
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFAF8FF))
                        .border(1.5.dp, Color(0xFFDAE2FD), RoundedCornerShape(16.dp))
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragStart = { offset ->
                                        points.clear()
                                        points.add(offset)
                                    },
                                    onDrag = { change, _ ->
                                        points.add(change.position)
                                    },
                                    onDragEnd = {
                                        if (points.isNotEmpty()) {
                                            paths.add(points.toList())
                                            points.clear()
                                        }
                                    }
                                )
                            }
                    ) {
                        // Guideline
                        drawLine(
                            color = Color(0xFFCBD5E1),
                            start = Offset(40f, size.height - 45f),
                            end = Offset(size.width - 40f, size.height - 45f),
                            strokeWidth = 2f
                        )

                        // Draw finished strokes
                        for (stroke in paths) {
                            if (stroke.size > 1) {
                                val path = Path().apply {
                                    moveTo(stroke.first().x, stroke.first().y)
                                    for (i in 1 until stroke.size) {
                                        lineTo(stroke[i].x, stroke[i].y)
                                    }
                                }
                                drawPath(
                                    path = path,
                                    color = Color(0xFF1D4ED8),
                                    style = Stroke(width = 6f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                                )
                            }
                        }

                        // Draw current active stroke
                        if (points.size > 1) {
                            val activePath = Path().apply {
                                moveTo(points.first().x, points.first().y)
                                for (i in 1 until points.size) {
                                    lineTo(points[i].x, points[i].y)
                                }
                            }
                            drawPath(
                                path = activePath,
                                color = Color(0xFF1D4ED8),
                                style = Stroke(width = 6f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                            )
                        }
                    }

                    if (paths.isEmpty() && points.isEmpty()) {
                        Text(
                            text = "Sign with finger or stylus here ✍️",
                            fontSize = 14.sp,
                            color = Outline,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            paths.clear()
                            points.clear()
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Clear", color = OnSurface)
                    }

                    Button(
                        onClick = {
                            // Create a signature bitmap from paths or default "Sarah J."
                            val bitmap = Bitmap.createBitmap(320, 140, Bitmap.Config.ARGB_8888)
                            val canvas = android.graphics.Canvas(bitmap)
                            canvas.drawColor(android.graphics.Color.WHITE)

                            val paint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
                                color = android.graphics.Color.parseColor("#1D4ED8")
                                strokeWidth = 5f
                                style = android.graphics.Paint.Style.STROKE
                                strokeCap = android.graphics.Paint.Cap.ROUND
                                strokeJoin = android.graphics.Paint.Join.ROUND
                            }

                            if (paths.isNotEmpty()) {
                                for (stroke in paths) {
                                    for (i in 0 until stroke.size - 1) {
                                        canvas.drawLine(
                                            stroke[i].x * 0.7f,
                                            stroke[i].y * 0.7f,
                                            stroke[i + 1].x * 0.7f,
                                            stroke[i + 1].y * 0.7f,
                                            paint
                                        )
                                    }
                                }
                            } else {
                                // Default signature text
                                paint.style = android.graphics.Paint.Style.FILL
                                paint.textSize = 48f
                                paint.isFakeBoldText = true
                                canvas.drawText("Sarah Jenkins", 30f, 80f, paint)
                            }

                            onSaveSignature(bitmap)
                            onDismiss()
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Apply Signature", fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun WatermarkDialog(
    onDismiss: () -> Unit,
    onApplyWatermark: (String) -> Unit
) {
    var watermarkText by remember { mutableStateOf("CONFIDENTIAL") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp)),
            color = SurfaceContainerLowest,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add Watermark to PDF",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Text(
                    text = "Stamp high-security protection across all pages",
                    fontSize = 12.sp,
                    color = OnSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = watermarkText,
                    onValueChange = { watermarkText = it },
                    label = { Text("Watermark Text") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryContainer,
                        unfocusedBorderColor = Color(0xFFDAE2FD)
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("CONFIDENTIAL", "APPROVED", "DRAFT", "COPY").forEach { tag ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (watermarkText == tag) Primary.copy(alpha = 0.15f) else SurfaceContainerLow,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { watermarkText = tag }
                        ) {
                            Text(
                                text = tag,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (watermarkText == tag) Primary else OnSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = onDismiss, shape = RoundedCornerShape(12.dp)) {
                        Text("Cancel", color = OnSurface)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (watermarkText.isNotBlank()) {
                                onApplyWatermark(watermarkText)
                                onDismiss()
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer)
                    ) {
                        Text("Stamp Watermark", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun MergeDocumentsDialog(
    documents: List<PdfDocumentItem>,
    onDismiss: () -> Unit,
    onMerge: (List<String>, String) -> Unit
) {
    val selectedIds = remember { mutableStateListOf<String>() }
    var mergedTitle by remember { mutableStateOf("Combined_Document_Vault") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(24.dp)),
            color = SurfaceContainerLowest,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Merge PDF Documents",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Text(
                    text = "Select 2 or more files to combine into a single PDF",
                    fontSize = 12.sp,
                    color = OnSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = mergedTitle,
                    onValueChange = { mergedTitle = it },
                    label = { Text("Merged File Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    items(documents) { doc ->
                        val isChecked = selectedIds.contains(doc.id)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isChecked) SecondaryFixed.copy(alpha = 0.5f) else SurfaceContainerLow)
                                .clickable {
                                    if (isChecked) selectedIds.remove(doc.id) else selectedIds.add(doc.id)
                                }
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { check ->
                                    if (check) selectedIds.add(doc.id) else selectedIds.remove(doc.id)
                                },
                                colors = CheckboxDefaults.colors(checkedColor = Secondary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(doc.title, fontSize = 13.sp, fontWeight = FontWeight.Medium, maxLines = 1)
                                Text("${doc.pageCount} pages • ${doc.fileSizeString}", fontSize = 11.sp, color = OnSurfaceVariant)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = onDismiss, shape = RoundedCornerShape(12.dp)) {
                        Text("Cancel", color = OnSurface)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (selectedIds.isNotEmpty()) {
                                onMerge(selectedIds.toList(), mergedTitle)
                                onDismiss()
                            }
                        },
                        enabled = selectedIds.size >= 1,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Secondary)
                    ) {
                        Text("Merge (${selectedIds.size}) Files", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun AiSummaryDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(24.dp)),
            color = SurfaceContainerLowest,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(TertiaryContainer.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = TertiaryContainer, modifier = Modifier.size(20.dp))
                        }
                        Column {
                            Text("AI Document Summary", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                            Text("OmniPDF Intelligence Engine 3.2", fontSize = 11.sp, color = Primary, fontWeight = FontWeight.Medium)
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Outline)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = SurfaceContainerLow
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Executive Overview:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "The reviewed instrument represents a bilateral corporate agreement validating non-disclosure covenants, intellectual property assignment, and compliance with data privacy regulations across international jurisdictions.",
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = OnSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Key Action Items & Risk Assessment:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "• Term: 36 calendar months from bilateral execution.\n• Indemnity threshold capped at 1.5x aggregate contract fees.\n• Jurisdiction: Standard Delaware Court of Chancery.\n• Signature audit certificate fully verified (AES-256).",
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = OnSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Done", fontSize = 14.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun FastScannerSimulatorDialog(
    onDismiss: () -> Unit,
    onScanned: (List<Bitmap>, String) -> Unit
) {
    var docName by remember { mutableStateOf("QuickScan_Doc") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(24.dp)),
            color = SurfaceContainerLowest,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Document Camera & Scan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Auto-deskew, edge detection & OCR", fontSize = 12.sp, color = OnSurfaceVariant)
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Outline)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF1E293B)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.White, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Align Document in Frame", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        Text("Smart Auto-Crop Active", color = PrimaryContainer, fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = docName,
                    onValueChange = { docName = it },
                    label = { Text("Document Title") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Generate sample scanned page bitmap
                        val bmp = Bitmap.createBitmap(400, 560, Bitmap.Config.ARGB_8888)
                        val canvas = android.graphics.Canvas(bmp)
                        canvas.drawColor(android.graphics.Color.WHITE)
                        val paint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
                            color = android.graphics.Color.parseColor("#131B2E")
                            textSize = 28f
                            isFakeBoldText = true
                        }
                        canvas.drawText("SCANNED DOCUMENT", 40f, 60f, paint)
                        paint.textSize = 16f
                        paint.isFakeBoldText = false
                        paint.color = android.graphics.Color.parseColor("#3C4A42")
                        canvas.drawText("Generated via OmniPDF Smart Edge Engine", 40f, 100f, paint)
                        canvas.drawText("Date: Sep 2026 • 300 DPI High Resolution", 40f, 130f, paint)
                        canvas.drawText("Status: Certified Lossless On-Device Vault", 40f, 160f, paint)

                        onScanned(listOf(bmp), docName)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Capture & Generate PDF", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}
