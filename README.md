<h1 align="center">Compose RecipeApp</h1>

<p align="center">A demo Recipe App using Jetpack Compose and Hilt based on MVI and clean architecture. The App showcases some of thebest practices to 
follow when building apps with jetpack compose. This App also supports Dark and light mode.</p>
</br>

## Tech stack & Open-source libraries </br>
- Minimum SDK level 21 </br>
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) (alpha) for dependency injection.</br>
- JetPack</br>
-- [Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAjw64eJBhAGEiwABr9o2BRlFiZCYMbzXNFymS-fc9pxa66UPYGxS_MqRZsfAQnok2Dxyw-RgRoCHCsQAvD_BwE&gclsrc=aw.ds) - A modern toolkit for building native Android UI.</br>
-- LiveData - notify domain layer data to views.</br>
-- ViewModel - UI related data holder, lifecycle aware.</br>
-- Room Persistence - construct database.</br>
- Architecture</br>
-- MVI Architecture (Declarative View - Intent - State)</br>
-- Repository and Usecase pattern</br>
-- StateFlow to Observe State</br>
- Material Design & Animations</br>
- [Accompanist](https://github.com/google/accompanist) - A collection of extension libraries for Jetpack Compose.
- [Landscapist](https://github.com/skydoves/Landscapist) - Jetpack Compose image loading library</br>
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore?gclid=CjwKCAjw64eJBhAGEiwABr9o2FD-BDrGrl7q2ZrxLXPQcOBCs22FgfloM8yLgoZzW21JZdeuYPxP0RoCcSwQAvD_BwE&gclsrc=aw.ds) 


<table>
    <caption>Dark Mode</caption>
  <td>
    <p align="center">
      <img src="https://user-images.githubusercontent.com/4759634/152944374-4d7c28c7-01c8-443f-9bd7-2bcb7952c4f7.png" alt="Recipe Home Screen" width="300"/>
    </p>
  </td>
  <td>
    <p align="center">
      <img src="https://user-images.githubusercontent.com/4759634/152944396-87708a75-8786-4a06-9929-b077bf458372.png" alt="Recipe Video Screen" width="300"/>
    </p>
  </td>
</tr>
</table>


<table>
    <caption>Light Mode</caption>
  <td>
    <p align="center">
      <img src="https://user-images.githubusercontent.com/4759634/152944407-f09b8e65-d8d6-48c9-b44c-3d1d519e24ef.png" alt="Recipe Home Screen" width="300"/>
    </p>
  </td>
  <td>
    <p align="center">
      <img src="https://user-images.githubusercontent.com/4759634/152944417-ad7cdfbf-678b-48bc-b75b-04e2366f8029.png" alt="Recipe Video Screen" width="300"/>
    </p>
  </td>
</tr>
</table>

