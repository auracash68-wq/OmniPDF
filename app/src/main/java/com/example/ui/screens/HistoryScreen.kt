package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.DocumentTagType
import com.example.model.PdfDocumentItem
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.OnTertiaryFixedVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
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
fun HistoryScreen(
    viewModel: PdfViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val documents by viewModel.documents.collectAsState()
    val searchQuery by viewModel.historySearchQuery.collectAsState()
    val activeTab by viewModel.historyFilterTab.collectAsState()
    val isBatchMode by viewModel.isBatchSelectMode.collectAsState()
    val selectedDocIds by viewModel.selectedDocIds.collectAsState()

    val filterTabs = listOf("All", "Scanned", "Converted", "Signed", "Shared")

    val filteredDocs = documents.filter { doc ->
        val matchesQuery = searchQuery.isEmpty() || doc.title.contains(searchQuery, ignoreCase = true)
        val matchesTab = when (activeTab) {
            "All" -> true
            "Scanned" -> doc.tag.contains("OCR", ignoreCase = true) || doc.tag.contains("Scan", ignoreCase = true)
            "Converted" -> doc.tag.contains("PPTX", ignoreCase = true) || doc.tag.contains("Merged", ignoreCase = true)
            "Signed" -> doc.tag.contains("Signed", ignoreCase = true)
            "Shared" -> doc.isStarred
            else -> true
        }
        matchesQuery && matchesTab
    }

    // Grouping: Today, Yesterday, Last Week
    val groupedDocs = filteredDocs.groupBy { it.dateGroup }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceContainerLowest),
        contentPadding = PaddingValues(top = 10.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Header
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
                        Column {
                            Text(
                                text = "File History & Archives",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "48 Documents • Vault Encrypted",
                                fontSize = 12.sp,
                                color = OnSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isBatchMode) PrimaryContainer else SecondaryFixed,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { viewModel.toggleBatchSelectMode() }
                        ) {
                            Text(
                                text = if (isBatchMode) "Done" else "Select",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isBatchMode) Color.White else Secondary,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                            )
                        }
                    }
                }
            }
        }

        // 2. Search History Bar
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
                                    text = "Search history, tags, dates...",
                                    fontSize = 13.sp,
                                    color = Outline
                                )
                            }
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { viewModel.setHistorySearchQuery(it) },
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
                        IconButton(
                            onClick = { viewModel.showToast("Filter criteria applied") },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = "Filter",
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(19.dp)
                            )
                        }
                    }
                }
            }
        }

        // 3. Filter Tabs (All, Scanned, Converted, Signed, Shared)
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filterTabs) { tab ->
                    val isSelected = activeTab == tab
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) PrimaryContainer else SurfaceContainer,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { viewModel.setHistoryFilterTab(tab) }
                    ) {
                        Text(
                            text = tab,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (isSelected) Color.White else OnSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // 4. Batch Select Mode Bar
        if (isBatchMode) {
            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = SecondaryFixed.copy(alpha = 0.5f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val allSelected = selectedDocIds.size == filteredDocs.size && filteredDocs.isNotEmpty()
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { viewModel.toggleSelectAll(!allSelected) }
                            ) {
                                Checkbox(
                                    checked = allSelected,
                                    onCheckedChange = { viewModel.toggleSelectAll(it) },
                                    colors = CheckboxDefaults.colors(checkedColor = Secondary)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Select All (${filteredDocs.size})",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = OnSurface
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Button(
                                    onClick = { viewModel.showToast("Batch exporting ${selectedDocIds.size} files...") },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Secondary),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text("Batch Export", fontSize = 11.sp)
                                }
                                Button(
                                    onClick = { viewModel.showToast("Batch processing ${selectedDocIds.size} files...") },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = TertiaryContainer),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(13.dp))
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text("Process", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // 5. Grouped Document Cards (Today, Yesterday, Last Week)
        groupedDocs.forEach { (dateHeader, docsInGroup) ->
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${dateHeader.uppercase()} (${docsInGroup.size} FILES)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Outline,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            items(docsInGroup) { doc ->
                HistoryDocumentCard(
                    item = doc,
                    isBatchMode = isBatchMode,
                    isSelected = selectedDocIds.contains(doc.id),
                    onToggleSelect = { viewModel.toggleDocSelection(doc.id) },
                    onCardClick = { viewModel.openPreview(doc) },
                    onShare = { viewModel.shareDocument(context, doc) },
                    onSign = { viewModel.openSignDialog() },
                    onUnlock = { viewModel.openEncryptDialog() }
                )
            }
        }

        // 6. Quick Scan to History CTA Button
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Button(
                    onClick = { viewModel.openScannerDialog() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .shadow(3.dp, RoundedCornerShape(14.dp)),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Quick Scan to History", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
        }

        // 7. Storage & Sync Status Card
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = SurfaceContainerLow,
                    shadowElevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
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
                                Icon(Icons.Default.CloudDone, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
                                Text("Storage & Sync Status", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                            }
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Primary.copy(alpha = 0.1f)
                            ) {
                                Text(
                                    text = "100% Synced",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        // Dual Color Progress Meter
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(SurfaceContainerLowest)
                        ) {
                            Row(modifier = Modifier.fillMaxSize()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.28f)
                                        .fillMaxSize()
                                        .background(PrimaryContainer)
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.18f)
                                        .fillMaxSize()
                                        .background(Secondary)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Local Cache: 142 MB", fontSize = 11.sp, color = OnSurfaceVariant)
                            Text("Cloud Vault: 4.8 GB Free", fontSize = 11.sp, color = Secondary, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryDocumentCard(
    item: PdfDocumentItem,
    isBatchMode: Boolean,
    isSelected: Boolean,
    onToggleSelect: () -> Unit,
    onCardClick: () -> Unit,
    onShare: () -> Unit,
    onSign: () -> Unit,
    onUnlock: () -> Unit
) {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = if (isBatchMode) onToggleSelect else onCardClick),
            shape = RoundedCornerShape(16.dp),
            color = SurfaceContainerLowest,
            shadowElevation = 1.5.dp
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        if (isBatchMode) {
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = { onToggleSelect() },
                                colors = CheckboxDefaults.colors(checkedColor = Secondary)
                            )
                        }

                        // Thumbnail with badge
                        Box(
                            modifier = Modifier
                                .size(width = 46.dp, height = 54.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerHigh),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Icon(
                                imageVector = if (item.isEncrypted) Icons.Default.Lock else Icons.Default.Description,
                                contentDescription = null,
                                tint = Secondary,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(24.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(14.dp)
                                    .background(if (item.tag.contains("OCR")) TertiaryContainer else Secondary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "PDF",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val tagBg = when (item.tagType) {
                                    DocumentTagType.PRIMARY -> Primary.copy(alpha = 0.1f)
                                    DocumentTagType.SECONDARY -> SecondaryFixed
                                    DocumentTagType.TERTIARY -> TertiaryFixed
                                }
                                val tagColor = when (item.tagType) {
                                    DocumentTagType.PRIMARY -> Primary
                                    DocumentTagType.SECONDARY -> Secondary
                                    DocumentTagType.TERTIARY -> Tertiary
                                }
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = tagBg
                                ) {
                                    Text(
                                        text = item.tag,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = tagColor,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Text(
                                    text = "${item.fileSizeString} • ${item.timeString}",
                                    fontSize = 11.sp,
                                    color = OnSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // Contextual Bottom Action Row inside card
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(SurfaceContainerLow)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (item.isEncrypted) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.Key, contentDescription = null, tint = Secondary, modifier = Modifier.size(13.dp))
                            Text("Passcode Protected", fontSize = 11.sp, color = Secondary, fontWeight = FontWeight.Medium)
                        }
                        Text(
                            text = "Unlock Preview ->",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Secondary,
                            modifier = Modifier.clickable { onUnlock() }
                        )
                    } else if (item.tag.contains("OCR")) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Primary, modifier = Modifier.size(13.dp))
                            Text("Searchable text extracted", fontSize = 11.sp, color = Primary, fontWeight = FontWeight.Medium)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("Quick Share", fontSize = 11.sp, color = Secondary, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { onShare() })
                            Text("Export Text", fontSize = 11.sp, color = Secondary, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { onShare() })
                        }
                    } else {
                        Text(
                            text = item.description ?: "",
                            fontSize = 11.sp,
                            color = OnSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("Share", fontSize = 11.sp, color = Secondary, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { onShare() })
                            Text("Details", fontSize = 11.sp, color = Primary, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { onCardClick() })
                        }
                    }
                }
            }
        }
    }
}
