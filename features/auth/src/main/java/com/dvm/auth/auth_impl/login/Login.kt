package com.dvm.auth.auth_impl.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dvm.ui.components.AppBarIconBack
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
internal fun Login(

) {

    var login by remember{ mutableStateOf("") }
    var password by remember{  mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Spacer(modifier = Modifier.statusBarsHeight())
            TopAppBar(
                title = { Text("Вход") },
                navigationIcon = { AppBarIconBack(onNavigateUp = { }) }
            )
        }

        Column(Modifier.padding(10.dp)) {
            TextField(
                value = login,
                onValueChange = { login = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Войти" )
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Регистрация" )
            }
        }

        Column {
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Забыли пароль?")
            }
            Spacer(modifier = Modifier.navigationBarsHeight())
        }



    }
}