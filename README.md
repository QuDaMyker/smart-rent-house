
# Smart Rent House

Đồ án môn học **Nhập môn ứng dụng di động - SE114.N21**, trường Đại học Công nghệ Thông tin - Đại học Quốc gia Thành phố Hồ Chí Minh.


## Tác giả

- Giáo viên hướng dẫn: **Nguyễn Tấn Toàn**, , giảng viên khoa **Công Nghệ Phần Mềm**, trường Đại học Công nghệ Thông tin, Đại học Quốc gia Thành phố Hồ Chí Minh.

| STT | MSSV     | Họ và tên                                                  | Lớp      | 
| --- | -------- | ---------------------------------------------------------- | -------- | 
| 1   | 21520082 | [Lê Bảo Như](https://github.com/nhubaole)          | KTPM2021 | 
| 2   | 21522423 | [Huỳnh Ngọc Ý Nhi](https://github.com/Nhongnhong-0101)             | KTPM2021 | 
| 4   | 21520003 | [Hà Mai Anh](https://github.com/AnhHa03) | KTPM2021 | 
| 3   | 21520238 | [Nguyễn Cao Hoài](https://github.com/Kaowai) | KTPM2021 | 
| 4   | 21520178 | [Phạm Quốc Danh](https://github.com/QuDaMyker)          | KTPM2021 | 


## Mô tả

- **Smart Rent House** là một ứng dụng di động dành cho người dùng muốn tìm kiếm và thuê nhà hoặc căn hộ một cách thuận tiện. Ứng dụng cung cấp các tính năng hữu ích giúp người dùng tìm kiếm và xem thông tin về các căn nhà, gửi yêu cầu thuê, tương tác với chủ sở hữu và thực hiện các giao dịch liên quan đến thuê nhà.



## Người dùng
- Chủ nhà có nhu cầu đăng cho thuê
- Khách hàng có nhu cho đi tìm nhà để thuê
- Quản trị viên quản lí người dùng, phòng trọ
## Mục tiêu
### Ứng dụng thực tế:
- Ứng dụng giúp người cho thuê nhà, trọ, căn hộ một cách hiệu quả. Chủ nhà có thể tạo, cập nhật, xóa và theo dõi thông tin về căn sản phẩm họ đã đăng một cách chi tiết tiết bao gồm địa chỉ, giá cả, mô tả phòng trọ, thông tin liên hệ.
- Ứng dụng giúp người cần tìm nhà, trọ, căn hộ thông qua các thông tin cơ bản như địa chỉ, diện tích, tiện ích của sản phẩm, được đề xuất các sản phẩm ở vị trí hiện tại (GPS).
- Ứng dụng giúp quản trị viên thống kê lượng truy cập, số lượng người dùng, phê duyệt phòng trọ, quản lí người dùng.

### Yêu cầu ứng dụng:
*	Giao diện trực quan, đơn giản nhưng không nhàm chán, thân thiện với người dùng.
*	Phân quyền rõ ràng, tăng tính bảo mật dữ liệu.
*	Quản lý thông tin hợp lý, dễ dàng truy xuất, chỉnh sửa thông tin.
*	Tìm kiếm và lọc dữ liệu nhanh chóng.
*	Thông báo và nhắc nhở những thông tin quan trọng.
*	Báo cáo, thống kê.
*	Dễ dàng sửa chữa, phát triển các tính năng trong tương lai.
## Chức năng
<details>
  <summary>Người dùng</summary>
  
  - Đăng nhập theo phần quyền người dùng thông qua Google hoặc đăng kí tài khoản thông qua email và mật khẩu.
  - Tạo tin cho thuê nhà, căn hộ, phòng trọ mới
  - Tìm kiếm phòng trọ theo khu vực
  - Nhắn tin, hỏi đáp, thực hiện cuộc gọi với chủ nhà.
  - Báo cáo lỗi ứng dụng.
  - Thay đổi thông tin cá nhân.
  - Quan sát vị trí phòng trọ thông qua Google Map được tích hợp sẵn.
</details>
<details>
  <summary>Quản trị viên</summary>
  
  - Đăng nhập theo phần quyền quản trị viên.
  - Giám sát thông kê dữ liệu.
  - Phê duyệt, từ chối bài đăng.
  - Quản lí người dùng, khóa/ mở khóa tài khoản.
</details>
 
## Công nghệ
- Ngôn ngữ: Java
- IDE: Android Studio Flamingo | 2022.2.1 Patch 1 hoặc các phiên bản cao hơn
- Database: Real-time Database Firebase
- Storage Cloud: Storage Firebase
- Xác thực tài khoản: Authentication Firebase
- Công cụ quản lí: Github, Git
## Hướng dẫn cài đặt

[Cài đặt thông qua của hàng ứng dụng Google]()

[Cài đặt thông qua file APK](https://drive.google.com/drive/folders/1sqYQaI4tuwSryxaeNmVJDI5hGH190OTQ?usp=sharing) 
## Demo

[Demo người dùng](https://www.figma.com/proto/j72g4d8wd3Lpg1OHMvnLRa/SE114-Project-UI?node-id=3-3456&scaling=scale-down&page-id=3%3A3456&starting-point-node-id=6%3A63&prev-org-id=external-teams) 

[Demo quản trị vien](https://www.figma.com/proto/j72g4d8wd3Lpg1OHMvnLRa/SE114-Project-UI?node-id=1083-10659&scaling=scale-down&page-id=1083%3A10659&starting-point-node-id=1120%3A10635&prev-org-id=external-teams)


## Phản hồi
- Mọi phản hồi của các bạn sau khi trải nghiệm ứng dụng hãy tạo ở mục Issues. Phản hồi của các bạn sẽ là động lực và lời khuyên quý giá giúp chúng tôi cải thiện ứng dụng tốt hơn. Cảm ơn các bạn đã dành thời gian trải nghiệm ứng dụng của chúng tôi.