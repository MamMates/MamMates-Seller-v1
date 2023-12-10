package com.mammates.mammates_seller_v1.presentation.pages.main.order.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    foods: List<FoodOrderItem>
) {
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
                text = "Gede Mahardika",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.inverseSurface
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu Dropdown")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // TODO : Foreach every food order
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            foods.forEach { item ->
                CardOrderFood(
                    foodName = item.name ?: "No Food Name",
                    quantity = item.quantity ?: 0,
                    price = item.price ?: 0
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Confirm Order")
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
        )
    )
}