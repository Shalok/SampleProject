package com.sample.presentation.util.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sample.presentation.R
import com.sample.presentation.theme.primaryColor
import com.sample.presentation.util.model.RecipeCardData

@Composable
fun RecipeCard(
    recipeCard: RecipeCardData,
    cardRoundedCornerShape: Shape = RoundedCornerShape(10.dp)
) {
    Card(
        modifier = recipeCard.modifier
            .fillMaxWidth()
            .clickable {
                recipeCard.callback?.invoke(recipeCard.id)
            },
        shape = cardRoundedCornerShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.standard_elevation_medium),
            pressedElevation = 5.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            AsyncImage(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                model = recipeCard.imageUrl,
                contentScale = ContentScale.FillWidth,
                contentDescription = "Translated description of what the image contains"
            )
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = recipeCard.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = primaryColor
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    RecipeInfo(
                        R.drawable.ic_timer,
                        recipeCard.cookingTime.plus(stringResource(R.string.recipe_minute))
                    )
                    RecipeInfo(R.drawable.ic_difficulty_level, recipeCard.difficulty)
                    RecipeInfo(R.drawable.ic_cuisine, recipeCard.cuisine)
                }

            }
        }
    }
}

@Composable
private fun RecipeInfo(
    @DrawableRes icon: Int,
    info: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "cooking_icon"
        )
        Text(
            text = info,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall,
            color = primaryColor
        )
    }
}


@Preview
@Composable
fun PreviewRecipeCard() {
    RecipeCard(
        RecipeCardData(
            id = "1",
            name = "Chicken Biryani",
            imageUrl = "https://cdn.pixabay.com/photo/2016/06/15/19/09/food-145969",
            cookingTime = "30 Min",
            difficulty = "Easy",
            cuisine = "Indian",
            modifier = Modifier.padding(10.dp)
        )
    )
}