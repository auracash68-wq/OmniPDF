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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AutoDelete
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EnhancedEncryption
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.Error
import com.example.ui.theme.OnError
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
fun SettingsScreen(
    viewModel: PdfViewModel,
    modifier: Modifier = Modifier
) {
    val autoOcr by viewModel.autoOcrEnabled.collectAsState()
    val cloudSync by viewModel.cloudSyncEnabled.collectAsState()
    val appLock by viewModel.appLockEnabled.collectAsState()
    val isCacheClearing by viewModel.isCacheClearing.collectAsState()
    val cacheCleared by viewModel.cacheCleared.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceContainerLowest),
        contentPadding = PaddingValues(top = 10.dp, bottom = 32.dp),
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
                                text = "Settings & Preferences",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "Customize your scanning pipeline and security",
                                fontSize = 12.sp,
                                color = OnSurfaceVariant
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(SecondaryFixed),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                tint = Secondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // 2. Profile Card (Sarah Jenkins)
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = SurfaceContainerLow,
                    shadowElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            // Avatar with verified badge
                            Box(contentAlignment = Alignment.BottomEnd) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, Color(0xFFDAE2FD), CircleShape)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_avatar_sarah),
                                        contentDescription = "Sarah Jenkins",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(PrimaryContainer)
                                        .border(2.dp, SurfaceContainerLowest, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(10.dp)
                                    )
                                }
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Sarah Jenkins",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnSurface
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = TertiaryFixed
                                    ) {
                                        Text(
                                            text = "OmniPDF PRO",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = OnTertiaryFixedVariant,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = "Active Subscription • Renews Nov 2025",
                                    fontSize = 11.sp,
                                    color = OnSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFFDAE2FD))
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.Shield, contentDescription = null, tint = Primary, modifier = Modifier.size(16.dp))
                                Text("Enterprise License", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
                            }
                            Button(
                                onClick = { viewModel.showToast("Opening Plan Management...") },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("Manage Plan", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }

        // 3. SECTION: Document & Scanner Defaults
        item {
            SettingsSectionHeader(title = "DOCUMENT & SCANNER DEFAULTS")
        }

        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = SurfaceContainerLow,
                    shadowElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        // 1. Export Quality
                        SettingsNavRow(
                            icon = Icons.Default.HighQuality,
                            title = "Default Export Quality",
                            subtitle = "Recommended for sharp prints",
                            pillValue = "Ultra High (300 DPI)",
                            pillColor = SecondaryFixed,
                            pillTextColor = Secondary,
                            onClick = { viewModel.showToast("Quality set to Ultra High (300 DPI)") }
                        )

                        SettingsDivider()

                        // 2. Auto-OCR Text Recognition
                        SettingsToggleRow(
                            icon = Icons.Default.DocumentScanner,
                            title = "Auto-OCR Text Recognition",
                            subtitle = "Instant text extraction & searchability",
                            checked = autoOcr,
                            onCheckedChange = { viewModel.setAutoOcr(it) }
                        )

                        SettingsDivider()

                        // 3. Save Original Scans to Cloud
                        SettingsToggleRow(
                            icon = Icons.Default.CloudUpload,
                            title = "Save Original Scans to Cloud",
                            subtitle = "Store lossless RAW snapshots",
                            checked = cloudSync,
                            onCheckedChange = { viewModel.setCloudSync(it) }
                        )

                        SettingsDivider()

                        // 4. Default Signature
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.openSignDialog() }
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(TertiaryFixed),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Draw, contentDescription = null, tint = Tertiary, modifier = Modifier.size(18.dp))
                                }
                                Column {
                                    Text("Default Signature", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
                                    Text("Auto-applied in 1-Tap Sign", fontSize = 11.sp, color = OnSurfaceVariant)
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = SurfaceContainerLowest,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDAE2FD))
                                ) {
                                    Text(
                                        text = "Sarah J.",
                                        fontSize = 12.sp,
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.Bold,
                                        color = Secondary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                                Icon(Icons.Default.Edit, contentDescription = null, tint = Outline, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }

        // 4. SECTION: Security & Encryption
        item {
            SettingsSectionHeader(title = "SECURITY & ENCRYPTION")
        }

        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = SurfaceContainerLow,
                    shadowElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        // 1. App Lock
                        SettingsToggleRow(
                            icon = Icons.Default.Fingerprint,
                            title = "App Lock (Face ID / Passcode)",
                            subtitle = "Require authentication on app launch",
                            checked = appLock,
                            onCheckedChange = { viewModel.setAppLock(it) }
                        )

                        SettingsDivider()

                        // 2. Auto-Wipe Sensitive Cache
                        SettingsNavRow(
                            icon = Icons.Default.AutoDelete,
                            title = "Auto-Wipe Sensitive Cache",
                            subtitle = "Remove temporary scanned buffers",
                            pillValue = "After 7 Days",
                            pillColor = SurfaceContainerLowest,
                            pillTextColor = OnSurface,
                            trailingIcon = Icons.Default.UnfoldMore,
                            onClick = { viewModel.showToast("Cache retention set to 7 days") }
                        )

                        SettingsDivider()

                        // 3. Default PDF Encryption
                        SettingsNavRow(
                            icon = Icons.Default.EnhancedEncryption,
                            title = "Default PDF Encryption",
                            subtitle = "Standard for newly created documents",
                            pillValue = "AES-256 Bit",
                            pillColor = SecondaryFixed,
                            pillTextColor = Secondary,
                            onClick = { viewModel.showToast("Standard encryption: 256-bit AES") }
                        )
                    }
                }
            }
        }

        // 5. SECTION: Cloud & Storage
        item {
            SettingsSectionHeader(title = "CLOUD & STORAGE")
        }

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
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Synced Providers", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OnSurface)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            CloudProviderChip(name = "GDrive", isSynced = true, modifier = Modifier.weight(1f))
                            CloudProviderChip(name = "Dropbox", isSynced = true, modifier = Modifier.weight(1f))
                            CloudProviderChip(name = "iCloud", isSynced = false, modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Storage Used", fontSize = 12.sp, color = OnSurfaceVariant)
                            Text("14.2 GB / 50 GB", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                        }

                        // Progress Meter
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(SurfaceContainerLowest)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.284f)
                                    .height(8.dp)
                                    .background(PrimaryContainer)
                            )
                        }

                        Text("35.8 GB remaining in your OmniCloud vault", fontSize = 11.sp, color = Secondary)

                        Spacer(modifier = Modifier.height(4.dp))

                        // Clear Local Cache Button with interactive state
                        OutlinedButton(
                            onClick = { viewModel.clearCache() },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            if (isCacheClearing) {
                                CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = PrimaryContainer)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Clearing Cache...", fontSize = 12.sp)
                            } else if (cacheCleared) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Primary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Cache Cleared (0 MB)", fontSize = 12.sp, color = Primary, fontWeight = FontWeight.SemiBold)
                            } else {
                                Icon(Icons.Default.DeleteSweep, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Clear Local Cache (142 MB)", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }

        // 6. SECTION: App & Support
        item {
            SettingsSectionHeader(title = "APP & SUPPORT")
        }

        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = SurfaceContainerLow,
                    shadowElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        SettingsSimpleRow(
                            icon = Icons.AutoMirrored.Filled.Help,
                            title = "Help Center & Tutorials",
                            onClick = { viewModel.showToast("Opening Help Center...") }
                        )
                        SettingsDivider()
                        SettingsSimpleRow(
                            icon = Icons.Default.SupportAgent,
                            title = "Contact Priority Support",
                            onClick = { viewModel.showToast("Connecting to VIP Support Agent...") }
                        )
                        SettingsDivider()
                        SettingsSimpleRow(
                            icon = Icons.Default.VerifiedUser,
                            title = "Privacy Policy & Compliance",
                            onClick = { viewModel.showToast("Opening Privacy Policy...") }
                        )
                        SettingsDivider()
                        SettingsSimpleRow(
                            icon = Icons.Default.RestartAlt,
                            title = "Replay Intro Tour / Onboarding",
                            onClick = { viewModel.restartOnboarding() }
                        )
                    }
                }
            }
        }

        // 7. Log Out CTA
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.showToast("Logged out of Sarah Jenkins profile") },
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFFFDAD6)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = null,
                            tint = Error,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Log Out",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Error
                        )
                    }
                }
            }
        }

        // 8. Footer
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "OmniPDF Engine v4.8.2 (Build 942)",
                    fontSize = 11.sp,
                    color = Outline,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "End-to-End Encrypted Document Utility",
                    fontSize = 11.sp,
                    color = Outline
                )
            }
        }
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = Outline,
        letterSpacing = 0.5.sp,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
    )
}

@Composable
private fun SettingsDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 58.dp, end = 16.dp)
            .height(1.dp)
            .background(Color(0xFFDAE2FD).copy(alpha = 0.6f))
    )
}

@Composable
private fun SettingsNavRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    pillValue: String,
    pillColor: Color,
    pillTextColor: Color,
    trailingIcon: androidx.compose.ui.graphics.vector.ImageVector = Icons.Default.ChevronRight,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainerLowest),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = Secondary, modifier = Modifier.size(18.dp))
            }
            Column {
                Text(title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
                Text(subtitle, fontSize = 11.sp, color = OnSurfaceVariant)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = pillColor
            ) {
                Text(
                    text = pillValue,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = pillTextColor,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Icon(trailingIcon, contentDescription = null, tint = Outline, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
private fun SettingsToggleRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainerLowest),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = Primary, modifier = Modifier.size(18.dp))
            }
            Column {
                Text(title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
                Text(subtitle, fontSize = 11.sp, color = OnSurfaceVariant)
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PrimaryContainer
            )
        )
    }
}

@Composable
private fun SettingsSimpleRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainerLowest),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = OnSurfaceVariant, modifier = Modifier.size(18.dp))
            }
            Text(title, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = OnSurface)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Outline, modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun CloudProviderChip(
    name: String,
    isSynced: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = SurfaceContainerLowest,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDAE2FD))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(if (isSynced) PrimaryContainer else Outline)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = name,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = OnSurface
            )
        }
    }
}
