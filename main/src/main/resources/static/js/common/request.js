/**
 * 刷新页面
 */
function refresh() {
    window.location.reload();
}

/**
 * 获取 username
 */
function getUsername(vue, success) {
    axios({
        url: "/common/username",
        method: "GET",
        responseType: "json"
    }).then(function (response) {
        success(vue, response.data)
    })
}

/**
 * 获取用户姓名
 * @param vue
 * @param success
 */
function getName(vue, success) {
    axios({
        url: "/common/name",
        method: "GET",
        responseType: "json"
    }).then(function (response) {
        success(vue, response.data)
    })
}

/**
 * 获取区域
 */
function getRegions(vue, success) {
    axios({
        url: "/common/regions",
        method: "GET",
        responseType: "json"
    }).then(function (response) {
        success(vue, response.data)
    })
}

/**
 * 根据regionId获取regionName
 * @param regionId
 * @param regions
 * @returns {string}
 */
function getRegionName(regionId, regions) {
    let regionName = '';
    for (let i = 0; i < regions.length; i++) {
        if (regions[i].regionId === regionId) {
            regionName = regions[i].regionName;
            break;
        }
    }
    return regionName;
}

/**
 * 添加地址
 * @param data 地址信息
 */
function addAddress(data, vue) {
    axios({
        url: "/common/address",
        method: "POST",
        data: data,
        responseType: "json",
    }).then(function (response) {
        if (requestSuccess(response)) {
            refresh();
        } else {
            vue.$message({
                message: '提交失败',
                type: 'error'
            });
        }
    })
}

/**
 * 获取所有地址
 * @param vue
 * @param success
 */
function getAddresses(vue, success) {
    axios({
        url: "/common/address",
        method: "GET",
        responseType: "json",
    }).then(function (response) {
        success(vue, response.data);
    })
}

/**
 * 更新地址
 * @param data
 */
function updateAddress(data, vue) {
    vue.loading = true;
    axios({
        url: "/common/address",
        method: "PUT",
        data: data,
        responseType: "json",
    }).then(function (response) {
        vue.loading = false;
        //更新成功
        if (requestSuccess(response)) {
            getAddresses(vue);
            vue.$message({
                message: '地址更新成功',
                type: 'success'
            });
        } else {
            vue.$message({
                message: '地址更新失败',
                type: 'error'
            });
        }
    })
}

/**
 * 删除地址
 * @param addressId
 * @param vue
 */
function deleteAddress(addressId, vue) {
    vue.loading = true;
    axios({
        url: "/common/address",
        method: "DELETE",
        data: {
            addressId: addressId
        },
        responseType: "json",
    }).then(function (response) {
        vue.loading = false;
        if (requestSuccess(response)) {
            refresh();
        } else {
            vue.$message({
                message: '地址删除失败',
                type: 'error'
            });
        }
    })
}

/**
 * 请求是否执行成功
 * true 成功
 * false 失败
 */
function requestSuccess(response) {
    return response.data.status === 'ok';
}

/**
 * 获取手机验证码
 */
function getSmsCode(phoneNumber, vue) {

    vue.canGetSmsCode = false;
    vue.timeRemain = setInterval(this.smsCodeCount, 1000);

    axios({
        url: "/smsCode",
        method: "GET",
        params: {
            phoneNumber: phoneNumber,
            type: "login",
        }
    });
}

/**
 * 检查属性是否存在
 * @param name 属性名
 * @param val 属性值
 * @param data vue对象
 * @param success 成功执行方法
 * @param fail 失败执行方法
 */
function isExit(name, val, data, success, fail) {
    axios({
        url: "/isExit",
        method: "GET",
        params: {
            name: name,
            val: val,
        }
    }).then(function (response) {
        if (response.data.status === 'ok') {
            //回调函数
            success(data);
        } else {
            //回调函数
            fail(data);
        }
    })
}

/**
 * 更新手机号码
 * @param phoneNumber 手机号码
 * @param smsCode   短信验证码
 * @param data  数据
 * @param success   成功时的回调函数
 * @param fail  失败时的回调函数
 */
function updatePhoneNumber(phoneNumber, smsCode, data, success, fail) {
    axios({
        url: "/common/updatePhoneNumber",
        method: "PUT",
        data: {
            phoneNumber: phoneNumber,
            smsCode: smsCode
        },
    }).then(function (response) {
        if (response.data.status === 'ok') {
            success(data);
        } else {
            fail(data, response.data.message);
        }
    })
}

/**
 * 更新用户信息
 * 用户名和姓名
 * @param username 用户名
 * @param name  姓名
 * @param data
 * @param success
 * @param fail
 */
function updateUserName(username, name, data, success, fail) {
    axios({
        url: "/common/updateUserName",
        method: "PUT",
        data: {
            username: username,
            name: name
        },
    }).then(function (response) {
        if (response.data.status === 'ok') {
            success(data);
        } else {
            fail(data, response.data.message);
        }
    })
}

/**
 * 更改密码
 * @param password 新密码
 * @param smsCode   短信验证码
 * @param data
 * @param success
 * @param fail
 */
function updatePassword(password, smsCode, data, success, fail) {
    axios({
        url: "/common/updatePassword",
        method: "PUT",
        data: {
            password: password,
            smsCode: smsCode
        },
    }).then(function (response) {
        if (response.data.status === 'ok') {
            success(data);
        } else {
            fail(data, response.data.message);
        }
    })
}

/**
 * 获取手机号码
 * @param vue
 * @param func
 */
function getPhoneNumber(vue, func) {
    axios({
        url: "/common/phoneNumber",
        method: "GET",
    }).then(function (response) {
        func(vue, response.data);
    })
}

/**
 * 添加订单
 * @param data
 * @param vue
 * @param func 回调函数
 */
function addOrder(data, vue, func) {
    axios({
        url: "/common/addOrder",
        method: "POST",
        data: data
    }).then(function (response) {
        func(vue, response.data);
    })
}