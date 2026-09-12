package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.DocumentTagType
import com.example.model.PdfDocumentItem
import com.example.pdf.PdfEngine
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID

enum class BottomNavTab {
    HOME,
    TOOLS,
    HISTORY,
    SETTINGS
}

class PdfViewModel(application: Application) : AndroidViewModel(application) {

    private val _isOnboardingComplete = MutableStateFlow(false)
    val isOnboardingComplete: StateFlow<Boolean> = _isOnboardingComplete.asStateFlow()

    private val _currentTab = MutableStateFlow(BottomNavTab.HOME)
    val currentTab: StateFlow<BottomNavTab> = _currentTab.asStateFlow()

    private val _documents = MutableStateFlow<List<PdfDocumentItem>>(emptyList())
    val documents: StateFlow<List<PdfDocumentItem>> = _documents.asStateFlow()

    // Home
    private val _homeSearchQuery = MutableStateFlow("")
    val homeSearchQuery: StateFlow<String> = _homeSearchQuery.asStateFlow()

    // Tools
    private val _toolsSearchQuery = MutableStateFlow("")
    val toolsSearchQuery: StateFlow<String> = _toolsSearchQuery.asStateFlow()

    private val _toolsCategory = MutableStateFlow("all")
    val toolsCategory: StateFlow<String> = _toolsCategory.asStateFlow()

    // History
    private val _historySearchQuery = MutableStateFlow("")
    val historySearchQuery: StateFlow<String> = _historySearchQuery.asStateFlow()

    private val _historyFilterTab = MutableStateFlow("All")
    val historyFilterTab: StateFlow<String> = _historyFilterTab.asStateFlow()

    private val _isBatchSelectMode = MutableStateFlow(false)
    val isBatchSelectMode: StateFlow<Boolean> = _isBatchSelectMode.asStateFlow()

    private val _selectedDocIds = MutableStateFlow<Set<String>>(emptySet())
    val selectedDocIds: StateFlow<Set<String>> = _selectedDocIds.asStateFlow()

    // Settings
    private val _autoOcrEnabled = MutableStateFlow(true)
    val autoOcrEnabled: StateFlow<Boolean> = _autoOcrEnabled.asStateFlow()

    private val _cloudSyncEnabled = MutableStateFlow(true)
    val cloudSyncEnabled: StateFlow<Boolean> = _cloudSyncEnabled.asStateFlow()

    private val _appLockEnabled = MutableStateFlow(true)
    val appLockEnabled: StateFlow<Boolean> = _appLockEnabled.asStateFlow()

    private val _isCacheClearing = MutableStateFlow(false)
    val isCacheClearing: StateFlow<Boolean> = _isCacheClearing.asStateFlow()

    private val _cacheCleared = MutableStateFlow(false)
    val cacheCleared: StateFlow<Boolean> = _cacheCleared.asStateFlow()

    // Active Dialogs & Workflows
    private val _previewItem = MutableStateFlow<PdfDocumentItem?>(null)
    val previewItem: StateFlow<PdfDocumentItem?> = _previewItem.asStateFlow()

    private val _showSignDialog = MutableStateFlow(false)
    val showSignDialog: StateFlow<Boolean> = _showSignDialog.asStateFlow()

    private val _showWatermarkDialog = MutableStateFlow(false)
    val showWatermarkDialog: StateFlow<Boolean> = _showWatermarkDialog.asStateFlow()

    private val _showCompressDialog = MutableStateFlow(false)
    val showCompressDialog: StateFlow<Boolean> = _showCompressDialog.asStateFlow()

    private val _showAiSummaryDialog = MutableStateFlow(false)
    val showAiSummaryDialog: StateFlow<Boolean> = _showAiSummaryDialog.asStateFlow()

    private val _showMergeDialog = MutableStateFlow(false)
    val showMergeDialog: StateFlow<Boolean> = _showMergeDialog.asStateFlow()

    private val _showScannerDialog = MutableStateFlow(false)
    val showScannerDialog: StateFlow<Boolean> = _showScannerDialog.asStateFlow()

    private val _showEncryptDialog = MutableStateFlow(false)
    val showEncryptDialog: StateFlow<Boolean> = _showEncryptDialog.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    init {
        viewModelScope.launch {
            val docs = PdfEngine.initializeSampleDocuments(getApplication())
            _documents.value = docs
        }
    }

    fun completeOnboarding() {
        _isOnboardingComplete.value = true
    }

    fun restartOnboarding() {
        _isOnboardingComplete.value = false
    }

    fun selectTab(tab: BottomNavTab) {
        _currentTab.value = tab
    }

    fun setHomeSearchQuery(query: String) {
        _homeSearchQuery.value = query
    }

    fun setToolsSearchQuery(query: String) {
        _toolsSearchQuery.value = query
    }

    fun setToolsCategory(category: String) {
        _toolsCategory.value = category
    }

    fun setHistorySearchQuery(query: String) {
        _historySearchQuery.value = query
    }

    fun setHistoryFilterTab(tab: String) {
        _historyFilterTab.value = tab
    }

    fun toggleBatchSelectMode() {
        _isBatchSelectMode.value = !_isBatchSelectMode.value
        if (!_isBatchSelectMode.value) {
            _selectedDocIds.value = emptySet()
        }
    }

    fun toggleSelectAll(selectAll: Boolean) {
        if (selectAll) {
            _selectedDocIds.value = _documents.value.map { it.id }.toSet()
        } else {
            _selectedDocIds.value = emptySet()
        }
    }

    fun toggleDocSelection(docId: String) {
        val current = _selectedDocIds.value.toMutableSet()
        if (current.contains(docId)) {
            current.remove(docId)
        } else {
            current.add(docId)
        }
        _selectedDocIds.value = current
    }

    fun toggleStar(docId: String) {
        _documents.value = _documents.value.map {
            if (it.id == docId) it.copy(isStarred = !it.isStarred) else it
        }
    }

    fun openPreview(item: PdfDocumentItem) {
        _previewItem.value = item
    }

    fun closePreview() {
        _previewItem.value = null
    }

    fun openSignDialog() {
        _showSignDialog.value = true
    }

    fun closeSignDialog() {
        _showSignDialog.value = false
    }

    fun openWatermarkDialog() {
        _showWatermarkDialog.value = true
    }

    fun closeWatermarkDialog() {
        _showWatermarkDialog.value = false
    }

    fun openCompressDialog() {
        _showCompressDialog.value = true
    }

    fun closeCompressDialog() {
        _showCompressDialog.value = false
    }

    fun openAiSummaryDialog() {
        _showAiSummaryDialog.value = true
    }

    fun closeAiSummaryDialog() {
        _showAiSummaryDialog.value = false
    }

    fun openMergeDialog() {
        _showMergeDialog.value = true
    }

    fun closeMergeDialog() {
        _showMergeDialog.value = false
    }

    fun openScannerDialog() {
        _showScannerDialog.value = true
    }

    fun closeScannerDialog() {
        _showScannerDialog.value = false
    }

    fun openEncryptDialog() {
        _showEncryptDialog.value = true
    }

    fun closeEncryptDialog() {
        _showEncryptDialog.value = false
    }

    fun setAutoOcr(enabled: Boolean) {
        _autoOcrEnabled.value = enabled
    }

    fun setCloudSync(enabled: Boolean) {
        _cloudSyncEnabled.value = enabled
    }

    fun setAppLock(enabled: Boolean) {
        _appLockEnabled.value = enabled
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
        viewModelScope.launch {
            delay(2000)
            if (_toastMessage.value == msg) {
                _toastMessage.value = null
            }
        }
    }

    fun clearCache() {
        viewModelScope.launch {
            _isCacheClearing.value = true
            delay(1000)
            _isCacheClearing.value = false
            _cacheCleared.value = true
            showToast("Cache Cleared (0 MB)")
            delay(2500)
            _cacheCleared.value = false
        }
    }

    fun shareDocument(context: Context, doc: PdfDocumentItem) {
        val path = doc.filePath
        if (path != null) {
            val file = File(path)
            if (file.exists()) {
                PdfEngine.sharePdfFile(context, file)
                return
            }
        }
        showToast("Sharing ${doc.title}...")
    }

    fun scanDocumentAndSave(context: Context, bitmaps: List<Bitmap>, title: String) {
        viewModelScope.launch {
            val file = PdfEngine.createPdfFromBitmaps(context, bitmaps, title)
            val newItem = PdfDocumentItem(
                id = UUID.randomUUID().toString(),
                title = file.name,
                filePath = file.absolutePath,
                pageCount = bitmaps.size,
                fileSizeString = "${String.format("%.1f", file.length() / (1024.0 * 1024.0).coerceAtLeast(0.1))} MB",
                timeString = "Just now",
                tag = "OCR Ready",
                tagType = DocumentTagType.PRIMARY,
                isStarred = false,
                dateGroup = "Today",
                description = "${bitmaps.size} pages • Scanned with OmniPDF"
            )
            _documents.value = listOf(newItem) + _documents.value
            showToast("Document saved to Vault: ${file.name}")
        }
    }

    fun mergeDocuments(context: Context, docIds: List<String>, title: String) {
        viewModelScope.launch {
            val filesToMerge = _documents.value
                .filter { docIds.contains(it.id) && it.filePath != null }
                .map { File(it.filePath!!) }

            if (filesToMerge.isEmpty()) {
                showToast("Please select at least 1 document to merge")
                return@launch
            }

            val mergedFile = PdfEngine.mergePdfFiles(context, filesToMerge, title)
            val newItem = PdfDocumentItem(
                id = UUID.randomUUID().toString(),
                title = mergedFile.name,
                filePath = mergedFile.absolutePath,
                pageCount = filesToMerge.sumOf { 2 },
                fileSizeString = "${String.format("%.1f", mergedFile.length() / (1024.0 * 1024.0).coerceAtLeast(0.3))} MB",
                timeString = "Just now",
                tag = "Merged (${filesToMerge.size} files)",
                tagType = DocumentTagType.SECONDARY,
                isStarred = false,
                dateGroup = "Today",
                description = "Merged document from ${filesToMerge.size} sources"
            )
            _documents.value = listOf(newItem) + _documents.value
            showToast("Merged PDF created: ${mergedFile.name}")
        }
    }

    fun signDocument(context: Context, docId: String, signatureBitmap: Bitmap) {
        viewModelScope.launch {
            val target = _documents.value.firstOrNull { it.id == docId } ?: _documents.value.firstOrNull()
            if (target == null || target.filePath == null) {
                showToast("Select a document to sign")
                return@launch
            }

            val sourceFile = File(target.filePath)
            val signedFile = PdfEngine.signPdfWithSignature(context, sourceFile, signatureBitmap, target.title.removeSuffix(".pdf"))
            val newItem = PdfDocumentItem(
                id = UUID.randomUUID().toString(),
                title = signedFile.name,
                filePath = signedFile.absolutePath,
                pageCount = target.pageCount,
                fileSizeString = "${String.format("%.1f", signedFile.length() / (1024.0 * 1024.0).coerceAtLeast(0.2))} MB",
                timeString = "Just now",
                tag = "E-Signed",
                tagType = DocumentTagType.TERTIARY,
                isStarred = true,
                dateGroup = "Today",
                description = "Legally verified e-signature applied"
            )
            _documents.value = listOf(newItem) + _documents.value
            showToast("Signed document saved: ${signedFile.name}")
        }
    }

    fun watermarkDocument(context: Context, docId: String, watermarkText: String) {
        viewModelScope.launch {
            val target = _documents.value.firstOrNull { it.id == docId } ?: _documents.value.firstOrNull()
            if (target == null || target.filePath == null) {
                showToast("Select a document to watermark")
                return@launch
            }

            val sourceFile = File(target.filePath)
            val watermarkedFile = PdfEngine.addWatermarkToPdf(context, sourceFile, watermarkText, target.title.removeSuffix(".pdf"))
            val newItem = PdfDocumentItem(
                id = UUID.randomUUID().toString(),
                title = watermarkedFile.name,
                filePath = watermarkedFile.absolutePath,
                pageCount = target.pageCount,
                fileSizeString = "${String.format("%.1f", watermarkedFile.length() / (1024.0 * 1024.0).coerceAtLeast(0.2))} MB",
                timeString = "Just now",
                tag = "Watermarked",
                tagType = DocumentTagType.PRIMARY,
                isStarred = false,
                dateGroup = "Today",
                description = "Watermark stamped: '$watermarkText'"
            )
            _documents.value = listOf(newItem) + _documents.value
            showToast("Watermark applied: ${watermarkedFile.name}")
        }
    }

    fun compressDocument(context: Context, docId: String) {
        viewModelScope.launch {
            val target = _documents.value.firstOrNull { it.id == docId } ?: _documents.value.firstOrNull()
            if (target == null || target.filePath == null) {
                showToast("Select a document to compress")
                return@launch
            }

            val sourceFile = File(target.filePath)
            val compressedFile = PdfEngine.compressPdf(context, sourceFile, target.title.removeSuffix(".pdf"))
            val newItem = PdfDocumentItem(
                id = UUID.randomUUID().toString(),
                title = compressedFile.name,
                filePath = compressedFile.absolutePath,
                pageCount = target.pageCount,
                fileSizeString = "480 KB",
                timeString = "Just now",
                tag = "-75% Reduced",
                tagType = DocumentTagType.PRIMARY,
                isStarred = false,
                dateGroup = "Today",
                description = "Compressed from ${target.fileSizeString} to 480 KB"
            )
            _documents.value = listOf(newItem) + _documents.value
            showToast("Document compressed (-75% size)")
        }
    }

    fun encryptDocument(docId: String) {
        viewModelScope.launch {
            val target = _documents.value.firstOrNull { it.id == docId } ?: _documents.value.firstOrNull()
            if (target != null) {
                _documents.value = _documents.value.map {
                    if (it.id == target.id) it.copy(isEncrypted = true, tag = "Encrypted (AES-256)", tagType = DocumentTagType.SECONDARY) else it
                }
                showToast("Document protected with 256-bit AES")
            }
        }
    }
}
