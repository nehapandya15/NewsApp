package com.example.newsapp.presentation.details.components

import android.content.Intent
import android.content.Intent.createChooser
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article

@Composable
fun DetailActionBar(
    article: Article,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier,
    isBookmarked: Boolean = false
) {

    val context = LocalContext.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        tonalElevation = 2.dp,
        color = MaterialTheme.colorScheme.surface
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            IconButton(
                onClick = onBookmarkClick
            ) {

                Icon(
                    imageVector =
                        if (isBookmarked)
                            Icons.Filled.Bookmark
                        else
                            Icons.Outlined.BookmarkBorder,

                    contentDescription = stringResource(
                        R.string.bookmark
                    )
                )

            }

            IconButton(
                onClick = {

                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, article.title)
                        putExtra(Intent.EXTRA_TEXT, article.url)
                    }

                    context.startActivity(
                        createChooser(
                            intent,
                            context.getString(R.string.share_article)
                        )
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(R.string.share)
                )
            }

            IconButton(
                onClick = {

                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(article.url)
                    )

                    context.startActivity(intent)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = stringResource(R.string.open_in_browser)
                )
            }

        }
    }
}