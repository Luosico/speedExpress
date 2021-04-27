let Main = {
    data() {
        let validateUsername = (rule, value, callback) => {
            if (!value) {
                callback(new Error('用户名不能为空'));
            } else if (value.length < 3) {
                callback(new Error("不能少于3位"))
            } else if (!/^[0-9a-zA-Z_]*$/.test(value)) {
                callback(new Error("只能由数字、字母、_(下划线) 组成"))
            } else {
                let data = {
                    callback: callback,
                }
                isExit('username', value, data, this.usernameNotExit, this.usernameExit)
            }
        }

        let validateName = (rule, value, callback) => {
            if (!value) {
                callback(new Error('姓名不能为空'));
            } else if (!/^[\p{sc=Han}]*$/u.test(value)) {
                callback(new Error('字符格式有误'));
            }
            callback();
        }

        let validatePhoneNumber = (rule, value, callback) => {
            if (!value) {
                callback(new Error('手机号码不能为空'))
            } else if (!/^\d*$/.test(value)) {
                callback(new Error('手机号码格式不正确'))
            } else if (value.length < 6) {
                callback(new Error('不少于 6 位'))
            } else {
                isExit('phoneNumber', value, callback, this.phoneNumberNotExit, this.phoneNumberExit)
            }
        }

        let validatePhoneSmsCode = (rule, value, callback) => {
            if (!value) {
                callback(new Error('验证码不能为空'))
            } else if (!/^\d*$/.test(value)) {
                callback(new Error('验证码格式不正确'))
            } else if (!/^\d{6}$/.test(value)) {
                callback(new Error('验证码只能为 6 位'))
            }
            callback();
        }

        let validateNewPassword = (rule, value, callback) => {
            if (!value) {
                callback(new Error('密码不能为空'));
            } else if (value.length < 6) {
                callback(new Error("密码不能少于6位"))
            } else if (!/^[0-9a-zA-Z_]*$/.test(value)) {
                callback(new Error("只能由数字、字母、_(下划线) 组成"))
            }
            callback();
        }

        let validateRepeatPassword = (rule, value, callback) => {
            if (value !== this.password.newPassword) {
                callback(new Error("两次输入的密码不一致"));
            }
            callback();
        }

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
            username: '',
            user: {
                username: '',
                name: '',
            },
            timeRemaining1: 60,
            password: {
                timeRemain: "",
                canGetSmsCode: true,
                newPassword: '',
                repeatPassword: '',
                smsCode: '',
            },
            timeRemaining2: 60,
            phone: {
                timeRemain: "",
                canGetSmsCode: true,
                phoneSmsCode: '',
                phoneNumber: '',
            },
            userRules: {
                username: [
                    {validator: validateUsername, trigger: "blur"}
                ],
                name: [
                    {validator: validateName, trigger: "blur"}
                ]
            },
            phoneRules: {
                phoneNumber: [
                    {validator: validatePhoneNumber, trigger: 'blur'}
                ],
                phoneSmsCode: [
                    {validator: validatePhoneSmsCode, trigger: 'blur'}
                ]
            },
            passwordRules: {
                newPassword: [
                    {validator: validateNewPassword, trigger: 'blur'}
                ],
                repeatPassword: [
                    {validator: validateRepeatPassword, trigger: 'blur'}
                ],
                smsCode: [
                    {validator: validateSmsCode, trigger: 'blur'}
                ]
            }
        }
    },
    methods: {

        /**
         * 更新用户信息
         * 用户名和姓名
         */
        updateUser() {
            this.$refs['user'].validate((valid) => {
                //全部校验成功
                if (valid) {
                    let username = this.user.username;
                    let name = this.user.name;
                    updateUserName(username, name, this, (val) => {
                        val.$message({
                            message: '修改成功',
                            type: 'success'
                        })
                    }, (val, message) => {
                        val.$message({
                            message: message,
                            type: 'error'
                        })
                    })
                }
            })
        },

        /**
         * 更改密码
         */
        updatePassword() {
            this.$refs['password'].validate((valid) => {
                if (valid) {
                    let password = this.password.newPassword;
                    let smsCode = this.path.smsCode;
                    updatePassword(password, smsCode, this, (val) => {
                        val.$message({
                            message: '修改成功',
                            type: 'success'
                        })
                    }, (val, message) => {
                        val.$message({
                            message: message,
                            type: 'error'
                        })
                    })
                }
            })
        },

        /**
         * 更新手机号码
         */
        updatePhoneNumber() {
            this.$refs['phone'].validate((valid) => {
                if (valid) {
                    let phoneNumber = this.phone.phoneNumber;
                    let smsCode = this.phone.phoneSmsCode;
                    updatePhoneNumber(phoneNumber, smsCode, this, (val) => {
                        val.$message({
                            message: '修改成功',
                            type: 'success'
                        })
                    }, (val, message) => {
                        val.$message({
                            message: message,
                            type: 'error'
                        })
                    })
                }
            })
        },

        /**
         * 获取手机验证码
         */
        getSmsCode(way) {
            let data = {
                need: 'no',
                phoneNumber: ''
            }
            //密码
            if (way === 'pwd') {
                this.password.canGetSmsCode = false;
                this.password.timeRemain = setInterval(this.smsCodeCount1, 1000);
            } else {
                let phoneNumber = this.phone.phoneNumber;
                data.need = 'yes';
                data.phoneNumber = phoneNumber;
                if (!this.validatePhoneNumber(phoneNumber)) {
                    return;
                }
                this.phone.canGetSmsCode = false;
                this.phone.timeRemain = setInterval(this.smsCodeCount2, 1000);
            }
            axios({
                url: "/common/smsCode",
                method: "GET",
                params: data
            });
        },

        /**
         * 短信验证码计时动作
         */
        smsCodeCount1() {
            this.timeRemaining1 = this.timeRemaining1 - 1;
        },
        smsCodeCount2() {
            this.timeRemaining2 = this.timeRemaining2 - 1;
        },

        /**
         * 校验手机号码
         */
        validatePhoneNumber(phoneNumber) {
            if (/^\d*$/.test(phoneNumber)) {
                if (phoneNumber.length < 6) {
                    this.$message({
                        message: '手机号码不少于6位！',
                        type: 'error',
                    })
                } else {
                    return true;
                }
            } else {
                this.$message({
                    message: '手机号码格式不正确，请确认再输入！',
                    type: 'error',
                })
            }
            return false;
        },

        //用户名不存在，调用的方法
        usernameNotExit(data) {
            data.callback();
        },

        //用户名存在，调用的方法
        usernameExit(data) {
            data.callback(new Error("已被注册，换一个试试"));
        },

        phoneNumberNotExit(data) {
            data();
        },

        phoneNumberExit(data) {
            data(new Error("该手机号码已被使用, 可直接登录"));
        },

        getUsername(username) {
            this.username = username;
        }
    },
    watch: {
        timeRemaining1: function (val) {
            //计时时间到
            if (val === 0) {
                this.timeRemaining1 = 60;
                this.password.canGetSmsCode = true;
                //重置计时
                clearInterval(this.password.timeRemain);
            }
        },
        timeRemaining2: function (val) {
            //计时时间到
            if (val === 0) {
                this.timeRemaining2 = 60;
                this.phone.canGetSmsCode = true;
                //重置计时
                clearInterval(this.phone.timeRemain);
            }
        },
        username(val) {
            this.user.username = val;
        }
    },
    created() {
        getUsername(this.getUsername)
    }
};
let main = Vue.createApp(Main);
main.use(ElementPlus);
let app = main.mount('#userInfo');