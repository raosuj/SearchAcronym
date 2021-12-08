# Search APP


#### Frameworks and Library
- Android: Databinding
- Async: Coroutine
- Network: Retrofit, Okttp, Gson
- DI: I have setup dagger and service locator on other project but
    with this DI is a singleton that lazily loads the object

#### Flow of data fetching.

```
- Viewmodel data from search will asks repository for data.
- Repository makes network call through ApiClient
- All the get call pass thorough a Cache Interceptor that caches the netowrk call.
(This is done instead of using a database)
- Viewmodel gets back data in Result object which is a sealed class of Success and failure
- If Result is success we bind the data to the view
- If Result is failure we show a error message
- search debounce is implemented with call cancellation
```