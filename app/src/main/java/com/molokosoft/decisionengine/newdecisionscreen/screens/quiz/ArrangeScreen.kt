package com.molokosoft.decisionengine.newdecisionscreen.screens.quiz

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun ArrangeTile(
    text: String,
    rank: Int,
    isDragging: Boolean,
    color: Color,
    modifier: Modifier = Modifier
) {
    val typography = LocalAppTypography.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = if (isDragging) DecisionBlueLight else color,
                shape = RoundedCornerShape(12.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Spacer(
            modifier = Modifier
                .width(32.dp)
        )

        Text(
            text = "$rank.",
            textAlign = TextAlign.Left,
            fontSize = typography.titleSmall.fontSize * 1.1f,
            color = if (isDragging) Color.Black else Color.White
        )

        Spacer(
            modifier = Modifier
                .width(16.dp)
        )

        Text(
            modifier = Modifier
                .weight(1f),
            text = text,
            textAlign = TextAlign.Left,
            fontSize = typography.titleSmall.fontSize * 1.1f,
            color = if (isDragging) Color.Black else Color.White
        )

        Spacer(
            modifier = Modifier
                .width(16.dp)
        )
    }
}

@Composable
fun ArrangeScreen(
    question: String,
    options: List<String>,
    onNextClicked: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalAppTypography.current

    val arrangedOptions = remember(options) { options.toMutableStateList() }
    var draggedOffset by remember { mutableFloatStateOf(0f) }
    var draggedItem by remember { mutableStateOf<String?>(null) }
    val itemBounds = remember { mutableStateMapOf<String, Rect>() }

    Column(
        modifier = modifier
            .background(
                color = Color.White
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(64.dp)
        )

        Image(
            painter = painterResource(
                id = R.drawable.decisionenginelogonew
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = question,
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "Drag the items to rank your priorities.\n1 - Very Important; 4 - Less Important",
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(32.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 2.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            itemsIndexed(
                items = arrangedOptions,
                key = { _, item -> item }
            ) { index, item ->

                val isDragging = draggedItem == item

                ArrangeTile(
                    modifier = Modifier
                        .onGloballyPositioned {
                            itemBounds[item] = it.boundsInRoot()
                        }
                        .graphicsLayer {
                            if (isDragging) {
                                translationY = draggedOffset
                                scaleX = 1.03f
                                scaleY = 1.03f
                                shadowElevation = 12.dp.toPx()
                            }
                        }
                        .pointerInput(item) {
                            detectDragGestures(
                                onDragStart = {
                                    draggedItem = item
                                    draggedOffset = 0f
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()

                                    draggedOffset += dragAmount.y

                                    val currentIndex = arrangedOptions.indexOf(item)

                                    val currentBounds = itemBounds[item]
                                        ?: return@detectDragGestures

                                    val draggedCenter = currentBounds.center.y + draggedOffset

                                    val targetIndex = when {
                                        dragAmount.y > 0 && currentIndex < arrangedOptions.lastIndex ->
                                            currentIndex + 1

                                        dragAmount.y < 0 && currentIndex > 0 ->
                                            currentIndex - 1

                                        else -> -1
                                    }

                                    if (targetIndex == -1)
                                        return@detectDragGestures

                                    val targetItem = arrangedOptions[targetIndex]

                                    val targetBounds = itemBounds[targetItem]
                                            ?: return@detectDragGestures

                                    val shouldSwap = when {
                                        dragAmount.y > 0 ->
                                            draggedCenter > targetBounds.center.y

                                        dragAmount.y < 0 ->
                                            draggedCenter < targetBounds.center.y

                                        else -> false
                                    }

                                    if (shouldSwap) {
                                        val slotDistance = currentBounds.height + with(density) {
                                            12.dp.toPx()
                                        }

                                        val moved = arrangedOptions.removeAt(currentIndex)
                                        arrangedOptions.add(targetIndex, moved)

                                        draggedOffset += when {
                                            dragAmount.y > 0 -> -slotDistance
                                            else -> slotDistance
                                        }
                                    }
                                },
                                onDragEnd = {
                                    draggedItem = null
                                    draggedOffset = 0f
                                },
                                onDragCancel = {
                                    draggedItem = null
                                    draggedOffset = 0f
                                }
                            )
                        },
                    text = item,
                    rank = index + 1,
                    isDragging = isDragging,
                    color = when (index) {
                        0 -> DecisionBlue
                        1 -> DecisionBlue
                        2 -> DecisionBlue
                        3 -> DecisionBlue
                        else -> DecisionBlue
                    }
                )
            }
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Box(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(0.75f)
                .border(
                    width = 1.dp,
                    color = DecisionBlue,
                    shape = RoundedCornerShape(64.dp)
                )
                .background(
                    color = DecisionBlue,
                    shape = RoundedCornerShape(64.dp)
                )
                .clickable {
                    onNextClicked()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Done",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "Back",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = typography.titleSmall.fontSize,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable {
                    onBackClicked()
                }
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}