package com.practice.shoppinglist

import android.graphics.drawable.shapes.Shape
import android.util.Log
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.NumberFormatException

data class ShoppingItem(
    val id: Int,
    var name: String,
    val quantity: Int,
    var isEditing: Boolean = false
)

@Composable
fun ShoppingListApp() {
    val context = LocalContext.current
    var alertState by remember {
        mutableStateOf(false)
    }
    var sItems by remember {
        mutableStateOf(listOf<ShoppingItem>())
    }
    var itemName by remember {
        mutableStateOf("")
    }
    var itemQuantity by remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(sItems) {
                ShoppingListItem(it,{},{})
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
        if (alertState) {
            AlertDialog(
                onDismissRequest = { alertState = false },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = {
                            itemName = ""
                            itemQuantity = ""
                            alertState = false
                        }) {
                            Text(text = "Cancel")
                        }
                        Spacer(modifier = Modifier.size(18.dp))
                        Button(onClick = {
                            if (itemName.isNotBlank() && itemQuantity.isNotBlank()) {
                                try {
                                    val newItem: ShoppingItem = ShoppingItem(
                                        id = sItems.size + 1,
                                        name = itemName,
                                        quantity = itemQuantity.toInt(),
                                        isEditing = false
                                    )
                                    sItems+=newItem
                                    itemName = ""
                                    itemQuantity = ""
                                    alertState = false
                                } catch (e: NumberFormatException) {
                                    Toast.makeText(
                                        context,
                                        "Please input number at quantity",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            } else {
                                Toast.makeText(context, "Please input all data", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }) {
                            Text(text = "Add")
                        }
                    }
                },
                title = { Text(text = "Input your Item", fontWeight = FontWeight.Bold) },
                text = {
                    Column {
                        OutlinedTextField(
                            value = itemName,
                            onValueChange = { itemName = it },
                            label = { Text(text = "Item Name") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                        OutlinedTextField(
                            value = itemQuantity,
                            onValueChange = { itemQuantity = it },
                            label = { Text(text = "Quantity") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                })
        }
        Button(
            onClick = {
                alertState = true
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
                .height(50.dp)
        ) {
            Text(text = "Add Item", fontSize = 15.sp)
        }

    }
}

@Composable
fun ShoppingListItem(item: ShoppingItem, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .border(border = BorderStroke(1.dp, Color.Blue), shape = RoundedCornerShape(10))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.name)
        Text(text = "${item.quantity}")
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}
@Composable
fun EditItem(item:ShoppingItem,onEditComplete:(String,Int)->Unit){

}


