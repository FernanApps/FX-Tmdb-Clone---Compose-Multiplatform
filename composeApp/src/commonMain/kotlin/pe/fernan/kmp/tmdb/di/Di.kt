package pe.fernan.kmp.tmdb.di

import pe.fernan.kmp.tmdb.data.remote.TmdbClientApi
import pe.fernan.kmp.tmdb.data.remote.TmdbRepositoryImp
import pe.fernan.kmp.tmdb.ui.home.HomeViewModel

object DataModule {
    val api = TmdbClientApi
    val tmdbRepository = TmdbRepositoryImp(api)
}

object AppModule {
    val homeViewModel = HomeViewModel(DataModule.tmdbRepository)
}
