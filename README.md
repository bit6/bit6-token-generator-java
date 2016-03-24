## Bit6 Token Generator for Java

A super simple application demonstrating the external authentication in Bit6.


### Prerequisites

* Get the API Key and Secret at [Bit6 Dashboard](https://dashboard.bit6.com).


### Running Locally

Make sure you have `Gradle` installed.

```sh
$ git clone git@github.com:bit6/bit6-token-generator-java.git
$ cd bit6-token-generator-java
```

Specify your Bit6 API key and secret in `com.bit6.auth.SampleServlet` source code or set the environment variables:

```
export BIT6_API_KEY=abc
export BIT6_API_SECRET=xyz
```

Start the application

```sh
$ gradle appRun
```

Your app should now be running on [localhost:5000](http://localhost:5000/auth).


### Generating a Token

You would normally generate an external token by doing a POST from your app client to your application server. To simulate this using `curl`:

```sh
curl -X POST \
    -H "Content-Type: application/json" \
    -d '{"identities": ["usr:john","tel:+12123331234"]}' \
    http://localhost:5000/auth
```

The response should be a JSON object:

```json
{
    "ext_token": "..."
}
```
