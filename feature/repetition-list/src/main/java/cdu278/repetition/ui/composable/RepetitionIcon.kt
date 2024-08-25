package cdu278.repetition.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import cdu278.repetition.RepetitionType
import cdu278.repetition.RepetitionType.Email
import cdu278.repetition.RepetitionType.Password
import cdu278.repetition.RepetitionType.Phone
import cdu278.repetition.RepetitionType.Pin
import cdu278.foundation.android.R as CommonR

@Composable
fun RepetitionIcon(
    type: RepetitionType,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(percent = 50),
        color = backgroundColor,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painterResource(
                    id = when (type) {
                        Password -> CommonR.drawable.ic_password
                        Pin -> CommonR.drawable.ic_pin
                        Email -> CommonR.drawable.ic_email
                        Phone -> CommonR.drawable.ic_phone
                    }
                ),
                contentDescription = null
            )
        }
    }
}