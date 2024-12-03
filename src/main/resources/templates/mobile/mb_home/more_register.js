import React, { useState } from "react";
import axios from "axios";
import "./SignUpForm.css";

function SignUpForm() {
const [formData, setFormData] = useState({
nicname: "",
mbti: "",
favorite: {
activities: [],
healing: [],
food: [],
hobbies: [],
},
birthdate: "",
gender: "",
});

const [nicknameAvailable, setNicknameAvailable] = useState(null); // 닉네임 중복 확인 결과

const minDate = "1950-01-01";
const maxDate = "2024-12-31";

const handleChange = (e) => {
const { name, value, type, checked, dataset } = e.target;

if (name === "birthdate") {
// 날짜 유효성 검증
if (value < minDate || value > maxDate) {
alert("올바른 날짜를 입력해주세요 (1950-01-01 ~ 2024-12-31)");
return; // 유효하지 않은 값은 무시
}
}

if (dataset.group) {
// favorite 그룹에 속한 체크박스 처리
const group = dataset.group;
setFormData((prevData) => ({
...prevData,
favorite: {
...prevData.favorite,
[group]: checked
? [...prevData.favorite[group], value]
: prevData.favorite[group].filter((item) => item !== value),
},
}));
} else if (type === "checkbox") {
setFormData((prevData) => ({
...prevData,
[name]: checked,
}));
} else {
setFormData((prevData) => ({
...prevData,
[name]: value,
}));
}
};

// 닉네임 중복 확인 요청
const checkNickname = async () => {
if (!formData.nicname.trim()) {
alert("닉네임을 입력해주세요.");
return;
}

try {
const response = await axios.get("http://127.0.0.1:5500/api/check-nickname", {
params: { nickname: formData.nicname },
});
setNicknameAvailable(response.data.available);
alert(response.data.available ? "사용 가능한 닉네임입니다." : "이미 사용 중인 닉네임입니다.");
} catch (error) {
console.error("닉네임 중복 확인 중 오류 발생:", error);
alert("닉네임 확인 중 오류가 발생했습니다. 다시 시도해주세요.");
}
};

// 회원가입 데이터 전송
const handleSubmit = async (e) => {
e.preventDefault();

try {
const response = await axios.post("http://127.0.0.1:5500/api/signup", formData);
if (response.data.success) {
alert("회원가입이 완료되었습니다!");
} else {
alert("회원가입 실패: " + response.data.message);
}
} catch (error) {
console.error("회원가입 요청 중 오류 발생:", error);
alert("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
}
};

return (
<form className="signup-form" onSubmit={handleSubmit}>
    <h1 className="signup-title">CoseCose 회원가입</h1>

    {/* 닉네임 */}
    <div className="form-group">
        <label htmlFor="nicname">닉네임</label>
        <input
                type="text"
                id="nicname"
                name="nicname"
                value={formData.nicname}
                onChange={handleChange}
                placeholder="닉네임을 입력해주세요"
                required
        />
        <button type="button" className="check-button" onClick={checkNickname}>
            중복확인
        </button>
        {nicknameAvailable !== null && (
        <span className="nickname-status">
                        {nicknameAvailable ? "✔ 사용 가능" : "❌ 사용 불가능"}
                    </span>
        )}
    </div>

    {/* MBTI */}
    <div className="form-group">
        <label htmlFor="mbti">MBTI</label>
        <select
                id="mbti"
                name="mbti"
                value={formData.mbti}
                onChange={handleChange}
                required
        >
            <option value="">MBTI를 선택해주세요</option>
            {["ISTJ", "ISFJ", "INFJ", "INTJ", "ISTP", "ISFP", "INFP", "INTP", "ESTP", "ESFP", "ENFP", "ENTP", "ESTJ", "ESFJ", "ENFJ", "ENTJ"].map((mbti) => (
            <option key={mbti} value={mbti}>
                {mbti}
            </option>
            ))}
        </select>
    </div>

    {/* 액티비티 */}
    <div className="form-group">
        <label>액티비티</label>
        <div className="form-options">
            {["헬스", "자전거", "볼링", "야구", "클라이밍"].map((activity) => (
            <label key={activity} className="checkbox-label">
                <input
                        type="checkbox"
                        name="activities"
                        value={activity}
                        onChange={handleChange}
                        data-group="activities"
                />
                {activity}
            </label>
            ))}
        </div>
    </div>

    {/* 힐링 */}
    <div className="form-group">
        <label>힐링</label>
        <div className="form-options">
            {["바다", "계곡", "산책", "꽃구경", "드라이브"].map((healing) => (
            <label key={healing} className="checkbox-label">
                <input
                        type="checkbox"
                        name="healing"
                        value={healing}
                        onChange={handleChange}
                        data-group="healing"
                />
                {healing}
            </label>
            ))}
        </div>
    </div>

    {/* 음식 */}
    <div className="form-group">
        <label>음식</label>
        <div className="form-options">
            {["한식", "중식", "양식", "일식", "디저트"].map((food) => (
            <label key={food} className="checkbox-label">
                <input
                        type="checkbox"
                        name="food"
                        value={food}
                        onChange={handleChange}
                        data-group="food"
                />
                {food}
            </label>
            ))}
        </div>
    </div>

    {/* 취미 */}
    <div className="form-group">
        <label>취미</label>
        <div className="form-options">
            {["게임", "드로잉", "글램핑", "독서", "음악"].map((hobby) => (
            <label key={hobby} className="checkbox-label">
                <input
                        type="checkbox"
                        name="hobbies"
                        value={hobby}
                        onChange={handleChange}
                        data-group="hobbies"
                />
                {hobby}
            </label>
            ))}
        </div>
    </div>

    {/* 생년월일 */}
    <div className="form-group">
        <label htmlFor="birthdate">생년월일</label>
        <input
                type="date"
                id="birthdate"
                name="birthdate"
                value={formData.birthdate}
                onChange={handleChange}
                min={minDate}
                max={maxDate}
                required
        />
    </div>

    {/* 성별 */}
    <div className="form-group">
        <label>성별</label>
        <label className="radio-label">
            <input
                    type="radio"
                    name="gender"
                    value="남성"
                    onChange={handleChange}
            />
            남성
        </label>
        <label className="radio-label">
            <input
                    type="radio"
                    name="gender"
                    value="여성"
                    onChange={handleChange}
            />
            여성
        </label>
    </div>

    <button type="submit" className="submit-button">회원가입 완료하기</button>
</form>
);
}

export default SignUpForm;
import React, { useState } from "react";
import axios from "axios";
import "/static/mb_home_css/more_register.css";

function SignUpForm() {
const [formData, setFormData] = useState({
nicname: "",
mbti: "",
favorite: {
activities: [],
healing: [],
food: [],
hobbies: [],
},
birthdate: "",
gender: "",
});

const [nicknameAvailable, setNicknameAvailable] = useState(null); // 닉네임 중복 확인 결과

const minDate = "1950-01-01";
const maxDate = "2024-12-31";

const handleChange = (e) => {
const { name, value, type, checked, dataset } = e.target;

if (name === "birthdate") {
// 날짜 유효성 검증
if (value < minDate || value > maxDate) {
alert("올바른 날짜를 입력해주세요 (1950-01-01 ~ 2024-12-31)");
return; // 유효하지 않은 값은 무시
}
}

if (dataset.group) {
// favorite 그룹에 속한 체크박스 처리
const group = dataset.group;
setFormData((prevData) => ({
...prevData,
favorite: {
...prevData.favorite,
[group]: checked
? [...prevData.favorite[group], value]
: prevData.favorite[group].filter((item) => item !== value),
},
}));
} else if (type === "checkbox") {
setFormData((prevData) => ({
...prevData,
[name]: checked,
}));
} else {
setFormData((prevData) => ({
...prevData,
[name]: value,
}));
}
};

// 닉네임 중복 확인 요청
const checkNickname = async () => {
if (!formData.nicname.trim()) {
alert("닉네임을 입력해주세요.");
return;
}

try {
const response = await axios.get("http://127.0.0.1:5500/api/check-nickname", {
params: { nickname: formData.nicname },
});
setNicknameAvailable(response.data.available);
alert(response.data.available ? "사용 가능한 닉네임입니다." : "이미 사용 중인 닉네임입니다.");
} catch (error) {
console.error("닉네임 중복 확인 중 오류 발생:", error);
alert("닉네임 확인 중 오류가 발생했습니다. 다시 시도해주세요.");
}
};

// 회원가입 데이터 전송
const handleSubmit = async (e) => {
e.preventDefault();

try {
const response = await axios.post("http://127.0.0.1:5500/api/signup", formData);
if (response.data.success) {
alert("회원가입이 완료되었습니다!");
} else {
alert("회원가입 실패: " + response.data.message);
}
} catch (error) {
console.error("회원가입 요청 중 오류 발생:", error);
alert("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
}
};

return (
<form className="signup-form" onSubmit={handleSubmit}>
    <h1 className="signup-title">CoseCose 회원가입</h1>

    {/* 닉네임 */}
    <div className="form-group">
        <label htmlFor="nicname">닉네임</label>
        <input
                type="text"
                id="nicname"
                name="nicname"
                value={formData.nicname}
                onChange={handleChange}
                placeholder="닉네임을 입력해주세요"
                required
        />
        <button type="button" className="check-button" onClick={checkNickname}>
            중복확인
        </button>
        {nicknameAvailable !== null && (
        <span className="nickname-status">
                        {nicknameAvailable ? "✔ 사용 가능" : "❌ 사용 불가능"}
                    </span>
        )}
    </div>

    {/* MBTI */}
    <div className="form-group">
        <label htmlFor="mbti">MBTI</label>
        <select
                id="mbti"
                name="mbti"
                value={formData.mbti}
                onChange={handleChange}
                required
        >
            <option value="">MBTI를 선택해주세요</option>
            {["ISTJ", "ISFJ", "INFJ", "INTJ", "ISTP", "ISFP", "INFP", "INTP", "ESTP", "ESFP", "ENFP", "ENTP", "ESTJ", "ESFJ", "ENFJ", "ENTJ"].map((mbti) => (
            <option key={mbti} value={mbti}>
                {mbti}
            </option>
            ))}
        </select>
    </div>

    {/* 액티비티 */}
    <div className="form-group">
        <label>액티비티</label>
        <div className="form-options">
            {["헬스", "자전거", "볼링", "야구", "클라이밍"].map((activity) => (
            <label key={activity} className="checkbox-label">
                <input
                        type="checkbox"
                        name="activities"
                        value={activity}
                        onChange={handleChange}
                        data-group="activities"
                />
                {activity}
            </label>
            ))}
        </div>
    </div>

    {/* 힐링 */}
    <div className="form-group">
        <label>힐링</label>
        <div className="form-options">
            {["바다", "계곡", "산책", "꽃구경", "드라이브"].map((healing) => (
            <label key={healing} className="checkbox-label">
                <input
                        type="checkbox"
                        name="healing"
                        value={healing}
                        onChange={handleChange}
                        data-group="healing"
                />
                {healing}
            </label>
            ))}
        </div>
    </div>

    {/* 음식 */}
    <div className="form-group">
        <label>음식</label>
        <div className="form-options">
            {["한식", "중식", "양식", "일식", "디저트"].map((food) => (
            <label key={food} className="checkbox-label">
                <input
                        type="checkbox"
                        name="food"
                        value={food}
                        onChange={handleChange}
                        data-group="food"
                />
                {food}
            </label>
            ))}
        </div>
    </div>

    {/* 취미 */}
    <div className="form-group">
        <label>취미</label>
        <div className="form-options">
            {["게임", "드로잉", "글램핑", "독서", "음악"].map((hobby) => (
            <label key={hobby} className="checkbox-label">
                <input
                        type="checkbox"
                        name="hobbies"
                        value={hobby}
                        onChange={handleChange}
                        data-group="hobbies"
                />
                {hobby}
            </label>
            ))}
        </div>
    </div>

    {/* 생년월일 */}
    <div className="form-group">
        <label htmlFor="birthdate">생년월일</label>
        <input
                type="date"
                id="birthdate"
                name="birthdate"
                value={formData.birthdate}
                onChange={handleChange}
                min={minDate}
                max={maxDate}
                required
        />
    </div>

    {/* 성별 */}
    <div className="form-group">
        <label>성별</label>
        <label className="radio-label">
            <input
                    type="radio"
                    name="gender"
                    value="남성"
                    onChange={handleChange}
            />
            남성
        </label>
        <label className="radio-label">
            <input
                    type="radio"
                    name="gender"
                    value="여성"
                    onChange={handleChange}
            />
            여성
        </label>
    </div>

    <button type="submit" className="submit-button">회원가입 완료하기</button>
</form>
);
}

export default SignUpForm;
