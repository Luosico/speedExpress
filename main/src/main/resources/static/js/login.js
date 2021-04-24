let app = new Vue({
    el: "#app",
    data: {
        form: {
            username: "",
            password: "",
            phoneNumber: "",
            smsCode: "",
            canGetSmsCode: true,
        },
        phoneLogin: true,
        loginWay: "密码登录",
        timeRemaining: 60,
        timeRemain: "",
    },

    //在method对象中定义方法
    methods: {

        /**
         * 登录
         */
        login() {
            if (this.phoneLogin) {
                //手机号码登录
                if (this.checkForm()) {
                    this.phoneLoginWay();
                }
            } else {
                //账号密码登录
                if (this.checkForm()) {
                    this.namePwdLoginWay();
                }
            }
        },

        /**
         * 账号密码登录
         */
        namePwdLoginWay() {
            let data = new FormData();
            data.append("username", this.form.username);
            data.append("password", this.form.password);

            axios({
                url: "/login",
                method: "POST",
                headers: {
                    'Content-type': 'application/x-www-form-urlencoded',
                },
                //发送数据会使用 json 格式
                //注意，后台使用getParameter()获取不到参数，而Spring Security是使用这种方式的
                data: data,
                //responseType: "json", //默认为json
            }).then(function (response) {
                app.toMain(response.data, 'usernamePwd');
            }).catch(function (err) {
                //console.log(err.data);
            });
        },

        /**
         * 手机号码登录
         */
        phoneLoginWay() {
            let data = new FormData();
            data.append("phoneNumber", this.form.phoneNumber);
            data.append("smsCode", this.form.smsCode);

            axios({
                url: "/loginSmsCode",
                method: "POST",
                headers: {
                    'Content-type': 'application/x-www-form-urlencoded',
                },
                data: data,
            }).then(function (response) {
                app.toMain(response.data, 'phone');
            }).catch(function (err) {
                //console.log(err.data);
            });

        },

        /**
         * 改变登录方式
         */
        changeLoginWay() {
            let temp = this.phoneLogin;
            this.phoneLogin = !temp;
        },

        /**
         * 获取手机验证码
         */
        getSmsCode() {
            let phoneNumber = this.form.phoneNumber;
            if (!this.validatePhoneNumber(phoneNumber)) {
                return;
            }
            this.form.canGetSmsCode = false;
            this.timeRemain = setInterval(this.smsCodeCount, 1000);

            axios({
                url: "/smsCode",
                method: "GET",
                params: {
                    phoneNumber: phoneNumber,
                    type: "login",
                }
            });
        },

        /**
         * 短信验证码计时动作
         */
        smsCodeCount() {
            this.timeRemaining = this.timeRemaining - 1;
        },

        /**
         * 检查表单
         */
        checkForm() {
            //手机验证码登录
            if (this.phoneLogin) {
                if (this.validatePhoneNumber(this.form.phoneNumber)) {
                    if (/^\d{6}$/.test(this.form.smsCode)) {
                        return true
                    } else {
                        this.noticeMessage("验证码格式错误！");
                    }
                }
            } else {
                //账号密码登录
                let username = this.form.username.trim();
                let password = this.form.password.trim();
                if (username.length < 3 || password.length < 3) {
                    this.noticeMessage("账号或密码格式错误！")
                } else {
                    return true;
                }
            }

            return false;
        },

        /**
         * 忘记密码
         */
        forgetPwd() {
            window.location.href = "forgetPassword";
        },

        /**
         * 注册
         */
        registerUser() {
            window.location.href = "register";
        },

        /**
         * 消息提示
         */
        noticeMessage(message) {
            app.$message({
                showClose: true,
                message: message,
                type: 'warning',
                duration: 1500,
            });
        },

        /**
         * 校验手机号码
         */
        validatePhoneNumber(phoneNumber) {
            if (/^\d*$/.test(this.form.phoneNumber)) {
                if (phoneNumber.length < 6) {
                    this.noticeMessage("手机号码不少于6位！");
                } else {
                    return true;
                }
            } else {
                this.noticeMessage("手机号码格式不正确，请确认再输入！");
            }
            return false;
        },

        /**
         * 响应回车键
         */
        keyboardEvent(event) {
            //回车键
            if (event.key == 'Enter') {
                this.login();
            }

        },

        /**
         * 首页导航路径
         * @param data
         * @param way
         */
        toMain(data, way){
            if (data.status === "ok"){
                let role = data.role;
                if(role === "user"){
                    window.location = "http://localhost:8080/user/main";
                }else if (role === "courier"){
                    window.location = "http://localhost:8080/courier/main";
                }else if(role === "admin"){
                    window.location = "http://localhost:8080/admin/main";
                }else{
                    if (way==='usernamePwd'){
                        app.$message.error('账号或密码错误，请重试! ');
                    }else{
                        app.$message.error('验证码错误或用户未注册！');
                    }
                }
            }
        }
    },
    //计算属性
    computed: {},

    //侦听属性
    watch: {
        phoneLogin: function (val) {
            if (val === true) {
                this.loginWay = "密码登录";
            } else {
                this.loginWay = "手机登录";
            }
        },
        timeRemaining: function (val) {
            //console.log(val)
            //计时时间到
            if (val === 0) {
                this.timeRemaining = 60;
                this.form.canGetSmsCode = true;
                //重置计时
                clearInterval(this.timeRemain);
            }
        }
    }
});

