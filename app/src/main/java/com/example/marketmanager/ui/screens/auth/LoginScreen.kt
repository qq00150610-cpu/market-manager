package com.example.marketmanager.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.marketmanager.ui.theme.Primary
import com.example.marketmanager.ui.theme.PrimaryDark
import com.example.marketmanager.viewmodel.MainViewModel

@Composable
fun LoginScreen(
    viewModel: MainViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val apiError by viewModel.errorMessage.collectAsStateWithLifecycle()

    LaunchedEffect(apiError) {
        apiError?.let {
            showError = true
            errorMessage = it
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Primary, PrimaryDark)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "农贸市场管理系统", fontSize = 28.sp,
                fontWeight = FontWeight.Bold, color = Color.White,
                textAlign = TextAlign.Center, modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "让农贸市场管理更简单", fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center, modifier = Modifier.padding(bottom = 48.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("用户登录", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Primary,
                        modifier = Modifier.padding(bottom = 24.dp))

                    OutlinedTextField(
                        value = email, onValueChange = { email = it; showError = false },
                        label = { Text("邮箱地址") }, placeholder = { Text("请输入邮箱地址") },
                        singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Primary, unfocusedBorderColor = Color.Gray)
                    )

                    OutlinedTextField(
                        value = password, onValueChange = { password = it; showError = false },
                        label = { Text("密码") }, placeholder = { Text("请输入密码") },
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Text(if (passwordVisible) "隐藏" else "显示", color = Primary, fontSize = 12.sp)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Primary, unfocusedBorderColor = Color.Gray)
                    )

                    TextButton(
                        onClick = { /* 找回密码 */ },
                        modifier = Modifier.align(Alignment.End).padding(bottom = 24.dp)
                    ) { Text("忘记密码？", color = Primary, fontSize = 14.sp) }

                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {
                                showError = true; errorMessage = "请输入邮箱和密码"
                            } else {
                                showError = false
                                viewModel.login(email, password) { onLoginSuccess() }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary, contentColor = Color.White),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                        } else {
                            Text("登录", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (showError) {
                        Text(errorMessage, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(top = 16.dp))
                    }
                }
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text("还没有账号？", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                TextButton(onClick = onNavigateToRegister) {
                    Text("点击注册", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}
