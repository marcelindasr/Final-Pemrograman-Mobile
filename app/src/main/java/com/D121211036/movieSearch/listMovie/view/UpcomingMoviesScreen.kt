package com.D121211036.movieSearch.listMovie.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.room.Index
import androidx.room.util.getColumnIndex
import com.D121211036.movieSearch.listMovie.util.Category
import com.D121211036.movieSearch.listMovie.view.ListMovieState
import com.D121211036.movieSearch.listMovie.view.ListMovieUiEvent
import com.D121211036.movieSearch.listMovie.view.components.MovieItem

@Composable
fun UpcomingMovieScreen(
    listMovieState: ListMovieState,
    navController: NavHostController,
    onEvent: (ListMovieUiEvent) -> Unit
) {
    if(listMovieState.upcomingListMovie.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)

        ) {
            items(listMovieState.upcomingListMovie.size) {index ->
                MovieItem(
                    movie = listMovieState.upcomingListMovie[index],
                    navHostController = navController
                )
                Spacer(modifier = Modifier.height(16.dp) )

                if (index >= listMovieState.upcomingListMovie.size - 1 && !listMovieState.isLoading) {
                    onEvent(ListMovieUiEvent.Paginate(Category.UPCOMING))
                }
            }

        }
    }

}
































