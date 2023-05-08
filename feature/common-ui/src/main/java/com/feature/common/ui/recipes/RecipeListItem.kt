import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.core.themes.spacerSmall
import com.feature.common.OnClick
import com.feature.common.ui.common_views.CookingTime
import com.feature.common.ui.common_views.SaveIcon
import com.feature.common.ui.common_views.Servings
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListItem(
    title:String,
    imageUrl:String,
    cookingTime:Int,
    servings:Int,
    isSaved:Boolean,
    index: Int,
    onRowClick: OnClick<Unit>,
    onSaveClick: OnClick<Unit>,
    onRemoveClick: OnClick<Unit>
) {
    Card(
        onClick = { onRowClick(Unit) },
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 12.dp,
                vertical = if (index == 0) 8.dp else 4.dp
            )
    ) {
        Row {
            GlideImage(
                imageModel = imageUrl,
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp),
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = title, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.spacerSmall())
                CookingTime(time = cookingTime.toString())
                Servings(serving = servings.toString())
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    SaveIcon(isSaved = isSaved) {
                        if (it)
                            onSaveClick(Unit)
                        else
                            onRemoveClick(Unit)
                    }
                }
            }
        }
    }
}
