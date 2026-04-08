# Json Web Token

## Introduction
- A JSON Web Token (JWT) is a secure way to send information between a client and a server. It is mainly used in web applications and APIs to verify users and prevent unauthorized access.



## Structure
- A JWT consists of three parts: Header, Payload, and Signature.
- **Header**: contains metadata about the token, such as the type of token and the signing algorithm used.
```json
// Example of JWT header
{
  "alg": "HS256",
  "typ": "JWT"
}
```

- **Payload**: contains the claims, which are statements about an entity (typically, the user) and additional data. Claims can be registered (predefined), public (custom), or private (custom and not registered).
```json
// Example of JWT payload
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true,
  "iat": 1516239022
}
```

- **Signature**: is created by taking the encoded header, the encoded payload, a secret key, and the algorithm specified in the header. The signature is used to verify that the token has not been tampered with.
```json
// Example of JWT signature
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret)
```


- Default Password Encoder in spring security is 10 by default

