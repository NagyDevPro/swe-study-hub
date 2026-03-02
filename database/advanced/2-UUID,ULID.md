# UUID (Universal Unique Identifer)

- 128-bit identifier with versions (e.g., v1: timestamp + MAC address; v4: random).

- 36 char (16 hexadecimal encoding chars + dashes)

ex: - f47ac10b-58cc-4372-a567-0e02b2c3d479.



## Disadvantages

- poor encoding method
- uses mac address which can provides information about device
- UUIDv4 lacks sorting
- More Storage Space



# ULID

- Fixed structure: 48-bit timestamp (milliseconds) + 80-bit randomness.

- Base32 encoding (26 characters, no hyphens), e.g., 01ARZ3NDEKTSV4RRFFQ69G5FAV





performance in read
number of entered data
performance in write


Long
UUID to string


Postgres supports uuid
- Ulid to uuid

both consists of 128 bit
- change from format to different format


