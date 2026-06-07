Access Token dùng để gọi API. Nó có thời gian sống ngắn (thường vài phút) và chứa quyền truy cập của người dùng. Nếu bị lộ thì rủi ro không quá lớn vì token sẽ hết hạn nhanh.

Refresh Token không dùng để gọi API, mà chỉ để xin Access Token mới khi token cũ hết hạn. Nó có thời gian sống dài hơn nhiều (vài ngày hoặc vài tuần), nên nếu bị lộ thì kẻ xấu có thể xin token mới liên tục. Vì vậy Refresh Token nguy hiểm hơn Access Token.

Trong thực tế, Access Token ngắn hạn để đảm bảo an toàn, còn Refresh Token giúp người dùng không phải đăng nhập lại quá thường xuyên. Khi người dùng logout, đổi mật khẩu hoặc có dấu hiệu bất thường, Refresh Token cần bị vô hiệu hóa ngay.

Về lưu trữ phía client:

Access Token nên giữ trong bộ nhớ (memory).
Refresh Token nên lưu trong cookie HttpOnly và Secure.

Cách này giúp giảm rủi ro XSS và CSRF. Không nên lưu Refresh Token trong localStorage vì rất dễ bị đánh cắp.