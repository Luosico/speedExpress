let app = new Vue({
    el: "#app",
    data: {
        message: "Hello Vue",
        form: {
            username: "",
            password: "",
        },
    },

    //在method对象中定义方法
    methods: {
        login: function () {
            let data = new FormData();
            data.append("username", this.form.username);
            data.append("password", this.form.password);

            axios({
                url: "/login",
                method: "POST",
                headers: {
                    'Content-type':'application/x-www-form-urlencoded',
                },
                //发送数据会使用 json 格式
                //注意，后台使用getParameter()获取不到参数，而Spring Security是使用这种方式的
                data:data,
                //responseType: "json", //默认为json
            }).then(function (response) {
                console.log(response)
            }).catch(function (err) {
                console.log(err)
            });
        },

        loginSuccess: function () {

        }
    },
    //计算属性
    computed: {},

    //侦听属性
    watch: {}
})