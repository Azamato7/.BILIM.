package com.example.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.BlueDark
import com.example.ui.theme.BlueLight
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.GreenContainer
import com.example.ui.theme.GreenDark
import com.example.ui.theme.GreenSuccess
import com.example.ui.theme.RedContainer
import com.example.ui.theme.RedError
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900

object AvatarHelper {
    fun isCustomImageUri(avatarId: String?): Boolean {
        if (avatarId.isNullOrBlank()) return false
        return avatarId.startsWith("content://") ||
                avatarId.startsWith("file://") ||
                avatarId.startsWith("http://") ||
                avatarId.startsWith("https://")
    }
}

@Composable
fun UserAvatarView(
    avatarId: String?,
    fullName: String,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    shape: androidx.compose.ui.graphics.Shape = CircleShape,
    is3x4PassportStyle: Boolean = false
) {
    val isCustom = AvatarHelper.isCustomImageUri(avatarId)
    val initials = fullName.trim().split(" ")
        .mapNotNull { it.firstOrNull()?.uppercase() }
        .take(2)
        .joinToString("")
        .ifBlank { "U" }

    if (isCustom && !avatarId.isNullOrBlank()) {
        // User's custom photo from their phone gallery
        Box(
            modifier = modifier
                .clip(shape)
                .background(Color(0xFFF1F5F9)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Uri.parse(avatarId))
                    .crossfade(true)
                    .build(),
                contentDescription = fullName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if (is3x4PassportStyle) {
                Surface(
                    shape = RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp),
                    color = Color(0xFF0F766E).copy(alpha = 0.85f),
                    modifier = Modifier.align(Alignment.TopCenter)
                ) {
                    Text(
                        text = "3x4 FOTO",
                        fontSize = 7.sp,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }
            }
        }
    } else {
        // Fallback initials representation
        if (is3x4PassportStyle) {
            Box(
                modifier = modifier
                    .clip(shape)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF1E3A8A), Color(0xFF3B82F6))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize().padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(size * 0.45f)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar Foto",
                            tint = Color.White,
                            modifier = Modifier.size(size * 0.35f)
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = initials,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "3x4 FOTO",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 7.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        } else {
            Box(
                modifier = modifier
                    .size(size)
                    .clip(shape)
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF1E3A8A), Color(0xFF3B82F6))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = (size.value * 0.38f).sp
                )
            }
        }
    }
}

/**
 * User Photo Picker from Phone Gallery (without fixed colored person icons)
 */
@Composable
fun AvatarPickerRow(
    selectedAvatarId: String?,
    onSelectAvatar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isCustom = AvatarHelper.isCustomImageUri(selectedAvatarId)

    // Android Photo Picker Launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onSelectAvatar(uri.toString())
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp)),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isCustom) 1.5.dp else 1.dp,
            color = if (isCustom) GreenSuccess else MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Foydalanuvchi 3x4 fotosurati:",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (isCustom) {
                    Surface(
                        color = GreenContainer,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = GreenDark,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Galereyadan tanlandi",
                                fontSize = 11.sp,
                                color = GreenDark,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // 3x4 Photo Preview Frame
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 106.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFE2E8F0))
                        .border(
                            width = 1.5.dp,
                            color = if (isCustom) GreenSuccess else MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (isCustom && !selectedAvatarId.isNullOrBlank()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(Uri.parse(selectedAvatarId))
                                .crossfade(true)
                                .build(),
                            contentDescription = "Sertifikat fotosi",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddAPhoto,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "3x4 FOTO",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                // Action Column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = if (isCustom) "Rasmingiz muvaffaqiyatli yuklandi va rasmiy sertifikatingizda ko'rsatiladi."
                        else "Telefoningiz galereyasidan o'zingizning 3x4 fotosuratingizni yuklang. Ushbu rasm davlat sertifikatiga joylashtiriladi.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCustom) BluePrimary else MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("btn_pick_gallery_photo")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isCustom) "Boshqa rasm tanlash" else "Galereyadan tanlash",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (isCustom) {
                            OutlinedButton(
                                onClick = { onSelectAvatar("") },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = RedError)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "O'chirish",
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
