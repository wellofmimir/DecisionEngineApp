package com.molokosoft.decisionengine.homescreen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.NavigationBar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.material3.NavigationBarItem
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.NavigationBarItemDefaults

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.theme.DecisionBlue


@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    selectedItem: NavigationItem = NavigationItem.HOME,
    onNavigationSelected: (NavigationItem) -> Unit
){
    val typography = LocalAppTypography.current

    Column{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.25.dp)
                .background(DecisionBlue)
        )

        NavigationBar(
            containerColor = DecisionBlueLight,
        ) {
            NavigationBarItem(
                selected = selectedItem == NavigationItem.HOME,
                onClick = {
                    onNavigationSelected(NavigationItem.HOME)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    indicatorColor = DecisionBlue
                ),
                icon = {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = "Home",
                        tint = Color.Black
                    )
                },
                label = {
                    Text(
                        text = "Home",
                        fontSize = typography.titleSmall.fontSize,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                }
            )

            NavigationBarItem(
                selected = selectedItem == NavigationItem.NEW_DECISION,
                onClick = {
                    onNavigationSelected(NavigationItem.NEW_DECISION)
                },
                icon = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "New\nDecision",
                        tint = Color.Black
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    indicatorColor = DecisionBlue
                ),
                label = {
                    Text(
                        text = "New Decision",
                        fontSize = typography.titleSmall.fontSize * 1.25f,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            )

            NavigationBarItem(
                selected = selectedItem == NavigationItem.HISTORY,
                onClick = {
                    onNavigationSelected(NavigationItem.HISTORY)
                },
                icon = {
                    Icon(
                        Icons.Default.List,
                        contentDescription = "History",
                        tint = Color.Black
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    indicatorColor = DecisionBlue
                ),
                label = {
                    Text(
                        text = "History",
                        fontSize = typography.titleSmall.fontSize,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                }
            )
        }
    }
}