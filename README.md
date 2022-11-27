# Spring eWallet App
Project aplikasi backend service berbasis REST untuk studi kasus eWallet app. Project ini tidak untuk digunakan untuk lingkungan produksi, hanya untuk sebuah demo implementasi menggunakan Java Spring.

## Prasyarat
Install beberapa tools berikut pada komputer lokal:
- JDK 11
- Maven
- Docker

## Persiapan
1. Build wallet-service dengan menjalankan perintah `mvn package -Dmaven.test.skip` pada directory wallet-service.
2. Buat docker container dengan menjalankan perintah `docker compose up`.
3. Cek service dengan mengakses URL `http://localhost:8080/health/check`.

## Service
### Registrasi sebagai Customer
``POST http://localhost:8080/customer/register`` <br />
Payload:
```json
{
    "name": "Budi",
    "phone": "081234567890"
}
```
Response:
```json
{
    "message": "success to register new customer",
    "status": "ok"
}
```

### Registrasi sebagai Merchant
``POST http://localhost:8080/merchant/register`` <br />
Payload:
```json
{
    "name": "Caca",
    "phone": "081234567891"
}
```
Response:
```json
{
    "message": "success to register new merchant",
    "status": "ok"
}
```

### Transaksi TopUp
``POST http://localhost:8080/transaction/topup`` <br />
Payload:
```json
{
    "amount": 10,
    "userId": 10
}
```
Response:
```json
{
    "message": "success to topup",
    "status": "ok"
}
```

### Transaksi Payment
``POST http://localhost:8080/transaction/pay`` <br />
Payload:
```json
{
    "amount": 10,
    "customerId": 10,
    "merchantId": 12
}
```
Response:
```json
{
    "message": "success to pay",
    "status": "ok"
}
```

### Cek Saldo Wallet
``GET http://localhost:8080/wallet/balance?userId=12`` <br />
Response:
```json
{
    "data": 10,
    "message": "success",
    "status": "ok"
}
```

### Daftar Customer
``GET http://localhost:8080/customer/list?skip=0&limit=1`` <br />
Response:
```json
{
    "data": [
      {
        "id": 1,
        "name": "Ahmad",
        "phone": "081234567890",
        "userType": "CUSTOMER"
      }
    ],
    "message": "success",
    "status": "ok"
}
```

### Detail Customer
``GET http://localhost:8080/customer/detail?customerId=1`` <br />
Response:
```json
{
    "data": {
      "id": 1,
      "name": "Ahmad",
      "phone": "081234567890",
      "userType": "CUSTOMER"
    },
    "message": "success",
    "status": "ok"
}
```

### Daftar Merchant
``GET http://localhost:8080/merchant/list?skip=0&limit=5`` <br />
Response:
```json
{
    "data": [
      {
        "id": 6,
        "name": "Cici",
        "phone": "081234567892",
        "userType": "MERCHANT"
      },
      {
        "id": 12,
        "name": "Een",
        "phone": "081234567894",
        "userType": "MERCHANT"
      }
    ],
    "message": "success",
    "status": "ok"
}
```

### Detail Merchant
``GET http://localhost:8080/merchant/detail?merchantId=12`` <br />
Response:
```json
{
    "data": {
      "id": 12,
      "name": "Een",
      "phone": "081234567894",
      "userType": "MERCHANT"
    },
    "message": "success",
    "status": "ok"
}
```