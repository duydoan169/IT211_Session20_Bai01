Access Token dùng để gọi API và có thời gian sống ngắn, thường chỉ vài phút.
Token này chứa quyền truy cập nên nếu bị lộ thì rủi ro chỉ tồn
tại trong thời gian ngắn trước khi token hết hạn.

Refresh Token không dùng để gọi API mà chỉ dùng để cấp Access Token mới.
Token này có thời gian sống dài hơn nhiều nên nếu bị lộ thì mức độ nguy
hiểm cao hơn vì có thể tạo ra Access Token mới liên tục.

Access Token được thiết kế ngắn hạn để tăng bảo mật,
còn Refresh Token giúp tránh việc đăng nhập lại thường xuyên.
Khi đăng xuất, đổi mật khẩu hoặc phát hiện bất thường, Refresh Token cần bị vô hiệu hóa ngay.

Access Token nên được lưu trong bộ nhớ tạm của ứng dụng,
còn Refresh Token nên lưu trong cookie bảo mật.
Không nên lưu Refresh Token trong localStorage vì dễ bị đánh cắp.

Việc kiểm tra trạng thái token trong database cho phép thu hồi token
ngay cả khi chữ ký JWT vẫn hợp lệ. Cơ chế này giúp giảm rủi ro khi token
bị lộ và hỗ trợ đăng xuất cưỡng chế.