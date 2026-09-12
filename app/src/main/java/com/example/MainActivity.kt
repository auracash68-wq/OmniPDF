package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AppBottomNavigationBar
import com.example.ui.components.AppTopBar
import com.example.ui.components.ToastNotificationBanner
import com.example.ui.dialogs.AiSummaryDialog
import com.example.ui.dialogs.DocumentPreviewDialog
import com.example.ui.dialogs.FastScannerSimulatorDialog
import com.example.ui.dialogs.MergeDocumentsDialog
import com.example.ui.dialogs.SignaturePadDialog
import com.example.ui.dialogs.WatermarkDialog
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.ToolsScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SurfaceContainerLowest
import com.example.viewmodel.BottomNavTab
import com.example.viewmodel.PdfViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                OmniPdfApp()
            }
        }
    }
}

@Composable
fun OmniPdfApp(viewModel: PdfViewModel = viewModel()) {
    val context = LocalContext.current
    val isOnboardingComplete by viewModel.isOnboardingComplete.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()
    val toastMsg by viewModel.toastMessage.collectAsState()

    // Dialog states
    val previewDoc by viewModel.previewItem.collectAsState()
    val showSignDialog by viewModel.showSignDialog.collectAsState()
    val showWatermarkDialog by viewModel.showWatermarkDialog.collectAsState()
    val showMergeDialog by viewModel.showMergeDialog.collectAsState()
    val showAiDialog by viewModel.showAiSummaryDialog.collectAsState()
    val showScannerDialog by viewModel.showScannerDialog.collectAsState()
    val documents by viewModel.documents.collectAsState()

    if (!isOnboardingComplete) {
        WelcomeScreen(
            onGetStarted = { viewModel.completeOnboarding() },
            onSignIn = {
                viewModel.showToast("Signed in as Sarah Jenkins")
                viewModel.completeOnboarding()
            }
        )
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = SurfaceContainerLowest,
            topBar = {
                AppTopBar(
                    currentTab = currentTab,
                    onNotificationClick = {
                        viewModel.showToast("No new notifications • All vaults synced")
                    },
                    onProfileClick = {
                        viewModel.selectTab(BottomNavTab.SETTINGS)
                    }
                )
            },
            bottomBar = {
                AppBottomNavigationBar(
                    currentTab = currentTab,
                    onTabSelected = { viewModel.selectTab(it) }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Crossfade(
                    targetState = currentTab,
                    animationSpec = tween(220),
                    label = "tab_crossfade"
                ) { tab ->
                    when (tab) {
                        BottomNavTab.HOME -> HomeScreen(viewModel = viewModel)
                        BottomNavTab.TOOLS -> ToolsScreen(viewModel = viewModel)
                        BottomNavTab.HISTORY -> HistoryScreen(viewModel = viewModel)
                        BottomNavTab.SETTINGS -> SettingsScreen(viewModel = viewModel)
                    }
                }

                // Floating Toast Banner
                ToastNotificationBanner(
                    message = toastMsg,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                )
            }
        }
    }

    // Modal Operations Dialogs
    previewDoc?.let { doc ->
        DocumentPreviewDialog(
            item = doc,
            onDismiss = { viewModel.closePreview() },
            onShare = { viewModel.shareDocument(context, doc) },
            onSign = { viewModel.openSignDialog() },
            onWatermark = { viewModel.openWatermarkDialog() },
            onCompress = { viewModel.compressDocument(context, doc.id) }
        )
    }

    if (showSignDialog) {
        SignaturePadDialog(
            onDismiss = { viewModel.closeSignDialog() },
            onSaveSignature = { bmp ->
                val targetId = previewDoc?.id ?: documents.firstOrNull()?.id ?: ""
                viewModel.signDocument(context, targetId, bmp)
            }
        )
    }

    if (showWatermarkDialog) {
        WatermarkDialog(
            onDismiss = { viewModel.closeWatermarkDialog() },
            onApplyWatermark = { text ->
                val targetId = previewDoc?.id ?: documents.firstOrNull()?.id ?: ""
                viewModel.watermarkDocument(context, targetId, text)
            }
        )
    }

    if (showMergeDialog) {
        MergeDocumentsDialog(
            documents = documents,
            onDismiss = { viewModel.closeMergeDialog() },
            onMerge = { selectedIds, title ->
                viewModel.mergeDocuments(context, selectedIds, title)
            }
        )
    }

    if (showAiDialog) {
        AiSummaryDialog(
            onDismiss = { viewModel.closeAiSummaryDialog() }
        )
    }

    if (showScannerDialog) {
        FastScannerSimulatorDialog(
            onDismiss = { viewModel.closeScannerDialog() },
            onScanned = { bitmaps, title ->
                viewModel.scanDocumentAndSave(context, bitmaps, title)
            }
        )
    }
}
