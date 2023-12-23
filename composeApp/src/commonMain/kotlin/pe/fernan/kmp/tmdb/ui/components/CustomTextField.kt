package pe.fernan.kmp.tmdb.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
itemContent: @Composable (text: String) -> Unit = { text ->
        Text(text = text)
    },
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    shape: Shape = RoundedCornerShape(25.dp),
    label: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    backgroundColor: Color = Color.White,
    contentPaddingValues: PaddingValues = PaddingValues()

) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .background(color = backgroundColor, shape = shape)
                    .padding(contentPaddingValues), // inner padding
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(width = 15.dp))
                Box(modifier.weight(1f)){
                    if (value.isEmpty()) {
                        label?.invoke()
                    }
                    innerTextField()
                }

                Spacer(modifier = Modifier.width(width = 15.dp))
                CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                    trailingIcon?.invoke()
                }
            }


        }
    )
}