# A minimal demo of the problem presented in the StackOverflow question [Why does my WorkManager Work never have Internet connection when running in the background?](https://stackoverflow.com/q/79478376/20594090)

The goal: do API requests in the background with WorkManager using the Internet.

The problem: it doesn't seem to work for me.

How this app proves that point: When you click the button "Schedule work with 15s delay"...

- ... if you leave the app open on-screen ("in the foreground"), the worker has Internet connection and does a successful POST request
- ... if you put the app in off-screen ("in the background), the worker has no Internet connection and fails the POST request

This can be traced by testing this (leaving app in foreground vs. putting it in background during the 15s work delay) and looking at the Logcat.

## Technology used

- [Android WorkManager](https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started) for background work
- [Retrofit HTTP client](https://square.github.io/retrofit/) for HTTP requests
- [restful-api.dev](https://restful-api.dev/) for free REST endpoints for demo projects

## What this minimal problem implementation consists of

See the single Git commit `add minimal problem implementation` for all that is changed compared to a newly generated Empty Activity project.

## Steps get this up and running

Just build the project and run the app.

Make sure your emulator is at least API-level 29 (this is the `minSDK`). I use the Pixel 7 API 35.

## Observe this

Click the button "Schedule work with 15s delay" in the application.

When you leave the app like this, open on-screen and wait for 15s, the Logcat will say:

```text
Network available: true
GET Response: {"id":"7","name":"Apple MacBook Pro 16","data":{"year":2019,"price":1849.99,"CPU model":"Intel Core i9","Hard disk size":"1 TB"}}
```

When you put the app in the background (e.g. swipe it up and chill on the home screen), the Logcat will say:

```text
Network available: false
GET request exception: Unable to resolve host "api.restful-api.dev": No address associated with hostname
```

### Edit: Sometimes it works, but not reliably

I've observed times, where it works.

But very often it does not work.

It looks like a coin-flip. I fail to understand why.

E.g. in one try that I just did while writing this, I am staying in Chrome Browser in the foreground, with the work scheduled in the background, and I get those logs:

```text
Network available: false
GET request exception: Unable to resolve host "api.restful-api.dev": No address associated with hostname
Worker result RETRY for Work

Network available: false
GET request exception: Unable to resolve host "api.restful-api.dev": No address associated with hostname
Worker result RETRY for Work

Network available: true
GET Response: {"id":"7","name":"Apple MacBook Pro 16","data":{"year":2019,"price":1849.99,"CPU model":"Intel Core i9","Hard disk size":"1 TB"}}
Worker result SUCCESS for Work
```

If you can get it to work reliably, this problem would be solved.