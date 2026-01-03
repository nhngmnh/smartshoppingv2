# FreshFridge API Testing Guide - NEW FEATURES ONLY ⭐

Base URL: `http://localhost:8080/api/v1`

> 📌 **Tài liệu này chỉ chứa CÁC APIs MỚI: Kitchen, Shopping và Recipe**
> 
> 👉 Xem file `API_TESTING_GUIDE.md` cho các APIs cơ bản (Auth, User, Food, Group...)

---

## 📋 Table of Contents
1. [Kitchen Management APIs](#kitchen-management-apis) 🍳
2. [Shopping List APIs](#shopping-list-apis) 🛒
3. [Recipe APIs](#recipe-apis) 📖
4. [Complete Testing Flows](#complete-testing-flows)

---

## 🍳 Kitchen Management APIs ⭐ NEW

### 1. Create Kitchen Item
**POST** `/kitchen`

Headers: `Authorization: Bearer {token}`

**Request:**
```json
{
  "foodId": 1,
  "quantity": 3,
  "expiryDate": "2026-01-10",
  "location": "Tủ lạnh",
  "notes": "Mua ở siêu thị"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Thêm thực phẩm vào bếp thành công",
  "data": {
    "id": 1,
    "foodId": 1,
    "foodName": "Cà chua",
    "quantity": 2.5,
    "unitName": "Kg",
    "expiryDate": "2026-01-10",
    "location": "Tủ lạnh",
    "notes": "Mua ở siêu thị",
    "createdAt": "2026-01-03T10:00:00"
  }
}
```

---

### 2. Get Kitchen Items
**GET** `/kitchen?foodId={id}&foodName={name}&expiryBefore={date}`

Headers: `Authorization: Bearer {token}`

**Query Params:**
- `foodId` (optional): Filter by food
- `foodName` (optional): Search by food name
- `expiryBefore` (optional): Filter items expiring before date (format: YYYY-MM-DD)

**Response:**
```json
{
  "success": true,
  "data": {
    "items": [
      {
        "id": 1,
        "foodId": 1,
        "foodName": "Cà chua",
        "quantity": 2.5,
        "unitName": "Kg",
        "expiryDate": "2026-01-10",
        "location": "Tủ lạnh"
      }
    ]
  }
}
```

---

### 3. Get Kitchen Item By ID
**GET** `/kitchen/{itemId}`

Headers: `Authorization: Bearer {token}`

---

### 4. Get Expiring Items
**GET** `/kitchen/expiring?days={days}`

Headers: `Authorization: Bearer {token}`

**Query Params:**
- `days` (default: 3): Number of days to check

**Example:** `/kitchen/expiring?days=7` - Get items expiring in next 7 days

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "foodName": "Cà chua",
      "quantity": 2.5,
      "expiryDate": "2026-01-05",
      "daysUntilExpiry": 2
    }
  ]
}
```

---

### 5. Update Kitchen Item
**PUT** `/kitchen/{itemId}`

Headers: `Authorization: Bearer {token}`

**Request:**
```json
{
  "quantity": 1.5,
  "expiryDate": "2026-01-12",
  "location": "Ngăn mát",
  "notes": "Đã dùng một phần"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Cập nhật thành công",
  "data": { ... }
}
```

---

### 6. Delete Kitchen Item
**DELETE** `/kitchen/{itemId}`

Headers: `Authorization: Bearer {token}`

**Response:**
```json
{
  "success": true,
  "message": "Xóa thành công",
  "data": null
}
```

---

## 🛒 Shopping List APIs ⭐ NEW

### 1. Create Shopping List
**POST** `/shopping/lists`

Headers: `Authorization: Bearer {token}`

**Request:**
```json
{
  "name": "Mua sắm tuần này",
  "scheduledDate": "2026-01-05",
  "assignedToId": 2,
  "notes": "Nhớ mua thêm rau"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Tạo danh sách mua sắm thành công",
  "data": {
    "id": 1,
    "name": "Mua sắm tuần này",
    "status": "PENDING",
    "scheduledDate": "2026-01-05",
    "assignedTo": {
      "id": 2,
      "name": "Jane Doe"
    },
    "createdBy": {
      "id": 1,
      "name": "John Doe"
    },
    "items": [],
    "totalItems": 0,
    "purchasedItems": 0,
    "createdAt": "2026-01-03T10:00:00"
  }
}
```

---

### 2. Get Shopping Lists
**GET** `/shopping/lists?status={status}&name={name}`

Headers: `Authorization: Bearer {token}`

**Query Params:**
- `status` (optional): PENDING, IN_PROGRESS, COMPLETED, CANCELLED
- `name` (optional): Search by name

**Response:**
```json
{
  "success": true,
  "message": "Lấy danh sách mua sắm thành công",
  "data": {
    "lists": [
      {
        "id": 1,
        "name": "Mua sắm tuần này",
        "status": "PENDING",
        "scheduledDate": "2026-01-05",
        "totalItems": 5,
        "purchasedItems": 0
      }
    ]
  }
}
```

---

### 3. Get Shopping List By ID
**GET** `/shopping/lists/{listId}`

Headers: `Authorization: Bearer {token}`

**Response:**
```json
{
  "success": true,
  "message": "Lấy chi tiết danh sách thành công",
  "data": {
    "id": 1,
    "name": "Mua sắm tuần này",
    "status": "PENDING",
    "items": [
      {
        "id": 1,
        "foodId": 1,
        "foodName": "Cà chua",
        "quantity": 2,
        "unitName": "Kg",
        "isPurchased": false,
        "notes": "Chọn quả chín"
      }
    ]
  }
}
```

---

### 4. Get My Assigned Lists
**GET** `/shopping/lists/my-assigned`

Headers: `Authorization: Bearer {token}`

**Description:** Lấy danh sách mua sắm được giao cho tôi

---

### 5. Update Shopping List
**PUT** `/shopping/lists/{listId}`

Headers: `Authorization: Bearer {token}`

**Request:**
```json
{
  "name": "Mua sắm cuối tuần",
  "scheduledDate": "2026-01-06",
  "assignedToId": 3,
  "notes": "Cập nhật ghi chú"
}
```

---

### 6. Delete Shopping List
**DELETE** `/shopping/lists/{listId}`

Headers: `Authorization: Bearer {token}`

---

### 7. Complete Shopping List
**POST** `/shopping/lists/{listId}/complete`

Headers: `Authorization: Bearer {token}`

**Response:**
```json
{
  "success": true,
  "message": "Hoàn thành danh sách mua sắm",
  "data": {
    "id": 1,
    "status": "COMPLETED",
    "completedAt": "2026-01-03T15:00:00"
  }
}
```

---

### 8. Add Items to Shopping List
**POST** `/shopping/lists/{listId}/items`

Headers: `Authorization: Bearer {token}`

**Request:**
```json
{
  "items": [
    {
      "foodId": 1,
      "quantity": 2,
      "notes": "Chọn quả chín"
    },
    {
      "foodId": 2,
      "quantity": 1.5,
      "notes": "Loại tươi"
    }
  ]
}
```

**Response:**
```json
{
  "success": true,
  "message": "Thêm mục thành công",
  "data": {
    "id": 1,
    "items": [...]
  }
}
```

---

### 9. Update Shopping Item
**PUT** `/shopping/items/{itemId}`

Headers: `Authorization: Bearer {token}`

**Request:**
```json
{
  "quantity": 3,
  "notes": "Đã sửa số lượng"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Cập nhật mục thành công",
  "data": {
    "id": 1,
    "foodId": 1,
    "foodName": "Cà chua",
    "quantity": 3,
    "notes": "Đã sửa số lượng"
  }
}
```

---

### 10. Delete Shopping Item
**DELETE** `/shopping/items/{itemId}`

Headers: `Authorization: Bearer {token}`

---

### 11. Mark Item as Purchased
**POST** `/shopping/items/{itemId}/purchased`

Headers: `Authorization: Bearer {token}`

**Request:**
```json
{
  "actualQuantity": 2,
  "actualPrice": 45000
}
```

**Response:**
```json
{
  "success": true,
  "message": "Đánh dấu đã mua thành công",
  "data": {
    "id": 1,
    "isPurchased": true,
    "actualQuantity": 2,
    "actualPrice": 45000,
    "purchasedAt": "2026-01-03T14:30:00"
  }
}
```

---

## 📖 Recipe APIs ⭐ NEW

### 1. Create Recipe
**POST** `/recipes`

Headers: 
- `Authorization: Bearer {token}`
- `Content-Type: application/json`

> ⚠️ **LƯU Ý QUAN TRỌNG VỀ HÌNH ẢNH:**
> - Endpoint này chỉ nhận **JSON**, không nhận **multipart/form-data**
> - Không thể upload file trực tiếp qua API này
> - Nếu muốn có hình ảnh: Upload lên Cloudinary trước → Lấy URL → Gửi URL trong JSON
> - Hoặc để `imageUrl` là `null` nếu không có hình

**Request:**
```json
{
  "name": "Canh chua cá",
  "description": "Món canh chua truyền thống",
  "instructions": "Bước 1: Rửa sạch nguyên liệu...\nBước 2: Nấu nước...",
  "servings": 4,
  "prepTime": 15,
  "cookTime": 30,
  "imageUrl": "https://res.cloudinary.com/your-cloud/image/upload/v123456/recipe.jpg"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Tạo công thức thành công",
  "data": {
    "id": 1,
    "name": "Canh chua cá",
    "description": "Món canh chua truyền thống",
    "instructions": "Bước 1: Rửa sạch...",
    "servings": 4,
    "prepTime": 15,
    "cookTime": 30,
    "totalTime": 45,
    "imageUrl": "https://...",
    "ingredients": [],
    "createdBy": {
      "id": 1,
      "name": "John Doe"
    },
    "createdAt": "2026-01-03T10:00:00"
  }
}
```

---

### 2. Get Recipes
**GET** `/recipes?name={name}`

Headers: `Authorization: Bearer {token}`

**Query Params:**
- `name` (optional): Search by recipe name

**Response:**
```json
{
  "success": true,
  "message": "Lấy danh sách công thức thành công",
  "data": [
    {
      "id": 1,
      "name": "Canh chua cá",
      "description": "Món canh chua truyền thống",
      "servings": 4,
      "totalTime": 45,
      "imageUrl": "https://..."
    }
  ]
}
```

---

### 3. Get Recipe By ID
**GET** `/recipes/{recipeId}`

Headers: `Authorization: Bearer {token}`

**Response:**
```json
{
  "success": true,
  "message": "Lấy công thức thành công",
  "data": {
    "id": 1,
    "name": "Canh chua cá",
    "instructions": "Bước 1...",
    "ingredients": [
      {
        "id": 1,
        "foodId": 5,
        "foodName": "Cá",
        "quantity": 0.5,
        "unitName": "Kg",
        "notes": "Cá tươi"
      },
      {
        "id": 2,
        "foodId": 10,
        "foodName": "Cà chua",
        "quantity": 3,
        "unitName": "Quả",
        "notes": "Cà chua chín"
      }
    ]
  }
}
```

---

### 4. Update Recipe
**PUT** `/recipes/{recipeId}`

Headers: `Authorization: Bearer {token}`

**Request:**
```json
{
  "name": "Canh chua cá bông lau",
  "description": "Công thức cải tiến",
  "instructions": "Bước mới...",
  "servings": 6,
  "prepTime": 20,
  "cookTime": 35
}
```

---

### 5. Delete Recipe
**DELETE** `/recipes/{recipeId}`

Headers: `Authorization: Bearer {token}`

**Response:**
```json
{
  "success": true,
  "message": "Xóa công thức thành công",
  "data": null
}
```

---

### 6. Add Ingredient to Recipe
**POST** `/recipes/{recipeId}/ingredients`

Headers: `Authorization: Bearer {token}`

**Request:**
```json
{
  "foodId": 15,
  "quantity": 2,
  "notes": "Thái nhỏ"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Thêm nguyên liệu thành công",
  "data": {
    "id": 1,
    "ingredients": [
      {
        "id": 3,
        "foodId": 15,
        "foodName": "Hành tây",
        "quantity": 2,
        "unitName": "Củ",
        "notes": "Thái nhỏ"
      }
    ]
  }
}
```

---

### 7. Update Ingredient
**PUT** `/recipes/ingredients/{ingredientId}`

Headers: `Authorization: Bearer {token}`

**Request:**
```json
{
  "quantity": 3,
  "notes": "Thái lát"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Cập nhật nguyên liệu thành công",
  "data": { ... }
}
```

---

### 8. Delete Ingredient
**DELETE** `/recipes/ingredients/{ingredientId}`

Headers: `Authorization: Bearer {token}`

**Response:**
```json
{
  "success": true,
  "message": "Xóa nguyên liệu thành công",
  "data": null
}
```

---

### 9. Find Recipes By Ingredient
**GET** `/recipes/by-ingredient/{foodId}`

Headers: `Authorization: Bearer {token}`

**Description:** Tìm các công thức có chứa nguyên liệu cụ thể

**Example:** `/recipes/by-ingredient/5` - Tìm công thức có cá

**Response:**
```json
{
  "success": true,
  "message": "Tìm công thức thành công",
  "data": [
    {
      "id": 1,
      "name": "Canh chua cá",
      "description": "...",
      "ingredients": [...]
    }
  ]
}
```

---

## 🧪 Complete Testing Flows

### Flow 1: Kitchen Management
```
1. Đăng nhập và lấy token (xem API_TESTING_GUIDE.md)
2. Lấy danh sách foods → GET /food
3. Thêm vào bếp → POST /kitchen
4. Xem tất cả items → GET /kitchen
5. Kiểm tra sắp hết hạn → GET /kitchen/expiring?days=7
6. Cập nhật số lượng → PUT /kitchen/{itemId}
7. Xóa item → DELETE /kitchen/{itemId}
```

### Flow 2: Shopping List Complete
```
1. Đăng nhập (xem API_TESTING_GUIDE.md)
2. Tạo danh sách → POST /shopping/lists
3. Thêm items → POST /shopping/lists/{listId}/items
4. Xem danh sách → GET /shopping/lists
5. Đi mua sắm:
   - Đánh dấu đã mua từng item → POST /shopping/items/{itemId}/purchased
   - Cập nhật số lượng thực tế → PUT /shopping/items/{itemId}
6. Hoàn thành → POST /shopping/lists/{listId}/complete
```

### Flow 3: Recipe Management Complete
```
1. Đăng nhập (xem API_TESTING_GUIDE.md)
2. Tạo công thức → POST /recipes
3. Thêm nhiều nguyên liệu:
   - POST /recipes/{recipeId}/ingredients (lặp lại)
4. Xem chi tiết công thức → GET /recipes/{recipeId}
5. Sửa công thức → PUT /recipes/{recipeId}
6. Cập nhật nguyên liệu → PUT /recipes/ingredients/{ingredientId}
7. Tìm công thức theo nguyên liệu có sẵn → GET /recipes/by-ingredient/{foodId}
```

### Flow 4: Tích hợp Kitchen + Shopping + Recipe
```
1. Kiểm tra bếp → GET /kitchen
2. Xem thực phẩm sắp hết hạn → GET /kitchen/expiring?days=3
3. Tạo recipe với nguyên liệu có sẵn → POST /recipes
4. Kiểm tra nguyên liệu thiếu
5. Tạo shopping list cho nguyên liệu thiếu → POST /shopping/lists
6. Thêm items cần mua → POST /shopping/lists/{listId}/items
7. Đi mua → Mark items as purchased
8. Hoàn thành shopping → POST /shopping/lists/{listId}/complete
9. Thêm items mới mua vào kitchen → POST /kitchen
```

---

## 📝 Important Notes

### Prerequisites
- Cần có **token** từ login (xem `API_TESTING_GUIDE.md` để register/login)
- Cần có **foodId** hợp lệ (tạo food trước hoặc dùng system food)
- Cần có **groupId** nếu làm việc nhóm

### Authentication
- Tất cả APIs đều yêu cầu: `Authorization: Bearer {token}`

### Date Format
- Sử dụng format: `YYYY-MM-DD`
- Ví dụ: `2026-01-03`, `2026-12-25`

### Shopping List Status
- `PENDING`: Mới tạo, chưa bắt đầu
- `IN_PROGRESS`: Đang thực hiện mua sắm
- `COMPLETED`: Đã hoàn thành
- `CANCELLED`: Đã hủy

### Kitchen Best Practices
- Luôn set `expiryDate` khi thêm thực phẩm
- Dùng endpoint `/kitchen/expiring` để kiểm tra định kỳ
- Cập nhật `quantity` khi sử dụng một phần

### Shopping Best Practices
- Assign danh sách cho thành viên group
- Mark từng item khi mua để theo dõi tiến độ
- Lưu `actualPrice` để quản lý chi tiêu

### Recipe Best Practices
- Thêm đầy đủ `prepTime` và `cookTime`
- Ghi rõ `servings` để tính khẩu phần
- Dùng `notes` trong ingredients để ghi chú cách chế biến

---

## 🚀 Quick Test with cURL

### Kitchen - Add Item
```bash
curl -X POST http://localhost:8080/api/v1/kitchen \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "foodId": 1,
    "quantity": 2.5,
    "expiryDate": "2026-01-10",
    "location": "Tủ lạnh"
  }'
```

### Shopping - Create List
```bash
curl -X POST http://localhost:8080/api/v1/shopping/lists \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Mua sắm tuần này",
    "scheduledDate": "2026-01-05"
  }'
```

### Recipe - Create
```bash
curl -X POST http://localhost:8080/api/v1/recipes \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Canh chua",
    "description": "Món canh truyền thống",
    "instructions": "Bước 1...",
    "servings": 4,
    "prepTime": 15,
    "cookTime": 30
  }'
```

---

## 🎯 Quick Testing Checklist

### Kitchen Module
- [ ] Thêm thực phẩm vào bếp
- [ ] Lọc theo food, tên, hạn sử dụng
- [ ] Kiểm tra items sắp hết hạn (3, 7 ngày)
- [ ] Cập nhật số lượng
- [ ] Xóa item

### Shopping Module
- [ ] Tạo shopping list
- [ ] Thêm nhiều items
- [ ] Giao việc cho thành viên
- [ ] Đánh dấu từng item đã mua
- [ ] Lưu giá thực tế
- [ ] Hoàn thành danh sách
- [ ] Xem danh sách được giao cho tôi

### Recipe Module
- [ ] Tạo công thức
- [ ] Thêm nguyên liệu
- [ ] Cập nhật nguyên liệu
- [ ] Tìm công thức theo ingredient
- [ ] Xóa nguyên liệu
- [ ] Cập nhật công thức

---

## 🔧 Common Errors & Solutions

### 1. HttpMediaTypeNotSupportedException: multipart/form-data not supported
**Error:**
```
Content-Type 'multipart/form-data' is not supported
```

**Nguyên nhân:** Bạn đang gửi file upload nhưng API chỉ nhận JSON

**Giải pháp:**
- ✅ **Đúng:** Gửi JSON với Content-Type: `application/json`
- ✅ Upload hình lên Cloudinary trước, rồi gửi URL
- ❌ **SAI:** Gửi multipart/form-data với file

**Ví dụ đúng (Postman/Insomnia):**
1. Chọn Body → **raw** → **JSON**
2. Gửi: `{"name": "...", "imageUrl": "https://cloudinary-url"}`

### 2. Shopping Item Not Found
**Error:** "Không tìm thấy mục trong danh sách"

**Nguyên nhân:** Dùng `foodId` thay vì `itemId`

**Giải pháp:**
1. GET `/shopping/lists/{listId}` trước
2. Copy `items[].id` từ response (đây là itemId)
3. POST `/shopping/items/{itemId}/purchased`

### 3. Database Schema Mismatch
**Error:** "function lower(bytea) does not exist"

**Giải pháp:**
1. Drop database: `DROP DATABASE smartshopping;`
2. Recreate: `CREATE DATABASE smartshopping;`
3. Restart app để Flyway tạo lại schema

---

**Last Updated:** January 3, 2026  
**APIs:** 26 endpoints mới  
**Modules:** Kitchen (6) + Shopping (11) + Recipe (9)
