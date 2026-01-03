# FreshFridge API Testing Guide

Base URL: `http://localhost:8080/api/v1`

## 📋 Table of Contents
1. [Authentication APIs](#authentication-apis)
2. [User APIs](#user-apis)
3. [Category APIs](#category-apis)
4. [Unit APIs](#unit-apis)
5. [Food APIs](#food-apis)
6. [Admin Food APIs](#admin-food-apis)
7. [Group APIs](#group-apis)
8. [Notification APIs](#notification-apis)

---

## 🔐 Authentication APIs

### 1. Register
**POST** `/auth/register`

**Body (JSON):**
```json
{
  "email": "user@example.com",
  "password": "Password@123",
  "name": "John Doe",
  "phone": "0123456789"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Đăng ký thành công",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "expiresIn": 86400000,
    "user": {
      "id": 1,
      "email": "user@example.com",
      "name": "John Doe",
      "phone": "0123456789",
      "avatarUrl": null,
      "role": "USER",
      "isVerified": false,
      "createdAt": "2026-01-02T23:00:00"
    }
  }
}
```

---

### 2. Login
**POST** `/auth/login`

**Body (JSON):**
```json
{
  "email": "user@example.com",
  "password": "Password@123"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Đăng nhập thành công",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "expiresIn": 86400000,
    "user": {
      "id": 1,
      "email": "user@example.com",
      "name": "John Doe",
      "role": "USER"
    }
  }
}
```

---

### 3. Verify Email
**POST** `/auth/verify-email`

**Headers:**
```
Authorization: Bearer {token}
```

**Body (JSON):**
```json
{
  "code": "123456"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Xác thực email thành công",
  "data": null
}
```

---

### 4. Resend Verification Code
**POST** `/auth/resend-verification`

**Headers:**
```
Authorization: Bearer {token}
```

**Response:**
```json
{
  "success": true,
  "message": "Đã gửi lại mã xác thực",
  "data": null
}
```

---

### 5. Change Password
**POST** `/auth/change-password`

**Headers:**
```
Authorization: Bearer {token}
```

**Body (JSON):**
```json
{
  "oldPassword": "OldPassword@123",
  "newPassword": "NewPassword@123",
  "confirmPassword": "NewPassword@123"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Đổi mật khẩu thành công",
  "data": null
}
```

---

## 👤 User APIs

### 1. Get Profile
**GET** `/user/profile`

**Headers:**
```
Authorization: Bearer {token}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "email": "user@example.com",
    "name": "John Doe",
    "phone": "0123456789",
    "avatarUrl": "https://cloudinary.com/avatar.jpg",
    "role": "USER",
    "isVerified": true,
    "createdAt": "2026-01-02T23:00:00"
  }
}
```

---

### 2. Update Profile
**PUT** `/user/profile`

**Headers:**
```
Authorization: Bearer {token}
Content-Type: multipart/form-data
```

**Body (Form-data):**
- `name`: John Updated (text)
- `phone`: 0987654321 (text)
- `avatar`: avatar.jpg (file)

**Response:**
```json
{
  "success": true,
  "message": "Cập nhật thông tin thành công",
  "data": {
    "id": 1,
    "email": "user@example.com",
    "name": "John Updated",
    "phone": "0987654321",
    "avatarUrl": "https://cloudinary.com/new-avatar.jpg",
    "role": "USER"
  }
}
```

---

## 📂 Category APIs

### 1. Get All Categories
**GET** `/category`

**Response:**
```json
{
  "success": true,
  "data": {
    "categories": [
      {
        "id": 1,
        "name": "Rau củ",
        "imageUrl": "https://cloudinary.com/vegetable.jpg"
      },
      {
        "id": 2,
        "name": "Trái cây",
        "imageUrl": "https://cloudinary.com/fruit.jpg"
      }
    ]
  }
}
```

---

## 📏 Unit APIs

### 1. Get All Units
**GET** `/unit`

**Response:**
```json
{
  "success": true,
  "data": {
    "units": [
      {
        "id": 1,
        "name": "Kg"
      },
      {
        "id": 2,
        "name": "Lít"
      },
      {
        "id": 3,
        "name": "Gói"
      }
    ]
  }
}
```

---

## 🍎 Food APIs (User)

### 1. Create Food
**POST** `/food`

**Headers:**
```
Authorization: Bearer {token}
Content-Type: multipart/form-data
```

**Body (Form-data):**
- `name`: Cà chua (text)
- `categoryId`: 1 (text)
- `unitId`: 1 (text)
- `description`: Cà chua tươi (text)
- `image`: tomato.jpg (file)

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Cà chua",
    "categoryId": 1,
    "categoryName": "Rau củ",
    "unitId": 1,
    "unitName": "Kg",
    "description": "Cà chua tươi",
    "imageUrl": "https://cloudinary.com/tomato.jpg",
    "createdBy": 1,
    "isSystemFood": false
  }
}
```

---

### 2. Get All Foods
**GET** `/food?categoryId={categoryId}&name={name}`

**Headers:**
```
Authorization: Bearer {token}
```

**Query Params:**
- `categoryId` (optional): Filter by category
- `name` (optional): Search by name

**Response:**
```json
{
  "success": true,
  "data": {
    "foods": [
      {
        "id": 1,
        "name": "Cà chua",
        "categoryName": "Rau củ",
        "unitName": "Kg",
        "imageUrl": "https://cloudinary.com/tomato.jpg",
        "isSystemFood": false
      }
    ]
  }
}
```

---

### 3. Get Food By ID
**GET** `/food/{foodId}`

**Headers:**
```
Authorization: Bearer {token}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Cà chua",
    "categoryId": 1,
    "categoryName": "Rau củ",
    "unitId": 1,
    "unitName": "Kg",
    "description": "Cà chua tươi",
    "imageUrl": "https://cloudinary.com/tomato.jpg"
  }
}
```

---

### 4. Update Food
**PUT** `/food/{foodId}`

**Headers:**
```
Authorization: Bearer {token}
Content-Type: multipart/form-data
```

**Body (Form-data):**
- `name`: Cà chua cherry (text)
- `categoryId`: 1 (text)
- `unitId`: 1 (text)
- `description`: Cà chua cherry nhập khẩu (text)
- `image`: cherry-tomato.jpg (file, optional)

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Cà chua cherry",
    "description": "Cà chua cherry nhập khẩu",
    "imageUrl": "https://cloudinary.com/cherry-tomato.jpg"
  }
}
```

---

### 5. Delete Food
**DELETE** `/food/{foodId}`

**Headers:**
```
Authorization: Bearer {token}
```

**Response:**
```json
{
  "success": true,
  "data": null
}
```

---

## 🔧 Admin Food APIs

**Note:** All admin APIs require `ADMIN` role.

### 1. Create System Food
**POST** `/admin/food`

**Headers:**
```
Authorization: Bearer {admin_token}
Content-Type: multipart/form-data
```

**Body (Form-data):**
- `name`: Gạo ST25 (text)
- `categoryId`: 5 (text)
- `unitId`: 1 (text)
- `description`: Gạo ST25 cao cấp (text)
- `image`: rice.jpg (file)

**Response:**
```json
{
  "success": true,
  "message": "Tạo thực phẩm hệ thống thành công",
  "data": {
    "id": 100,
    "name": "Gạo ST25",
    "categoryName": "Lương thực",
    "unitName": "Kg",
    "imageUrl": "https://cloudinary.com/rice.jpg",
    "isSystemFood": true
  }
}
```

---

### 2. Get System Foods
**GET** `/admin/food?categoryId={categoryId}&name={name}`

**Headers:**
```
Authorization: Bearer {admin_token}
```

**Query Params:**
- `categoryId` (optional)
- `name` (optional)

**Response:**
```json
{
  "success": true,
  "data": {
    "foods": [
      {
        "id": 100,
        "name": "Gạo ST25",
        "categoryName": "Lương thực",
        "imageUrl": "https://cloudinary.com/rice.jpg",
        "isSystemFood": true
      }
    ]
  }
}
```

---

### 3. Get System Food By ID
**GET** `/admin/food/{foodId}`

**Headers:**
```
Authorization: Bearer {admin_token}
```

---

### 4. Update System Food
**PUT** `/admin/food/{foodId}`

**Headers:**
```
Authorization: Bearer {admin_token}
Content-Type: multipart/form-data
```

**Body (Form-data):**
- Same as create

**Response:**
```json
{
  "success": true,
  "message": "Cập nhật thực phẩm hệ thống thành công",
  "data": { ... }
}
```

---

### 5. Delete System Food
**DELETE** `/admin/food/{foodId}`

**Headers:**
```
Authorization: Bearer {admin_token}
```

**Response:**
```json
{
  "success": true,
  "message": "Xóa thực phẩm hệ thống thành công",
  "data": null
}
```

---

## 👥 Group APIs

### 1. Create Group
**POST** `/group`

**Headers:**
```
Authorization: Bearer {token}
```

**Body (JSON):**
```json
{
  "name": "Gia đình tôi"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Gia đình tôi",
    "ownerId": 1,
    "members": [
      {
        "id": 1,
        "name": "John Doe",
        "email": "user@example.com"
      }
    ],
    "createdAt": "2026-01-02T23:00:00"
  }
}
```

---

### 2. Get My Group
**GET** `/group`

**Headers:**
```
Authorization: Bearer {token}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Gia đình tôi",
    "ownerId": 1,
    "members": [...]
  }
}
```

---

### 3. Add Member
**POST** `/group/add-member`

**Headers:**
```
Authorization: Bearer {token}
```

**Body (JSON):**
```json
{
  "email": "member@example.com"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Gia đình tôi",
    "members": [
      { "id": 1, "name": "John Doe" },
      { "id": 2, "name": "New Member" }
    ]
  }
}
```

---

### 4. Remove Member
**POST** `/group/remove-member`

**Headers:**
```
Authorization: Bearer {token}
```

**Body (JSON):**
```json
{
  "memberId": 2
}
```

**Response:**
```json
{
  "success": true,
  "data": null
}
```

---

### 5. Leave Group
**POST** `/group/leave`

**Headers:**
```
Authorization: Bearer {token}
```

**Response:**
```json
{
  "success": true,
  "data": null
}
```

---

## 🔔 Notification APIs

### 1. Get Notifications
**GET** `/notification`

**Headers:**
```
Authorization: Bearer {token}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "notifications": [
      {
        "id": 1,
        "title": "Thông báo mới",
        "content": "Bạn có thông báo mới",
        "isRead": false,
        "createdAt": "2026-01-02T23:00:00"
      }
    ]
  }
}
```

---

### 2. Mark Notification As Read
**POST** `/notification/{notificationId}/read`

**Headers:**
```
Authorization: Bearer {token}
```

**Response:**
```json
{
  "success": true,
  "data": null
}
```

---

### 3. Mark All As Read
**POST** `/notification/read-all`

**Headers:**
```
Authorization: Bearer {token}
```

**Response:**
```json
{
  "success": true,
  "data": null
}
```

---

### 4. Delete Notification
**DELETE** `/notification/{notificationId}`

**Headers:**
```
Authorization: Bearer {token}
```

**Response:**
```json
{
  "success": true,
  "data": null
}
```

---

## 🧪 Testing Flow

### 1. Basic Flow
```
1. Register user → Get token
2. Login → Get token
3. Verify email → Use verification code from email
4. Get profile
5. Update profile
```

### 2. Food Management Flow
```
1. Get all categories
2. Get all units
3. Create food
4. Get foods list
5. Update food
6. Delete food
```

### 3. Admin Flow
```
1. Login with admin account
2. Create system food
3. Get system foods
4. Update system food
5. Delete system food
```

### 4. Group Flow
```
1. Create group
2. Get my group
3. Add member by email
4. Remove member
```

---

## 📝 Notes

1. **Authorization Header**: Tất cả APIs (trừ register/login) đều cần token trong header:
   ```
   Authorization: Bearer {your_token}
   ```

2. **File Upload**: Khi upload file, dùng `Content-Type: multipart/form-data`

3. **Error Response Format**:
   ```json
   {
     "success": false,
     "message": "Error message",
     "data": null
   }
   ```

4. **Admin Account**: Cần tạo admin account với role `ADMIN` để test admin APIs

5. **Environment Variables**: Đảm bảo đã cấu hình:
   - Database connection
   - Cloudinary credentials (cho upload ảnh)
   - Email service (cho verification code)

---

## 🚀 Quick Start with cURL

### Register
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test@123",
    "name": "Test User",
    "phone": "0123456789"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test@123"
  }'
```

### Get Profile
```bash
curl -X GET http://localhost:8080/api/v1/user/profile \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## 📦 Postman Collection

Bạn có thể import tất cả APIs này vào Postman để test dễ dàng hơn. Tạo collection với các requests như trên và lưu token vào environment variable để sử dụng cho các requests tiếp theo.
