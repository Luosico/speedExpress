const App = {
    data() {
        let validateName = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请前去个人中心完善信息'));
            }
            callback();
        }
        let validateIdentityId = (rule, value, callback) => {
            if (!value) {
                callback(new Error('身份证号不能为空'))
            } else if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value)) {
                callback(new Error('身份证号格式不正确'))
            }
            callback();
        }
        let validatePhoneNumber = (rule, value, callback) => {
            if (!value) {
                callback(new Error('请前去个人中心完善信息'));
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
            loading: false,
            timeRemaining: 60,
            timeRemain: "",
            canGetSmsCode: true,
            form: {
                name: '',
                identityId: '',
                phoneNumber: '',
                smsCode: ''
            },
            formRules: {
                name: [
                    {validator: validateName, trigger: "blur"}
                ],
                identityId: [
                    {validator: validateIdentityId, trigger: "blur"}
                ],
                phoneNumber: [
                    {validator: validatePhoneNumber, trigger: "blur"}
                ],
                smsCode: [
                    {validator: validateSmsCode, trigger: "blur"}
                ]
            }
        }
    },
    methods: {
        /**
         * 获取手机验证码
         */
        getSmsCode() {
            this.canGetSmsCode = false;
            this.timeRemain = setInterval(this.smsCodeCount, 1000);

            let data = {
                need: 'no'
            }
            getSmsCode(data);
        },
        /**
         * 短信验证码计时动作
         */
        smsCodeCount() {
            this.timeRemaining = this.timeRemaining - 1;
        },
        //提交表单
        submitForm() {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    this.loading = true;
                    let data = {
                        identityId: this.form.identityId,
                        smsCode: this.form.smsCode
                    }
                    becomeCourier(this, data, (vue, response) => {
                        if (response.status === 'ok') {
                            vue.loading = false;
                            this.$alert('欢迎加入我们，请重新登录', '加入成功', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    window.location.href = "/logout";
                                }
                            })
                        } else {
                            vue.$message({
                                type: 'error',
                                message: response.message
                            })
                        }
                    })
                }
            })
        }
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
    },
    created() {
        getUsername(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.username = response.data;
            }
        })
        getName(this, (vue, response) => {
            if (response.status === 'ok') {
                if (response.data == null || response.data === '') {
                    vue.$message({
                        type: 'warning',
                        message: '您还未填写姓名，请前去个人中心填写姓名',
                        duration: 0
                    })
                }
                vue.form.name = response.data;
            }
        })
        getPhoneNumber(this, (vue, response) => {
            if (response.status === 'ok') {
                vue.form.phoneNumber = response.data;
            }
        })
    }
}

let app = Vue.createApp(App);
app.use(ElementPlus);
app.mount('#becomeSender');