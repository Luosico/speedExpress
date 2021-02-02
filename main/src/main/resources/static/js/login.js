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
        timeRemaining:  60,
        timeRemain: "",
    },

    //在method对象中定义方法
    methods: {

        /**
         * 登录
         */
        login() {
            if(this.phoneLogin){
                //手机号码登录
                this.phoneLoginWay();
            }else{
                //帐号密码登录
                this.namePwdLoginWay();
            }
        },

        /**
         * 帐号密码登录
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
                console.log(response.data);
                if(response.data==="ok"){
                    window.location = "http://localhost:8080/main";
                }else{
                    app.$message.error("账号或密码错误，请重试!");
                }
            }).catch(function (err) {
                console.log(err.data);
            });
        },

        /**
         * 手机号码登录
         */
        phoneLoginWay(){
            let data = new FormData();
            data.append("phoneNumber", this.form.phoneNumber);
            data.append("smsCode", this.form.smsCode);

            axios({
                url: "/loginSmsCode",
                method: "POST",
                headers: {
                    'Content-type': 'application/x-www-form-urlencoded',
                },
            }).then(function (response) {
                console.log(response.data);
                //验证成功
                if(response.data==="ok"){
                    window.location = "http://localhost:8080/main";
                }else{
                    app.$message.error('验证码错误或用户未注册！');
                }
            }).catch(function (err) {
                console.log(err.data);
            });

        },

        /**
         * 改变登录方式
         */
        changeLoginWay(){
            let temp = this.phoneLogin;
            this.phoneLogin = !temp;
        },

        /**
         * 获取手机验证码
         */
        getSmsCode(){
            this.form.canGetSmsCode=false;
            this.timeRemain = setInterval(this.smsCodeCount,1000);
            let phoneNumber = this.form.phoneNumber;

            axios({
                url: "/smsCode",
                method: "GET",
                params:{
                    phoneNumber: phoneNumber,
                }
            })
        },

        /**
         * 获取短信验证码计时
         */
        smsCodeCount(){
            this.timeRemaining = this.timeRemaining -1;
        },

        /**
         * 忘记密码
         */
        forgetPwd(){

        },

        /**
         * 注册
         */
        registerUser(){

        }


    },
    //计算属性
    computed: {},

    //侦听属性
    watch: {
        phoneLogin: function (val) {
            if(val===true){
                this.loginWay="密码登录";
            }else{
                this.loginWay="手机登录";
            }
        },
        timeRemaining: function (val) {
            //console.log(val)
            //计时时间到
            if (val===0){
                this.timeRemaining = 60;
                this.form.canGetSmsCode=true;
                //重置计时
                clearInterval(this.timeRemain);
            }
        }
    }
});

