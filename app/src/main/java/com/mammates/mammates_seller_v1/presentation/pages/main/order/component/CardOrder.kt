package com.mammates.mammates_seller_v1.presentation.pages.main.order.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mammates.mammates_seller_v1.domain.model.FoodOrderItem
import com.mammates.mammates_seller_v1.util.StatusOrder

@Composable
fun CardOrder(
    modifier: Modifier = Modifier,
    statusOrder: StatusOrder,
    buyer: String,
    total: Int,
    foods: List<FoodOrderItem>,
    onConfirmOrder: () -> Unit,
    onSeeDetail: () -> Unit,
    onCancelOrder: () -> Unit,
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buyer,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.inverseSurface
            )
            Box {
                IconButton(onClick = {
                    expanded = true
                }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu Dropdown")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "See Details",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        onClick = onSeeDetail
                    )
                    if (
                        statusOrder != StatusOrder.Finish &&
                        statusOrder != StatusOrder.Canceled &&
                        statusOrder != StatusOrder.Unidentified
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Cancel Order",
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            },
                            onClick = onCancelOrder
                        )
                    }
                }
            }

        }
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            foods.forEach { item ->
                CardOrderFood(
                    foodName = item.name ?: "No Food Name",
                    quantity = item.quantity ?: 0,
                    price = item.price ?: 0,
                    image = item.image
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .padding(vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Rp. $total",
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Status",
                    style = MaterialTheme.typography.titleSmall
                )
                Button(
                    modifier = Modifier.defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = 10.dp
                    ),
                    onClick = { },
                    enabled = false,
                    contentPadding = PaddingValues(horizontal = 15.dp, vertical = 1.dp),
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = statusOrder.color,
                        disabledContentColor = Color.White
                    )

                ) {
                    Text(
                        text = statusOrder.name,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        if (
            statusOrder != StatusOrder.Finish &&
            statusOrder != StatusOrder.Canceled &&
            statusOrder != StatusOrder.Unidentified
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = onConfirmOrder) {
                    Text(text = "Confirm Order")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardOrderPreview() {
    CardOrder(
        buyer = "Gede Mahardika",
        statusOrder = StatusOrder.Unconfirmed,
        total = 40000,
        foods = listOf(
            FoodOrderItem(
                image = null,
                name = "Donut Kentang Rasa Coklat",
                quantity = 4,
                price = 20000
            ),
            FoodOrderItem(
                image = null,
                name = "Donut Kentang Rasa Coklat",
                quantity = 4,
                price = 20000
            ),
        ),
        onConfirmOrder = {},
        onSeeDetail = {},
        onCancelOrder = {}
    )

}