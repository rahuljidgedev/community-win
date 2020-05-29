package com.app.communities_win_crisis.network_interfacing.utils

import com.app.communities_win_crisis.ui.main.presentor.HomePresenter
import com.app.communities_win_crisis.ui.main.presentor.SplashPresenter
import com.app.communities_win_crisis.ui.main.presentor.VendorPresenter
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class HttpRequestsUtils {

    companion object {

        fun httpRequestGetTokenStatus(url: String, map: HashMap<String, String>, context: Any)
                = run {
            val client = OkHttpClient()
            val jsonString: String  = Gson().toJson(map)

            val requestBody =
                jsonString.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    if (context is SplashPresenter){
                        context.onFailure(e.message)
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        (context as SplashPresenter).onSucceed(
                            response.body!!.string(),
                            map[HttpConstants.REQ_BODY_NAME_CEL],
                            HttpConstants.SERVICE_REQUEST_TOKEN
                        )
                    }else{
                        if (context is SplashPresenter){
                            context.onFailure(response.body!!.string())
                        }
                    }
                }
            })
        }

        fun httpRequestTokenUpdate(url: String, map: HashMap<String, String>, context: Any)
                = run {
            val client = OkHttpClient()
            val jsonString: String  = Gson().toJson(map)

            val requestBody =
                jsonString.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    if (context is SplashPresenter){
                        context.onFailure(e.message)
                    }else if (context is HomePresenter) {
                        context.onFailure(e.message)
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful){
                        if (context is SplashPresenter){
                            context.onSucceed(
                                response.body!!.string(),
                                map[HttpConstants.REQ_BODY_NAME_CEL],
                                HttpConstants.SERVICE_REQUEST_TOKEN_UPDATE
                            )
                        }else if (context is HomePresenter) {
                            context.onSucceed(
                                response.body!!.string(),
                                map[HttpConstants.REQ_BODY_NAME_CEL],
                                HttpConstants.SERVICE_REQUEST_TOKEN_UPDATE
                            )
                        }
                    }else{
                        if (context is SplashPresenter){
                            context.onFailure(response.body!!.string())
                        }else if (context is HomePresenter) {
                            context.onFailure(response.body!!.string())
                        }
                    }
                }
            })
        }

        fun httpRequestUserActiveConnectionList(url: String, map: HashMap<String, String>, context: Any)
                = run {
            val client = OkHttpClient()

            var modifiedServiceUrl: String = "$url?"
            for ((k,v) in map){
                modifiedServiceUrl = "$modifiedServiceUrl$k=$v&"
            }

            val request = Request.Builder()
                .url(modifiedServiceUrl)
                .build()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    if (context is HomePresenter)
                        context.onFailure(e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (context is HomePresenter){
                        context.onSucceed(
                            response.body!!.string(),
                            map[HttpConstants.REQ_BODY_NAME_CEL],
                            HttpConstants.SERVICE_REQUEST_USER_CONTACT_LIST
                        )
                    }
                }
            })
        }

        fun httpRequestUserAnonymousContactUpload(url: String, map: HashMap<String, String>, context: Any)
                = run {
            val client = OkHttpClient()
            val jsonString: String  = Gson().toJson(map)

            val requestBody =
                jsonString.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    if (context is UploadAnonymousContactRequest)
                        context.onFailure(e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful){
                        if (context is UploadAnonymousContactRequest) {
                            context.onSucceed(
                                response.body!!.string(),
                                map[HttpConstants.REQ_BODY_NAME_CEL],
                                HttpConstants.SERVICE_REQUEST_ANONYMOUS_CONTACT_UPLOAD
                            )
                        }
                    }else{
                        if (context is UploadAnonymousContactRequest) {
                            context.onFailure(response.body!!.string())
                        }
                    }
                }
            })
        }

        /*Vendor related Requests*/
        fun httpRequestVendorRegisterAddUpdate(url: String, map: HashMap<String, String>, context: Any)
                = run {
            val client = OkHttpClient()
            val jsonString: String  = Gson().toJson(map)

            val requestBody =
                jsonString.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val request = Request.Builder()
                .url(url)
                .header(HttpConstants.REQ_HEADER_API_KEY,HttpConstants.REQ_APP)
                .post(requestBody)
                .build()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    (context as VendorPresenter).onFailure(e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    (context as VendorPresenter).onSucceed(response.body!!.string(), url)
                }
            })
        }

        fun httpRequestVendorCategory(url: String, map: HashMap<String, String>, context: Any)
                = run {
            val client = OkHttpClient()
            val jsonString: String  = Gson().toJson(map)

            val requestBody =
                jsonString.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val request = Request.Builder()
                .url(url)
                .header(HttpConstants.REQ_HEADER_API_KEY,HttpConstants.REQ_APP)
                .post(requestBody)
                .build()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    (context as VendorPresenter).onFailure(e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    (context as VendorPresenter).onSucceed(response.body!!.string(), url)
                }
            })
        }

        fun httpRequestVendorProductList(url: String, context: Any)
                = run {
            val client = OkHttpClient()

            val request = Request.Builder()
                .header(HttpConstants.REQ_HEADER_API_KEY,HttpConstants.REQ_APP)
                .url(url)
                .build()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    (context as VendorPresenter).onFailure(e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    (context as VendorPresenter).onSucceed(response.body!!.string(), url)
                }
            })
        }

        fun httpRequestProductPricesVendor(url: String, map: HashMap<String, String>, context: Any)
                = run {
            val client = OkHttpClient()
            val jsonString: String  = Gson().toJson(map)

            val requestBody =
                jsonString.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val request = Request.Builder()
                .url(url)
                .header(HttpConstants.REQ_HEADER_API_KEY,HttpConstants.REQ_APP)
                .post(requestBody)
                .build()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    (context as VendorPresenter).onFailure(e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    (context as VendorPresenter).onSucceed(response.body!!.string(), url)
                }
            })
        }

        fun httpRequestVendorProductPriceList(url: String, map: HashMap<String, String>, context: Any)
                = run {
            val client = OkHttpClient()

            var modifiedServiceUrl: String = "$url?"
            for ((k,v) in map){
                modifiedServiceUrl = "$modifiedServiceUrl$k=$v&"
            }

            val request = Request.Builder()
                .url(modifiedServiceUrl)
                .build()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {}
            })
        }

        fun httpRequestVendorCoronaPrecautionsUpdate(url: String, map: HashMap<String, String>, context: Any)
                = run {
            val client = OkHttpClient()
            val jsonString: String  = Gson().toJson(map)

            val requestBody =
                jsonString.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val request = Request.Builder()
                .url(url)
                .header(HttpConstants.REQ_HEADER_API_KEY,HttpConstants.REQ_APP)
                .post(requestBody)
                .build()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    (context as VendorPresenter).onFailure(e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    (context as VendorPresenter).onSucceed(response.body!!.string(), url)
                }
            })
        }

        fun httpRequestGetVendor(url: String, map: HashMap<String, String>, context: Any)
                = run {
            val client = OkHttpClient()
            val jsonString: String  = Gson().toJson(map)

            val requestBody =
                jsonString.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val request = Request.Builder()
                .url(url)
                .header(HttpConstants.REQ_HEADER_API_KEY,HttpConstants.REQ_APP)
                .post(requestBody)
                .build()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    (context as VendorPresenter).onFailure(e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    (context as VendorPresenter).onSucceed(response.body!!.string(), url)
                }
            })
        }
    }
}