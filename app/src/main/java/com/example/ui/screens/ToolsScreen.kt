package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BrandingWatermark
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CallMerge
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.RotateRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Transform
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Error
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnSecondary
import com.example.ui.theme.OnSecondaryContainer
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.OnTertiaryFixedVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.Tertiary
import com.example.ui.theme.TertiaryContainer
import com.example.ui.theme.TertiaryFixed
import com.example.viewmodel.PdfViewModel

@Composable
fun ToolsScreen(
    viewModel: PdfViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val searchQuery by viewModel.toolsSearchQuery.collectAsState()
    val selectedCategory by viewModel.toolsCategory.collectAsState()

    val categories = listOf(
        "all" to "All Tools",
        "convert" to "Convert",
        "organize" to "Organize",
        "security" to "Security",
        "ai" to "AI Smart"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceContainerLowest),
        contentPadding = PaddingValues(top = 10.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Header Block
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = SurfaceContainerLow,
                    shadowElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "PDF Tool Suite",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "24 powerful tools for editing, converting & securing PDFs",
                                fontSize = 12.sp,
                                color = OnSurfaceVariant
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(TertiaryFixed),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Build,
                                contentDescription = null,
                                tint = Tertiary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        }

        // 2. Search Tools Bar
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = SurfaceContainerLow,
                    shadowElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Secondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "Search tools (e.g. compress, sign, split)...",
                                    fontSize = 13.sp,
                                    color = Outline
                                )
                            }
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { viewModel.setToolsSearchQuery(it) },
                                singleLine = true,
                                textStyle = TextStyle(
                                    fontSize = 13.sp,
                                    color = OnSurface,
                                    fontWeight = FontWeight.Normal
                                ),
                                cursorBrush = SolidColor(PrimaryContainer),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        if (searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { viewModel.setToolsSearchQuery("") },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = OnSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Category Filter Chips
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { (key, label) ->
                    val isSelected = selectedCategory == key
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = if (isSelected) PrimaryContainer else SurfaceContainer,
                        modifier = Modifier
                            .clip(RoundedCornerShape(18.dp))
                            .clickable { viewModel.setToolsCategory(key) }
                    ) {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (isSelected) Color.White else OnSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }

        // SECTION 1: AI & Smart Assistant
        if (selectedCategory == "all" || selectedCategory == "ai") {
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(TertiaryContainer)
                        )
                        Text(
                            text = "AI & Smart Assistant",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = TertiaryFixed
                        ) {
                            Text(
                                text = "NEW",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnTertiaryFixedVariant,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    // Hero Bento Card (AI Document Summarizer)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { viewModel.openAiSummaryDialog() },
                        shape = RoundedCornerShape(20.dp),
                        color = Color.Transparent
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF131B2E),
                                            Color(0xFF264191)
                                        )
                                    )
                                )
                                .padding(18.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color.White.copy(alpha = 0.15f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AutoAwesome,
                                            contentDescription = null,
                                            tint = Color(0xFFFFB784),
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = Color.White.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = "Ultra Fast",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }

                                Column {
                                    Text(
                                        text = "AI Document Summarizer",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Instantly extract executive summaries, key clauses, and risk metrics from complex 100+ page PDFs.",
                                        fontSize = 12.sp,
                                        lineHeight = 17.sp,
                                        color = Color(0xFFDCE1FF)
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        listOf("PDF", "DOC", "TXT").forEach { ext ->
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = Color.White.copy(alpha = 0.15f)
                                            ) {
                                                Text(
                                                    text = ext,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = "Try AI Summary",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF6FFBBE)
                                        )
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                            contentDescription = null,
                                            tint = Color(0xFF6FFBBE),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // SECTION 2: Convert & Create
        if (selectedCategory == "all" || selectedCategory == "convert") {
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Transform, contentDescription = null, tint = Primary, modifier = Modifier.size(18.dp))
                            Text("Convert & Create", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                        }
                        Text("4 Tools", fontSize = 12.sp, color = OnSurfaceVariant)
                    }

                    // 2x2 Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ToolGridItem(
                            title = "Image to PDF",
                            desc = "JPG, PNG, HEIC to crisp doc",
                            badge = "Fast",
                            icon = Icons.Default.CameraAlt,
                            containerColor = TertiaryContainer,
                            iconColor = Color.White,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.openScannerDialog() }
                        )
                        ToolGridItem(
                            title = "PDF to Office",
                            desc = "Editable Word & Excel sheets",
                            badge = "99.8%",
                            icon = Icons.Default.Description,
                            containerColor = Secondary,
                            iconColor = OnSecondary,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.showToast("Opening PDF to Office Converter...") }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ToolGridItem(
                            title = "Scanner to PDF",
                            desc = "Real-time edge auto-detection",
                            badge = "Auto Crop",
                            icon = Icons.Default.DocumentScanner,
                            containerColor = TertiaryContainer,
                            iconColor = Color.White,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.openScannerDialog() }
                        )
                        ToolGridItem(
                            title = "Web to PDF",
                            desc = "Save URLs as clean offline PDFs",
                            icon = Icons.Default.Language,
                            containerColor = SecondaryFixed,
                            iconColor = Secondary,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.showToast("Opening Web URL to PDF...") }
                        )
                    }
                }
            }
        }

        // SECTION 3: Organize & Edit
        if (selectedCategory == "all" || selectedCategory == "organize") {
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Layers, contentDescription = null, tint = Secondary, modifier = Modifier.size(18.dp))
                            Text("Organize & Edit", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                        }
                        Text("4 Tools", fontSize = 12.sp, color = OnSurfaceVariant)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ToolGridItem(
                            title = "Merge PDFs",
                            desc = "Combine multiple files seamlessly",
                            badge = "Popular",
                            icon = Icons.Default.CallMerge,
                            containerColor = Secondary,
                            iconColor = OnSecondary,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.openMergeDialog() }
                        )
                        ToolGridItem(
                            title = "Split PDF",
                            desc = "Extract specific pages or ranges",
                            icon = Icons.Default.ContentCut,
                            containerColor = Color(0xFFFFDAD6),
                            iconColor = Error,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.showToast("Opening PDF Page Splitter...") }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ToolGridItem(
                            title = "Reorder & Rotate",
                            desc = "Organize pages via drag & drop",
                            icon = Icons.Default.RotateRight,
                            containerColor = SecondaryFixed,
                            iconColor = Secondary,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.showToast("Opening Page Reordering...") }
                        )
                        ToolGridItem(
                            title = "Compress PDF",
                            desc = "Drastically reduce file weight",
                            badge = "-80%",
                            icon = Icons.Default.Compress,
                            containerColor = PrimaryContainer,
                            iconColor = OnPrimary,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.openCompressDialog() }
                        )
                    }
                }
            }
        }

        // SECTION 4: Sign & Secure
        if (selectedCategory == "all" || selectedCategory == "security") {
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Shield, contentDescription = null, tint = PrimaryContainer, modifier = Modifier.size(18.dp))
                            Text("Sign & Secure", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                        }
                        Text("4 Tools", fontSize = 12.sp, color = OnSurfaceVariant)
                    }

                    // Featured Fill & Sign Card
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .clickable { viewModel.openSignDialog() },
                        shape = RoundedCornerShape(18.dp),
                        color = SurfaceContainerLow,
                        shadowElevation = 1.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(PrimaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Draw, contentDescription = null, tint = OnPrimary, modifier = Modifier.size(24.dp))
                                }
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text("Fill & Sign", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = Primary.copy(alpha = 0.1f)
                                        ) {
                                            Text(
                                                text = "Legal",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Primary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Draw finger signature, type initials, or stamp certificates",
                                        fontSize = 11.sp,
                                        color = OnSurfaceVariant
                                    )
                                }
                            }

                            Button(
                                onClick = { viewModel.openSignDialog() },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Text("Sign Document", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }

                    // 3-Column Subgrid: Protect, Unlock, Watermark
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SecurityMiniCard(
                            icon = Icons.Default.Lock,
                            title = "Protect",
                            subtext = "256-bit AES",
                            containerColor = SecondaryFixed,
                            iconColor = Secondary,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.openEncryptDialog() }
                        )
                        SecurityMiniCard(
                            icon = Icons.Default.LockOpen,
                            title = "Unlock",
                            subtext = "Decrypt files",
                            containerColor = TertiaryFixed,
                            iconColor = Tertiary,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.showToast("Opening Document Unlocker...") }
                        )
                        SecurityMiniCard(
                            icon = Icons.Default.BrandingWatermark,
                            title = "Watermark",
                            subtext = "Stamp logo",
                            containerColor = SurfaceContainerHigh,
                            iconColor = OnSurface,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.openWatermarkDialog() }
                        )
                    }
                }
            }
        }

        // Bottom Guarantee Banner
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = SurfaceContainerLow
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Local & Secure Processing", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
                            Text("Files processed on-device for total privacy.", fontSize = 11.sp, color = OnSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ToolGridItem(
    title: String,
    desc: String,
    badge: String? = null,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    containerColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = SurfaceContainerLow,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(containerColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = title, tint = iconColor, modifier = Modifier.size(20.dp))
                }
                if (badge != null) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SurfaceContainerLowest
                    ) {
                        Text(
                            text = badge,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                } else {
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Outline, modifier = Modifier.size(16.dp))
                }
            }
            Column {
                Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = desc, fontSize = 11.sp, color = OnSurfaceVariant, maxLines = 2)
            }
        }
    }
}

@Composable
private fun SecurityMiniCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtext: String,
    containerColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        color = SurfaceContainerLow,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(containerColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = title, tint = iconColor, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
            Text(text = subtext, fontSize = 10.sp, color = OnSurfaceVariant)
        }
    }
}
