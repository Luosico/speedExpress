let forgetPassword = {
    data() {
        let validatePassword = (rule, value, callback) => {
            if (!value) {
                callback(new Error('密码不能为空'));
            } else if (value.length < 6) {
                callback(new Error("密码不能少于6位"))
            } else if (!/^[0-9a-zA-Z_]*$/.test(value)) {
                callback(new Error("只能由数字、字母、_(下划线) 组成"))
            }
            callback();
        };
        let validatePasswordRepeat = (rule, value, callback) => {
            if (!value) {
                callback(new Error('密码不能为空'));
            } else if (value.length < 6) {
                callback(new Error("密码不能少于6位"))
            } else if (!/^[0-9a-zA-Z_]*$/.test(value)) {
                callback(new Error("只能由数字、字母、_(下划线) 组成"))
            } else if (value !== this.form.password) {
                callback(new Error("两次输入的密码不一致"));
            }
            callback();
        };
        let validatePhoneNumber = (rule, value, callback) => {
            if (!value) {
                callback(new Error('手机号码不能为空'))
            } else if (!/^\d*$/.test(value)) {
                callback(new Error('手机号码格式不正确'))
            } else if (value.length < 6) {
                callback(new Error('不少于 6 位'))
            } /*else if (!this.isExit("phoneNumber", value)) {
                callback(new Error("该手机号码不存在 "))
            }*/
            callback();
        };
        let validateSmsCode = (rule, value, callback) => {
            if (!value) {
                callback(new Error('验证码不能为空'))
            } else if (!/^\d*$/.test(value)) {
                callback(new Error('验证码格式不正确'))
            } else if (!/^\d{6}$/.test(value)) {
                callback(new Error('验证码只能为 6 位'))
            }
            callback();
        }
        return {
            form: {
                phoneNumber: "",
                password: "",
                passwordRepeat: "",
                smsCode: "",
            },
            canGetSmsCode: true,
            timeRemaining: 60,
            timeRemain: "",
            rules: {
                password: [
                    {validator: validatePassword, trigger: 'blur'}
                ],
                passwordRepeat: [
                    {validator: validatePasswordRepeat, trigger: 'blur'}
                ],
                phoneNumber: [
                    {validator: validatePhoneNumber, trigger: 'blur'}
                ],
                smsCode: [
                    {validator: validateSmsCode, trigger: 'blur'}
                ],
            },
        };
    },
    methods: {
        /**
         * 返回到登录界面
         */
        toLogin() {
            window.location.href = "login";
        },

        /**
         * 检查属性是否存在
         * @param name 属性名
         * @param val 属性值
         */
        isExit(name, val) {
            let result = "";
            $.ajax({
                method: 'GET',
                url: '/isExit',
                data: {
                    name: name,
                    val: val,
                },
                async: false, //同步请求
                success: function (response) {
                    if (response.status === 'false') {
                        result = false;
                    } else {
                        result = true;
                    }
                },
                error: function (error) {
                    console.log(error)
                }
            })
            return result;
        },

        /**
         * 获取手机短信验证码
         */
        getSmsCode() {
            this.$refs.form.validateField('phoneNumber', (val) => {
                //phoneNumber is correct
                if (val === "") {
                    let phoneNumber = this.form.phoneNumber;
                    this.canGetSmsCode = false;
                    this.timeRemain = setInterval(this.smsCodeCount, 1000);

                    axios({
                        url: "/smsCode",
                        method: "GET",
                        params: {
                            phoneNumber: phoneNumber,
                            type: "register",
                        }
                    });
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
         * 提交表单
         */
        submitForm() {
            this.$refs['form'].validate((valid) => {
                let password = this.form.password;
                let smsCode = this.form.smsCode;
                //全部校验成功，提交表单
                if (valid) {
                    let data = {
                        'password': password,
                        'phoneNumber': this.form.phoneNumber,
                        'smsCode': this.form.smsCode,
                    };
                    axios({
                        url: "/changePassword",
                        method: "POST",
                        headers: {
                            'Content-type': 'application/json',
                        },
                        data: JSON.stringify(data),
                    }).then(function (response) {
                        if (response.data === "ok") {
                            this.$confirm('密码修改成功', '提示', {
                                confirmButtonText: '返回登录',
                                cancelButtonText: '取消',
                                type: 'info'
                            }).then(() => {
                                //返回登录
                                window.location.href="/login";
                            })
                        } else {
                            app.$message.error(response.data);
                        }
                    }).catch(function (err) {
                        console.log(err.data);
                    });
                }
            });
        },
    },
    watch: {
        timeRemaining: function (val) {
            //计时时间到
            if (val === 0) {
                this.timeRemaining = 60;
                this.canGetSmsCode = true;
                //重置计时
                clearInterval(this.timeRemain);
            }
        }
    }
};
let app = Vue.createApp(forgetPassword);
app.use(ElementPlus);
app.mount("#app");