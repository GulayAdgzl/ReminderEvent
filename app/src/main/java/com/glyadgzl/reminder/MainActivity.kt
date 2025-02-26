package com.glyadgzl.reminder

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.Manifest
import android.content.res.Configuration

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton


import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.glyadgzl.reminder.data.DataSource
import com.glyadgzl.reminder.models.ComposeRandomItem
import com.glyadgzl.reminder.ui.dialogs.ReminderDialog
import com.glyadgzl.reminder.ui.theme.ReminderTheme
class MainActivity : ComponentActivity() {

    companion object {
        private var TAG = "MainActivity"
        const val REQUEST_CODE_NOTIFICATION_PERMISSIONS = 11
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getNotificationPermissions()

        setContent {
            ReminderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListItems()
                }
            }
        }
    }
   /* override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            // Check if the user granted the permissions.
            REQUEST_CODE_NOTIFICATION_PERMISSIONS -> {
                val hasAccessNotificationPolicyPermission =
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                val hasPostNotificationsPermission =
                    grantResults[1] == PackageManager.PERMISSION_GRANTED

                // If the user denied the permissions, show a check.
                when {
                    !hasAccessNotificationPolicyPermission || !hasPostNotificationsPermission -> {
                        getNotificationPermissions()
                    }
                    else -> {
                        Log.d(TAG, "Notification Permissions : Granted successfully")
                    }
                }
            }
        }
    }*/
    private fun getNotificationPermissions() {
        try {
            // Check if the app already has the permissions.
            val hasAccessNotificationPolicyPermission =
                checkSelfPermission(Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED
                    //.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED
            val hasPostNotificationsPermission =
                checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED

            // If the app doesn't have the permissions, request them.
            when {
                !hasAccessNotificationPolicyPermission || !hasPostNotificationsPermission -> {
                    // Request the permissions.
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                            Manifest.permission.POST_NOTIFICATIONS
                        ),
                        REQUEST_CODE_NOTIFICATION_PERMISSIONS
                    )
                }
                else -> {
                    // proceed
                    Log.d(TAG, "Notification Permissions : previously granted successfully")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
@Composable
fun ListItems(
    modifier: Modifier = Modifier,
    data: List<ComposeRandomItem> = DataSource.plants.map { it },
) {
    LazyColumn(
        modifier = modifier
            .padding(vertical = 4.dp)
            .testTag("list_items"),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val gridCells = 2 // Number of columns

        val chunkedItems = data.chunked(gridCells)

        items(chunkedItems.size) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                chunkedItems[rowIndex].forEach { item ->
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        PlantCard(
                            name = item.name,
                            type = item.type,
                            imageResId = item.imageResId,
                            description = item.description,
                            modifier = Modifier.testTag("plantCard_${rowIndex}_${chunkedItems[rowIndex].indexOf(item)}")
                        )
                    }
                }

                // If the last row isn't complete, add empty boxes to maintain grid layout
                repeat(gridCells - chunkedItems[rowIndex].size) {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
@Composable
fun PlantCard(
    name: String,
    type: String,
    imageResId: Int,
    description: String,
    modifier: Modifier = Modifier
) {
    val dialogState = remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                dialogState.value = true
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Display plant image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Loading the actual image resource
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Content with expand/collapse animation
            ExpandableCardContent(name = name, imageResId = imageResId, type = type, description = description)

            Spacer(modifier = Modifier.height(4.dp))

            // Price and favorite button row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {


                // Favorite button
                IconButton(
                    onClick = { /* Toggle favorite */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color.LightGray.copy(alpha = 0.2f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Add to favorites",
                        tint = Color.Black
                    )
                }
            }
        }
    }

    if (dialogState.value) {
        ReminderDialog(name = name, onDismiss = { dialogState.value = false })
    }
}

@Composable
fun ExpandableCardContent(name: String,  imageResId :Int,type: String, description: String) {
    // Using remember to cache the expanded state
    val expanded = remember { mutableStateOf(false) }

    // Animate expanding and collapsing with spring animation
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = type,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            if (expanded.value && description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )
            }
        }

        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(
                painter = if (expanded.value)
                    painterResource(id = R.drawable.baseline_expand_less_24)
                else
                    painterResource(id = R.drawable.baseline_expand_more_24),
                contentDescription = if (expanded.value) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}
/*
@Composable
fun CardContent(name: String, type: String, description: String) {
    /**
     * @remember is a composable function that can be used to cache expensive operations.
     *  Since we want to change state and also update the UI, we can use a MutableState.
     */
    val expanded = remember { mutableStateOf(false) }

    /**
     * Animate collapsing.
     * - Let's do something more fun like adding a spring-based animation
     * - The spring spec does not take any time-related parameters. Instead it relies on physical
     * properties (damping and stiffness) to make animations more natural.
     */
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = type)
            Text(
                text = "$name.",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )

            if (expanded.value) {
                // Some random text here.
                Text(
                    text = (description)
                )
            }
        }

        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(
                painter = if (expanded.value) painterResource(id = R.drawable.baseline_expand_less_24) else painterResource(
                    id = R.drawable.baseline_expand_more_24
                ),
                contentDescription = if (expanded.value) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )

        }
    }
}*/

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun DefaultPreview() {
    ReminderTheme {
        ListItems()
    }
}