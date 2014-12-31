# Example usage of Wiremock Extensions

Run Wiremock in standalone mode with the following option:

```
--extensions=info.batey.wiremock.extension.CopiesBateyHeaders
```

And see that any header that's name starts with Batey is copied to the response.