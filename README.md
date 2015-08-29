# HttpClient
根据HttpUrlConnection扩展的HttpClient

HttpClient

一、同步
>HttpClient可以提交Header、FormData等一些数据，并且将获取到的结果写到HttpResponse中`client.getHttpResponse()`。

二、异步
>AsyncHttpClient继承HttpClient进行异步处理不用直接调用`execute()`，而使用`request(HttpResponseListener)`，结果将可以直接从HttpResponse中获取。
>
>使用`download(HttpResponseListener)`则可以获取InputStream然后自定义下载