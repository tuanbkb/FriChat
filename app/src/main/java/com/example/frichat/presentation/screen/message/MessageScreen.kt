package com.example.frichat.presentation.screen.message

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.frichat.domain.model.Message
import com.example.frichat.util.TimeUtil
import com.example.frichat.viewmodel.UserViewModel

@Composable
fun MessageScreen(
    viewModel: MessageViewModel = hiltViewModel(),
    userViewModel: UserViewModel,
    chatId: String,
    targetId: String,
    onReturnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getTargetUser(targetId)
        viewModel.addListener(chatId)
    }

    Scaffold(
        topBar = { MessageTopBar(state.targetUser.username, onReturnClick) },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            MessageList(
                targetId = targetId,
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth(),
                messageList = state.messageList,
                viewModel = viewModel
            )
            Spacer(modifier = modifier.height(16.dp))
            MessageField(
                value = state.message,
                onMessageValueChange = { viewModel.onMessageChange(it) },
                onSendButtonClick = {
                    viewModel.onSendButtonClick(
                        userId = userViewModel.user.value.uid,
                        chatId = chatId
                    )
                }
            )
        }
    }
}

@Composable
fun MessageList(
    targetId: String,
    messageList: List<Message>,
    viewModel: MessageViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        items(messageList) { message ->
            MessageItem(targetId = targetId, message = message, viewModel = viewModel)
        }
    }
}

@Composable
fun MessageItem(
    message: Message,
    targetId: String,
    viewModel: MessageViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.collectAsState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = if (targetId == message.senderId) Alignment.CenterStart
            else Alignment.CenterEnd,
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (targetId == message.senderId) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.large
                    )
                    .widthIn(max = 280.dp)
                    .clickable { viewModel.onMessageClick(message.timestamp) }
                    .clip(shape = MaterialTheme.shapes.large)
            ) {
                Text(
                    text = message.text,
                    modifier = Modifier.padding(8.dp),
                    color = if (message.senderId == targetId) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        if (state.value.showTimeMessage == message.timestamp) {
            Text(
                text = TimeUtil.formatLastUpdateTime(message.timestamp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun MessageField(
    value: String,
    onMessageValueChange: (String) -> Unit,
    onSendButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onMessageValueChange,
            placeholder = { Text(text = "Enter message") },
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = onSendButtonClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send message",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageTopBar(
    targetUsername: String,
    onReturnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .size(40.dp)
                ) {
                    Text(
                        text = targetUsername[0].toString(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = targetUsername,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onReturnClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        },
        modifier = modifier.shadow(4.dp)
    )
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun MessageScreenPreview() {
//    AppTheme(darkTheme = true) {
//        MessageScreen(
//            targetId = "target",
//            onReturnClick = {}
//        )
//    }
//}