package com.example.frichat.presentation.screen.chatlist

import android.util.Log
import android.widget.Space
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frichat.R
import com.example.frichat.domain.model.Chat
import com.example.frichat.domain.model.User
import com.example.frichat.ui.theme.AppTheme
import com.example.frichat.util.TimeUtil
import com.example.frichat.viewmodel.UserViewModel
import java.util.Date

@Composable
fun ChatListScreen(
    userViewModel: UserViewModel,
    chatListViewModel: ChatListViewModel = hiltViewModel(),
    onChatClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by chatListViewModel.state.collectAsState()
    val user by userViewModel.user.collectAsState()

    LaunchedEffect(Unit) {
        chatListViewModel.listenToChat(user.uid)
    }
    // Dummy database
//    val chatList = listOf(
//        Chat(
//            "test",
//            "How are you doing",
//            Date(),
//            User("test", "Test", "test", "")
//        ),
//        Chat(
//            "test",
//            "You good?",
//            Date(),
//            User("test", "Test", "test", "")
//        ),
//        Chat(
//            "test",
//            "How are you doing. I'm doing very good right now, hope to see you someday!",
//            Date(),
//            User("test", "Test", "test", "")
//        ),
//    )

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(state.chats) { chat ->
                ChatItem(chat = chat, onClick = onChatClick)
            }
        }
    }
}

@Composable
fun ChatItem(
    chat: Chat,
    onClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.height(80.dp).clickable { onClick(chat.id, chat.targetUser.uid) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(60.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                text = chat.targetUser.username[0].toString(),
                style = MaterialTheme.typography.displaySmall
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
        ) {
            Text(
                text = chat.targetUser.username,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = chat.lastMessage,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = TimeUtil.formatLastUpdateTime(chat.lastUpdate)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ChatListScreenPreview() {
//    AppTheme {
//        ChatListScreen(viewModel())
//    }
//}