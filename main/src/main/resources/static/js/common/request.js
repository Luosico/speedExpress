/**
 * 刷新页面
 */
function refresh() {
    window.location.reload();
}

/**
 * 获取 username
 */
function getUsername(vue, func) {
    axios({
        url: "/common/username",
        method: "GET",
        responseType: "json"
    }).then(function (response) {
        func(vue, response.data)
    })
}

/**
 * 获取用户姓名
 * @param vue
 * @param func
 */
function getName(vue, func) {
    axios({
        url: "/common/name",
        method: "GET",
        responseType: "json"
    }).then(function (response) {
        func(vue, response.data)
    })
}

/**
 * 获取区域
 */
function getRegions(vue, func) {
    axios({
        url: "/common/regions",
        method: "GET",
        responseType: "json"
    }).then(function (response) {
        func(vue, response.data)
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
 * @param func
 */
function getAddresses(vue, func) {
    axios({
        url: "/common/address",
        method: "GET",
        responseType: "json",
    }).then(function (response) {
        func(vue, response.data);
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
 * data = {
 *     need: no/yes   no不需要手机号码
 *     phoneNumber:   手机号码
 * }
 */
function getSmsCode(data) {
    axios({
        url: "/common/smsCode",
        method: "GET",
        params: data
    }).then(function (response) {

    })
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
        url: "/common/order",
        method: "POST",
        data: data
    }).then(function (response) {
        func(vue, response.data);
    })
}

/**
 * 查找用户所有快递订单信息
 */
function getUserOrders(vue, func) {
    axios({
        url: "/common/order",
        method: "GET",
    }).then(function (response) {
        func(vue, response.data);
    })
}

/**
 * 成为快取员
 * @param vue
 * @param data
 * @param func
 */
function becomeCourier(vue, data, func) {
    axios({
        url: "/user/courier",
        method: "POST",
        data: data
    }).then(function (response) {
        func(vue, response.data);
    })
}

/**
 * 确认收货
 */
function orderConfirmReceived(orderId, vue, func) {
    axios({
        url: "/common/orderConfirmReceived",
        method: "PUT",
        data: {
            orderId: orderId
        }
    }).then(function (response) {
        func(vue, response.data);
    })
}

/**
 * 休眠
 *
 * @param mil 毫秒
 */
function sleep(mil) {
    let start = new Date().getTime();
    while (true) {
        if (new Date().getTime() - start > n) {
            break;
        }
    }
}

/**
 * 查询订单数量
 * @param data
 * @param vue
 * @param func
 */
function countOrder(data, vue, func) {
    axios({
        url: "/common/countOrderByStatus",
        method: "POST",
        data: {
            types: data
        },
    }).then((response) => {
        func(vue, response.data)
    })
}

/**
 * 查找指定状态的订单
 * @param data
 * @param vue
 * @param fuc
 */
function selectOrder(data, vue, func) {
    axios({
        url: "/common/selectOrderByStatus",
        method: "POST",
        data: {
            types: data
        }
    }).then((response) => {
        func(vue, response.data)
    })
}

/**
 * 查找快取员的订单
 * @param data
 * @param vue
 * @param func
 */
function selectCourierOrder(data, vue, func) {
    axios({
        url: "/courier/selectCourierOrder",
        method: "PUT",
        data: {
            types: data
        }
    }).then((response) => {
        func(vue, response.data)
    })
}

/**
 * 统计快取员的订单
 * @param vue
 * @param func
 */
function countCourierOrder(vue, func) {
    axios({
        url: "/courier/countCourierOrder",
        method: "GET",
    }).then((response) => {
        func(vue, response.data)
    })
}

/**
 * 获取未被接单的订单信息
 * @param vue
 * @param func
 */
function getUnAcceptOrder(vue, func) {
    axios({
        url: "/courier/getUnAcceptOrder",
        method: "GET",
    }).then((response) => {
        func(vue, response.data)
    })
}

/**
 * 抢单
 * @param orderId
 * @param vue
 * @param func
 */
function tryAcceptOrder(orderId, vue, func) {
    axios({
        url: "/courier/tryAcceptOrder",
        method: "PUT",
        data: {
            orderId: orderId
        }
    }).then((response) => {
        func(vue, response.data)
    })
}

/**
 * 更新订单状态
 */
function updateOrderStatus(data, vue) {
    axios({
        url: "/courier/order",
        method: "PUT",
        data: data
    }).then((response) => {
        if (response.data.status === 'ok') {
            vue.$message({
                type: 'success',
                message: response.data.message,
            })
        } else {
            vue.$message({
                type: 'error',
                message: response.data.message,
            })
        }
    })
}

/**
 * 获取所有订单金额的总和
 * @param vue
 */
function getCourierTotalBalance(vue) {
    axios({
        url: "/courier/selectTotalBalance",
        method: "GET",
    }).then((response) => {
        if (response.data.status === 'ok') {
            vue.totalBalance = response.data.data;
        } else {
            vue.$message({
                type: 'error',
                message: response.data.message,
            })
        }
    })
}

/**
 * 获取当前的余额
 * @param vue
 */
function getBalance(vue) {
    axios({
        url: "/courier/selectBalance",
        method: "GET",
    }).then((response) => {
        if (response.data.status === 'ok') {
            vue.balance = response.data.data;
        } else {
            vue.$message({
                type: 'error',
                message: response.data.message,
            })
        }
    })
}

/**
 * 累计的快递酬金金额
 * @param vue
 * @param func
 */
function getTotalBalance(vue, func) {
    axios({
        url: "/admin/selectTotalBalance",
        method: "GET"
    }).then((response) => {
        func(vue, response.data);
    })
}

/**
 * 统计当前所有订单
 * @param vue
 * @param func
 */
function countTotalOrder(vue, func) {
    axios({
        url: "/admin/countTotalOrder",
        method: "GET"
    }).then((response) => {
        func(vue, response.data);
    })
}


function deleteOrder(orderId, vue, func) {
    axios({
        url: "/admin/order",
        method: "DELETE",
        data:{
            orderId: orderId
        }
    }).then((response) => {
        func(vue, response.data);
    })
}